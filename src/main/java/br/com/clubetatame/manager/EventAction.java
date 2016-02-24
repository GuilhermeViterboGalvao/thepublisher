package br.com.clubetatame.manager;

import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Account;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.Event;
import br.com.clubetatame.service.CompanyService;
import br.com.clubetatame.service.EventService;

public class EventAction extends AbstractAction<Event> {

	private static final long serialVersionUID = -5904510467677268384L;

	private EventService eventService;
	
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	
	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	private CompanyService companyService;
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@Override
	protected void indexAll() {
		eventService.indexAll();
	}	

	@Override
	protected void populateForm(Event entity) {
		if (entity != null) {
			entity.setActive(active);
			entity.setAddress(address);
			entity.setCity(city);
			entity.setCompany(company);
			entity.setContact(contact);
			if (entity.getCreated() != null) {
				entity.setCreated(new Date());	
			}
			if (entity.getCreatedBy() != null) {
				entity.setCreatedBy(getAccount());	
			}
			entity.setDescription(description);
			entity.setEnd(end);
			entity.setLastModified(new Date());
			entity.setLastModifiedBy(getAccount());
			entity.setLat(lat);
			entity.setLon(lon);
			entity.setName(name);
			entity.setPhoto(photo);
			entity.setStart(start);
			entity.setState(state);
			entity.setZoomGoogleMaps(zoomGoogleMaps);
		}
	}

	@Override
	protected Event updateObject(Event entity) {
		if (entity != null) {
			this.active = entity.isActive();
			this.address = entity.getAddress();
			this.city = entity.getCity();
			this.company = entity.getCompany();
			this.contact = entity.getContact();
			this.created = entity.getCreated();
			this.createdBy = entity.getCreatedBy();
			this.description = entity.getDescription();
			this.end = entity.getEnd();
			this.lastModified = entity.getLastModified();
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lat = entity.getLat();
			this.lon = entity.getLon();
			this.name = entity.getName();
			this.photo = entity.getPhoto();
			this.start = entity.getStart();
			this.state = entity.getState();
			this.zoomGoogleMaps = entity.getZoomGoogleMaps();
			
		}
		return entity;
	}

	@Override
	protected Event createEmptyInstance() {
		return new Event();
	}

	@Override
	protected void saveObject(Event entity, boolean isNew) {
		if (isNew) {
			eventService.persist(entity);
		} else {
			eventService.update(entity);
		}
	}

	@Override
	protected Collection<Event> generateList() {
		setPages((int)Math.floor(1f * eventService.count() / getPageSize()) + 1);
		return eventService.list(null, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Event> generateSearch() {
		ResultList<Event> result = eventService.search(getSearch(), getCurrentPage(), getPages());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);		
		return result.getResult();
	}

	@Override
	protected Event getObject() {
		return eventService.get(id);
	}
	
	//Action properties and methods
	
	public void setPhotoId(long photoId) {
		if (photoId <= 0) {
			this.photo = photoService.get(photoId);
		}
	}
	
	public long getPhotoId() {
		return photo != null ? photo.getId() : 0l;
	}
	
	private String orderBy = "id";
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	private boolean orderly = true;

	public boolean isOrderly() {
		return orderly;
	}

	public void setOrderly(boolean orderly) {
		this.orderly = orderly;
	}
	
	public Collection<Company> getCompanys() {
		return companyService.list(true);
	}
	
	//POJO
	
	private long id = -1;
	
	private String name;
	
	private String description;
	
	private String contact;
	
	private String state;
	
	private String city;
	
	private String address;
	
	private Date start;
	
	private Date end;
	
	private Float lat;
	
	private Float lon;
	
	private int zoomGoogleMaps;
	
	private boolean active = false;
	
	private Company company;
	
	private Photo photo;
	
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
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

	public EventService getEventService() {
		return eventService;
	}
}