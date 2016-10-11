package com.publisher.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import com.publisher.entity.PermanentLink;
import com.publisher.service.PermanentLinkService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PermanentLinkServiceImplementation extends AbstractServiceImplementation<PermanentLink> implements PermanentLinkService {

	private static Log log = LogFactory.getLog(PermanentLinkServiceImplementation.class);

	@Override
	public Class<PermanentLink> getServiceClass() {
		return PermanentLink.class;
	}

	private static Object notPermanent = new Object();

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