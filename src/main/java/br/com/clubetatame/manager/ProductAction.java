package br.com.clubetatame.manager;

import java.util.Collection;
import java.util.Date;

import com.publisher.entity.Account;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Product;
import br.com.clubetatame.service.ProductService;

public class ProductAction extends AbstractAction<Product> {

	private static final long serialVersionUID = 6843359693103899377L;

	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	protected void indexAll() {
		productService.indexAll();
	}

	@Override
	protected void populateForm(Product entity) {
		if (entity != null) {
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			this.createdBy = entity.getCreatedBy();
			this.created = entity.getCreated();
			this.active = entity.isActive();
			this.value = entity.getValue();
			this.name = entity.getName();
			this.id = entity.getId();
		}		
	}

	@Override
	protected Product updateObject(Product entity) {
		if (entity != null) {
			entity.setActive(active);
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
	protected Product createEmptyInstance() {
		return new Product();
	}

	@Override
	protected void saveObject(Product entity, boolean isNew) {
		if (isNew) {
			productService.persist(entity);
		} else {
			productService.update(entity);
		}
	}

	@Override
	protected Collection<Product> generateList() {
		setPages((int)Math.floor(1f * productService.count() / getPageSize()) + 1);		
		return productService.list(null, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Product> generateSearch() {
		ResultList<Product> result = productService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected Product getObject() {
		return productService.get(id);
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
	
	private double value;
	
	private boolean active;
	
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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