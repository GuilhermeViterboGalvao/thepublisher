package com.publisher.service.implementation;

import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.service.CategoryService;
import com.publisher.utils.ResultList;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CategoryServiceImplementation extends TransactionalService implements CategoryService {
	
	private static Log log = LogFactory.getLog(CategoryServiceImplementation.class);
	
	@Override
	public Category get(Long id) {
		return id != null ? entityManager.find(Category.class, id) : null;
	}

	@Override
	public void persist(Category entity) {
		if (entity != null) {
			entityManager.persist(entity);
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
	public void delete(Category entity) {
		if (entity != null) {
			entityManager.merge(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Category> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Category> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(c) from Category c");
        return (Long)query.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(c.id) from Category c");
            long total = (Long) query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Category.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select c from Category c where c.id>=? and c.id<=? order by c.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);				
				List<Category> list = query.getResultList();
                for (Category category : list) {
                    ft.index(category);
                    log.info(category.getId() + ": " + category.getName());
                }
                ft.flushToIndexes();
                ft.clear();
            }
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
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

	@Override
	public Collection<Category> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Category> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Category c ");
		sql.append("order by ");
		if (orderBy != null && !orderBy.equals("")
				&& order != null && !order.equals("")) {
			sql.append("c." + orderBy + " " + order);
		} else {
			sql.append("c.id desc");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (pageSize > 0) {
			query.setMaxResults(pageSize);	
		}
		if (page > 0 && pageSize > 0) {
			query.setFirstResult((page - 1) * pageSize);	
		}
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Category> search(String query, int page, int pageSize) {
    	long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Category.class).get();
		String[] matches = query != null ? query.split(" ") : new String[1];		
		BooleanJunction<?> junction = qb.bool();
		for (String match : matches) {
			junction.must(qb.keyword().onFields("name").matching(match).createQuery());
		}
        org.apache.lucene.search.Query luceneQuery = junction.createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Category.class);        
        if (page > 0 && pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Category> result = new ResultList<Category>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int) (System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("CATEGORY SEARCH=[" + luceneQuery.toString() + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}