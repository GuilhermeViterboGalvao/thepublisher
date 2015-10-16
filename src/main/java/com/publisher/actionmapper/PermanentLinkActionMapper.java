package com.publisher.actionmapper;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.publisher.service.PermanentLinkService;

public class PermanentLinkActionMapper implements ActionMapper, ApplicationContextAware {

	private static Log log = LogFactory.getLog(PermanentLinkActionMapper.class);
	
	private LinkedHashMap<String, String> hosts;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		if (ctx instanceof WebApplicationContext) {
			WebApplicationContext webContext = (WebApplicationContext)ctx;
			Properties properties = new Properties();
			String realPath = webContext.getServletContext().getRealPath(File.separator);
			realPath = !realPath.endsWith(File.separator)   ? realPath + File.separator : realPath;
			realPath = !realPath.startsWith(File.separator) ? File.separator + realPath : realPath;
			try {
				properties.load(new FileInputStream(realPath + "WEB-INF/system-hosts.properties"));				
				Iterator<Object> keyIterator = properties.keySet().iterator();				
				hosts = new LinkedHashMap<String, String>();				
				while (keyIterator.hasNext()) {
					String key = (String)keyIterator.next();					
					String value = properties.getProperty(key);
					if (value != null && !value.isEmpty()) {
						hosts.put(key, value);
						log.info("PermanentLinkActionMapper: host " + key + " points to the Page.id=" + value);
					}
				}				
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}			
		}
	}
	
	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}
	
	private ActionMapper backupMapper;
	
    public void setBackupMapper(ActionMapper backupMapper) {
		this.backupMapper = backupMapper;
	}
	
    public String getUriFromActionMapping(ActionMapping mapping) {
        if (backupMapper != null) {
        	return backupMapper.getUriFromActionMapping(mapping);
        }        	
        return null;
    }

    public ActionMapping getMappingFromActionName(String actionName) {
        if (backupMapper != null) {
        	return backupMapper.getMappingFromActionName(actionName);
        }        	
        return null;
    }

    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager config) {
        String uri = request.getServletPath();
        if (uri == null) {
            uri = request.getRequestURI();
            uri = uri.substring(request.getContextPath().length());
        }        
        if (request.getRequestURI().equals("/") && hosts != null && hosts.size() > 0) {        	
        	String url = request.getServerName().trim();
        	url = url.replace("http://",  "");
        	url = url.replace("https://", "");        	
        	String value = hosts.get(url);        	
        	if (value != null && !value.isEmpty()) {
        		String action = "";
        		Integer id = 0;
        		try {
					String[] values = value.split("-");
					action = values[0];
					id = Integer.parseInt(values[1]);
				} catch (Exception e) { 
					action = "";
					id = 0;
				}
        		if (action != null && !action.isEmpty() && id > 0) {
                	HashMap<String, Object> params = new HashMap<String, Object>();
                	params.put("id", id);
                	return new ActionMapping(action, "/", "execute", params);	
        		}        		
        	}       	
        }
        String includeUri = (String)request.getAttribute("javax.servlet.include.servlet_path");
        if (includeUri != null) {
            uri = includeUri;
        }        
        ActionMapping mapping = permanentLinkService.getActionMapping(uri);        
        log.info(uri + " " + (mapping == null ? "mapping not found" : "namespace: " + mapping.getName() + "name: " + mapping.getName()));
        if (mapping == null && backupMapper != null) {
        	mapping = backupMapper.getMapping(request, config);
        }        	
        return mapping;
    }
}