package com.publisher.service.implementation;

import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import com.publisher.entity.Skin;
import com.publisher.service.SkinService;
import com.publisher.utils.ResultList;

public class SkinServiceImplementation extends TransactionalService implements SkinService {

	private static Log log = LogFactory.getLog(SkinServiceImplementation.class);
	
	@Override
	public Skin get(Long id) {
		return id != null ? entityManager.find(Skin.class, id) : null;
	}

	@Override
	public void persist(Skin entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Skin entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Skin entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Skin> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Skin> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(s) from Skin s");
        return (Long)query.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(s.id) from Skin s");
            long total = (Long) query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Skin.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select s from Skin s where s.id>=? and s.id<=? order by s.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
                List<Skin> list = query.getResultList();
                for (Skin skin : list) {                    
                    ft.index(skin);
                    log.info(skin.getId() + ": " + skin.getName());
                }
                ft.flushToIndexes();
                ft.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public Collection<Skin> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")	
	public Collection<Skin> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Skin s ");
		sql.append("order by ");
		if (orderBy != null && !orderBy.equals("")
				&& order != null && !order.equals("")) {
			sql.append("s." + orderBy + " " + order);
		} else {
			sql.append("s.id desc");
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
	public ResultList<Skin> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Skin.class).get();
        org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("name", "path").matching(query).createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Skin.class);
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);
		}
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Skin> result = new ResultList<Skin>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("SKIN SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}