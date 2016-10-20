package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.service.ArticleService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestArticleServiceImplementation extends DefaultTest<Article> implements ArticleService {

	@Autowired
	private ArticleService articleService;
	
	@Override
	public void testIt() {
		assertNotNull(articleService);
		
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
		assertTrue(page > 0);
		assertTrue(pageSize > 0);
		ResultList<Article> search = articleService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page > 0);
		assertTrue(pageSize > 0);
		assertNotNull(published);
		ResultList<Article> search = articleService.search(query, page, pageSize, published);
		assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page > 0);
		assertTrue(pageSize > 0);
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
		assertTrue(page > 0);
		assertTrue(pageSize > 0);
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
}