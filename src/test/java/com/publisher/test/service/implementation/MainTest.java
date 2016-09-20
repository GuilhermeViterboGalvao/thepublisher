package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Account;
import com.publisher.service.AccountService;

public class MainTest extends AppTest {

	@Autowired
	private AccountService accountService;
	
	@Test
	public void testAccountService() {
		assertTrue(accountService != null);
		LOGGER.info("AccountService isn't null.");
		
		LOGGER.info("Creating Account object...");
		String password = "test123";
		Account account = new Account();
		account.setActive(true);
		account.setEmail("test@junit.com");
		account.setHash(accountService.hash(password));
		account.setName("Test JUnit");
		account.setSecurityHole("admin");
		accountService.persist(account);
		
		Account persistedAccount = accountService.getByEmail("test@junit.com");
		assertNotNull(persistedAccount);
		LOGGER.info("Account object created with success on database.");
		
		LOGGER.info("Removing Account object...");
		accountService.delete(persistedAccount);
		persistedAccount = accountService.getByEmail("test@junit.com");
		assertNull(persistedAccount);
		LOGGER.info("Account object removed with success on database.");
	}
}