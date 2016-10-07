package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.PermanentLink;
import com.publisher.service.PermanentLinkService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestPermanentLinkServiceImplementation extends DefaultTest<PermanentLink> implements PermanentLinkService {

	@Autowired
	private PermanentLinkService permanentLinkService;
	
	private PermanentLink newPermanentLink;
	
	@Before
	@Override
	public void init() {
		assertNotNull(permanentLinkService);
		
		entity = new PermanentLink();
		entity.setCreated(new Date());
		entity.setParam(1l);
		entity.setUri("/category/test/junit");
		entity.setType("category");
	}

	@Test
	@Override
	public void testIt() {
		persist(entity);		
		
		get(entity.getUri());
		
		getPermanentLink(entity.getUri());
		
		persistedEntity = get(entity.getId());
		
		persistedEntity.setUri("/category/test/junit/2");
		update(persistedEntity);
		
		getActionMapping(persistedEntity.getUri());
		
		newPermanentLink = new PermanentLink();
		newPermanentLink.setCreated(new Date());
		newPermanentLink.setParam(1l);
		newPermanentLink.setUri("/category/test/junit/3");
		newPermanentLink.setType("category");
		
		change(newPermanentLink, persistedEntity);
		
		removeFromCacheIfIsNotPermanent(newPermanentLink.getUri());
		
		count();
		
		list();
		
		search("category");
		
		search("category", 0, 50);
	}

	@After
	@Override
	public void finish() {
		delete(persistedEntity);
		persistedEntity = permanentLinkService.get(entity.getId());
		assertNull(persistedEntity);
		
		Long id = newPermanentLink.getId();
		delete(newPermanentLink);
		newPermanentLink = permanentLinkService.get(id);
		assertNull(newPermanentLink);
	}	
	
	@Override
	public PermanentLink get(Long id) {
		assertNotNull(id);
		PermanentLink permanentLink = permanentLinkService.get(id);
		assertNotNull(permanentLink);
		return permanentLink;
	}

	@Override
	public void persist(PermanentLink entity) {
		assertNotNull(entity);
		permanentLinkService.persist(entity);
	}

	@Override
	public void update(PermanentLink entity) {
		assertNotNull(entity);
		permanentLinkService.update(entity);
	}

	@Override
	public void delete(PermanentLink entity) {
		assertNotNull(entity);
		permanentLinkService.delete(entity);
	}

	@Override
	public Collection<PermanentLink> list() {
		Collection<PermanentLink> list = permanentLinkService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<PermanentLink> search(String query) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		Collection<PermanentLink> search = permanentLinkService.search(query);
		//TODO
		//assertNotNull(search);
		return search;
	}

	@Override
	public ResultList<PermanentLink> search(String query, int page, int pageSize) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		ResultList<PermanentLink> search = permanentLinkService.search(query, page, pageSize);
		//TODO
		//assertNotNull(search);
		return search;
	}

	@Override
	public long count() {
		long qtd = permanentLinkService.count();
		assertTrue(qtd >= 0);
		return qtd;
	}

	@Override
	public void indexAll() {
		//permanentLinkService.indexAll();
	}

	@Override
	public PermanentLink get(String uri) {
		assertNotNull(uri);
		assertTrue(!uri.isEmpty());
		PermanentLink permanentLink = permanentLinkService.get(uri);
		assertNotNull(permanentLink);
		return permanentLink;
	}

	@Override
	public PermanentLink getPermanentLink(String uri) {
		assertNotNull(uri);
		assertTrue(!uri.isEmpty());
		PermanentLink permanentLink = permanentLinkService.getPermanentLink(uri);
		assertNotNull(permanentLink);
		return permanentLink;
	}

	@Override
	public ActionMapping getActionMapping(String uri) {
		assertNotNull(uri);
		assertTrue(!uri.isEmpty());
		ActionMapping actionMapping = permanentLinkService.getActionMapping(uri);
		assertNotNull(actionMapping);
		return actionMapping;
	}

	@Override
	public void change(PermanentLink oldPermanentLink, PermanentLink newPermanentLink) {
		assertNotNull(oldPermanentLink);
		assertNotNull(newPermanentLink);
		permanentLinkService.change(oldPermanentLink, newPermanentLink);
	}

	@Override
	public void removeFromCacheIfIsNotPermanent(String uri) {
		assertNotNull(uri);
		assertTrue(!uri.isEmpty());
		permanentLinkService.removeFromCacheIfIsNotPermanent(uri);
	}
}