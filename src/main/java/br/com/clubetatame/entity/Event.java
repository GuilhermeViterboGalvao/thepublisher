package br.com.clubetatame.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;
import com.publisher.entity.Account;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Photo;


import br.com.clubetatame.entity.search.ActiveFilterFactory;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@FullTextFilterDef(name = "activeEvent", impl = ActiveFilterFactory.class)
public class Event implements Serializable {

	private static final long serialVersionUID = -209990624506721314L;

	@Id
	@DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Field
	private String name;
	
	private String description;
	
	@Field
	private String contact;

	@Field
	private String state;
	
	@Field
	private String city;	
	
	@Field
	private String address;
	
	private Date start;
	
	private Date end;
	
	private Float lat;
	
	private Float lon;
	
	private int zoomGoogleMaps;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PermanentLink permanentLink;
	
	@Field(index = Index.YES, store = Store.YES)
	private boolean active = true;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
		
	@OneToOne(fetch = FetchType.LAZY)
	private Photo photo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @IndexedEmbedded(includeEmbeddedObjectId=true)
	private Account createdBy;
	
    @SortableField
    @DateBridge(resolution = Resolution.DAY)
    @Field(index = Index.YES, store = Store.YES)    
	private Date created;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @IndexedEmbedded(includeEmbeddedObjectId=true)	
	private Account lastModifiedBy;
	
	private Date lastModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public PermanentLink getPermanentLink() {
		return permanentLink;
	}

	public void setPermanentLink(PermanentLink permanentLink) {
		this.permanentLink = permanentLink;
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
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
	
	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "br.com.clubetatame.entity.Event#" + String.valueOf(id);
    }	
}