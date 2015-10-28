package com.publisher.service.implementation;

import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import com.publisher.entity.Page;
import com.publisher.entity.PermanentLink;
import com.publisher.service.PageService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class PageServiceImplementation extends TransactionalService implements PageService {

	private static Log log = LogFactory.getLog(PageServiceImplementation.class);

	@Override
	public Page get(Long id) {
		return id != null ? entityManager.find(Page.class, id) : null;
	}

	@Override
	public void persist(Page entity) {
		if (entity != null) {
			entityManager.persist(entity);
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
	public void delete(Page entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Page> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Page> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(p) from Page p");
        return (Long)query.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(p.id) from Page p");
            long total = (Long) query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Page.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select p from Page p where p.id>=? and p.id<=? order by p.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);				
				List<Page> list = query.getResultList();
                for (Page page : list) {
                    ft.index(page);
                    log.info(page.getId() + ": " + page.getName());
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

	@Override
	public Collection<Page> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Page> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Page p ");
		sql.append("order by ");
		if (orderBy != null && !orderBy.equals("")
				&& order != null && !order.equals("")) {
			sql.append("p." + orderBy + " " + order);
		} else {
			sql.append("p.id desc");
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
	public ResultList<Page> search(String query, int page, int pageSize) {
    	long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Page.class).get();
        org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "contentFile").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Page.class);
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Page> result = new ResultList<Page>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int) (System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("PAGE SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}