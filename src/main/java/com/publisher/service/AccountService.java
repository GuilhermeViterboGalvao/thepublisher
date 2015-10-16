package com.publisher.service;

import java.util.Collection;
import com.publisher.entity.Account;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface AccountService extends Service<Account> {

	Account authenticate(String email, String pasword);
	
	Account getByEmail(String email);
	
    Collection<Account> list(int page, int pageSize);
    
    Collection<Account> list(int page, int pageSize, String orderBy, String order);
    
    ResultList<Account> search(String query, int page, int pageSize);
    
    String hash(String password);
}