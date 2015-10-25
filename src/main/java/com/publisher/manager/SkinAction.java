package com.publisher.manager;

import java.util.Collection;
import com.publisher.entity.Skin;
import com.publisher.service.SkinService;
import com.publisher.utils.ResultList;

public class SkinAction extends AbstractAction<Skin> {

	private static final long serialVersionUID = 8441443654000931383L;

	private SkinService skinService;
	
	public void setSkinService(SkinService skinService) {
		this.skinService = skinService;
	}
	
	@Override
	protected void indexAll() {
		skinService.indexAll();
	}

	@Override
	protected void populateForm(Skin entity) {
		if(entity == null) {
			return;
		}
		this.id = entity.getId();
		this.name = entity.getName();
		this.path = entity.getPath();
		this.contentFolder = entity.getContentFolder();
	}

	@Override
	protected Skin updateObject(Skin entity) {
		if (entity != null) {
			entity.setName(name);
			entity.setPath(path);
			entity.setContentFolder(contentFolder);
		}
		return entity;
	}

	@Override
	protected Skin createEmptyInstance() {
		return new Skin();
	}

	@Override
	protected void saveObject(Skin entity, boolean isNew) {
		if(isNew) {
			skinService.persist(entity);
		} else {
			skinService.update(entity);	
		}
	}

	@Override
	protected Collection<Skin> generateList() {
		setPages((int)Math.floor(1f * skinService.count() / getPageSize()) + 1);
		return skinService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Skin> generateSearch() {
		ResultList<Skin> result = skinService.search(getSearch(), getCurrentPage(), getPages());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}

	@Override
	protected Skin getObject() {
		return skinService.get(id);
	}
	
	//Action properties
	
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
	
	private Long id;
	
	private String name;
	
	private String path;
	
	private String contentFolder;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContentFolder() {
		return contentFolder;
	}

	public void setContentFolder(String contentFolder) {
		this.contentFolder = contentFolder;
	}
}