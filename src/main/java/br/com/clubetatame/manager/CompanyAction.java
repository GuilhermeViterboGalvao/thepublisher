package br.com.clubetatame.manager;

import java.util.Collection;
import java.util.Date;

import com.publisher.entity.Account;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.service.CompanyService;

public class CompanyAction extends AbstractAction<Company> {

	private static final long serialVersionUID = 6843359693103899377L;

	private CompanyService companyService;
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Override
	protected void indexAll() {
		companyService.indexAll();
	}

	@Override
	protected void populateForm(Company entity) {
		if (entity != null) {
			this.zoomGoogleMaps = entity.getZoomGoogleMaps();
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			this.createdBy = entity.getCreatedBy();
			this.instagram = entity.getInstagram();
			this.document = entity.getDocument();
			this.facebook = entity.getFacebook();
			this.contact = entity.getContact();
			this.created = entity.getCreated();
			this.address = entity.getAddress();
			this.active = entity.isActive();
			this.phone = entity.getPhone();
			this.email = entity.getEmail();
			this.name = entity.getName();			
			this.lat = entity.getLat();
			this.lon = entity.getLon();
			this.cep = entity.getCep();
			this.id = entity.getId();
		}		
	}

	@Override
	protected Company updateObject(Company entity) {
		if (entity != null) {
			entity.setZoomGoogleMaps(zoomGoogleMaps);
			entity.setInstagram(instagram);
			entity.setFacebook(facebook);
			entity.setDocument(document);
			entity.setContact(contact);
			entity.setAddress(address);
			entity.setActive(active);
			entity.setEmail(email);
			entity.setPhone(phone);
			entity.setName(name);
			entity.setLat(lat);
			entity.setLon(lon);
			entity.setCep(cep);

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
	protected Company createEmptyInstance() {
		return new Company();
	}

	@Override
	protected void saveObject(Company entity, boolean isNew) {
		if (isNew) {
			companyService.persist(entity);
		} else {
			companyService.update(entity);
		}
	}

	@Override
	protected Collection<Company> generateList() {
		setPages((int)Math.floor(1f * companyService.count() / getPageSize()) + 1);		
		return companyService.list(null, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Company> generateSearch() {
		ResultList<Company> result = companyService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected Company getObject() {
		return companyService.get(id);
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
	
	private String contact;
	
	private String document;
	
	private String email;
	
	private String phone;
	
	private String address;
	
	private String cep;
	
	private String instagram;
	
	private String facebook;
	
	private boolean active;
	
	private Float lat;
	
	private Float lon;
	
	private int zoomGoogleMaps;
	
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public int getZoomGoogleMaps() {
		return zoomGoogleMaps;
	}

	public void setZoomGoogleMaps(int zoomGoogleMaps) {
		this.zoomGoogleMaps = zoomGoogleMaps;
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