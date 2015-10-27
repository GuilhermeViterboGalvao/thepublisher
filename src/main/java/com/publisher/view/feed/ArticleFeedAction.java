package com.publisher.view.feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;

public class ArticleFeedAction extends ActionSupport {

	private static final long serialVersionUID = -8359317767745664083L;
	
	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Override
	public String execute() throws Exception {
		Category category = null;
		Article article = null;
		if (articleId > 0) {
			article = articleService.get(articleId);
		}		
		if(categoryId > 0) {
			category = categoryService.get(categoryId);
		}
		Collection<Article> articles = null;
		if (article != null && article.getPublishedAt() != null && category != null && query != null && !query.isEmpty()) {
			articles = articleService.search(query, currentPage, pageSize, true, article.getPublishedAt(), category.getName()).getResult();
		} else if (category != null && query != null && !query.isEmpty()) {
			articles = articleService.search(query, currentPage, pageSize, true, new Date(), category.getName()).getResult();
		} else if (query != null && !query.isEmpty()) {
			articles = articleService.search(query, currentPage, pageSize, true, new Date(), null).getResult();
		} else if (article != null && article.getPublishedAt() != null && category != null) {
			articles = articleService.get(category, currentPage, pageSize, null, article.getPublishedAt(), true);
		} else if (category != null) {
			articles = articleService.get(category, currentPage, pageSize, null, new Date(), true);
		} else {
			articles = articleService.get(null, currentPage, pageSize, null, new Date(), true);
		}
		result = new ArrayList<Result>(articles.size());
		for (Article a : articles) {
			if (article != null && !article.getId().equals(a.getId())) {
				result.add(new Result(a));	
			} else if (article == null)  {
				result.add(new Result(a));
			}
		}		
		return SUCCESS;
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
	
	public void setPageSize(int pageSize) {
		if (pageSize <= 30) {
			this.pageSize = pageSize;	
		}
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