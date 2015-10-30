package com.publisher.utils.autocomplete;

import java.util.Collection;

import com.opensymphony.xwork2.Action;
import com.publisher.entity.Account;
import com.publisher.manager.AccountAware;

public abstract class AutoCompleteAction implements Action, AccountAware {

	private String term;
	private Integer page;
	private Integer pagesize;
	private Collection<LabelValue> result;
	private Account account;
	
	public String execute() {
		populate();
		return "success";
	}
	
	public abstract void populate();
	
	public Collection<LabelValue> getResult() {
		return result;
	}

	public void setResult(Collection<LabelValue> result) {
		this.result = result;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	@Override
	public boolean isAdmin() {
		if (account != null && account.getSecurityHole() != null && !account.getSecurityHole().isEmpty() && account.getSecurityHole().equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}	

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (page<1) page=1;
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		if (pagesize<1) pagesize=null;
		this.pagesize = pagesize;
	}

	protected String addWildcards(String s) {
		String[] keys = s.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String key : keys){
			sb.append(key);
			sb.append("* ");						
		}
		return sb.toString();
	}
	
}
