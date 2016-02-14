package br.com.clubetatame.manager;

import com.publisher.entity.Account;

public interface AccountAware {
	
	void setAccount(Account account);
	
	Account getAccount();
	
	boolean isAdmin();
}