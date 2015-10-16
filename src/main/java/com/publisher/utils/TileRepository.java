package com.publisher.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

public class TileRepository {
	
	private static Log log = LogFactory.getLog(TileRepository.class);
	
	private static final Monitor monitor = new Monitor();
	
	private static TileRepository instance;
	
	private final Cache cache;
	
	private final File dataFolder;
	
	private final File viewFolder;	
	
	private final GroovyScriptEngine groovyEngine;
	
	public static TileRepository getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (TileRepository.class) {
			if (instance == null) {
				try {
					TileRepository repository = new TileRepository();
					instance = repository;
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return instance;
	}
	
	private TileRepository() throws IOException {
		CacheManager manager = CacheManager.getInstance();
		cache = manager.getCache("tile");
		dataFolder = new File((String)ServletActionContext.getServletContext().getAttribute("data-folder"));
		viewFolder = new File(ServletActionContext.getServletContext().getRealPath("/xml/layouts"));		
		groovyEngine = new GroovyScriptEngine((new File((String)ServletActionContext.getServletContext().getAttribute("data-folder"))).getAbsolutePath());
	}
	
	public void clear(String path) {
		log.info("cleaning cache " + path);
		Attribute<String> m = cache.getSearchAttribute("xml");
		String regex = path.replaceAll("([\\\\*+\\[\\](){}\\$.?\\^|])", "\\\\$1")+"*";
		log.info("Clean cache for tile matching " + regex);
		Results results = cache.createQuery().includeKeys().addCriteria(m.ilike(regex)).execute();
		for (Result result : results.all()) {			
			cache.remove(result.getKey());
			log.info("Removing from cache " + result.getKey());
		}
		results.discard();
	}
	
	public String get(String path) {
		return get(path,path);
	}
	
	public  String get(String dataPath, String viewPath) {
		String key = dataPath + ":" + viewPath;
		log.debug("key:" + key);
		Element element = cache.get(key);
		if (element != null) {
			log.debug("get from cache " + key);
			return getContent(element.getObjectValue());
		}
		Object content = null;
		synchronized(monitor.get(key)) {
			element = cache.get(key);
			if (element != null) {
				return getContent(element.getObjectValue());
			}
			long t = System.currentTimeMillis();
			content =  createContent(dataPath, viewPath);
			log.info("create " + key + " in " + (System.currentTimeMillis() - t) + " ms");
			if (content == null) {
				element = new Element(key, "null");
			}
			element = new Element(key, content);			
			cache.put(element);
			log.debug("added to cache " + key);
		}
		return getContent(content);
	}
	
	@SuppressWarnings("unchecked")
	private String getContent(Object object) {
		if (object == null) {
			return "";
		}
		if (object instanceof String) {
			return (String)object;
		}
		if (object instanceof List) {
			try {
				List<String> list = (List<String>)object;
				if (list.size() == 0) {
					return "";
				}
				return list.get(((int)Math.floor((Math.random() * list.size()))));
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		return "";
	}
	
	private Object createContent(String dataPath, String viewPath) {
		Object content = null;		
		File xmlFile = new File(dataFolder, dataPath + ".xml");		
		File groovyFile = new File(dataFolder, dataPath + ".groovy");
		File templateFile = new File(viewFolder, viewPath + ".template.html");		
		if (groovyFile.exists() && templateFile.exists()) {
			content = groovyTemplateTransform(dataPath, templateFile);
		} else if (xmlFile.exists() && templateFile.exists()) {
			content = xmlTemplateTransform(xmlFile, templateFile);
		}
		return content;
	}
	
	private Object groovyTemplateTransform(String script, File templateFile) {
		try {
			log.info("groovy: " + script + " template: " + templateFile.getName());
			Binding binding = new Binding();
			binding.setVariable("log", log);
			binding.setVariable("templateFile", templateFile);
			return groovyEngine.run(script + ".groovy", binding);
		} catch (Exception e) {
			log.error("groovy: " + script + " template: " + templateFile.getName(), e);
			e.printStackTrace();
		}
		return "";
	}

	private String xmlTemplateTransform(File xmlFile, File templateFile) {
		try {
			log.debug("groovy: util/XMLToTemplate xml: " + xmlFile.getName() + " template: " + templateFile.getName());
			Binding binding = new Binding();
			binding.setVariable("log", log);
			binding.setVariable("templateFile", templateFile);
			binding.setVariable("xmlFile", xmlFile);
			String result = (String)groovyEngine.run("util/XMLToTemplate.groovy", binding);
			return result;
		} catch (Exception e) {
			log.error("groovy: util/XMLToTemplate xml: " + xmlFile.getName() + " template: " + templateFile.getName(), e);
			e.printStackTrace();
		}
		return null;
	}	
}