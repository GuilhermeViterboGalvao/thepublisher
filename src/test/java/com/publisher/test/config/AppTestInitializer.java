package com.publisher.test.config;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class AppTestInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	protected Log LOGGER = LogFactory.getLog(AppTestInitializer.class);
	
	private Properties properties = new Properties();
	
	private String runningContext = "guilherme";
	
	private ServletContext context;
	
	@Override
	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		assertNotNull(applicationContext);
		context = applicationContext.getServletContext();
		assertNotNull(context);
		tryLoadRunningContext();
		loadDefaultFoldersProperties();
	}
	
	private void tryLoadRunningContext() {
		String myRunningContext = System.getProperty("running.context");
		if (myRunningContext != null && !myRunningContext.isEmpty()) {
			runningContext = myRunningContext;
		} else {
			myRunningContext = System.getenv("running.context");
			if (myRunningContext != null && !myRunningContext.isEmpty()) {				
				runningContext = myRunningContext;
			}
		}
		System.setProperty("running.context", runningContext);
	}

	private void loadDefaultFoldersProperties() {
		try {
			properties.load(new FileInputStream("src/main/webapp/WEB-INF/config-files/default-folders.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
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
					LOGGER.info("Application " + key  + " folder: " + dir.getAbsolutePath());
		    		System.setProperty(key, dir.getAbsolutePath());
		    		context.setAttribute(key, dir.getAbsolutePath());
		        }
			}
		}	
	}
	
	private File init(File file) {
		if (file.exists()) {
			if (!file.isDirectory() || !file.canRead()) {
				LOGGER.info("Can not use " + file.getAbsolutePath());
				return null;
			}
		} else if (!file.mkdirs()) {
			LOGGER.info("Can not create " + file.getAbsolutePath());
			return null;
		}
		return file;
	}	
}