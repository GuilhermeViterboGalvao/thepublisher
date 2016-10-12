package br.com.tatame.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Skin;
import com.publisher.entity.search.PublishedFilter;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@FullTextFilterDef(name = "liveStatsPublished", impl = PublishedFilter.class)
public class LiveStats implements Serializable {

    private static final long serialVersionUID = 358789591798823523L;

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field
    private String eventName;

    @Field
    private String description;

    @Field
    private String tags;

    @Lob
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skin skin;

    @SortableField
    @DateBridge(resolution = Resolution.DAY)
    @Field(index = Index.YES, store = Store.YES)
    private Date publishedAt = new Date();

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

    @Field(index = Index.YES, store = Store.YES)
    private boolean published = false;

    private boolean forumEnabled = true;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private PermanentLink permanentLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
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

    public boolean isForumEnabled() {
        return forumEnabled;
    }

    public void setForumEnabled(boolean forumEnabled) {
        this.forumEnabled = forumEnabled;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LiveStats)) {
            return false;
        }
        LiveStats other = (LiveStats)object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tatame.entity.LiveStats#" + String.valueOf(id);
    }    
}