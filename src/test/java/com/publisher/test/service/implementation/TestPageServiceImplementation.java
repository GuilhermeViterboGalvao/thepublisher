package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.lucene.search.Sort;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Page;
import com.publisher.entity.PermanentLink;
import com.publisher.entity.Skin;
import com.publisher.service.PageService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.SkinService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestPageServiceImplementation extends DefaultTest<Page> implements PageService {

	@Autowired
	private PageService pageService;
	
	@Autowired
	private SkinService skinService;
	
	@Autowired
	private PermanentLinkService permanentLinkService;
	
	private Skin entitySkin;
	
	private PermanentLink entityPermanentLink;

	@Test
	public void testIt() {
		assertNotNull(pageService);
		assertNotNull(skinService);
		assertNotNull(permanentLinkService);
		
		entity = new Page();
		entity.setName("Page Teste for JUnit...");
		entity.setContentFile("/skins/tatame/default/home.jsp");
		
		entitySkin = new Skin();
		entitySkin.setName("Skin Test for JUnit.");
		entitySkin.setPath("/skins/tatame/default/layout.jsp");
		entitySkin.setContentFolder("/skins/tatame/pages/");
		skinService.persist(entitySkin);		
		entity.setSkin(entitySkin);
		
		entityPermanentLink = new PermanentLink();
		entityPermanentLink.setCreated(new Date());
		entityPermanentLink.setParam(1l);
		entityPermanentLink.setUri("/test/junit/home/tatame");
		entityPermanentLink.setType("page");
		//permanentLinkService.persist(entityPermanentLink);		
		entity.setPermanentLink(entityPermanentLink);
		
		persist(entity);
		
		persistedEntity = get(entity.getId());
		
		persistedEntity.setName("Skin Test for JUnit...");
		update(persistedEntity);
		
		update(persistedEntity, persistedEntity.getPermanentLink());
		
		count();
		
		list();
		
		list(0, 50);
		
		list(0, 50, "name", "desc");
		
		search("Test");
		
		search("Test", 0, 50);
		
		delete(persistedEntity);
		persistedEntity = pageService.get(entity.getId());
		assertNull(persistedEntity);
		
		Long id = entityPermanentLink.getId();
		permanentLinkService.delete(entityPermanentLink);
		assertNull(permanentLinkService.get(id));
		
		id = entitySkin.getId();
		skinService.delete(entitySkin);
		assertNull(skinService.get(id));
	}
	
	@Override
	public Page get(Long id) {
		assertNotNull(id);
		Page page = pageService.get(id);
		assertNotNull(page);
		return page;
	}

	@Override
	public void persist(Page entity) {
		assertNotNull(entity);
		pageService.persist(entity);
	}

	@Override
	public void update(Page entity) {
		assertNotNull(entity);
		pageService.update(entity);
	}

	@Override
	public void delete(Page entity) {
		assertNotNull(entity);
		pageService.delete(entity);
	}

	@Override
	public Collection<Page> list() {
		Collection<Page> list = pageService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Page> search(String query) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		Collection<Page> search = pageService.search(query);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count() {
		long total = pageService.count();
		assertTrue(total >= 0);
		return total;
	}

	@Override
	public void indexAll() {
		//pageService.indexAll();
	}

	@Override
	public List<Page> getByName(String name) {
		assertNotNull(name);
		assertTrue(!name.isEmpty());
		List<Page> list = pageService.getByName(name);
		assertNotNull(list);
		return list;
	}

	@Override
	public void update(Page page, PermanentLink oldPermanentLink) {
		assertNotNull(page);
		assertNotNull(oldPermanentLink);
		pageService.update(page, oldPermanentLink);
	}

	@Override
	public Collection<Page> list(int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		Collection<Page> list = pageService.list(page, pageSize);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Page> list(int page, int pageSize, String orderBy, String order) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(orderBy);
		assertTrue(!orderBy.isEmpty());
		assertNotNull(order);
		assertTrue(!order.isEmpty());
		Collection<Page> list = pageService.list(page, pageSize, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public ResultList<Page> search(String query, int page, int pageSize) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());		
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		ResultList<Page> search = pageService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<Page> search(String query, int page, int pageSize, Sort sort) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());		
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(sort);
		ResultList<Page> search = pageService.search(query, page, pageSize, sort);
		assertNotNull(search);
		return search;
	}
}