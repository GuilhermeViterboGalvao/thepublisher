package com.publisher.view;

import java.util.Collection;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Article;
import com.publisher.service.ArticleService;

public class SearchAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = -1176587945501286989L;

	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/tatame/pages/SearchArticles.jsp";
	}	
	
	@Override
	public String execute() throws Exception {
		if (query != null && !query.isEmpty() && query.length() > 2) {
			articles = articleService.search(query, currentPage, pageSize, true).getResult();
		}
		return SUCCESS;
	}
	
	//Action properties
	
	private String query;
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
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
	
	public int getPageSize() {
		return pageSize;
	}
	
	private Collection<Article> articles;
	
	public Collection<Article> getArticles() {
		return articles;
	}
}