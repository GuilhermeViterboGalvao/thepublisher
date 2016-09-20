package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import com.publisher.entity.Account;
import com.publisher.service.AccountService;

public class TestAccountServiceImplementation {

	private TestAccountServiceImplementation() { }
	
	public static void doTheTests(AccountService accountService) {
		assertTrue(accountService != null);
		
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
		
		accountService.delete(persistedAccount);
		persistedAccount = accountService.getByEmail("test@junit.com");
		assertNull(persistedAccount);
	}
}