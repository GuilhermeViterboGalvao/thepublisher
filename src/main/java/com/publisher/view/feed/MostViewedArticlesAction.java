package com.publisher.view.feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;

public class MostViewedArticlesAction extends ActionSupport {

	private static final long serialVersionUID = 1413903973827394356L;

	private static Log log = LogFactory.getLog(MostViewedArticlesAction.class);
	
	private static HashMap<Category, ResultInMemory> cache = new HashMap<Category, ResultInMemory>();
	
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
		long t = System.currentTimeMillis();
		Category category = null;
		if (categoryId > 0) {
			category = categoryService.get(categoryId);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date currentDate = calendar.getTime();
		if (category != null) {			
			ResultInMemory resultInMemory = cache.get(category);
			if (resultInMemory == null || resultInMemory.getDate().getTime() < currentDate.getTime()) {
				synchronized(cache) {
					resultInMemory = cache.get(category);
					if (resultInMemory == null || resultInMemory.getDate().getTime() < currentDate.getTime()) {
						cache.remove(category);
						calendar.add(Calendar.DAY_OF_MONTH, dayRange);
						Date start = calendar.getTime();
						List<Article> articles = articleService.get(category, currentPage, pageSize, start, currentDate, true, "views", "desc");
						if (articles != null && articles.size() > 0) {
							result = new ArrayList<Result>(articles.size());
							for (Article article : articles) {
								result.add(new Result(article));
							}
						}
						resultInMemory = new ResultInMemory(currentDate, result);
						cache.put(category, resultInMemory);
					}
				}
			}
			if (resultInMemory != null) {
				result = resultInMemory.getResult();
			}
		}
		log.info("TimeElapsed=" + String.valueOf((int)(System.currentTimeMillis() - t)));
		return SUCCESS;
	}	
	
	//Action properties
	
	private long categoryId = 0;
	
	private int pageSize = 15;
	
	private int currentPage = 1;
	
	private int dayRange = -7;

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}	
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public void setDayRange(int dayRange){
		this.dayRange = dayRange;
	}
	
	private Collection<Result> result;
	
	public Collection<Result> getResult() {
		return result;
	}	
	
	public class ResultInMemory {
		
		private Date date;
		
		private Collection<Result> result;
		
		public ResultInMemory(Date date, Collection<Result> result) {
			this.date = date;
			this.result = result;
		}
		
		public Date getDate() {
			return date;
		}
		
		public Collection<Result> getResult() {
			return result;
		}
	}//ResultInMemory
	
	public class Result {
		
		private long id;
		
		private String title;
		
		private String header;
		
		private String link;
		
		private long photoId;
		
		private String publishedAt;
		
		private long views;
		
		public Result(Article article) {
			if (article == null) {
				return;
			}
			this.views = article.getViews();			
			this.id = article.getId() != null ? article.getId() : 0;
			this.title = article.getTitle() != null ? article.getTitle() : "";
			this.header = article.getHeader() != null ? article.getHeader() : "";
			this.link = article.getPermanentLink() != null ? article.getPermanentLink().getUri() : "";
			this.photoId = article.getPhoto() != null ? article.getPhoto().getId() != null ? article.getPhoto().getId() : 0 : 0;
			this.publishedAt = article.getPublishedAt() != null ? new SimpleDateFormat("dd/MM/yyyy").format(article.getPublishedAt()) : new SimpleDateFormat("dd/MM/yyyy").format(new Date());
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
		
		public long getViews() {
			return views;
		}
	}//Result
	
}//MostViewedArticlesAction