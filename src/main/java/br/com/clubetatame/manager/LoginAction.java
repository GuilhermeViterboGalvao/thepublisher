package br.com.clubetatame.manager;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import com.publisher.service.AccountService;

public class LoginAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -6248461407049743596L;
	
	private AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public String execute() throws Exception {
		Object obj = session.get("account");
		if (obj != null && obj instanceof Account) {
			account = (Account)obj;
			return SUCCESS;
		}
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			account = accountService.authenticate(email, password);
			if (account != null && account.isActive()) {
				session.put("account", account);
				return SUCCESS;
			}
		}
		return LOGIN;
	}
	
	//Action properties
	
	private Account account;
	
	public Account getAccount() {
		return account;
	}
	
	public boolean isAdmin() {
		if (account != null && account.getSecurityHole() != null && !account.getSecurityHole().isEmpty() && account.getSecurityHole().equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}	
	
	//POJO
	
	private String email;
	
	private String password;

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}