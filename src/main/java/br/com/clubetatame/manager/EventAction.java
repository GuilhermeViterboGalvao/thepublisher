package br.com.clubetatame.manager;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Account;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Photo;
import com.publisher.service.PermanentLinkService;
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
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}
	
	@Override
	protected void indexAll() {
		eventService.indexAll();
	}	

	@Override
	protected void populateForm(Event entity) {
		if (entity != null) {
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.contact = entity.getContact();
			this.state = entity.getState();
			this.city = entity.getCity();
			this.address = entity.getAddress();
			this.start = getDate(entity.getStart());
			this.end = getDate(entity.getEnd());
			this.deadline = getDate(entity.getDeadline());
			this.lat = entity.getLat();
			this.lon = entity.getLon();
			this.zoomGoogleMaps = entity.getZoomGoogleMaps();
			this.active = entity.isActive();
			this.company = entity.getCompany();
			this.photo = entity.getPhoto();
			
			this.createdBy = entity.getCreatedBy();
			this.created = entity.getCreated();
			this.lastModifiedBy = entity.getLastModifiedBy();
			this.lastModified = entity.getLastModified();
			
			if (entity.getPermanentLink() != null) {
				permanentLink = entity.getPermanentLink().getUri();
			}
		}
	}

	@Override
	protected Event updateObject(Event entity) {
		if (entity != null) {
			entity.setName(name);
			entity.setDescription(description);
			entity.setContact(contact);
			entity.setState(state);
			entity.setCity(city);
			entity.setAddress(address);
			entity.setStart(getDate(start));
			entity.setDeadline(getDate(deadline));
			entity.setEnd(getDate(end));
			entity.setLat(lat);
			entity.setLon(lon);
			entity.setZoomGoogleMaps(zoomGoogleMaps);
			entity.setActive(active);
			entity.setCompany(company);
			entity.setPhoto(photo);
			
			if (permanentLink != null && permanentLink.length() > 0 
					&& (entity.getPermanentLink() == null || !permanentLink.equals(entity.getPermanentLink().getUri()))) {
				newPermanentLink = new PermanentLink();
				newPermanentLink.setUri(permanentLink);
				newPermanentLink.setCreated(new Date());
				newPermanentLink.setType("event");
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
	protected Event createEmptyInstance() {
		return new Event();
	}

	@Override
	protected void saveObject(Event entity, boolean isNew) {
		if (isNew) {
			if (newPermanentLink != null){
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				entity.setPermanentLink(newPermanentLink);
			}
			
			eventService.persist(entity);
		} else {
			if (newPermanentLink!=null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(entity.getId());
				newPermanentLink.setCreated(new Date());
				entity.setPermanentLink(newPermanentLink);
			}
			if (oldPermanentLink != null) {
				eventService.update(entity, oldPermanentLink);	
			} else {
				eventService.update(entity);
			}
			if (oldPermanentLink != null)
				permanentLinkService.change(oldPermanentLink, entity.getPermanentLink());
		}
	}
	
	@Override
	public void validate() {
		if (permanentLink != null && permanentLink.length() > 0) {			
			//Validation for removing the first character if it is equal to '/'
			while(permanentLink.charAt(0) == '/' && permanentLink.length() > 0) {				
				permanentLink = permanentLink.substring(1);			
			}			
			Event entity = eventService.get(id);
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
	
	private PermanentLink oldPermanentLink;
	
	private PermanentLink newPermanentLink;
	
	public void setPhotoId(long photoId) {
		if (photoId > 0) {
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
	
	private String getDate(Date date) {
		String d = null;
		if(date != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
	
			int year  = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day   = calendar.get(Calendar.DAY_OF_MONTH);
			d  = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
			d += "/" + (month < 10 ? "0" + String.valueOf(month) : String.valueOf(month));
			d += "/" + String.valueOf(year);
		}
		return (d != null ? d : "");
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
	
	//POJO
	
	private long id = -1;
	
	private String name;
	
	private String description;
	
	private String contact;
	
	private String state;
	
	private String city;
	
	private String address;
	
	private String start;
	
	private String end;
	
	private String deadline;
	
	private Float lat;
	
	private Float lon;
	
	private int zoomGoogleMaps;
	
	private String permanentLink;
	
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

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
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
	
	public long getCompany() {
		return company != null ? company.getId() : 0;
	}

	public void setCompany(long id) {
		this.company = companyService.get(id);
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