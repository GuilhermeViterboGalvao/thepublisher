package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Account;
import com.publisher.service.AccountService;

public class AccountTest extends AppTest {
	
	@Autowired
	private AccountService accountService;
	
	@Test
	public void test() {
		Account account = accountService.get(1l);		
		assertTrue(account != null);
	}
}