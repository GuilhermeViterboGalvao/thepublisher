package br.com.tatame.manager;

import java.util.Collection;
import java.util.Date;

import com.publisher.entity.PermanentLink;
import com.publisher.entity.Skin;
import com.publisher.manager.AbstractAction;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.SkinService;
import com.publisher.utils.ResultList;

import br.com.tatame.entity.LiveStats;
import br.com.tatame.service.LiveStatsService;

public class LiveStatsAction extends AbstractAction<LiveStats> {

	private static final long serialVersionUID = -3365748162128998650L;

	private LiveStatsService liveStatsService;
	
	public void setLiveStatsService(LiveStatsService liveStatsService) {
		this.liveStatsService = liveStatsService;
	}
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}
	
	private SkinService skinService;
	
	public void setSkinService(SkinService skinService) {
		this.skinService = skinService;
	}
	
	@Override
	protected void indexAll() {
		liveStatsService.indexAll();		
	}

	@Override
	protected void populateForm(LiveStats entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.tags = entity.getTags();
			this.code = entity.getCode();
			this.published = entity.isPublished();
			this.eventName = entity.getEventName();
			this.publishedAt = entity.getPublishedAt();
			this.description = entity.getDescription();
			this.forumEnabled = entity.isForumEnabled();
			if (entity.getSkin() != null) {
				this.skinId = entity.getSkin().getId(); 	
			}
			if (entity.getPermanentLink() != null) {
				this.permanentLink = entity.getPermanentLink().getUri();
			}
		}
	}

	@Override
	protected LiveStats updateObject(LiveStats entity) {
		if (entity != null) {
			entity.setTags(tags);
			entity.setCode(code);
			entity.setPublished(published);
			entity.setEventName(eventName);
			entity.setDescription(description);			
			entity.setPublishedAt(publishedAt);
			entity.setLastModified(new Date());
			entity.setLastModifiedBy(getAccount());
			entity.setForumEnabled(forumEnabled);
			if (entity.getCreated() == null) {
				entity.setCreated(new Date());	
			}
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(getAccount());	
			}			
			if (skinId > 0) {
				entity.setSkin(skinService.get(skinId));	
			}
			if (permanentLink != null && permanentLink.length() > 0 && (entity.getPermanentLink() == null || !permanentLink.equals(entity.getPermanentLink().getUri()))) {
				newPermanentLink = new PermanentLink();
				newPermanentLink.setUri(permanentLink);
				newPermanentLink.setCreated(new Date());
				newPermanentLink.setType("liveStats");
				if (permanentLink != null) {
					oldPermanentLink = entity.getPermanentLink();
				}
			}
		}
		return entity;
	}

	@Override
	protected LiveStats createEmptyInstance() {
		return new LiveStats();
	}

	@Override
	protected void saveObject(LiveStats entity, boolean isNew) {
		if (isNew) {
			if (newPermanentLink != null){
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				entity.setPermanentLink(newPermanentLink);
			}
			liveStatsService.persist(entity);
		} else {
			if (newPermanentLink != null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(entity.getId());
				newPermanentLink.setCreated(new Date());
				entity.setPermanentLink(newPermanentLink);
			}
			liveStatsService.update(entity);		
		}
		if (oldPermanentLink != null) {
			permanentLinkService.change(oldPermanentLink, entity.getPermanentLink());
		}
	}

	@Override
	protected Collection<LiveStats> generateList() {
		setPages((int)Math.floor(1f * liveStatsService.count() / getPageSize()) + 1);		
		return liveStatsService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<LiveStats> generateSearch() {
		ResultList<LiveStats> result = liveStatsService.search(getSearch(), getCurrentPage(), getPages());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}

	@Override
	protected LiveStats getObject() {
		return liveStatsService.get(id);
	}
	
	@Override
	public void validate() {
		super.validate();
		if (skinId != 0 && skinService.get(skinId) == null) {
			addFieldError("templateId", "Você deve definir um template padrão.");
		}
		if (publishedAt == null) {
			addFieldError("publishedAt", "Você deve entrar com a data de publicação.");
		}
		if (published && (permanentLink == null || permanentLink.equals(""))) {
			addFieldError("publishedAt", "Você deve entrar com um permanentLink para poder publicar a matéria.");		
		}
		if (permanentLink != null && permanentLink.length() > 0) {			
			//Validation for removing the first character if it is equal to '/'
			while(permanentLink.charAt(0) == '/' && permanentLink.length() > 0) {				
				permanentLink = permanentLink.substring(1);			
			}			
			LiveStats liveStats = liveStatsService.get(id);
			if (liveStats != null) {
				if(liveStats.getPermanentLink() != null && !permanentLink.equals(liveStats.getPermanentLink().getUri())) {
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
		//This procedure is necessary in case the user has chosen 
		//an template, the action can save the state of the page.
		if (skinId > 0) {
			Skin skin = skinService.get(skinId);
			if (skin != null && skin.getName() != null && !skin.getName().equals("")) {
				skinName = skin.getName();
			}
		}
	}
	
	//LiveStatsAction properties
	
	private PermanentLink oldPermanentLink;
	
	private PermanentLink newPermanentLink;
	
	private String orderBy = "publishedAt";
	
	private boolean orderly = true;
	
	private String skinName;

	//POJO
	
	private long id = 0;
	
	private String eventName;
	
	private String description;
	
	private String tags;
	
	private String code;
	
	private long skinId;
	
	private Date publishedAt;
	
	private boolean published;
	
	private boolean forumEnabled;
	
	private String permanentLink;

	public PermanentLink getOldPermanentLink() {
		return oldPermanentLink;
	}

	public void setOldPermanentLink(PermanentLink oldPermanentLink) {
		this.oldPermanentLink = oldPermanentLink;
	}

	public PermanentLink getNewPermanentLink() {
		return newPermanentLink;
	}

	public void setNewPermanentLink(PermanentLink newPermanentLink) {
		this.newPermanentLink = newPermanentLink;
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getSkinId() {
		return skinId;
	}

	public void setSkinId(long skinId) {
		this.skinId = skinId;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
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

	public String getPermanentLink() {
		return permanentLink;
	}

	public void setPermanentLink(String permanentLink) {
		this.permanentLink = permanentLink;
	}
	
	public String getSkinName() {
		return skinName;
	}
}