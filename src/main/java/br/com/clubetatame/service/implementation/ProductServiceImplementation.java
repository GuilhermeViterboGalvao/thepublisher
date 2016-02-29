package br.com.clubetatame.service.implementation;

import java.util.Collection;
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
import br.com.clubetatame.entity.Product;
import br.com.clubetatame.service.ProductService;

public class ProductServiceImplementation extends TransactionalService implements ProductService {

	private static Log log = LogFactory.getLog(ProductServiceImplementation.class);
	
	@Override
	public Product get(Long id) {
		return id != null ? entityManager.find(Product.class, id) : null;
	}

	@Override
	public void persist(Product entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Product entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Product entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Product> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Product> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(p) from Product p");
        return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(p.id) from Product p");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Product.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select p from Product p where p.id>=? and p.id<=? order by p.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Product> list = query.getResultList();
                for (Product product : list) {                	
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
	public Collection<Product> list(int page, int pageSize) {
		return list(null, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Product> list(Boolean active, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Product p ");
		if (active != null) {
			sql.append("where p.active=:active ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("p." + orderBy + " " + order);	
		} else {
			sql.append("p.id desc");
		}
        Query query = entityManager.createQuery(sql.toString());
        if (active != null) {
        	query.setParameter("active", active);
        }
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
	public ResultList<Product> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "description").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Product.class);
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Product> result = new ResultList<Product>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("PRODUCT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}