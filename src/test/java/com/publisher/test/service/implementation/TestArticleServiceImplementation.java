package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.lucene.search.Sort;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Account;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Photo;
import com.publisher.entity.Skin;
import com.publisher.service.AccountService;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.PhotoService;
import com.publisher.service.SkinService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestArticleServiceImplementation extends DefaultTest<Article> implements ArticleService {

	private static final String PASSWORD = "!@#test123$%^";
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SkinService skinService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private PermanentLinkService permanentLinkService;
	
	private Category entityCategory;
	
	private PermanentLink entityCategoryPermanentLink;
	
	private Skin entitySkin;
	
	private Account entityAccount;
	
	private Photo entityPhoto;
	
	private PermanentLink entityArticlePermanentLink;
	
	@Test
	public void testIt() {
		assertNotNull(articleService);
				
		entitySkin = new Skin();
		entitySkin.setName("Skin Test for JUnit.");
		entitySkin.setPath("/skins/tatame/default/layout.jsp");
		entitySkin.setContentFolder("/skins/tatame/pages/");
		skinService.persist(entitySkin);

		entityCategoryPermanentLink = new PermanentLink();
		entityCategoryPermanentLink.setCreated(new Date());
		entityCategoryPermanentLink.setParam(1l);
		entityCategoryPermanentLink.setUri("/category/test/junit");
		entityCategoryPermanentLink.setType("category");
		
		entityCategory = new Category();
		entityCategory.setFolder("");
		entityCategory.setName("Category Test for JUnit...");
		entityCategory.setTags("test junit category");
		entityCategory.setPermanentLink(entityCategoryPermanentLink);
		entityCategory.setSkin(entitySkin);
		categoryService.persist(entityCategory);
		
		entityAccount = new Account();
		entityAccount.setActive(true);
		entityAccount.setEmail("test@junit.com");
		entityAccount.setHash(hash(PASSWORD));
		entityAccount.setName("Test JUnit");
		entityAccount.setSecurityHole("admin");
		accountService.persist(entityAccount);
		
		entityPhoto = new Photo();
		entityPhoto.setCreated(new Date());
		entityPhoto.setCredits("Test Photo for JUnit...");
		entityPhoto.setDate(new Date());
		entityPhoto.setDescription("Test Photo for JUnit...");
		entityPhoto.setEvent(false);
		entityPhoto.setGym(false);
		entityPhoto.setHeight(250);
		entityPhoto.setHorizontalCenter(0.5f);
		entityPhoto.setLastModified(new Date());
		entityPhoto.setLastModifiedBy(entityAccount);
		entityPhoto.setPublished(true);
		entityPhoto.setTags("test photo junit");
		entityPhoto.setVerticalCenter(0.5f);
		entityPhoto.setWidth(300);
		photoService.persist(entityPhoto);
		
		entityArticlePermanentLink = new PermanentLink();
		entityArticlePermanentLink.setCreated(new Date());
		entityArticlePermanentLink.setParam(1l);
		entityArticlePermanentLink.setUri("/article/test/junit");
		entityArticlePermanentLink.setType("article");
		
		entity = new Article();
		entity.setCategory(entityCategory);
		entity.setContent("<p>Teste de matéria JUnit.</p>");
		entity.setCreated(new Date());
		entity.setCreatedBy(entityAccount);
		entity.setForumEnabled(true);
		entity.setHeader("Teste matéria JUnit...");
		entity.setLastModified(new Date());
		entity.setLastModifiedBy(entityAccount);
		entity.setNote("Teste matéria JUnit...");
		entity.setPublished(true);
		entity.setPublishedAt(new Date());
		entity.setTitle("Teste matéria JUnit...");
		entity.setViews(1000000l);
		entity.setPhoto(entityPhoto);
		entity.setPermanentLink(entityArticlePermanentLink);
		persist(entity);
		
		persistedEntity = get(entity.getId());
		
		persistedEntity.setTitle("Teste matéria JUnit 2...");
		update(persistedEntity);
		
		count();
		
		count(true);
		
		count(entityCategory);
		
		count(new Date(), new Date());
		
		count(entityCategory, new Date(), new Date());
		
		count(entityCategory, new Date(), new Date(), true);
		
		list();
		
		list(0, 10);
		
		list(true, 0, 10);
		
		list(true, 0, 10, new Date());
		
		list(true, entityCategory, 0, 10, new Date());
		
		list(true, entityCategory, 0, 10, new Date(), true);
		
		list(true, entityCategory, 0, 10, new Date(), "title", "desc");
		
		get(0, 10, new Date(), new Date(), true);
		
		get(entityCategory, 0, 10, new Date(), new Date(), true);
		
		get(entityCategory, 0, 10, new Date(), new Date(), true, "title", "desc");
		
		search("Teste");
		
		search("Teste", 0, 10);
		
		search("Teste", 0, 10, true);
		
		search("Teste", 0, 10, true, new Date(), "Category Test for JUnit...");
		
		delete(persistedEntity);
		persistedEntity = articleService.get(entity.getId());
		assertNull(persistedEntity);
		
		Long id = entityAccount.getId();
		accountService.delete(entityAccount);
		assertNull(accountService.get(id));
		
		id = entityArticlePermanentLink.getId();
		permanentLinkService.delete(entityArticlePermanentLink);
		assertNull(permanentLinkService.get(id));
		
		id = entityCategory.getId();
		categoryService.delete(entityCategory);
		assertNull(categoryService.get(id));
		
		id = entityCategoryPermanentLink.getId();
		permanentLinkService.delete(entityCategoryPermanentLink);
		assertNull(permanentLinkService.get(id));
		
		id = entityPhoto.getId();
		photoService.delete(entityPhoto);
		assertNull(photoService.get(id));
		
		id = entitySkin.getId();
		skinService.delete(entitySkin);
		assertNull(skinService.get(id));
	}
	
	@Override
	public Article get(Long id) {
		assertNotNull(id);
		Article article = articleService.get(id);
		assertNotNull(article);
		return article;
	}

	@Override
	public void persist(Article entity) {
		assertNotNull(entity);
		articleService.persist(entity);
	}

	@Override
	public void update(Article entity) {
		assertNotNull(entity);
		articleService.update(entity);
	}

	@Override
	public void delete(Article entity) {
		assertNotNull(entity);
		articleService.delete(entity);
	}

	@Override
	public Collection<Article> list() {
		Collection<Article> list = articleService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> search(String query) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		Collection<Article> search = articleService.search(query);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count() {
		long count = articleService.count();
		assertTrue(count >= 0);
		return count;
	}

	@Override
	public void indexAll() {
		//articleService.indexAll();
	}

	@Override
	public void update(Article entity, PermanentLink oldPermanentLink) {
		assertNotNull(entity);
		assertNotNull(oldPermanentLink);
		articleService.update(entity, oldPermanentLink);
	}

	@Override
	public List<Article> get(int page, int pageSize, Date start, Date end, Boolean publishedOnly) {
		List<Article> list = articleService.get(page, pageSize, start, end, publishedOnly);
		assertNotNull(list);
		return list;
	}

	@Override
	public List<Article> get(Category category, int page, int pageSize, Date start, Date end, Boolean publishedOnly) {
		List<Article> list = articleService.get(page, pageSize, start, end, publishedOnly);
		assertNotNull(list);
		return list;
	}

	@Override
	public List<Article> get(Category category, int page, int pageSize, Date start, Date end, Boolean publishedOnly, String orderBy, String order) {
		List<Article> list = articleService.get(category, page, pageSize, start, end, publishedOnly, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> list(int page, int pageSize) {
		Collection<Article> list = articleService.list(page, pageSize);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> list(Boolean published, int page, int pageSize) {
		Collection<Article> list = articleService.list(published, page, pageSize);
		assertNotNull(list);
		return list;
	}
	
	@Override
	public Collection<Article> list(int page, int pageSize, String orderBy, String order) {
		Collection<Article> list = articleService.list(page, pageSize, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> list(Boolean published, int page, int pageSize, Date publishedUntil) {
		Collection<Article> list = articleService.list(published, page, pageSize, publishedUntil);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil) {
		Collection<Article> list = articleService.list(published, category, page, pageSize, publishedUntil);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil, Boolean orderByDate) {
		Collection<Article> list = articleService.list(published, category, page, pageSize, publishedUntil, orderByDate);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil, String orderBy, String order) {
		Collection<Article> list = articleService.list(published, category, page, pageSize, publishedUntil, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		ResultList<Article> search = articleService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}
	
	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Sort sort) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(sort);
		ResultList<Article> search = articleService.search(query, page, pageSize, sort);
		assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(published);
		ResultList<Article> search = articleService.search(query, page, pageSize, published);
		assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(published);
		assertNotNull(publishedUntil);
		ResultList<Article> search = articleService.search(query, page, pageSize, published, publishedUntil);
		assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil, String categoryName) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(published);
		assertNotNull(publishedUntil);
		assertNotNull(categoryName);
		assertTrue(!categoryName.isEmpty());
		ResultList<Article> search = articleService.search(query, page, pageSize, published, publishedUntil, categoryName);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count(Category category) {
		assertNotNull(category);
		long count = articleService.count(category);
		assertTrue(count >= 0);
		return count;
	}

	@Override
	public long count(Date start, Date end) {
		assertNotNull(start);
		assertNotNull(end);
		long count = articleService.count(start, end);
		assertTrue(count >= 0);
		return count;
	}

	@Override
	public long count(Boolean publishedOnly) {
		assertNotNull(publishedOnly);
		long count = articleService.count(publishedOnly);
		assertTrue(count >= 0);
		return count;
	}

	@Override
	public long count(Category category, Date start, Date end) {
		assertNotNull(category);
		assertNotNull(start);
		assertNotNull(end);
		long count = articleService.count(category, start, end);
		assertTrue(count >= 0);
		return count;
	}

	@Override
	public long count(Category category, Date start, Date end, Boolean publishedOnly) {
		assertNotNull(category);
		assertNotNull(start);
		assertNotNull(end);
		assertNotNull(publishedOnly);
		long count = articleService.count(category, start, end, publishedOnly);
		assertTrue(count >= 0);
		return count;
	}
	
	public String hash(String password) {
		assertNotNull(password);
		password = accountService.hash(password);
		assertNotNull(password);
		return password;
	}
}