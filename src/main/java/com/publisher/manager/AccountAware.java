package com.publisher.manager;

import com.publisher.entity.Account;

public interface AccountAware {
	
	void setAccount(Account account);
	
	Account getAccount();
}