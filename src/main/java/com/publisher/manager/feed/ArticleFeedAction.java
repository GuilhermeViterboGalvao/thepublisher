package com.publisher.manager.feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.manager.AccountAware;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;
import com.publisher.utils.ResultList;

public class ArticleFeedAction extends ActionSupport implements AccountAware {

	private static final long serialVersionUID = -4645263733993106068L;

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
    	Collection<Article> collecion = null;
    	Category category = null;
    	if (this.category != null && !this.category.isEmpty()) {
    		List<Category> categories = categoryService.getByName(this.category);
    		if (categories != null && categories.size() > 0) {
    			category = categories.get(0);
    		}
    	}
        if (query.length() > 0) {
            ResultList<Article> list = category != null ? articleService.search(query, currentPage, pageSize, null, null, category.getTags()) : articleService.search(query, currentPage, pageSize);
            pages = (int) Math.floor(1f * list.getResultSize() / pageSize) + 1;
            collecion = list.getResult();
        } else {
        	pages = (int) Math.floor(1f * (category != null ? articleService.count(category) : articleService.count())  / pageSize) + 1;
        	collecion = category != null ? articleService.get(category, currentPage, pageSize, null, null, null) : articleService.list(pageSize, currentPage);
        }
        articles = new ArrayList<ArticleElement>(collecion.size());
        for (Article article : collecion) {
        	articles.add(new ArticleElement(article));
		}
        result = new Result(currentPage, pageSize, pages, query, articles);
        return SUCCESS;
	}
	
	private Account account;
	
	@Override
	public void setAccount(Account account) { 
		this.account = account;		
	}
	
	public Account getAccount() {
		return account;
	}
	
	//POJO
	
    private Collection<ArticleElement> articles;
    
    private int currentPage = 1;
    
    private int pageSize = 20;
    
    private int pages = 0;
    
    private String query = "";
    
    private Result result;
    
    private String category;
    
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    public Result getResult(){
    	return result;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }    
    
    public class Result {
    	
        private int currentPage = 1;
        private int pageSize = 50;
        private int pages = 0;
        private String query = "";
        private Collection<ArticleElement> articles;
        
        public Result(int currentPage, int pageSize, int pages, String query, Collection<ArticleElement> articles){
        	this.currentPage = currentPage;
        	this.pageSize = pageSize;
        	this.pages = pages;
        	this.query = query;
        	this.articles = articles;
        	
        }
		public int getCurrentPage() {
			return currentPage;
		}
		public int getPageSize() {
			return pageSize;
		}
		public int getPages() {
			return pages;
		}
		public String getQuery() {
			return query;
		}
		public Collection<ArticleElement> getArticles() {
			return articles;
		}   	
    }//Result
    
    public class ArticleElement {
    	
    	private long id;
    	private String title;
    	private String note;
    	private long photoId;
    	private String url;
    	private String publishedAt;
    	private String createdByName;
    	private String categoryName;
    	private String categoryLink;
    	private String header;
    	
    	public ArticleElement(Article article){
    		if (article == null) {
    			return; 
    		}
    		this.id = article.getId() != null ? article.getId() : 0;
    		this.title = article.getTitle() != null ? article.getTitle() : "";
    		this.note = article.getNote() != null ? article.getNote() : "";
    		this.photoId = article.getPhoto() != null && article.getPhoto().getId() != null ? article.getPhoto().getId() : 0;
    		this.url = article.getPermanentLink() != null && article.getPermanentLink().getUri() != null ? article.getPermanentLink().getUri() : "";
    		this.createdByName = article.getCreatedBy() != null && article.getCreatedBy().getName() != null ? article.getCreatedBy().getName() : "";
    		this.publishedAt = article.getPublishedAt() != null ? new SimpleDateFormat("dd/MM/yyy").format(article.getPublishedAt()) : "";
    		this.categoryName = article.getCategory() != null && article.getCategory().getName() != null ? article.getCategory().getName() : "";
    		this.categoryLink = article.getCategory() != null && article.getCategory().getPermanentLink() != null && article.getCategory().getPermanentLink().getUri() != null ? article.getCategory().getPermanentLink().getUri() : "";
    		this.header = article.getHeader() != null ? article.getHeader() : "";
    	}
		public long getId() {
			return id;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getNote() {
			return note;
		}
		
		public long getPhotoId() {
			return photoId;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getCreatedByName() {
			return createdByName;
		}
		
		public String getPublishedAt() {
			return publishedAt;
		}
		
		public String getCategoryName() {
			return categoryName;
		}
		
		public String getCategoryLink() {
			return categoryLink;
		}
		
		public String getHeader() {
			return header;
		}
    }//ArticleElement
    
}//ArticleFeedAction