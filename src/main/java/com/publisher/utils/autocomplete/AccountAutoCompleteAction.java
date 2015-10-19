package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import com.publisher.entity.Account;
import com.publisher.service.AccountService;
import com.publisher.utils.ResultList;

public class AccountAutoCompleteAction extends AutoCompleteAction {

	private AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Override
	public void populate() {
		if (getTerm() == null) {
			return;
		}
		ResultList<Account> result = accountService.search(getTerm(), getPage(), getPagesize());
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		for (Account account : result.getResult()) {
			collection.add(new LabelValue(account.getName() + " <" + account.getEmail() + ">", Long.toString(account.getId())));
		}
		setResult(collection);
	}
}