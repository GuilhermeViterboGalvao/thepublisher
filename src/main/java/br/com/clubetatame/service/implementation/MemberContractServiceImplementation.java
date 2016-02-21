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
import br.com.clubetatame.entity.MemberContract;
import br.com.clubetatame.service.MemberContractService;

public class MemberContractServiceImplementation extends TransactionalService implements MemberContractService {

	private static Log log = LogFactory.getLog(MemberContractServiceImplementation.class);
	
	@Override
	public MemberContract get(Long id) {
		return id != null ? entityManager.find(MemberContract.class, id) : null;
	}

	@Override
	public void persist(MemberContract entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(MemberContract entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(MemberContract entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<MemberContract> list() {
		return list(0, 0);
	}

	@Override
	public Collection<MemberContract> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(c) from MemberContract c");
        return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(c.id) from MemberContract c");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(MemberContract.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select c from MemberContract c where c.id>=? and c.id<=? order by c.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<MemberContract> list = query.getResultList();
                for (MemberContract product : list) {                	
                    ft.index(product);
                    log.info(product.getId() + ": " + product.getName());
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
	public Collection<MemberContract> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}

	@Override
	public Collection<MemberContract> list(int page, int pageSize, String orderBy, String order) {
		return list(page, pageSize, orderBy, order, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<MemberContract> list(int page, int pageSize, String orderBy, String order, Date end) {
		StringBuilder sql = new StringBuilder();
		sql.append("from MemberContract c ");
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
	public ResultList<MemberContract> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(MemberContract.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "description").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, MemberContract.class);        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<MemberContract> result = new ResultList<MemberContract>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("MEMBER_CONTRACT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}