package com.publisher.service.implementation;

import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.service.CategoryService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CategoryServiceImplementation extends AbstractServiceImplementation<Category> implements CategoryService {

	private static Log log = LogFactory.getLog(CategoryServiceImplementation.class);

	@Override
	public void persist(Category entity) {
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
	public void update(Category entity) {
		if (entity != null) {
			entityManager.merge(entity);
			cleanCache(entity.getPermanentLink());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getByName(String name) {
		Query query = entityManager.createQuery("select c from Category c where c.name like ? order by c.name");
		query.setParameter(1, name + "%");
		return query.getResultList();
	}

	@Override
	public void update(Category category, PermanentLink oldPermanentLink) {
		entityManager.merge(category);
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