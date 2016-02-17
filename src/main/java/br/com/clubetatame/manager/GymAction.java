package br.com.clubetatame.manager;

import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Account;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymAction extends AbstractAction<Gym> {

	private static final long serialVersionUID = -4976296568486569598L;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}

	@Override
	protected void indexAll() {
		gymService.indexAll();
	}

	@Override
	protected void populateForm(Gym entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.contact = entity.getContact();
			this.document = entity.getDocument();
			this.email = entity.getEmail();
			this.address = entity.getAddress();
			this.cep = entity.getCEP();
			this.instagram = entity.getInstagram();
			this.active = entity.isActive();
			this.lat = entity.getLat();
			this.lon = entity.getLon();
			this.createdBy = entity.getCreatedBy();
			this.created = entity.getCreated();
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
		}		
	}

	@Override
	protected Gym updateObject(Gym entity) {
		if (entity != null) {
			entity.setName(name);
			entity.setContact(contact);
			entity.setDocument(document);
			entity.setEmail(email);
			entity.setAddress(address);
			entity.setCEP(cep);
			entity.setInstagram(instagram);
			entity.setActive(active);
			entity.setLat(lat);
			entity.setLon(lon);
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
	protected Gym createEmptyInstance() {
		return new Gym();
	}

	@Override
	protected void saveObject(Gym entity, boolean isNew) {
		if (isNew) {
			gymService.persist(entity);
		} else {
			gymService.update(entity);
		}
	}

	@Override
	protected Collection<Gym> generateList() {
		setPages((int)Math.floor(1f * gymService.count() / getPageSize()) + 1);		
		return gymService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Gym> generateSearch() {
		ResultList<Gym> result = gymService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected Gym getObject() {
		return gymService.get(id);
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
	
	//Action properties
	
	private long id = -1;
	
	private String name;
	
	private String contact;
	
	private String document;
	
	private String email;
	
	private String phone;
	
	private String address;
	
	private String cep;
	
	private String instagram;
	
	private boolean active;
	
	private Float lat;
	
	private Float lon;
	
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

	public GymService getGymService() {
		return gymService;
	}
}