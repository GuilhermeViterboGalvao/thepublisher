package com.publisher.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextListener implements ServletContextListener {

	private static Log log = LogFactory.getLog(ContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) { 
        try { 
        	readAndExport(event.getServletContext()); 
        } catch (Exception e) {
        	log.error(e);
        	e.printStackTrace();
        }
	}
	
	private void readAndExport(ServletContext context) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		String realPath = context.getRealPath(File.separator);
		realPath = !realPath.endsWith(File.separator)   ? realPath + File.separator : realPath;
		realPath = !realPath.startsWith(File.separator) ? File.separator + realPath : realPath;
		properties.load(new FileInputStream(realPath + "WEB-INF/default-folders.properties"));		
		File home = null;		
		String property = System.getProperty("home-folder");		
		if (property != null && !properties.isEmpty()) {
			home = new File(property);
		} else {
			property = System.getenv("home-folder");
			if (property != null && !properties.isEmpty()) {
				home = new File(property);
			} else {
				property = properties.getProperty("home-folder");
				if (property != null && !properties.isEmpty()) {
					home = new File(property);	
				}
			}
		}
		System.setProperty("home-folder", home.getAbsolutePath());
        context.setAttribute("home-folder", home.getAbsolutePath());        
		Iterator<Object> keyIterator = properties.keySet().iterator();		
		while(keyIterator.hasNext()) {			
			File dir = null;			
			String key = (String)keyIterator.next();			
			String path = properties.getProperty(key);			
			File file = new File(path);			
			if (path != null && !path.isEmpty() && !path.startsWith(File.separator) && !file.isFile()) {
				dir = init(new File(home, path));
			}
			if (dir != null) {
				log.info("Application " + key  + " folder: " + dir.getAbsolutePath());
	    		System.setProperty(key, dir.getAbsolutePath());
	            context.setAttribute(key, dir.getAbsolutePath());
	        }
		}
	}
	
	private File init(File file) {
		if (file.exists()) {
			if (!file.isDirectory() || !file.canRead()) {
				log.info("Can not use " + file.getAbsolutePath());
				return null;
			}
		} else if (!file.mkdirs()) {
			log.info("Can not create " + file.getAbsolutePath());
			return null;
		}
		return file;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) { }
}