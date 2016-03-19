package br.com.clubetatame.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.publisher.entity.Account;
import com.publisher.entity.PermanentLink;
import com.publisher.service.PermanentLinkService;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.entity.Product;
import br.com.clubetatame.service.ContractService;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.service.ProductService;


public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = 6843359693103899377L;
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}

	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private ContractService<GymContract> contractService;
	
	public void setContractService(ContractService<GymContract> contractService) {
		this.contractService = contractService;
		this.contractService.setGenericClass(GymContract.class);
		this.contractService.setEntityName(GymContract.class.getName());
	}


	@Override
	protected void indexAll() {
		contractService.indexAll();
	}

	@Override
	protected void populateForm(GymContract entity) {
		if (entity != null) {
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			this.description = entity.getDescription();
			this.start = getDate(entity.getStart());
			this.createdBy = entity.getCreatedBy();
			this.end = getDate(entity.getEnd());
			this.products = entity.getProducts();
			this.created = entity.getCreated();
			this.gym = entity.getGym();
			this.value = entity.getValue();
			this.name = entity.getName();
			this.id = entity.getId();
			
			if (entity.getPermanentLink()!=null) {
				permanentLink = entity.getPermanentLink().getUri();
			}
		}		
	}

	@Override
	protected GymContract updateObject(GymContract entity) {
		if (entity != null) {
			if (permanentLink != null && permanentLink.length() > 0 
					&& (entity.getPermanentLink() == null || !permanentLink.equals(entity.getPermanentLink().getUri()))) {
				newPermanentLink = new PermanentLink();
				newPermanentLink.setUri(permanentLink);
				newPermanentLink.setCreated(new Date());
				newPermanentLink.setType("gymContract");
				if (permanentLink != null) {
					oldPermanentLink = entity.getPermanentLink();
				}
			}
			
			entity.setDescription(description);
			entity.setStart(getDate(start));
			entity.setProducts(products);
			entity.setEnd(getDate(end));
			entity.setGym(gym);
			entity.setValue(value);
			entity.setName(name);
			

			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(getAccount());
			}
			if (entity.getCreated() == null) {
				entity.setCreated(new Date());
			}
			entity.setLastModifiedBy(getAccount());
			entity.setLastModified(new Date());
		}
		return entity;
	}

	@Override
	protected GymContract createEmptyInstance() {
		return new GymContract();
	}

	@Override
	protected void saveObject(GymContract entity, boolean isNew) {
		if (isNew) {
			if (newPermanentLink != null){
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				entity.setPermanentLink(newPermanentLink);
			}
			
			contractService.persist(entity);
			
			if(entity.getPermanentLink() != null){
				entity.getPermanentLink().setParam(entity.getId());
				contractService.persistPermanentLink(entity.getPermanentLink());
			}
		} else {
			if (newPermanentLink!=null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(entity.getId());
				newPermanentLink.setCreated(new Date());
				entity.setPermanentLink(newPermanentLink);
			}
			
			if (oldPermanentLink != null) {
				contractService.update(entity, oldPermanentLink);	
			} else {
				contractService.update(entity);
			}
		}
		if (oldPermanentLink != null)
			permanentLinkService.change(oldPermanentLink, entity.getPermanentLink());
	}
	
	@Override
	public void validate() {
		if (permanentLink != null && permanentLink.length() > 0) {			
			//Validation for removing the first character if it is equal to '/'
			while(permanentLink.charAt(0) == '/' && permanentLink.length() > 0) {				
				permanentLink = permanentLink.substring(1);			
			}			
			GymContract entity = contractService.get(id);
			if (entity != null) {
				if(entity.getPermanentLink() != null && !permanentLink.equals(entity.getPermanentLink().getUri())) {
					if (permanentLinkService.get(permanentLink) != null) {
						addFieldError("permanentLink", "Link já cadastrado.");	
					}						
				}
			} else {
				if (permanentLinkService.get(permanentLink) != null)  {
					addFieldError("permanentLink", "Link já cadastrado.");
				}					
			}
		}
	}

	@Override
	protected Collection<GymContract> generateList() {
		setPages((int)Math.floor(1f * contractService.count() / getPageSize()) + 1);		
		return contractService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<GymContract> generateSearch() {
		ResultList<GymContract> result = contractService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected GymContract getObject() {
		return contractService.get(id);
	}
	
	private Collection<Product> listProducts;
	
	public Collection<Product> getListProducts() {
		if (listProducts == null) {
			listProducts = productService.list(true, 0, 0, "name", "asc");
		}
		return listProducts;
	}
	
	public Collection<Gym> getListGyms() {
		return gymService.list(true, 0, 0, "name", "asc");
	}
	
	private String getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String d = null;
		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day   = calendar.get(Calendar.DAY_OF_MONTH);
		d  = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
		d += "/" + (month < 10 ? "0" + String.valueOf(month) : String.valueOf(month));
		d += "/" + String.valueOf(year);
		return d;
	}
	
	private Date getDate(String date) {
		if (date != null && !date.isEmpty() && date.length() == 10) {
			String[] d = date.split("/");
			if (d != null && d.length == 3) {
				int year  = 0;
				int month = 0;
				int day   = 0;
				try {
					year  = Integer.parseInt(d[2]);
					month = Integer.parseInt(d[1]) - 1;
					day   = Integer.parseInt(d[0]);
				} catch (Exception e) { }
				if (year > 0 && month >= 0 && day > 0) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR,        year);
					calendar.set(Calendar.MONTH,      month);
					calendar.set(Calendar.DAY_OF_MONTH, day);
					calendar.set(Calendar.HOUR_OF_DAY,    0);
					calendar.set(Calendar.MINUTE,         0);
					calendar.set(Calendar.SECOND,         0);
					calendar.set(Calendar.MILLISECOND,    0);
					return calendar.getTime();
				}
			}
		}
		return null;
	}
	
	//Action properties
	
	private PermanentLink oldPermanentLink;
	
	private PermanentLink newPermanentLink;
	
	private String orderBy = "created";
	
	private boolean orderly = true;
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderly() {
		return orderly;
	}

	public void setOrderly(boolean orderly) {
		this.orderly = orderly;
	}	
	
	//POJO

	private long id = -1;
	
	private String name;
	
	private String description;
	
	private double value;
	
	private String start;
	
	private String end;
	
	private Gym gym;
	
	private Collection<Product> products;
	
	private String permanentLink;
	
	private Account createdBy;
	
	private Date created;
	
	private Account lastModifiedBy;
	
	private Date lastModified;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public long getGym() {
		return gym != null ? gym.getId() : 0;
	}

	public void setGym(long id) {
		this.gym = gymService.get(id);
	}

	public Collection<Long> getProducts() {
		if (products != null) {
			List<Long> ids = new ArrayList<Long>(products.size());
			for (Product product : products) {
				ids.add(product.getId());
			}
			return ids;
		}
		return null;
	}

	public void setProducts(long[] products) {
		this.products = new ArrayList<Product>(products.length);
		for (long id : products) {
			this.products.add(productService.get(id));
		}
	}

	public String getPermanentLink() {
		return permanentLink;
	}

	public void setPermanentLink(String permanentLink) {
		this.permanentLink = permanentLink;
	}

	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Account getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Account lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}