package br.com.clubetatame.service.implementation;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import com.publisher.service.implementation.TransactionalService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;
import br.com.clubetatame.service.ContractService;


public class ContractServiceImplementation<E> extends TransactionalService implements ContractService<E> {

	private static Log log = LogFactory.getLog(ContractServiceImplementation.class);
	
	private String entityName;
	
	private Class<E> genericClass;

	@Override
	public void setEntityName(String entityName){
		this.entityName = entityName;
	}
	
	@Override
	public void setGenericClass(Class<E> genericClass){
		this.genericClass = genericClass;
	}
	
	@Override
	public E get(Long id) {
		return id != null ? entityManager.find(genericClass, id) : null;
	}

	@Override
	public void persist(E entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(E entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(E entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<E> list() {
		return list(0, 0);
	}

	@Override
	public Collection<E> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery(String.format("select count(c) from %s c", entityName));
        return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery(String.format("select max(c.id) from %s c", entityName));
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(genericClass);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery(String.format("select c from %s c where c.id>=? and c.id<=? order by c.id", entityName));
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<E> list = query.getResultList();
                for (E contract : list) {                	
                    ft.index(contract);
                    log.info(String.format("INDEXED-OBJECT: %s", contract));
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
	public Collection<E> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}

	@Override
	public Collection<E> list(int page, int pageSize, String orderBy, String order) {
		return list(page, pageSize, orderBy, order, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<E> list(int page, int pageSize, String orderBy, String order, Date end) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("from %s c ", entityName));
		if (end != null) {
			sql.append("where c.end >=:end ");			
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("c." + orderBy + " " + order);	
		} else {
			sql.append("c.id desc");
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
	@SuppressWarnings("unchecked")
	public ResultList<E> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(genericClass).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "description").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, genericClass);
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<E> result = new ResultList<E>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("CONTRACT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}