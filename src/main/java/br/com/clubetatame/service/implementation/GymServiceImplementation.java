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

import com.publisher.entity.PermanentLink;
import com.publisher.service.implementation.TransactionalService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class GymServiceImplementation extends TransactionalService implements GymService {

	private static Log log = LogFactory.getLog(GymServiceImplementation.class);
	
	@Override
	public Gym get(Long id) {
		return id != null ? entityManager.find(Gym.class, id) : null;
	}
	
	@Override
	public void persist(Gym entity) {
		if (entity != null) {
			entityManager.persist(entity);
			if(entity.getPermanentLink() != null){
				entityManager.flush();
				entity.getPermanentLink().setParam(entity.getId());
				entityManager.merge(entity.getPermanentLink());
				entityManager.flush();
			}
		}
	}

	@Override
	public void update(Gym entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}
	
	@Override
	public void update(Gym entity, PermanentLink oldPermanentLink) {
		entityManager.merge(entity);
		cleanCache(oldPermanentLink);
	}

	@Override
	public void delete(Gym entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Gym> list() {
		return list(null);
	}
	
	@Override
	public long count(){
		return count(null);
	}

	@Override
	public Collection<Gym> search(String query) {
		return search(query, 0, 0).getResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(g.id) from Gym g");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Gym.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select g from Gym g where g.id>=? and g.id<=? order by g.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Gym> list = query.getResultList();
                for (Gym gym : list) {                	
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
	public Gym authenticate(String email, String password) {
        Gym gym = null;
        Query query = entityManager.createQuery("from Gym where email=:email").setParameter("email", email);
        if (query != null) {
            List<Gym> result = query.getResultList();
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
	public long count(Boolean active){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(g) from Gym g");
        
        if (active != null) {
			sql.append("where g.active=:active ");
		}
        Query query = entityManager.createQuery(sql.toString());
        if (active != null) {
        	query.setParameter("active", active);
        }
        query.setHint("org.hibernate.cacheable", true);
        return (Long) query.getSingleResult();
	}
	
	@Override
	public Collection<Gym> list(Boolean active) {
		return list(active, 0, 0);
	}

	@Override
	public Collection<Gym> list(Boolean active, int page, int pageSize) {
		return list(active, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Gym> list(Boolean active, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Gym g");
		if (active != null) {
			sql.append(" where g.active=:active");
		}
		sql.append(" order by");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append(" g." + orderBy + " " + order);	
		} else {
			sql.append(" g.id desc");
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
	public Collection<Gym> list(float lat, float lon, float distanceInKM) {
		return list(lat, lon, distanceInKM, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Gym> list(float lat, float lon, float distanceInKM, Boolean active) {
		StringBuilder sql = new StringBuilder();
        sql.append("select g from Gym g ");
        List<Float> area = null;
        if (distanceInKM > 0.0f) {
            area = this.getArea(lat, lon, distanceInKM);
            sql.append("where ");
            sql.append("g.lat>=:lat1 ");
            sql.append("and ");
            sql.append("g.lat<=:lat2 ");
            sql.append("and ");
            sql.append("g.lon<=:lon2 ");
            sql.append("and ");
            sql.append("g.lon>=:lon1 ");
        }
        if (area != null && active != null) {
            sql.append("and ");
            sql.append("g.active=:active ");
        } else if (area == null && active != null) {
            sql.append("where ");
            sql.append("g.active=:active ");
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
	public ResultList<Gym> search(String query, int page, int pageSize) {
        return search(query, page, pageSize, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Gym> search(String query, int page, int pageSize, Boolean active) {
		long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Gym.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "contact", "document", "email", "phone", "address", "cep").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Gym.class); 
        if (active != null) fullTextQuery.enableFullTextFilter("activeGym").setParameter("isActive", active);
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Gym> result = new ResultList<Gym>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("GYM SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
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