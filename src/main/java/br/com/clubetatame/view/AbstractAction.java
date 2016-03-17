package br.com.clubetatame.view;

import java.util.Collection;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class AbstractAction<T> extends ActionSupport implements ModelDriven<T>, Preparable, ViewAction {

	private static final long serialVersionUID = -843169202944052227L;

	private T model;
	
	@Override
	public T getModel() {
		return model;
	}
	
	public void setModel(long id){
		this.model = getEntity(id);
	}

	@Override
	public void prepare() throws Exception {
		if (id > 0) {
			setModel(id);
		}
	}
	
	//Action properties
	
	private long id;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	private int currentPage = 1;
	
	public int getCurrentPage() {
        return currentPage;
    }
	
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    private int pageSize = 6;
    
    public int getPageSize(){
    	return pageSize;
    }
    
    public void setPageSize(int pageSize) {
    	if (pageSize <= 31) {
    		this.pageSize = pageSize;	
    	}
    }
    
    private int pages;
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages){
    	this.pages = pages;
    }
    
    private Date start;
    
    public Date getStart(){
    	return start;
    }
    
    public void setStart(Date start) {
    	this.start = start;
    }    
    
    private Date end;
    
    public Date getEnd (){
    	return end;
    }
    
    public void setEnd(Date end) {
    	this.end = end;
    }

    public Collection<T> getList() {
        return getList(0);
    }
    
	@Override
	public abstract String getLayoutPath();

	@Override
	public abstract String getContentPath();
	
	public abstract T getEntity(long id);
	
	public abstract Collection<T> getList(int i);
}