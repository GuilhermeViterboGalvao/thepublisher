package com.publisher.service;

import java.util.Collection;
import java.util.List;
import com.publisher.entity.Page;
import com.publisher.entity.PermanentLink;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface PageService extends Service<Page> {

	List<Page> getByName(String name);
	
	void update(Page page, PermanentLink oldPermanentLink);
	
    Collection<Page> list(int page, int pageSize);
    
    Collection<Page> list(int page, int pageSize, String orderBy, String order);
    
    ResultList<Page> search(String query, int page, int pageSize);
}