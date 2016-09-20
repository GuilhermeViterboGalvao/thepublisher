package com.publisher.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.service.AccountService;
import com.publisher.test.service.implementation.TestAccountServiceImplementation;

public class MainTest extends AppTest {

	@Autowired
	private AccountService accountService;
	
	@Test
	public void testServicesImplementations() {
		TestAccountServiceImplementation.doTheTests(accountService);
	}
}