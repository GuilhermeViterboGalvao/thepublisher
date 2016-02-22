package br.com.clubetatame.service.implementation;

import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import com.publisher.entity.Photo;
import com.publisher.service.implementation.PhotoServiceImplementation;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;

import br.com.clubetatame.service.PhotoEventService;

public class PhotoEventServiceImplementation extends PhotoServiceImplementation implements PhotoEventService {

	private static Log log = LogFactory.getLog(PhotoEventServiceImplementation.class);	
	
	@Override
	public List<Photo> listEventsPhotos() {
		return listEventsPhotos(null);
	}

	@Override
	public List<Photo> listEventsPhotos(Boolean published) {
		return listEventsPhotos(published, 0, 0);
	}

	@Override
	public List<Photo> listEventsPhotos(Boolean published, int page, int pageSize) {
		return listEventsPhotos(published, page, pageSize, null, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Photo> listEventsPhotos(Boolean published, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Photo p ");
		sql.append("where p.isEvent=:isEvent ");
		if (published != null) {
			sql.append("p.published=:published ");
		}
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("p." + orderBy + " " + order);	
		} else {
			sql.append("p.id desc");
		}
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("isEvent", true);
        if (published != null) {
        	query.setParameter("published", published);        	
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
	public long countEventsPhotos() {
		return countEventsPhotos(null);
	}

	@Override
	public long countEventsPhotos(Boolean published) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(p) from Photo p ");
		sql.append("where p.isEvent=:isEvent ");
		if (published != null) {
			sql.append("and p.published=:published ");
		}
		Query query = entityManager.createQuery(sql.toString());
		return (Long)query.getSingleResult();
	}

	@Override
	public Collection<Photo> searchEventsPhotos(String query) {
		return searchEventsPhotos(query, 0, 0).getResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Photo> searchEventsPhotos(String query, int page, int pageSize) {
    	long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Photo.class).get();		
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "description", "tags", "credits").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Photo.class);        
        fullTextQuery.setSort(new Sort(new SortField("date", SortField.Type.LONG, true)));
        fullTextQuery.enableFullTextFilter("isEvent").setParameter("isEvent", true);     
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Photo> result = new ResultList<Photo>();
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int) (System.currentTimeMillis() - t));
        log.info("PHOTO-EVENT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}
}