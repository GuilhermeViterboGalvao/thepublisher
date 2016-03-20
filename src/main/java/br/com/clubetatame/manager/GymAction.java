package br.com.clubetatame.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.publisher.entity.Account;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Photo;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.PhotoService;
import com.publisher.utils.ResultList;


import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymAction extends AbstractAction<Gym> {

	private static final long serialVersionUID = -4976296568486569598L;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
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
			this.description = entity.getDescription();
			this.site = entity.getSite();
			this.operation = entity.getOperation();
			this.modality = entity.getModality();
			this.contact = entity.getContact();
			this.document = entity.getDocument();
			this.email = entity.getEmail();
			this.state = entity.getState();
			this.city = entity.getCity();
			this.address = entity.getAddress();
			this.cep = entity.getCEP();
			this.instagram = entity.getInstagram();
			this.facebook = entity.getFacebook();
			this.logo = entity.getLogo();
			this.photos = entity.getPhotos();
			this.active = entity.isActive();
			this.lat = entity.getLat();
			this.lon = entity.getLon();
			this.createdBy = entity.getCreatedBy();
			this.created = entity.getCreated();
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			this.phone = entity.getPhone();
			
			if (entity.getPermanentLink() != null) {
				permanentLink = entity.getPermanentLink().getUri();
			}
		}		
	}

	@Override
	protected Gym updateObject(Gym entity) {
		if (entity != null) {
			entity.setName(name);
			entity.setDescription(description);
			entity.setSite(site);
			entity.setOperation(operation);
			entity.setModality(modality);
			entity.setContact(contact);
			entity.setDocument(document);
			entity.setEmail(email);
			entity.setState(state);
			entity.setCity(city);
			entity.setAddress(address);
			entity.setCEP(cep);
			entity.setInstagram(instagram);
			entity.setFacebook(facebook);
			entity.setLogo(logo);
			entity.setActive(active);
			entity.setLat(lat);
			entity.setLon(lon);
			entity.setPhone(phone);
			
			if (photos != null && photos.size() > 0) {
				List<Photo> list = new ArrayList<Photo>(photos.size());
				for (Photo photo : photos) {
					Photo p = photoService.get(photo.getId());
					p.setDescription(photo.getDescription());
					list.add(p);
				}
				entity.setPhotos(list);
			}
			
			if (permanentLink != null && permanentLink.length() > 0 
					&& (entity.getPermanentLink() == null || !permanentLink.equals(entity.getPermanentLink().getUri()))) {
				newPermanentLink = new PermanentLink();
				newPermanentLink.setUri(permanentLink);
				newPermanentLink.setCreated(new Date());
				newPermanentLink.setType("gym");
				if (permanentLink != null) {
					oldPermanentLink = entity.getPermanentLink();
				}
			}
			
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
			if (newPermanentLink != null){
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				entity.setPermanentLink(newPermanentLink);
			}
			gymService.persist(entity);
		} else {
			if (newPermanentLink!=null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(entity.getId());
				newPermanentLink.setCreated(new Date());
				entity.setPermanentLink(newPermanentLink);
			}
			if (oldPermanentLink != null) {
				gymService.update(entity, oldPermanentLink);	
			} else {
				gymService.update(entity);
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
			Gym entity = gymService.get(id);
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
	protected Collection<Gym> generateList() {
		setPages((int)Math.floor(1f * gymService.count() / getPageSize()) + 1);		
		return gymService.list(null, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
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
	
	//Action properties
	
	private long id = -1;
	
	private String name;
	
	private String description;
	
	private String site;
	
	private String operation;
	
	private String modality;
	
	private String contact;
	
	private String document;
	
	private String email;
	
	private String phone;
	
	private String state;
	
	private String city;
	
	private String address;
	
	private String cep;
	
	private String instagram;
	
	private String facebook;
	
	private Photo logo;
	
	private List<Photo> photos;
	
	private String permanentLink;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
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
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Photo getLogo() {
		return logo;
	}

	public void setLogo(Photo logo) {
		this.logo = logo;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public String getPermanentLink() {
		return permanentLink;
	}

	public void setPermanentLink(String permanentLink) {
		this.permanentLink = permanentLink;
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