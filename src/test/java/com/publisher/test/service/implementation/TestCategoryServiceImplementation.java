package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Skin;
import com.publisher.service.CategoryService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.SkinService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestCategoryServiceImplementation extends DefaultTest<Category> implements CategoryService {

	@Autowired
	private CategoryService categoryService; 
	
	@Autowired
	private SkinService skinService;
	
	@Autowired
	private PermanentLinkService permanentLinkService;	
	
	private Skin entitySkin;
	
	private PermanentLink entityPermanentLink;

	@Test
	@Override
	public void testIt() {
		assertNotNull(categoryService);
		
		entity = new Category();
		entity.setFolder("");
		entity.setName("Category Test for JUnit...");
		entity.setTags("test junit category");
		
		entityPermanentLink = new PermanentLink();
		entityPermanentLink.setCreated(new Date());
		entityPermanentLink.setParam(1l);
		entityPermanentLink.setUri("/category/test/junit");
		entityPermanentLink.setType("category");		
		entity.setPermanentLink(entityPermanentLink);
		
		entitySkin = new Skin();
		entitySkin.setName("Skin Test for JUnit.");
		entitySkin.setPath("/skins/tatame/default/layout.jsp");
		entitySkin.setContentFolder("/skins/tatame/pages/");
		skinService.persist(entitySkin);
		skinService.persist(entitySkin);
		entity.setSkin(entitySkin);		
		
		persist(entity);
		
		persistedEntity = get(entity.getId());
		
		persistedEntity.setName("Category Test for JUnit...");
		update(persistedEntity);
		
		update(persistedEntity, persistedEntity.getPermanentLink());
		
		count();
		
		list();
		
		list(0, 50);
		
		list(0, 50, "name", "desc");
		
		search("Test");
		
		search("Test", 0, 50);
		
		delete(persistedEntity);
		persistedEntity = categoryService.get(entity.getId());
		assertNull(persistedEntity);
		
		Long id = entityPermanentLink.getId();
		permanentLinkService.delete(entityPermanentLink);
		assertNull(permanentLinkService.get(id));
		
		id = entitySkin.getId();
		skinService.delete(entitySkin);
		assertNull(skinService.get(id));		
	}
	
	@Override
	public Category get(Long id) {
		assertNotNull(id);
		Category category = categoryService.get(id);
		assertNotNull(category);
		return category;
	}

	@Override
	public void persist(Category entity) {
		assertNotNull(entity);
		categoryService.persist(entity);		
	}

	@Override
	public void update(Category entity) {
		assertNotNull(entity);
		categoryService.update(entity);
	}

	@Override
	public void delete(Category entity) {
		assertNotNull(entity);
		categoryService.delete(entity);
	}

	@Override
	public Collection<Category> list() {
		Collection<Category> list = categoryService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Category> search(String query) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		Collection<Category> list = categoryService.search(query);
		assertNotNull(list);
		return list;
	}

	@Override
	public long count() {
		Long qtd = categoryService.count();
		assertTrue(qtd >= 0);
		return qtd;
	}

	@Override
	public void indexAll() {
		//categoryService.indexAll();
	}

	@Override
	public List<Category> getByName(String name) {
		assertNotNull(name);
		assertTrue(!name.isEmpty());
		List<Category> list = categoryService.getByName(name);
		assertNotNull(list);
		return list;
	}

	@Override
	public void update(Category category, PermanentLink oldPermanentLink) {
		assertNotNull(category);
		assertNotNull(oldPermanentLink);
		categoryService.update(category, oldPermanentLink);
	}

	@Override
	public Collection<Category> list(int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		Collection<Category> list = categoryService.list(page, pageSize);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Category> list(int page, int pageSize, String orderBy, String order) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(orderBy);
		assertTrue(!orderBy.isEmpty());
		assertNotNull(order);
		assertTrue(!order.isEmpty());
		Collection<Category> list = categoryService.list(page, pageSize, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public ResultList<Category> search(String query, int page, int pageSize) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		ResultList<Category> search = categoryService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}
}