package com.publisher.manager;

import java.util.Collection;

import com.publisher.entity.AuthToken;
import com.publisher.service.AuthTokenService;
import com.publisher.utils.ResultList;

public class AuthTokenAction extends AbstractAction<AuthToken> {

	private static final long serialVersionUID = -8294007433745271442L;

	private AuthTokenService authTokenService;
	
	public void setAuthTokenService(AuthTokenService authTokenService) {
		this.authTokenService = authTokenService;
	}
	
	@Override
	protected void indexAll() {
		authTokenService.indexAll();
	}

	@Override
	protected void populateForm(AuthToken entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.token = entity.getToken();
			this.DNSs = entity.getDNSs();
			this.IPs = entity.getIPs();
			this.description = entity.getDescription();
			this.active = entity.isActive();
		}
	}

	@Override
	protected AuthToken updateObject(AuthToken entity) {
		if (entity != null && isAdmin()) {
			entity.setDNSs(DNSs);
			entity.setIPs(IPs);
			entity.setDescription(description);
			entity.setActive(active);
			
			if(entity.getToken() == null || entity.getToken().isEmpty()){
				entity.setToken(authTokenService.generateToken());
			}
		}
		return entity;
	}

	@Override
	protected AuthToken createEmptyInstance() {
		return new AuthToken();
	}

	@Override
	protected void saveObject(AuthToken entity, boolean isNew) {
		if(isNew) {
			authTokenService.persist(entity);
		} else {
			authTokenService.update(entity);
		}		
	}

	@Override
	protected Collection<AuthToken> generateList() {
		setPages((int)Math.floor(1f * authTokenService.count() / getPageSize()) + 1);
		return authTokenService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<AuthToken> generateSearch() {
		ResultList<AuthToken> result = authTokenService.search(getSearch(), getCurrentPage(), getPages());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);		
		return result.getResult();
	}

	@Override
	protected AuthToken getObject() {
		return authTokenService.get(id);
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
		
	
	
	//POJO

	private Long id = 0l;
	
	private String token;
	
	private String DNSs;
	
	private String IPs;
	
	private String description;
	
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public String getDNSs() {
		return DNSs;
	}

	public void setDNSs(String dNSs) {
		DNSs = dNSs;
	}

	public String getIPs() {
		return IPs;
	}

	public void setIPs(String iPs) {
		IPs = iPs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}