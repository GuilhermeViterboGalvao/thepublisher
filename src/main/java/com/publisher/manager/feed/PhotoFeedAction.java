package com.publisher.manager.feed;

import java.util.Collection;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import com.publisher.entity.Photo;
import com.publisher.manager.AccountAware;
import com.publisher.service.PhotoService;
import com.publisher.utils.ResultList;

public class PhotoFeedAction extends ActionSupport implements AccountAware {

	private static final long serialVersionUID = -7773197168008814278L;

	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	private Account account;

	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Account getAccount() {
		return account;
	}	
	
	@Override
	public String execute() throws Exception {
		if (query != null && !query.isEmpty()) {
            ResultList<Photo> list = photoService.search(query, currentPage, pageSize);
            pages = (int) Math.floor(1f * list.getResultSize() / pageSize) + 1;
            photos = list.getResult();			
		} else {
        	pages = (int) Math.floor(1f * photoService.count() / pageSize) + 1;
        	photos = photoService.list(currentPage, pageSize);			
		}
		result = new Result(currentPage, pageSize, pages, query, photos);
		return SUCCESS;
	}
	
	//Action properties
	
	private Collection<Photo> photos;
	
    private int currentPage = 1;
    
    private int pageSize = 60;
    
    private Result result;
    
    private String query;
    
    private int pages;
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setQuery(String query) {
        this.query = query;
    }  
    
    public Result getResult() {
        return result;
    }

    public class Result{
    	
    	private Collection<Photo> photos;
    	
    	private int currentPage;
    	
    	private int pageSize;
    	
    	private String query;
    	
    	private int pages;
    	
    	public Result(int currentPage, int pageSize, int pages, String query, Collection<Photo> photos){
    		this.currentPage = currentPage;
    		this.pageSize = pageSize;
    		this.photos = photos;
    		this.pages = pages;
    		this.query = query;    		
    	}  
    	
		public Collection<Photo> getPhotos() {
			return photos;
		}
		
		public String getQuery() {
			return query;
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public int getPageSize() {
			return pageSize;
		}

		public int getPages() {
			return pages;
		}    	
    }//Result
    
}//PhotoFeedAction