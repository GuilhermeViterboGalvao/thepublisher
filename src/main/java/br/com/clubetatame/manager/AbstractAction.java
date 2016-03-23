package br.com.clubetatame.manager;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;

public abstract class AbstractAction<T> extends ActionSupport implements AccountAware, ServletRequestAware {

	private static final long serialVersionUID = -7860719390991822454L;

	protected static Logger log = Logger.getLogger(AbstractAction.class);
	
	private int currentPage = 1;
	
	private int pageSize = 20;
	
	private int pages = 0;
	
	private String search = null;
	
	private Account account;
	
	private Collection<T> list;
	
	private HttpServletRequest request;
	
	@SkipValidation
	public String edit(){
		populateForm(getObject());
		return INPUT;
	}
	
	@SkipValidation
	public String list(){
		if (search != null && !search.isEmpty() && search.length() > 2) {
			list = generateSearch();
		} else {
			list = generateList();
		}
		return SUCCESS;
	}
	
	
	public String save(){
		boolean isNew = false;
		T object = getObject();
		if (object == null) {
			object = createEmptyInstance();
			isNew = true;
		}
		try { 
			updateObject(object); 
		} catch (Exception e) { 
			log.error(e.getMessage(), e); 
			return ERROR; 
		}
		saveObject(object,isNew);
		return "redirect";
	}
	
	@SkipValidation
	public String index(){
		indexAll();
		return "redirect";
	}
	
	protected abstract void indexAll();

	protected abstract void populateForm(T entity);

	protected abstract T updateObject(T entity);

	protected abstract T createEmptyInstance();

	protected abstract void saveObject(T entity, boolean isNew);
	
	protected abstract Collection<T> generateList();
	
	protected abstract Collection<T> generateSearch();

	protected abstract T getObject();
	
	public boolean isAdmin() {
		if (account != null && account.getSecurityHole() != null && !account.getSecurityHole().isEmpty() && account.getSecurityHole().equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}
	
	public Collection<T> getList() {
		return list;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search.replaceAll("^[^(a-z)(A-Z)(0-9)]*", "");
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getSessionId() {
    	return request.getSession().getId();
    }
}