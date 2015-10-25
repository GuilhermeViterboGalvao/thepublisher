package com.publisher.manager;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.service.CategoryService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.SkinService;
import com.publisher.utils.ResultList;

public class CategoryAction extends AbstractAction<Category> {

	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}
	
	private SkinService skinService;
	
	public void setSkinService(SkinService skinService) {
		this.skinService = skinService;
	}
	
	private static final long serialVersionUID = 8783194780725119584L;

	@Override
	protected void indexAll() {
		categoryService.indexAll();
	}

	@Override
	protected void populateForm(Category entity) {
		if (entity == null) {
			return;
		}
		this.skinName = entity.getSkin() != null ? entity.getSkin().getName() : "";
		this.skinId = entity.getSkin() != null ? entity.getSkin().getId() : 0;
		this.parents = entity.getParents();
		this.name = entity.getName();
		this.tags = entity.getTags();
		this.id = entity.getId();		
		if (entity.getPermanentLink() != null) {
			permanentLink = entity.getPermanentLink().getUri();
		}		
	}

	@Override
	protected Category updateObject(Category entity) {
		entity.setSkin(skinService.get(skinId));
		entity.setParents(parents);
		entity.setName(name);		
		entity.setTags(tags);		
		if (permanentLink != null && !permanentLink.isEmpty() && (entity.getPermanentLink() == null || !permanentLink.equals(entity.getPermanentLink().getUri()))) {
			newPermanentLink = new PermanentLink();
			newPermanentLink.setUri(permanentLink);
			newPermanentLink.setCreated(new Date());
			newPermanentLink.setType("category");
			if (permanentLink != null) {
				oldPermanentLink = entity.getPermanentLink();
			}
		}		
		return entity;
	}

	@Override
	protected Category createEmptyInstance() {
		return new Category();
	}

	@Override
	protected void saveObject(Category entity, boolean isNew) {
		if (isNew) {
			if (newPermanentLink != null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				entity.setPermanentLink(newPermanentLink);
			}
			categoryService.persist(entity);
		} else {
			if (newPermanentLink != null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(entity.getId());
				entity.setPermanentLink(newPermanentLink);
			}
			if (oldPermanentLink != null) {
				categoryService.update(entity, oldPermanentLink);
			} else {
				categoryService.update(entity);	
			}			
		}
		if (oldPermanentLink != null) {
			permanentLinkService.change(oldPermanentLink, entity.getPermanentLink());
		}					
	}

	@Override
	protected Collection<Category> generateList() {
		setPages((int)Math.floor(1f * categoryService.count() / getPageSize()) + 1);
		return categoryService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Category> generateSearch() {
		ResultList<Category> result = categoryService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);		
		return result.getResult();
	}

	@Override
	protected Category getObject() {
		return categoryService.get(id);
	}
	
	public void validate(){
		if(skinId <= 0)	{
			addFieldError("searchSkinBox", "É necessário cadastrar um Template.");	
		}
		if (permanentLink != null && !permanentLink.isEmpty()) {			
			//Validation for removing the first character if it is equal to '/'
			if(permanentLink.charAt(0) == '/') {
				permanentLink = permanentLink.substring(1);
			}			
			Category category = categoryService.get(id);
			if (category != null) {
				if(category.getPermanentLink() != null && !permanentLink.equals(category.getPermanentLink().getUri())) {
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
	
	public Collection<Category> getListCategories(){
		return categoryService.list();
	}
	
	//POJO
	
	private Collection<Category> categories;
	
	private Set<Category> parents;
	
	private String permanentLink;
	
	private String skinName;
	
	private String tags;
	
	private String name;
	
	private long skinId;
	
	private long id;
	
	public long[] getParents(){
		long[] parentsIds = new long[parents.size()];
		int i = 0;
		for (Category c : parents) {
			parentsIds[i] = c.getId();
			i++;
		}
		return parentsIds;
	}
	
	public void setParents(long[] ids){
		parents = new HashSet<Category>();
		for (int i = 0; i < ids.length; i++) {
			parents.add(categoryService.get(ids[i]));
		}
	}
	
	public Collection<Category> getCategories(){
		return categories;
	}
	
	public String getPermanentLink() {
		return permanentLink;
	}
	
	public void setPermanentLink(String permanentLink) {
		this.permanentLink = permanentLink;
	}	
	
	public void setSkinName(String skinName){
		this.skinName = skinName;
	}
	
	public String getSkinName(){
		return skinName;
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

	public long getSkinId() {
		return skinId;
	}

	public void setSkinId(long skinId) {
		this.skinId = skinId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}	
}