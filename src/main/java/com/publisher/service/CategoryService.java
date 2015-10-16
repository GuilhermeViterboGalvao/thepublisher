package com.publisher.service;

import java.util.Collection;
import java.util.List;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface CategoryService extends Service<Category> {

	List<Category> getByName(String name);
	
	void update(Category category, PermanentLink oldPermanentLink);
	
    Collection<Category> list(int page, int pageSize);
    
    Collection<Category> list(int page, int pageSize, String orderBy, String order);
    
    ResultList<Category> search(String query, int page, int pageSize);	
}