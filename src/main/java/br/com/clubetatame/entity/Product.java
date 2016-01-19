package br.com.clubetatame.entity;

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
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import com.publisher.entity.Account;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

	private static final long serialVersionUID = -4529064085062361145L;

	@Id
	@DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Field
	private String name;
	
	@Field
	private String description;
	
	@Field
	private int value;
	
	@Field(index = Index.YES, store = Store.YES)
	private boolean active = true;
	
	
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
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
	
	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "br.com.clubetatame.entity.Product#" + String.valueOf(id);
    }	
}