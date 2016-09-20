package com.publisher.test.service.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/applicationContext.xml"})
public class AppTest {
	
	private static Log log = LogFactory.getLog(AppTest.class);
	
	private static Properties properties = new Properties();
	
	private static String runningContext = "guilherme";
	
	static {
		tryLoadRunningContext();
		loadSystemProperties();
		loadDefaultFoldersProperties();
	}
	
	private static void tryLoadRunningContext() {
		String myRunningContext = System.getProperty("running.context");
		if (myRunningContext != null && !myRunningContext.isEmpty()) {
			runningContext = myRunningContext;
		} else {
			myRunningContext = System.getenv("running.context");
			if (myRunningContext != null && !myRunningContext.isEmpty()) {				
				runningContext = myRunningContext;
			}
		}
	}
	
	private static void loadSystemProperties() {
		System.setProperty("upload.files.dir", "/Users/Guilherme/publisher-data-files/upload-files");
		System.setProperty("publisher.log.path", "/Users/Guilherme/publisher-data-files/logs");		
		System.setProperty("photos.dir", "/Users/Guilherme/publisher-data-files/photos");
		System.setProperty("temp.dir", "/Users/Guilherme/publisher-data-files/temp");
		System.setProperty("running.context", runningContext);		
	}

	private static void loadDefaultFoldersProperties() {
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
		        }
			}
		}	
	}
	
	private static File init(File file) {
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
}