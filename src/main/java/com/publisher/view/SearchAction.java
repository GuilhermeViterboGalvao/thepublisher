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
		return "/view/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/view/pages/SearchArticles.jsp";
	}	
	
	@Override
	public String execute() throws Exception {
		if (query != null && !query.isEmpty()) {
			articles = articleService.search(query, currentPage, pageSize).getResult();
		}
		return SUCCESS;
	}
	
	//Action properties
	
	private String query;
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	private int currentPage = 1;
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	private int pageSize = 16;
	
	public void setPageSize(int pageSize) {
		if (pageSize <= 31) {
			this.pageSize = pageSize;	
		}
	}
	
	private Collection<Article> articles;
	
	public Collection<Article> getArticles() {
		return articles;
	}
}