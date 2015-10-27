package com.publisher.view;

import java.util.Collection;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.publisher.entity.Article;
import com.publisher.entity.Photo;
import com.publisher.service.ArticleService;

public class ArticleAction extends ActionSupport implements ModelDriven<Article>, Preparable, ViewAction {

	private static final long serialVersionUID = -9064936416980427761L;
	
	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	private Article model;
	
	@Override
	public Article getModel() {
		return model;
	}
	
	@Override
	public String getLayoutPath() {
		String layoutPath = null;
		if (model.getTemplate() == null || model.getTemplate().getLayoutPath().isEmpty()) {
			layoutPath = model.getCategory().getSkin().getLayoutPath();
		} else {
			layoutPath = model.getTemplate().getLayoutPath();
		}
		return layoutPath;
	}

	@Override
	public String getContentPath() {
		String contentPath = null;
		if (model.getTemplate() == null || model.getTemplate().getContentPath(model.getClass().getSimpleName()).isEmpty()) {
			contentPath = model.getCategory().getSkin().getContentPath(model.getClass().getSimpleName());
		} else {
			contentPath = model.getTemplate().getContentPath(model.getClass().getSimpleName());
		}
		return contentPath;
	}

	@Override
	public void prepare() throws Exception {
		if (id > 0) {
			model = articleService.get(id);
			if (model != null && model.getContent() != null && !model.getContent().isEmpty() && model.getContent().contains("##")) {
				String[] pages = model.getContent().split("##");
				this.articleContent = pages[page < pages.length ? page : 0];
				this.pages = pages.length;
			} else {
				this.articleContent = model.getContent();
			}
		}
	}
	
	//Action properties
	
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}
	
	private int page;
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	private int pages;
	
	public int getPages() {
		return pages;
	}
	
	private String articleContent;
	
	public String getArticleContent() {
		return articleContent;
	}
	
    public String getSize(Photo photo, Integer maxWidth, Integer maxHeight) {
        int width = photo.getWidth();
        int height = photo.getHeight();
        if ((maxWidth != null) && (width > maxWidth)) {
            height = (int) Math.floor((1d * maxWidth) / width * height);
            width = maxWidth;
        }
        if ((maxHeight != null) && (height > maxHeight)) {
            width = (int) Math.floor(1d * maxHeight / height * width);
            height = maxHeight;
        }
        return width + "x" + height;
    }
    
	public Collection<Article> getLast(int size) {
		return articleService.get(model.getCategory(), 1, size, null, null, true, null, null);
	}
}