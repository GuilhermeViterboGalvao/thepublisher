package com.publisher.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

	private static final long serialVersionUID = 7345917389143090774L;

	@Id
	@DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Field
	private String name;
		
	@Field
	private String tags;
	
	@Field
	private String folder;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)	
	private Skin skin;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
    	name = "Category_Category", 
    	joinColumns = { @JoinColumn(name = "Category_id", referencedColumnName="id")}, 
        inverseJoinColumns={@JoinColumn(name="parents_id", referencedColumnName="id")}
    )	
	private Set<Category> parents;
	
    @IndexedEmbedded(includeEmbeddedObjectId=true)
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})	
	private PermanentLink permanentLink;

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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public Set<Category> getParents() {
		return parents;
	}

	public void setParents(Set<Category> parents) {
		this.parents = parents;
	}

	public PermanentLink getPermanentLink() {
		return permanentLink;
	}

	public void setPermanentLink(PermanentLink permanentLink) {
		this.permanentLink = permanentLink;
	}
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Skin)) {
            return false;
        }
        Category other = (Category)object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.publisher.entity.Category#" + String.valueOf(id);
    }    
}
