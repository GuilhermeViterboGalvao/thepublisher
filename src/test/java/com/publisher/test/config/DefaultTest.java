package com.publisher.test.config;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback(false)
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