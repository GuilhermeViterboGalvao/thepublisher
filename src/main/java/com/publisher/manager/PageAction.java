package com.publisher.manager;

import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Page;
import com.publisher.entity.PermanentLink;
import com.publisher.service.PageService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.SkinService;
import com.publisher.utils.ResultList;

public class PageAction extends AbstractAction<Page> {

	private static final long serialVersionUID = -4853279835478949994L;
	
	private PageService pageService;
	
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
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
		pageService.indexAll();
	}

	@Override
	protected void populateForm(Page entity) {
		if (entity == null) {
			return; 
		}		
		this.skinName = entity.getSkin() != null ? entity.getSkin().getName() : "";
		this.skinId = entity.getSkin() != null ? entity.getSkin().getId() : 0;
		this.contentFile = entity.getContentFile();
		this.name = entity.getName();
		this.id = entity.getId();		
		if (entity.getPermanentLink() != null) {
			permanentLink = entity.getPermanentLink().getUri();
		}		
	}

	@Override
	protected Page updateObject(Page entity) {
		entity.setSkin(skinService.get(skinId));
		entity.setContentFile(contentFile);		
		entity.setName(name);		
		if (permanentLink != null && permanentLink.length() > 0 && (entity.getPermanentLink() == null || !permanentLink.equals(entity.getPermanentLink().getUri()))) {
			newPermanentLink = new PermanentLink();
			newPermanentLink.setUri(permanentLink);
			newPermanentLink.setCreated(new Date());
			newPermanentLink.setType("page");
			if (permanentLink!=null) {
				oldPermanentLink = entity.getPermanentLink();
			}
		}		
		return entity;
	}

	@Override
	protected Page createEmptyInstance() {
		return new Page();
	}

	@Override
	protected void saveObject(Page entity, boolean isNew) {
		if (isNew) {
			if (newPermanentLink!=null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				entity.setPermanentLink(newPermanentLink);
			}
			pageService.persist(entity);
		} else {
			if (newPermanentLink!=null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(entity.getId());
				entity.setPermanentLink(newPermanentLink);
			}
			if (oldPermanentLink != null) {
				pageService.update(entity, oldPermanentLink);
			} else {
				pageService.update(entity);
			}			
		}
		if (oldPermanentLink!=null) {
			permanentLinkService.change(oldPermanentLink, entity.getPermanentLink());
		}			
	}

	@Override
	protected Collection<Page> generateList() {
		setPages((int)Math.floor(1f * pageService.count() / getPageSize()) + 1);		
		return pageService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Page> generateSearch() {
		ResultList<Page> result = pageService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}

	@Override
	protected Page getObject() {
		return pageService.get(id);
	}
	
	@Override
	public void validate() {
		if (permanentLink != null && !permanentLink.isEmpty()) {			
			//Validation for removing the first character if it is equal to '/'
			if(permanentLink.charAt(0) == '/') {
				permanentLink = permanentLink.substring(1);
			}			
			Page page = pageService.get(id);
			if (page != null) {
				if(page.getPermanentLink() != null && !permanentLink.equals(page.getPermanentLink().getUri())) {
					if (permanentLinkService.get(permanentLink) != null) {
						addFieldError("permanentLink", "Link já cadastrado.");	
					}							
				}
			} else {
				if (permanentLinkService.get(permanentLink) != null) {
					addFieldError("permanentLink", "Link já cadastrado.");
				}
			}
		}
	}	

	//Action properties
	
	private PermanentLink oldPermanentLink;
	
	private PermanentLink newPermanentLink;
	
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
	
	//POJO
	
	private String permanentLink;
	
	private String contentFile;
	
	private String skinName;
	
	private long skinId;
	
	private String name;
	
	private long id;
	
	public String getPermanentLink() {
		return permanentLink;
	}
	
	public void setPermanentLink(String permanentLink) {
		this.permanentLink = permanentLink;
	}
	
	public String getContentFile() {
		return contentFile;
	}

	public void setContentFile(String contentFile) {
		this.contentFile = contentFile;
	}
	
	public void setSkinName(String skinName){
		this.skinName = skinName;
	}
	
	public String getSkinName(){
		return skinName;
	}

	public long getSkinId() {
		return skinId;
	}

	public void setSkinId(long skinId) {
		this.skinId = skinId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}