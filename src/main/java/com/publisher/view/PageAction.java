package com.publisher.view;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.publisher.entity.Page;
import com.publisher.service.PageService;

public class PageAction extends ActionSupport implements ModelDriven<Page>, Preparable, ViewAction {

	private static final long serialVersionUID = -1331530392183554069L;

	private PageService pageService;
	
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	
	private Page model;

	@Override
	public Page getModel() {
		return model;
	}
	
	@Override
	public String getLayoutPath() {
		return model != null ? model.getSkin().getLayoutPath() : null;
	}

	@Override
	public String getContentPath() {
		return model != null ? model.getContentFile() : null;
	}
	
	@Override
	public void prepare() throws Exception {
		if (id > 0) {
			model = pageService.get(id);
		}
	}
	
	//Action properties
	
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}
}