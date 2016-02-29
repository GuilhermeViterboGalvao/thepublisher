package br.com.clubetatame.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.publisher.entity.Account;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.CompanyContract;
import br.com.clubetatame.entity.Product;
import br.com.clubetatame.service.CompanyContractService;
import br.com.clubetatame.service.CompanyService;
import br.com.clubetatame.service.ProductService;


public class CompanyContractAction extends AbstractAction<CompanyContract> {

	private static final long serialVersionUID = 6843359693103899377L;

	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	private CompanyService companyService;
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	private CompanyContractService companyContractService;
	
	public void setCompanyContractService(CompanyContractService companyContractService) {
		this.companyContractService = companyContractService;
	}


	@Override
	protected void indexAll() {
		companyContractService.indexAll();
	}

	@Override
	protected void populateForm(CompanyContract entity) {
		if (entity != null) {
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			this.description = entity.getDescription();
			this.createdBy = entity.getCreatedBy();
			this.products = entity.getProducts();
			this.created = entity.getCreated();
			this.company = entity.getCompany();
			this.value = entity.getValue();
			this.start = entity.getStart();
			this.name = entity.getName();
			this.end = entity.getEnd();
			this.id = entity.getId();
		}		
	}

	@Override
	protected CompanyContract updateObject(CompanyContract entity) {
		if (entity != null) {
			entity.setDescription(description);
			entity.setProducts(products);
			entity.setCompany(company);
			entity.setStart(start);
			entity.setValue(value);
			entity.setName(name);
			entity.setEnd(end);

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
	protected CompanyContract createEmptyInstance() {
		return new CompanyContract();
	}

	@Override
	protected void saveObject(CompanyContract entity, boolean isNew) {
		if (isNew) {
			companyContractService.persist(entity);
		} else {
			companyContractService.update(entity);
		}
	}

	@Override
	protected Collection<CompanyContract> generateList() {
		setPages((int)Math.floor(1f * companyContractService.count() / getPageSize()) + 1);		
		return companyContractService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<CompanyContract> generateSearch() {
		ResultList<CompanyContract> result = companyContractService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected CompanyContract getObject() {
		return companyContractService.get(id);
	}
	
	public Collection<Product> getListProducts() {
		return productService.list(true, 0, 0, "name", "asc");
	}
	
	public Collection<Company> getListCompanys() {
		return companyService.list(true, 0, 0, "name", "asc");
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
	
	private int value;
	
	private Date start;
	
	private Date end;
	
	private Company company;
	
	private List<Product> products;
	
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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