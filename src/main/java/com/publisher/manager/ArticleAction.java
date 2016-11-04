package com.publisher.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.publisher.entity.Account;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.PhotoElement;
import com.publisher.entity.PhotoGallery;
import com.publisher.entity.Skin;
import com.publisher.service.AccountService;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.PhotoService;
import com.publisher.service.SkinService;
import com.publisher.utils.ResultList;

public class ArticleAction extends AbstractAction<Article> implements ServletRequestAware {

	private static final long serialVersionUID = -2622613201300260784L;
	
	public ArticleAction() {
		setPageSize(8);
	}
	
	private long cid = -1;
	
	public String getInput() {
		if (type != null && !type.equals("")) {
			return "article-" + type;
		}
		return "article-input";
	}
	
	private AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}
	
	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	private SkinService skinService;
	
	public void setSkinService(SkinService skinService) {
		this.skinService = skinService;
	}
	
	private HttpServletRequest request;
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	protected Article getObject() {
		return articleService.get(id);
	}
	
	@Override
	protected void indexAll() {
		articleService.indexAll();
	}

	@Override
	protected void populateForm(Article article) {
		if (article == null) {
			return;
		}		
		if (article instanceof PhotoGallery) {
			PhotoGallery gallery = (PhotoGallery)article;
			photos = gallery.getPhotos();
			type = "gallery";			
		}        
		id = article.getId();		
		try { 
			createdById = article.getCreatedBy().getId();
			createdByDescription = article.getCreatedBy().getName();
			if (article.getCreatedBy().getEmail() != null && !article.getCreatedBy().getEmail().isEmpty()) {
				createdByDescription = createdByDescription + " <" + article.getCreatedBy().getEmail() + ">";
			}
		} catch (Exception e) {}
		try { 
			photoId = article.getPhoto().getId();
			photoDescription = article.getPhoto().getDescription();
			if (article.getPhoto().getCredits() != null && !article.getPhoto().getCredits().isEmpty()) {
				photoDescription = article.getPhoto().getCredits();
			}
		} catch (Exception e) {}
		try { 
			categoryId = article.getCategory().getId();
			categoryName = article.getCategory().getName();
		} catch (Exception e) {}
		try {
			if (article.getTemplate() == null) {
				templateDescription = "Padrão da Categoria";
			} else {
				templateId = article.getTemplate().getId();
				templateDescription = article.getTemplate().getName();
			}
		} catch (Exception e) {}
		if (article.getPermanentLink()!=null) {
			permanentLink = article.getPermanentLink().getUri();
		}
		tags = article.getTags();
		header = article.getHeader();
		title = article.getTitle();
		note = article.getNote();
		content = article.getContent();
		published = article.isPublished();
		publishedAt = article.getPublishedAt();
		forumEnabled = article.isForumEnabled();
	    try { createdBy = article.getCreatedBy().getName(); } catch (Exception e) {}
	    created = article.getCreated();
	    try { lastModifiedBy = article.getLastModifiedBy().getName(); } catch (Exception e) {}
	    lastModified = article.getLastModified();	    
	    views = article.getViews();
	}

	@Override
	protected Article updateObject(Article article) {		
		if (article instanceof PhotoGallery) {
			PhotoGallery gallery = (PhotoGallery)article;
			gallery.setPhotos(photos);
		}
		if (permanentLink != null && permanentLink.length() > 0 && (article.getPermanentLink() == null || !permanentLink.equals(article.getPermanentLink().getUri()))) {
			newPermanentLink = new PermanentLink();
			newPermanentLink.setUri(permanentLink);
			newPermanentLink.setCreated(new Date());
			newPermanentLink.setType("article");
			if (permanentLink != null) {
				oldPermanentLink = article.getPermanentLink();
			}
		}
		Account account = createdById > 0 ? accountService.get(createdById) : null;
		if (account != null) {
			article.setCreatedBy(account);	
		} else {
			article.setCreatedBy(getAccount());	
		}
		if (article.getCreated() == null) {
			article.setCreated(new Date());
		}
		article.setTags(tags);		
		article.setNote(note);
		article.setTitle(title);
		article.setHeader(header);
		article.setContent(content);
		article.setPublished(published);
		article.setPublishedAt(publishedAt);
		article.setLastModified(new Date());
		article.setForumEnabled(forumEnabled);
		article.setLastModifiedBy(getAccount());
		article.setPhoto(photoService.get(photoId));
		article.setTemplate(skinService.get(templateId));
		article.setCategory(categoryService.get(categoryId));		
		return article;
	}

	@Override
	protected Article createEmptyInstance() {
		if (type.equals("gallery")) {
			return new PhotoGallery();
		} else {
			return new Article();	
		}
	}

	@Override
	protected void saveObject(Article article, boolean isNew) {
		if (isNew) {
			if (newPermanentLink != null){
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				article.setPermanentLink(newPermanentLink);
			}
			articleService.persist(article);
		} else {
			if (newPermanentLink!=null) {
				permanentLinkService.removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
				newPermanentLink.setParam(article.getId());
				newPermanentLink.setCreated(new Date());
				article.setPermanentLink(newPermanentLink);
			}
			if (oldPermanentLink != null) {
				articleService.update(article, oldPermanentLink);	
			} else {
				articleService.update(article);	
			}			
		}
		if (oldPermanentLink != null)
			permanentLinkService.change(oldPermanentLink, article.getPermanentLink());
	}

	@Override
	protected Collection<Article> generateList() {
		if (cid > 0) {
			Category category = categoryService.get(cid);
			if (category != null) {
				setPages((int)Math.floor(1f * articleService.count(category, null, null, false) / getPageSize()) + 1);				
				return articleService.get(category, getCurrentPage(), getPageSize(), null, null, false, orderBy, orderly ? "desc" : "asc");
			}
		}
		setPages((int)Math.floor(1f * articleService.count() / getPageSize()) + 1);		
		return articleService.list(null, null, getCurrentPage(), getPageSize(), null, orderBy, orderly ? "desc" : "asc");
	}

	public void setCid(long cid) {
		this.cid = cid;
	}
	
	public long getCid(){
		return cid;
	}

	@Override
	protected Collection<Article> generateSearch() {
		ResultList<Article> result;
		if (cid > 0) {
			Category category = categoryService.get(cid);
			if (category != null) {				
				result = articleService.search(getSearch(), getCurrentPage(), getPageSize(), null, null, category.getName());
				setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);				
			} else {
				result = articleService.search(getSearch(), getCurrentPage(), getPageSize(), new Sort(new SortField("publishedAt", SortField.Type.LONG, true)));
				setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);				
			}
		} else {
			result = articleService.search(getSearch(), getCurrentPage(), getPageSize(), new Sort(new SortField("publishedAt", SortField.Type.LONG, true)));
			setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		}
		return result != null ? result.getResult() : null;
	}

	@Override
	public void validate() {
		super.validate();
		if (accountService.get(createdById) == null) {
			addFieldError("authorId", "Você deve definir um autor.");
		}
		if (categoryService.get(categoryId) == null) {
			addFieldError("categoryId", "Você deve definir uma categoria.");
		}
		if (templateId != 0 && skinService.get(templateId) == null) {
			addFieldError("templateId", "Você deve definir um template padrão.");
		}
		if (publishedAt == null) {
			addFieldError("publishedAt", "Você deve entrar com a data de publicação.");
		}
		if (published && (permanentLink == null || permanentLink.equals(""))) {
			addFieldError("publishedAt", "Você deve entrar com um permanentLink para poder publicar a matéria.");		
		}
		if (permanentLink != null && permanentLink.length() > 0) {			
			//Validation for removing the first character if it is equal to '/'
			while(permanentLink.charAt(0) == '/' && permanentLink.length() > 0) {				
				permanentLink = permanentLink.substring(1);			
			}			
			Article article = articleService.get(id);
			if (article != null) {
				if(article.getPermanentLink() != null && !permanentLink.equals(article.getPermanentLink().getUri())) {
					if (permanentLinkService.get(permanentLink) != null) {
						addFieldError("permanentLink", "Link já cadastrado.");	
					}						
				}
			} else {
				if (permanentLinkService.get(permanentLink) != null)  {
					addFieldError("permanentLink", "Link já cadastrado.");
				}					
			}
		}		
		//This procedure is necessary in case the user has chosen 
		//an author, category or template, the action can save the state of the page.	
		if (createdById > 0) {
			Account account = accountService.get(createdById);
			if (account != null && account.getName() != null && !account.getName().equals("")) {
				createdByDescription = account.getName() + "<" + account.getEmail() + ">";
			}
		}		
		if (categoryId > 0) {
			Category category = categoryService.get(categoryId);
			if (category != null && category.getName() != null && !category.getName().equals("")) {
				categoryName = category.getName();
			}
		}		
		if (templateId > 0) {
			Skin skin = skinService.get(templateId);
			if (skin != null && skin.getName() != null && !skin.getName().equals("")) {
				templateDescription = skin.getName();
			}
		}
	}
	
	@Override
	public String save() {
		super.save();
		if (id > 0) {
			return "reload";
		} else {
			return "redirect";
		}		
	}	
	
	//ArticleAction properties
	protected String type;
	private PermanentLink oldPermanentLink;
	private PermanentLink newPermanentLink;
	private String orderBy = "publishedAt";
	private boolean orderly = true;
	private long views;
	
	//PhotoGallery properties
	private List<PhotoElement> photos;

	//Read and Write POJO properties
	private long id = -1; 
	private long createdById;
    private long photoId;
    private long categoryId;
    private long templateId;
	private String tags;
    private String header;
    private String title;
    private String note;
    private String content;
    private boolean published = false;
    private Date publishedAt;
    private boolean forumEnabled = true;
    private String permanentLink;
    
	//Read only POJO properties
    private String createdBy;
    private Date created;
    private String lastModifiedBy;
    private Date lastModified;
    private String templateDescription;
	private String createdByDescription;
    private String photoDescription;
    private String categoryName;

    
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}	

	public boolean isOrderly() {
		return orderly;
	}

	public void setOrderly(boolean orderly) {
		this.orderly = orderly;
	}
	
	public long getViews() {
		return views;
	}
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public List<PhotoElement> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoElement> photos) {
		this.photos = photos;
	}
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public Date getPublishedAt() {
		return publishedAt != null ? publishedAt : new Date();
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public boolean isForumEnabled() {
		return forumEnabled;
	}

	public void setForumEnabled(boolean forumEnabled) {
		this.forumEnabled = forumEnabled;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getLastModified() {
		return lastModified;
	}
	
	public void setCreatedByDescription(String createdByDescription) {
		this.createdByDescription = createdByDescription;
	}

	public String getCreatedByDescription() {
		return createdByDescription;
	}
	
	public String getPhotoDescription() {
		return photoDescription;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPermanentLink() {
		return permanentLink;
	}

    public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	
    public String getTemplateDescription() {
		return templateDescription;
	}
    
    public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public void setPermanentLink(String permanentLink) {
		this.permanentLink = permanentLink;
	}
	
	public String getSessionId() {
    	return request.getSession().getId();
    }
}