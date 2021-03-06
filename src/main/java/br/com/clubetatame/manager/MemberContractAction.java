package br.com.clubetatame.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.publisher.entity.Account;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.entity.MemberContract;
import br.com.clubetatame.entity.Product;
import br.com.clubetatame.service.MemberContractService;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.service.ProductService;


public class MemberContractAction extends AbstractAction<MemberContract> {

	private static final long serialVersionUID = -449918955922571227L;

	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	private MemberContractService memberContractService;
	
	public void setMemberContractService(MemberContractService memberContractService) {
		this.memberContractService = memberContractService;
	}


	@Override
	protected void indexAll() {
		memberContractService.indexAll();
	}

	@Override
	protected void populateForm(MemberContract entity) {
		if (entity != null) {
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			this.description = entity.getDescription();
			this.start = getDate(entity.getStart());
			this.createdBy = entity.getCreatedBy();
			this.end = getDate(entity.getEnd());
			this.products = entity.getProducts();
			this.created = entity.getCreated();
			this.member = entity.getMember();
			this.value = entity.getValue();
			this.name = entity.getName();
			this.id = entity.getId();
		}		
	}

	@Override
	protected MemberContract updateObject(MemberContract entity) {
		if (entity != null) {
			entity.setDescription(description);
			entity.setStart(getDate(start));
			entity.setProducts(products);
			entity.setEnd(getDate(end));
			entity.setMember(member);
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
	protected MemberContract createEmptyInstance() {
		return new MemberContract();
	}

	@Override
	protected void saveObject(MemberContract entity, boolean isNew) {
		if (isNew) {
			memberContractService.persist(entity);
		} else {
			memberContractService.update(entity);
		}
	}

	@Override
	protected Collection<MemberContract> generateList() {
		setPages((int)Math.floor(1f * memberContractService.count() / getPageSize()) + 1);		
		return memberContractService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<MemberContract> generateSearch() {
		ResultList<MemberContract> result = memberContractService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected MemberContract getObject() {
		return memberContractService.get(id);
	}
	
	private Collection<Product> listProducts;
	
	public Collection<Product> getListProducts() {
		if (listProducts == null) {
			listProducts = productService.list(true, 0, 0, "name", "asc");
		}
		return listProducts;
	}
	
	public Collection<Member> getListMembers() {
		return memberService.list(true, 0, 0, "name", "asc");
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
	
	private Member member;
	
	private Collection<Product> products;
	
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


	public long getMember() {
		return member != null ? member.getId() : 0;
	}

	public void setMember(long id) {
		this.member = memberService.get(id);
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