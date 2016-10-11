package br.com.clubetatame.service.implementation;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import com.publisher.entity.PermanentLink;
import com.publisher.service.implementation.AbstractServiceImplementation;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.Event;
import br.com.clubetatame.service.EventService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EventServiceImplementation extends AbstractServiceImplementation<Event> implements EventService {

	private static Log log = LogFactory.getLog(EventServiceImplementation.class);

	@Override
	public Class<Event> getServiceClass() {
		return Event.class;
	}

	@Override
	public void update(Event entity, PermanentLink oldPermanentLink) {
		entityManager.merge(entity);
		cleanCache(oldPermanentLink);
	}

	@Override
	@SuppressWarnings("unchecked")	
	public ResultList<Event> search(String query, int page, int pageSize, Boolean isActive) {
		long t = System.currentTimeMillis();
		FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Event.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "contact", "state", "city", "address").createQuery();
		FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Event.class);
		if (pageSize > 0) {
			fullTextQuery.setMaxResults(pageSize);
		}
		if (page > 0 && pageSize > 0) {        	
			fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}        
		if (isActive != null) {
			fullTextQuery.enableFullTextFilter("activeEvent").setParameter("isActive", isActive);
		}        
		fullTextQuery.setHint("org.hibernate.cacheable", true);
		ResultList<Event> result = new ResultList<Event>();
		result.setResult(fullTextQuery.getResultList());
		result.setResultSize(fullTextQuery.getResultSize());
		result.setTimeElapsed((int)(System.currentTimeMillis() - t));
		result.setPage(page);
		result.setPageSize(pageSize);
		log.info("EVENT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
		return result;		
	}
	
	public List<Event> list() {
		return list(null);
	}

	@Override
	public List<Event> list(Boolean isActive) {
		return list(isActive, 0, 0);
	}

	@Override
	public List<Event> list(Boolean isActive, int page, int pageSize) {
		return list(isActive, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> list(Boolean isActive, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Event e ");
		if (isActive != null) {
			sql.append("where e.active=:isActive ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("e." + orderBy + " " + order);	
		} else {
			sql.append("e.id desc");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (isActive != null) {
			query.setParameter("isActive", isActive);
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
	public List<Event> listByCompany(Company company) {
		return listByCompany(company, null);
	}

	@Override
	public List<Event> listByCompany(Company company, Boolean isActive) {
		return listByCompany(company, isActive, 0, 0);
	}

	@Override
	public List<Event> listByCompany(Company company, Boolean isActive, int page, int pageSize) {
		return listByCompany(company, isActive, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> listByCompany(Company company, Boolean isActive, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Event e ");
		if (company != null) {
			sql.append("where e.company=:company ");
		}
		if (company != null && isActive != null) {
			sql.append("and e.active=:isActive ");			
		} else if (company == null && isActive != null) {
			sql.append("where e.active=:isActive ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("e." + orderBy + " " + order);	
		} else {
			sql.append("e.id desc");
		}		
		Query query = entityManager.createQuery(sql.toString());
		if (company != null) {
			query.setParameter("company", company);
		}        
		if (isActive != null) {
			query.setParameter("isActive", isActive);
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
	public List<Event> listByDate(Date start) {
		return listByDate(start, null);
	}

	@Override
	public List<Event> listByDate(Date start, Date end) {
		return listByDate(start, end, null);
	}

	@Override
	public List<Event> listByDate(Date start, Date end, Boolean isActive) {
		return listByDate(start, end, isActive, 0, 0);
	}

	@Override
	public List<Event> listByDate(Date start, Date end, Boolean isActive, int page, int pageSize) {
		return listByDate(start, end, isActive, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> listByDate(Date start, Date end, Boolean isActive, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Event e ");
		if (start != null) {
			sql.append("where e.start>=start ");
		}
		if (start != null && end != null) {
			sql.append("and e.end<=end ");
		} else if (start == null && end != null) {
			sql.append("where e.end<=end ");
		}
		if ((start != null || end != null) && isActive != null) {
			sql.append("and e.active=:isActive ");
		} else if (start == null && end == null && isActive != null) {
			sql.append("where e.active=:isActive ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("e." + orderBy + " " + order);	
		} else {
			sql.append("e.id desc");
		}		
		Query query = entityManager.createQuery(sql.toString());
		if (start != null) {
			query.setParameter("start", start);
		}
		if (end != null) {
			query.setParameter("end", end);
		}                
		if (isActive != null) {
			query.setParameter("isActive", isActive);
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
	public long count(Boolean isActive) {
		return countByCompany(null, isActive);
	}

	@Override
	public long countByCompany(Company company) {
		return countByCompany(company, null);
	}

	@Override
	public long countByCompany(Company company, Boolean isActive) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(e) from Event e ");
		if (company != null) {
			sql.append("where e.company=:company ");
		}
		if (company != null && isActive != null) {
			sql.append("and e.active=:isActive ");
		} else if (company == null && isActive != null) {
			sql.append("where e.active=:isActive ");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (company != null) {
			query.setParameter("company", company);
		}
		if (isActive != null) {
			query.setParameter("isActive", isActive);
		}
		return (Long)query.getSingleResult();
	}

	@Override
	public long countByDate(Date start) {
		return countByDate(start, null);
	}

	@Override
	public long countByDate(Date start, Date end) {
		return countByDate(start, end, null);
	}

	@Override
	public long countByDate(Date start, Date end, Boolean isActive) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(e) from Event e ");
		if (start != null) {
			sql.append("where e.start>=start ");
		}
		if (start != null && end != null) {
			sql.append("and e.end<=end ");
		} else if (start == null && end != null) {
			sql.append("where e.end<=end ");
		}
		if ((start != null || end != null) && isActive != null) {
			sql.append("and e.active=:isActive ");
		} else if (start == null && end == null && isActive != null) {
			sql.append("where e.active=:isActive ");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (start != null) {
			query.setParameter("start", start);
		}
		if (end != null) {
			query.setParameter("end", end);
		}		
		if (isActive != null) {
			query.setParameter("isActive", isActive);
		}
		return (Long)query.getSingleResult();
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