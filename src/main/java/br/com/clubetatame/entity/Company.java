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
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

import com.publisher.entity.Account;

import br.com.clubetatame.entity.search.ActiveFilterFactory;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@FullTextFilterDef(name = "activeCompany", impl = ActiveFilterFactory.class)
public class Company implements Serializable {

	private static final long serialVersionUID = -7082129800486525379L;

	@Id
	@DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Field
	private String name;
	
	@Field
	private String contact;
	
	@Field
	private String document;
	
	@Field
	private String email;
	
	@Field
	private String phone;
	
	@Field
	private String address;
	
	@Field
	private String cep;
	
	private String hash;
	
	private String instagram;
	
	private String facebook;
	
	@Field(index = Index.YES, store = Store.YES)
	private boolean active = true;
	
	private Float lat;
	
	private Float lon;
	
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "br.com.clubetatame.entity.Company#" + String.valueOf(id);
    }	
}