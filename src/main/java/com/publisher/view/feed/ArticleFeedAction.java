package com.publisher.view.feed;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Article;
import com.publisher.entity.AuthToken;
import com.publisher.entity.Category;
import com.publisher.service.ArticleService;
import com.publisher.service.AuthTokenService;
import com.publisher.service.CategoryService;
import com.publisher.utils.IPUtils;

public class ArticleFeedAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = -8359317767745664083L;
	
	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private AuthTokenService authTokenService;
	
	public void setAuthTokenService(AuthTokenService authTokenService) {
		this.authTokenService = authTokenService;
	}
	
	private HttpServletRequest request;
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public String execute() throws Exception {
		
		if(!authentication()) return SUCCESS;
		
		Category category = null;
		Article article = null;
		if (articleId > 0) {
			article = articleService.get(articleId);
			
			result = new ArrayList<Result>(1);
			result.add(new Result(article));
		} else {	
			if(categoryId > 0) {
				category = categoryService.get(categoryId);
			}
			
			Collection<Article> articles = null;
			if (query != null && !query.isEmpty()) {
				articles = articleService.search(query, currentPage, pageSize, true, null, category != null ? category.getName() : null).getResult();
			}else  {
				articles = articleService.get(category != null ? category : null, currentPage, pageSize, null, null, true);
			}

			result = new ArrayList<Result>(articles.size());
			for (Article a : articles) {
				result.add(new Result(a));	
			}
		}
		
		return SUCCESS;
	}
	
	private boolean authentication(){
		boolean result = false;

		if(token != null && !token.isEmpty()){
			AuthToken authToken = authTokenService.get(token, true);
			
			if(authToken != null){
				result = true;
				String ips = authToken.getIPs();
				
				if(ips != null && !ips.isEmpty()){
					String remoteIp = IPUtils.getClientIP(request);
					if(ips.contains(",")){
						String[] ip = ips.replace(" ", "").split(",");
						result = (StringUtils.indexOfAny(remoteIp, ip) > -1);
					}else{
						result = ips.trim().equals(remoteIp);
					}
				}
			}	
		}
		return result;
	}
	
	//Action properties
	
	private long articleId;
	
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	
	private long categoryId;
	
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	private String query;
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	private int currentPage = 1;
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	private int pageSize = 30;
	
	public void setPageSize(int pageSize){
		if(pageSize > 0 && pageSize <= 30){
			this.pageSize = pageSize;
		}
	}
	
	private String token;
	
	public void setToken(String token) {
		this.token = token;
	}
	
	private List<Result> result;
	
	public List<Result> getResult() {
		return result;
	}	
	
	public class Result {
		
		private long id;
		
		private String title;
		
		private String header;
		
		private String link;
		
		private long photoId;
		
		private String publishedAt;
		
		private String note;
		
		public Result(Article article) {
			if (article == null) {
				return;
			}
			id = article.getId() != null ? article.getId() : 0;
			title = article.getTitle() != null ? article.getTitle() : "";
			header = article.getHeader() != null ? article.getHeader() : "";
			link = article.getPermanentLink() != null ? article.getPermanentLink().getUri() : "";
			photoId = article.getPhoto() != null ? article.getPhoto().getId() != null ? article.getPhoto().getId() : 0 : 0;
			publishedAt = article.getPublishedAt() != null ? new SimpleDateFormat("dd/MM/yyyy").format(article.getPublishedAt()) : new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			note = article.getNote() != null && !article.getNote().isEmpty() ? article.getNote() :  ""; 
		}

		public long getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getHeader() {
			return header;
		}

		public String getLink() {
			return link;
		}

		public long getPhotoId() {
			return photoId;
		}

		public String getPublishedAt() {
			return publishedAt;
		}
		
		public String getNote() {
			return note;
		}
	}//Result
	
}//ArticleFeedAction