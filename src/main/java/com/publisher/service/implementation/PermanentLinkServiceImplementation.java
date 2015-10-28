package com.publisher.service.implementation;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import com.publisher.entity.Page;
import com.publisher.entity.PermanentLink;
import com.publisher.service.PermanentLinkService;
import com.publisher.utils.HibernateSearchUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PermanentLinkServiceImplementation extends TransactionalService implements PermanentLinkService {

	private static Log log = LogFactory.getLog(PermanentLinkServiceImplementation.class);
	
	private static Object notPermanent = new Object();
	
	@Override
	public PermanentLink get(Long id) {
		return id != null ? entityManager.find(PermanentLink.class, id) : null;
	}

	@Override
	public void persist(PermanentLink entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(PermanentLink entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(PermanentLink entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<PermanentLink> list() {
		Query query = entityManager.createQuery("from PermanentLink");
		return query != null ? query.getResultList() : null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<PermanentLink> search(String query) {
    	long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(PermanentLink.class).get();
        org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "uri", "type").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, PermanentLink.class);
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        log.info("PAGE SEARCH=[" + luceneQuery + "] - TimeElapsed=" + (int)(System.currentTimeMillis() - t));
        return fullTextQuery.getResultList();
	}

	@Override
	public long count() {
		Query query = entityManager.createQuery("select count(pl) from PermanentLink pl");
		return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(pl.id) from PermanentLink pl");
            long total = (Long) query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Page.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select pl from PermanentLink pl where pl.id>=? and pl.id<=? order by pl.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);				
				List<PermanentLink> list = query.getResultList();
                for (PermanentLink permanentLink : list) {
                    ft.index(permanentLink);
                    log.info(permanentLink.getId() + ": " + permanentLink.getUri());
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
	public PermanentLink get(String uri) {
		if(uri == null || uri.isEmpty()) {
			return null;
		}
		PermanentLink result = null;
		try {
			List<PermanentLink> list = entityManager.createQuery("from PermanentLink where uri=:uri").setParameter("uri", uri).getResultList();
			Object object = null;
			if (list != null && list.size() > 0) {
				object = list.get(0);
			}
			if (object != null) {
				result = (PermanentLink)object;			
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")	
	public PermanentLink getPermanentLink(String uri) {
		Cache cache = CacheManager.getInstance().getCache("permanentLinks");
    	Element element = cache.get(uri);
    	PermanentLink link = null;
    	if (element != null) {
    		if (element.getObjectValue() == notPermanent) {
    			return null;
    		}
    		return (PermanentLink)element.getObjectValue();
    	}
        Query query = entityManager.createQuery("from PermanentLink where uri=:uri").setParameter("uri", uri.substring(1));
		List<PermanentLink> result = query.getResultList();    	
        if (result == null || result.isEmpty()) {
        	link = null;
        	cache.put(new Element(uri, notPermanent));
        	log.info("Caching not permanentLink " + uri);
        } else {
        	link = (PermanentLink)result.get(0);
        	cache.put(new Element(uri, link));
        	log.info("Caching permanentLink " + uri);
        }
        return link;
	}

	@Override
	public ActionMapping getActionMapping(String uri) {
    	PermanentLink link = getPermanentLink(uri);
    	if (link == null) {
    		return null;
    	}
        Map<String,Object> params = new HashMap<String, Object>();
        if (link.getParam() != null) {
        	params.put("id", link.getParam());
        }
		return new ActionMapping(link.getType(), "/", "execute", params);
	}

	@Override
	public void change(PermanentLink oldPermanentLink, PermanentLink newPermanentLink) {
		oldPermanentLink.setType("redirect");
		oldPermanentLink.setParam(newPermanentLink.getId());
		oldPermanentLink.setMoved(new Date());
		entityManager.merge(oldPermanentLink);
	}

	@Override
	public void removeFromCacheIfIsNotPermanent(String uri) {
		Cache cache = CacheManager.getInstance().getCache("permanentLinks");
    	final String key = "/" + uri;
    	Element element = cache.get(key);
    	if (element != null && element.getObjectValue().equals(notPermanent)){
    		cache.remove(key);
    	}
	}
}