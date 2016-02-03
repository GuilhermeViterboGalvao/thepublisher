package br.com.clubetatame.service.implementation;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import com.publisher.entity.Account;
import com.publisher.service.implementation.TransactionalService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.service.CompanyService;

public class CompanyServiceImplementation extends TransactionalService implements CompanyService {

	private static Log log = LogFactory.getLog(CompanyServiceImplementation.class);
	
	@Override
	public Company get(Long id) {
		return id != null ? entityManager.find(Company.class, id) : null;
	}

	@Override
	public void persist(Company entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Company entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Company entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Company> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Company> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(c) from Company c");
        return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(c.id) from Company c");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Company.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select c from Company g where c.id>=? and c.id<=? order by c.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Company> list = query.getResultList();
                for (Company gym : list) {                	
                    ft.index(gym);
                    log.info(gym.getId() + ": " + gym.getName());
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
	public Company authenticate(String email, String password) {
        Company gym = null;
        Query query = entityManager.createQuery("from Company where email=:email").setParameter("email", email);
        if (query != null) {
            List<Company> result = query.getResultList();
            if (result != null && !result.isEmpty()) {
            	gym = result.iterator().next();
                if (!gym.getHash().equals(hash(password))) {
                	gym = null;
                }
            }        	
        }
        return gym;
	}

	@Override
	public Collection<Company> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Company> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Company c ");
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
	public Collection<Company> list(float lat, float lon, float distanceInKM) {
		return list(lat, lon, distanceInKM, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Company> list(float lat, float lon, float distanceInKM, Boolean active) {
		StringBuilder sql = new StringBuilder();
        sql.append("select c from Company c ");
        List<Float> area = null;
        if (distanceInKM > 0.0f) {
            area = this.getArea(lat, lon, distanceInKM);
            sql.append("where ");
            sql.append("c.lat>=:lat1 ");
            sql.append("and ");
            sql.append("c.lat<=:lat2 ");
            sql.append("and ");
            sql.append("c.lon<=:lon2 ");
            sql.append("and ");
            sql.append("c.lon>=:lon1 ");
        }
        if (area != null && active != null) {
            sql.append("and ");
            sql.append("c.active=:active ");
        } else if (area == null && active != null) {
            sql.append("where ");
            sql.append("c.active=:active ");
        }
        Query query = entityManager.createQuery(sql.toString());
        if (area != null) {
            query.setParameter("lat1", area.get(0));
            query.setParameter("lon1", area.get(1));
            query.setParameter("lat2", area.get(2));
            query.setParameter("lon2", area.get(3));
        }
        if (active != null) {
            query.setParameter("active", active);
        }
        query.setHint("org.hibernate.cacheable", true);
        return query.getResultList();
	}
	
	 private List<Float> getArea(float lat, float lon, float distanceInKM) {
        int earthInKM = 40075;
        float oneKMinGrau = 360.0f / earthInKM;
        float distance = distanceInKM * oneKMinGrau;
        float lat1 = lat - distance;
        float lon1 = lon - distance;
        float lat2 = lat + distance;
        float lon2 = lon + distance;
        ArrayList<Float> result = new ArrayList<Float>(4);
        result.add(Float.valueOf(lat1));
        result.add(Float.valueOf(lon1));
        result.add(Float.valueOf(lat2));
        result.add(Float.valueOf(lon2));
        return result;
    }
	
	@Override
	public ResultList<Company> search(String query, int page, int pageSize) {
        return search(query, page, pageSize, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Company> search(String query, int page, int pageSize, Boolean active) {
		long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Account.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "contact", "document", "email", "phone", "address", "cep").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Company.class); 
        if (active != null) fullTextQuery.enableFullTextFilter("activeCompany").setParameter("isActive", active);
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Company> result = new ResultList<Company>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("MEMBER SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}

	@Override
	public String hash(String password) {
        String newPassword = "ThePublisher" + password;
        String hash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(newPassword.getBytes(), 0, newPassword.length());
            hash = new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return hash;
	}
}