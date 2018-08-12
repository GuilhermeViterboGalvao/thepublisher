package com.publisher.test.config;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.publisher.context.ContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class AppTestInitializer extends ContextListener implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	protected Log LOGGER = LogFactory.getLog(AppTestInitializer.class);
	
	@Override
	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		assertNotNull(applicationContext);
		ServletContext context = applicationContext.getServletContext();
		assertNotNull(context);
		runningContext = getRunningContext();
		try {
			readAndExport(context);
		} catch (IOException e) {
			LOGGER.error(e);
			e.printStackTrace();
		}
	}
}