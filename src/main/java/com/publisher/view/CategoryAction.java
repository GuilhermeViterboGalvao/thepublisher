package com.publisher.view;

import java.util.Collection;
import java.util.Date;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;

public class CategoryAction extends ActionSupport implements ModelDriven<Category>, Preparable, ViewAction {

	private static final long serialVersionUID = -8936493033567185173L;

	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private ArticleService articleService;
	
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }	
	
	private Category model;

	@Override
	public Category getModel() {
		return model;
	}
	
	@Override
	public String getLayoutPath() {
		return model.getSkin().getLayoutPath();
	}

	@Override
	public String getContentPath() {
		return model.getSkin().getContentPath(Category.class.getSimpleName());
	}	
	
	@Override
	public void prepare() throws Exception {
        if (id > 0) {
        	model = categoryService.get(id);
        	if (model != null) {
            	pages = (int) Math.floor(articleService.count(model, start, end, true) * 1f / pageSize) + 1;
            	articles = articleService.get(model, getCurrentPage(), pageSize, start, end, true, null, null);	
        	}
        }
        end = new Date();
	}
	
	//Action properties
	
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}
	
	private int currentPage = 1;
	
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    private int pageSize = 6;
    
    public void setPageSize(int pageSize) {
    	if (pageSize <= 31) {
    		this.pageSize = pageSize;	
    	}
    }
    
    private int pages;
    
    public int getPages() {
        return pages;
    }
    
    private Date start;
    
    public void setStart(Date start) {
    	this.start = start;
    }    
    
    private Date end;
    
    public void setEnd(Date end) {
    	this.end = end;
    }   
    
    private Collection<Article> articles;
    
    public Collection<Article> getArticles(int i) {
    	if (i > 0) {
    		pageSize = i;
    		pages = (int)Math.floor(articleService.count(model, start, end, true) * 1f / pageSize) + 1;
    		articles = articleService.get(model, getCurrentPage(), pageSize, start, end, true, null, null);
    	}        
        return articles;
    }

    public Collection<Article> getArticles() {
        return getArticles(0);
    }
}