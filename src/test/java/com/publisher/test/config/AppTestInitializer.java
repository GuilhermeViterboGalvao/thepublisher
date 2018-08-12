package com.publisher.test.config;

import static org.junit.Assert.*;
import javax.servlet.ServletContext;
import com.publisher.context.ContextListener;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class AppTestInitializer extends ContextListener implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
	
	@Override
	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		assertNotNull(applicationContext);
		ServletContext servletContext = applicationContext.getServletContext();
		assertNotNull(servletContext);
		initContext(servletContext);
	}
}