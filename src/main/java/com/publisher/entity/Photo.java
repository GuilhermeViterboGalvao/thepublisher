package com.publisher.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.FullTextFilterDefs;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;
import br.com.clubetatame.entity.search.PhotoEventFilterFactory;
import br.com.clubetatame.entity.search.PhotoGymFilterFactory;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@FullTextFilterDefs({
	@FullTextFilterDef(name = "isEvent", impl = PhotoEventFilterFactory.class),
	@FullTextFilterDef(name = "isGym", impl = PhotoGymFilterFactory.class)
})
public class Photo implements Serializable {

	private static final long serialVersionUID = 3169912176987305269L;

	@Id    
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Field
	private String tags;
	
	@Field
	private String description;
	
    private float horizontalCenter = 0.5f;
    
    private float verticalCenter = 0.5f;
    
    private int width;
    
    private int height;
    
    @Field
    private String credits;
    
	@SortableField
	@DateBridge(resolution = Resolution.DAY)
    @Field(index = Index.YES, store = Store.YES)    
    private Date date = new Date();
    	
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
    
	@SortableField
	@DateBridge(resolution = Resolution.DAY)
    @Field(index = Index.YES, store = Store.YES)    
    private Date lastModified;
    
    @Field(index = Index.YES, store = Store.YES)
    private boolean published = false;
    
    private boolean isEvent = false;
    
    private boolean isGym = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getHorizontalCenter() {
		return horizontalCenter;
	}

	public void setHorizontalCenter(float horizontalCenter) {
		this.horizontalCenter = horizontalCenter;
	}

	public float getVerticalCenter() {
		return verticalCenter;
	}

	public void setVerticalCenter(float verticalCenter) {
		this.verticalCenter = verticalCenter;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

    public boolean isEvent() {
		return isEvent;
	}

	public void setEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	public boolean isGym() {
		return isGym;
	}

	public void setGym(boolean isGym) {
		this.isGym = isGym;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo)object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.publisher.entity.Photo#" + String.valueOf(id);
    }	
}