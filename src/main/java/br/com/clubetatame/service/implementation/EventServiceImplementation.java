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
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.Event;
import br.com.clubetatame.service.EventService;

public class EventServiceImplementation extends TransactionalService implements EventService {

	private static Log log = LogFactory.getLog(EventServiceImplementation.class);
	
	@Override
	public Event get(Long id) {
		return id != null ? entityManager.find(Event.class, id) : null;
	}

	@Override
	public void persist(Event entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Event entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Event entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public Collection<Event> search(String query) {
		return search(query, 0, 0).getResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Event> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Event.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "contact", "state", "city", "address").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Event.class);        
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

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(e) from Event e");
        return query != null ? (Long)query.getSingleResult() : 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(e.id) from Event e");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Event.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select e from Event e where e.id>=? and e.id<=? order by e.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Event> list = query.getResultList();
                for (Event event : list) {                	
                    ft.index(event);
                    log.info(event.getId() + ": " + event.getName());
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
			sql.append("where e.isActive:=isActive ");
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
			sql.append("where e.company:=company ");
		}
		if (company != null && isActive != null) {
			sql.append("and e.isActive:=isActive ");			
		} else if (company == null && isActive != null) {
			sql.append("where e.isActive:=isActive ");
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
			sql.append("and e.isActive:=isActive ");
		} else if (start == null && end == null && isActive != null) {
			sql.append("where e.isActive:=isActive ");
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
	public int count(Boolean isActive) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countByCompany(Company company) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countByCompany(Company company, Boolean isActive) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countByDate(Date Start) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countByDate(Date Start, Date end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countByDate(Date Start, Date end, Boolean isActive) {
		// TODO Auto-generated method stub
		return 0;
	}

}
