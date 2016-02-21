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
import br.com.clubetatame.entity.CompanyContract;
import br.com.clubetatame.service.CompanyContractService;

public class CompanyContractServiceImplementation extends TransactionalService implements CompanyContractService {

	private static Log log = LogFactory.getLog(CompanyContractServiceImplementation.class);
	
	@Override
	public CompanyContract get(Long id) {
		return id != null ? entityManager.find(CompanyContract.class, id) : null;
	}

	@Override
	public void persist(CompanyContract entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(CompanyContract entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(CompanyContract entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<CompanyContract> list() {
		return list(0, 0);
	}

	@Override
	public Collection<CompanyContract> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(c) from CompanyContract c");
        return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(c.id) from CompanyContract c");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(CompanyContract.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select c from CompanyContract c where c.id>=? and c.id<=? order by c.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<CompanyContract> list = query.getResultList();
                for (CompanyContract contract : list) {                	
                    ft.index(contract);
                    log.info(contract.getId() + ": " + contract.getName());
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
	public Collection<CompanyContract> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}

	@Override
	public Collection<CompanyContract> list(int page, int pageSize, String orderBy, String order) {
		return list(page, pageSize, orderBy, order, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<CompanyContract> list(int page, int pageSize, String orderBy, String order, Date end) {
		StringBuilder sql = new StringBuilder();
		sql.append("from CompanyContract c ");
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
	public ResultList<CompanyContract> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(CompanyContract.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "description").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, CompanyContract.class);        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<CompanyContract> result = new ResultList<CompanyContract>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("COMPANY_CONTRACT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}