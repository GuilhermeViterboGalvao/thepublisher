package com.publisher.pagecache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.publisher.entity.PermanentLink;
import com.publisher.service.PermanentLinkService;
import com.publisher.utils.ViewsListener;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PageCacheFilter implements Filter {
	
	private static Log log = LogFactory.getLog(PageCacheFilter.class);
	
	private PermanentLinkService permanentLinkService;
	
	private List<String> cacheTypes;
	
	private Cache cache;

	public void init(FilterConfig fConfig) throws ServletException {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		permanentLinkService = (PermanentLinkService)webApplicationContext.getBean("permanentLinkService");
		cache = CacheManager.getInstance().getCache("pageCache");
		String types = fConfig.getInitParameter("cache-types");
		cacheTypes = new ArrayList<String>();
		if (types != null && !types.isEmpty() && types.contains(",")) {			
			for (String type : types.split(",")) {
				cacheTypes.add(type);
			}
		} else {
			cacheTypes.add("article");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		long t = System.currentTimeMillis();
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getServletPath();
		if (uri == null) {
			uri = req.getRequestURI();	
		}
		if (uri != null && !uri.isEmpty()) {
			uri = uri.substring(1);
		} else {
			uri = "";
		}
		if (isIgnoredUri(uri)) {			
			chain.doFilter(request, response);
			log.info("IGNORED EXTENSION - TimeElapsed=" + String.valueOf((int) (System.currentTimeMillis() - t)));
			return;
		}
		PermanentLink permanentLink = null;
		if (permanentLinkService != null) {
			permanentLink = permanentLinkService.get(uri);	
		}
		if (!isCacheable(permanentLink)) {			
			chain.doFilter(request, response);
			log.info("NO CACHEABLE LINK: " + uri + " - TimeElapsed=" + String.valueOf((int) (System.currentTimeMillis() - t)));
			return;
		}
		String queryString = req.getQueryString();
		String key = "";
		if (uri != null && !uri.isEmpty()) {
			key += uri; 
		}
		if (queryString != null && !queryString.isEmpty()) {
			key += "?" + queryString;
		}
		Element element = cache.get(key);
		if (element != null) {
			request.setAttribute("html", element.getObjectValue());
			request.getRequestDispatcher("/pageCache.jsp").forward(request, response);
			checkIfNeedToCount(permanentLink);
			log.info("PAGE " + key + " LOADED FROM CACHE - TimeElapsed=" + String.valueOf((int) (System.currentTimeMillis() - t)));
			return;
		}
		if (response.getCharacterEncoding() == null) {
			response.setCharacterEncoding("UTF-8");
		}
		HttpServletResponseCopier responseCopier = null;
		try {
			responseCopier = new HttpServletResponseCopier((HttpServletResponse)response);
			chain.doFilter(request, responseCopier);
			String content = new String(responseCopier.getCopy(), response.getCharacterEncoding()).trim();
			if (content != null && !content.isEmpty()) {
				cache.put(new Element(key, content));
				log.info("ADD PAGE " + key + " ON CACHE - TimeElapsed=" + String.valueOf((int) (System.currentTimeMillis() - t)));	
			} else {
				log.info("CONTENT IS EMPTY for PAGE " + key);
			}
		} catch(Exception e) {
			log.error(e);
		} finally {
			try {
				if (responseCopier != null) {
					responseCopier.flushBuffer();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
	

	private void checkIfNeedToCount(PermanentLink permanentLink) {
		if (permanentLink.getType() != null && permanentLink.getType().equals("article") && permanentLink.getParam() != null) {
			ViewsListener.getInstace().count(permanentLink.getParam());
		}	
	}

	private boolean isCacheable(PermanentLink permanentLink) {
		boolean isCacheable = false;
		if (permanentLink != null) {
			for (String type : cacheTypes) {
				if (permanentLink.getType().equals(type)) {
					isCacheable = true;
					break;
				}
			}
		}
		return isCacheable;
	}

	private boolean isIgnoredUri(String uri) {
		if (uri.endsWith(".css")
		|| uri.endsWith(".js")
		|| uri.endsWith(".jpg")
		|| uri.endsWith(".jpeg")
		|| uri.endsWith(".png")
		|| uri.endsWith(".gif")
		|| uri.endsWith(".ico")
		|| uri.endsWith(".jsp")
		|| uri.endsWith(".pdf")) {
			return true;
		}
		return false;
	}

	public void destroy() { }
}