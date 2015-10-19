package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;

import com.publisher.entity.Article;
import com.publisher.service.ArticleService;
import com.publisher.utils.ResultList;

public class ArticleAutoCompleteAction extends AutoCompleteAction {

	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService){
		this.articleService = articleService;
	}
	
	@Override
	public void populate() {
		ResultList<Article> result = null;
		if(getTerm() == null){
			result = new ResultList<Article>();
			result.setResult(articleService.list(getPage(), getPagesize()));
		} else {
			result = articleService.search(getTerm(), getPage(), getPagesize());
		}
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		String label = "";
		for (Article article : result.getResult()) {
			ArticleElement articleElement = new ArticleElement(article);
			if(article.getPermanentLink() != null && article.getPermanentLink().getUri() != null){
				label = "<" + article.getPermanentLink().getUri() + ">";
			}
			collection.add(new LabelValue(label, Long.toString(article.getId()), articleElement));
		}
		setResult(collection);		
	}
	
	public class ArticleElement {
		
		public ArticleElement(Article article){
			if (article != null) {
				this.id = article.getId() != null ? article.getId() : 0;
				this.title = article.getTitle() != null ? article.getTitle() : "";
				this.note = article.getNote() != null ? article.getNote() : "";
				this.permanentLink = article.getPermanentLink() != null && article.getPermanentLink().getUri() != null ? article.getPermanentLink().getUri() : "";
				this.authorName = article.getCreatedBy().getName() != null && article.getCreatedBy().getName() != null ? article.getCreatedBy().getName() : "";
				this.photoId = article.getPhoto() != null ? article.getPhoto().getId() : 0;	
			}
		}
		
		private long id;
		
		private String title;
		
		private String note;
		
		private String permanentLink;
		
		private String authorName;

		private long photoId;

		public long getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getNote() {
			return note;
		}

		public String getPermanentLink() {
			return this.permanentLink;
		}
		
		public String getAuthorName() {
			return authorName;
		}

		public long getPhotoId() {
			return photoId;
		}		
	}//ArticleElement
	
}//ArticleAutoCompleteAction