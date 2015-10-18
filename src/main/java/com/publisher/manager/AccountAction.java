package com.publisher.manager;

import java.util.Collection;

import com.publisher.entity.Account;
import com.publisher.service.AccountService;
import com.publisher.utils.ResultList;

public class AccountAction extends AbstractAction<Account> {

	private static final long serialVersionUID = -8294007433745271442L;

	private AccountService accountService;
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Override
	protected void indexAll() {
		accountService.indexAll();
	}

	@Override
	protected void populateForm(Account entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.email = entity.getEmail();			
			this.active = entity.isActive();
			this.securityHole = entity.getSecurityHole();
		}
	}

	@Override
	protected Account updateObject(Account entity) {
		if (entity != null) {
			setHash(entity);
			entity.setName(name);
			entity.setEmail(email);
			entity.setActive(active);		
			entity.setSecurityHole(securityHole);	
		}
		return entity;
	}

	@Override
	protected Account createEmptyInstance() {
		return new Account();
	}

	@Override
	protected void saveObject(Account entity, boolean isNew) {
		if(isNew) {
			accountService.persist(entity);
		} else {
			accountService.update(entity);
		}		
	}

	@Override
	protected Collection<Account> generateList() {
		setPages((int)Math.floor(1f * accountService.count() / getPageSize()) + 1);
		return accountService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Account> generateSearch() {
		ResultList<Account> result = accountService.search(getSearch(), getCurrentPage(), getPages());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);		
		return result.getResult();
	}

	@Override
	protected Account getObject() {
		return accountService.get(id);
	}
	
	public void validate() {
		Account account = getObject();
		if (!(account != null && account.getEmail().equals(email))){
			Account sameEmail = accountService.getByEmail(email);		
			if(sameEmail != null && sameEmail.getEmail().equals(email)){				
				addFieldError("email", "E-mail já cadastrado.");
			}
		}
		if (id <= 0) {
			if (password == null || password.isEmpty()) {
				addFieldError("password", "O campo senha é obrigatório!");	
			}
			if (password2 == null || password2.isEmpty()) {
				addFieldError("password2", "É obrigatório repetir a senha!");
			}			
		}
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			addFieldError("password", "Senhas diferentes. Tente novamente.");
		}		
	}	
	
	//Action properties and methods
	
	private String orderBy = "id";
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	private boolean orderly = true;

	public boolean isOrderly() {
		return orderly;
	}

	public void setOrderly(boolean orderly) {
		this.orderly = orderly;
	}	
		
	private void setHash(Account entity) {
		if (password != null && !password.isEmpty()) {
			entity.setHash(accountService.hash(password));
		}			
		password = "";
		password2 = "";		
	}
	
	private String password;
	
	private String password2;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}	
	
	//POJO

	private Long id;
	
	private String name;
	
	private String email;
	
	private String securityHole;
	
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurityHole() {
		return securityHole;
	}

	public void setSecurityHole(String securityHole) {
		this.securityHole = securityHole;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}