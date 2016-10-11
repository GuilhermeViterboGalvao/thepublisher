package com.publisher.service.implementation;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.publisher.entity.Page;
import com.publisher.entity.PermanentLink;
import com.publisher.service.PageService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class PageServiceImplementation extends AbstractServiceImplementation<Page> implements PageService {

	private static Log log = LogFactory.getLog(PageServiceImplementation.class);

	@Override
	public Class<Page> getServiceClass() {
		return Page.class;
	}

	@Override
	public void persist(Page entity) {
		if (entity != null) {
			entityManager.persist(entity);
			if (entity.getPermanentLink() != null) {
				entityManager.flush();
				entity.getPermanentLink().setParam(entity.getId());
				entityManager.merge(entity.getPermanentLink());
				entityManager.flush();
			}			
		}
	}

	@Override
	public void update(Page entity) {
		if (entity != null) {
			entityManager.merge(entity);
			cleanCache(entity.getPermanentLink());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Page> getByName(String name) {
		return entityManager.createQuery("from Page where name=:name").setParameter("name", name).getResultList();
	}

	@Override
	public void update(Page page, PermanentLink oldPermanentLink) {
		entityManager.merge(page);
		cleanCache(oldPermanentLink);
	}

	private void cleanCache(PermanentLink permanentLink) {
		if (permanentLink != null && permanentLink.getUri() != null) {
			try {
				Cache cache = CacheManager.getInstance().getCache("pageCache");
				if (cache != null) {
					cache.remove(permanentLink.getUri());	
				}	
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
}