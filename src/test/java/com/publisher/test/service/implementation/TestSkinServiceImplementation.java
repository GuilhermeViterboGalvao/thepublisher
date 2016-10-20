package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Skin;
import com.publisher.service.SkinService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestSkinServiceImplementation extends DefaultTest<Skin> implements SkinService {

	@Autowired
	private SkinService skinService;

	@Test
	public void testIt() {
		assertNotNull(skinService);
		
		entity = new Skin();
		entity.setName("Skin Test for JUnit.");
		entity.setPath("/skins/tatame/default/layout.jsp");
		entity.setContentFolder("/skins/tatame/pages/");
		
		persist(entity);
		
		persistedEntity = get(entity.getId());
		
		persistedEntity.setName("Skin Test for JUnit...");
		update(persistedEntity);
		
		count();
		
		list();
		
		list(0, 50);
		
		list(0, 50, "name", "desc");
		
		search("Test");
		
		search("Test", 0, 50);
		
		delete(persistedEntity);
		persistedEntity = skinService.get(entity.getId());
		assertNull(persistedEntity);
	}
	
	@Override
	public Skin get(Long id) {
		assertNotNull(id);
		Skin skin = skinService.get(id);
		assertNotNull(skin);
		return skin;
	}

	@Override
	public void persist(Skin entity) {
		assertNotNull(entity);
		skinService.persist(entity);
	}

	@Override
	public void update(Skin entity) {
		assertNotNull(entity);
		skinService.update(entity);
	}

	@Override
	public void delete(Skin entity) {
		assertNotNull(entity);
		skinService.delete(entity);
	}

	@Override
	public Collection<Skin> list() {
		Collection<Skin> list = skinService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Skin> search(String query) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		Collection<Skin> search = skinService.search(query);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count() {
		long total = skinService.count();
		assertTrue(total >= 0);
		return total;
	}

	@Override
	public void indexAll() {
		//skinService.indexAll();
	}

	@Override
	public Collection<Skin> list(int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		Collection<Skin> list = skinService.list(page, pageSize);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Skin> list(int page, int pageSize, String orderBy, String order) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(orderBy);
		assertTrue(!orderBy.isEmpty());
		assertNotNull(order);
		assertTrue(!order.isEmpty());
		Collection<Skin> list = skinService.list(page, pageSize, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public ResultList<Skin> search(String query, int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		ResultList<Skin> search = skinService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}
}