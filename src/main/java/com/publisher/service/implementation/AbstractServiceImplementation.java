package com.publisher.service.implementation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import com.publisher.service.Service;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;

public abstract class AbstractServiceImplementation<T> extends TransactionalService implements Service<T> {

	protected static Log log = LogFactory.getLog(AbstractServiceImplementation.class);

	public abstract Class<T> getServiceClass();
	
	@Override
	public T get(Long id) {
		return id != null ? entityManager.find(getServiceClass(), id) : null;
	}
	
	@Override
	public void persist(T entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(T entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(T entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}
	
	@Override
	public Collection<T> list() {
		return list(0, 0);
	}
	
	public Collection<T> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<T> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from " + getServiceClass().getSimpleName() + " a ");
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("a." + orderBy + " " + order);	
		} else {
			sql.append("a.id desc");
		}
        Query query = entityManager.createQuery(sql.toString());
        if (pageSize > 0) {
        	query.setMaxResults(pageSize);	
        }
        if (pageSize > 0 && page > 0) {
        	query.setFirstResult((page - 1) * pageSize);
        }        
        return query.getResultList();
	}

	@Override
	public Collection<T> search(String query) {
		return search(query, 0, 0).getResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<T> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
        ResultList<T> result = null;
        if (getServiceClass().isAnnotationPresent(org.hibernate.search.annotations.Indexed.class)) {
            Field[] entityFields = getServiceClass().getDeclaredFields();
            if (entityFields != null && entityFields.length > 0) {
            	List<String> fields = new ArrayList<String>();
            	for (Field entityField : entityFields) {
            		if (entityField.isAnnotationPresent(org.hibernate.search.annotations.Field.class)) {
            			fields.add(entityField.getName());	
            		}            		
				}            	
            	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
        		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(getServiceClass()).get();
        		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, fields.toArray(new String[fields.size()])).createQuery();
                FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, getServiceClass());        
                fullTextQuery.setHint("org.hibernate.cacheable", true);
                result = new ResultList<T>();
                result.setResult(fullTextQuery.getResultList());
                result.setResultSize(fullTextQuery.getResultSize());
                result.setTimeElapsed((int)(System.currentTimeMillis() - t));
                result.setPage(page);
                result.setPageSize(pageSize);
                log.info(getServiceClass().getSimpleName() + " SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
            } else {
            	log.info("On entity " + getServiceClass().getSimpleName() + " wasn't found any annotation 'org.hibernate.search.annotations.Field'.");	
            }
        } else {
        	log.info("Entity " + getServiceClass().getSimpleName() + " isn't annotated with 'org.hibernate.search.annotations.Indexed'.");
        }
        return result;
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(a) from " + getServiceClass().getSimpleName() + " a");
        return (Long)query.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(a.id) from " + getServiceClass().getSimpleName() + " a");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(getServiceClass());
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select a from " + getServiceClass().getSimpleName() + " a where a.id>=? and a.id<=? order by a.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<T> list = query.getResultList();
                for (T entity : list) {                	
                    ft.index(entity);
                    log.info(entity.toString());
                }
                ft.flushToIndexes();
                ft.clear();
            }
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
	}
}