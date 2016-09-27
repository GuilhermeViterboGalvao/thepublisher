package com.publisher.test.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.publisher.test.config.AppTestInitializer;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	locations = { "file:src/main/webapp/WEB-INF/spring/applicationContext.xml" },
	initializers = { AppTestInitializer.class }
)
public abstract class DefaultTest<T> {

	protected T entity;
	
	protected T persistedEntity;
	
	public abstract void init();
	
	public abstract void testIt();
	
	public abstract void finish();
}