package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Account;
import com.publisher.service.AccountService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestAccountServiceImplementation extends DefaultTest<Account> implements AccountService {
	
	private static final String PASSWORD = "!@#test123$%^";
	
	@Autowired
	private AccountService accountService;
	
	@Test
	public void testIt() {
		assertNotNull(accountService);
		
		entity = new Account();
		entity.setActive(true);
		entity.setEmail("test@junit.com");
		entity.setHash(hash(PASSWORD));
		entity.setName("Test JUnit");
		entity.setSecurityHole("admin");		
		
		persist(entity);
		
		persistedEntity = getByEmail("test@junit.com");
		assertNotNull(persistedEntity);
		
		persistedEntity.setName("Test JUnit");
		update(persistedEntity);
		
		persistedEntity = get(persistedEntity.getId());
		
		persistedEntity = authenticate(persistedEntity.getEmail(), PASSWORD);
		
		search(persistedEntity.getName());
		
		search(persistedEntity.getName(), 0, 50);
		
		list();
		
		list(0,  50);
		
		list(0, 50, "name", "desc");
		
		delete(persistedEntity);
		persistedEntity = accountService.get(entity.getId());
		assertNull(persistedEntity);
	}
	
	@After
	public void finish() {

	}

	@Override
	public Account get(Long id) {
		assertNotNull(id);
		Account account = accountService.get(id);
		assertNotNull(account);
		return account;
	}

	@Override
	public void persist(Account entity) {
		assertNotNull(entity);
		accountService.persist(entity);	
	}

	@Override
	public void update(Account entity) {
		assertNotNull(entity);
		accountService.update(entity);		
	}

	@Override
	public void delete(Account entity) {
		assertNotNull(entity);
		accountService.delete(entity);		
	}

	@Override
	public Collection<Account> list() {
		Collection<Account> list = accountService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Account> search(String query) {
		assertNotNull(query);
		Collection<Account> search = accountService.search(query);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count() {
		long count = accountService.count();
		assertTrue(count >= 0);
		return count;
	}

	@Override
	public void indexAll() {
		//accountService.indexAll();
	}

	@Override
	public Account authenticate(String email, String password) {
		assertNotNull(email);
		assertTrue(!email.isEmpty());
		assertNotNull(password);
		assertTrue(!password.isEmpty());
		Account account = accountService.authenticate(email, password);
		assertNotNull(account);
		return account;
	}

	@Override
	public Account getByEmail(String email) {
		assertNotNull(email);
		Account account = accountService.getByEmail(email);
		assertNotNull(account);
		return account;
	}

	@Override
	public Collection<Account> list(int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		Collection<Account> list = accountService.list(page, pageSize);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Account> list(int page, int pageSize, String orderBy, String order) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(orderBy);
		assertNotNull(order);		
		Collection<Account> list = accountService.list(page, pageSize, orderBy, order);
		assertNotNull(list);
		return list;
	}

	@Override
	public ResultList<Account> search(String query, int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(query);
		ResultList<Account> search = accountService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}

	@Override
	public String hash(String password) {
		assertNotNull(password);
		password = accountService.hash(password);
		assertNotNull(password);
		return password;
	}
}