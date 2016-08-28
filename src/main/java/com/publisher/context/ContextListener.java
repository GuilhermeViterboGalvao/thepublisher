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

import com.publisher.utils.ViewsListener;

public class ContextListener implements ServletContextListener {

	private static Log log = LogFactory.getLog(ContextListener.class);
	
	private String runningContext;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		runningContext = getRunningContext();
        try { 
        	readAndExport(event.getServletContext()); 
        } catch (Exception e) {
        	log.error(e);
        	e.printStackTrace();
        }
        ViewsListener.start(event.getServletContext());
	}
	
	private String getRunningContext() {
		String myRunningContext = System.getProperty("running.context");
		if (myRunningContext == null || myRunningContext.isEmpty()) {
			myRunningContext = System.getenv("running.context");
			if (myRunningContext == null || myRunningContext.isEmpty()) {				
				myRunningContext = "local";
				System.setProperty("running.context", myRunningContext);
			}
		}
		return myRunningContext;
	}
	
	private void readAndExport(ServletContext context) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		String realPath = context.getRealPath(File.separator);
		realPath = !realPath.endsWith(File.separator)   ? realPath + File.separator : realPath;
		realPath = !realPath.startsWith(File.separator) ? File.separator + realPath : realPath;
		properties.load(new FileInputStream(realPath + "WEB-INF/config-files/default-folders.properties"));		
		File home = null;		
		String property = System.getProperty("home-folder");		
		if (property != null && !properties.isEmpty()) {
			home = new File(property);
		} else {
			property = System.getenv("home-folder");
			if (property != null && !properties.isEmpty()) {
				home = new File(property);
			} else {
				property = properties.getProperty(runningContext + ".home-folder");
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
			if (key.contains(runningContext)) {				
				String path = properties.getProperty(key);
				key = key.replace(runningContext + ".", "");
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
	public void contextDestroyed(ServletContextEvent event) {
		if (ViewsListener.getInstace() != null && ViewsListener.getInstace().isRunning()) {
			ViewsListener.getInstace().finish();	
		}
	}
}