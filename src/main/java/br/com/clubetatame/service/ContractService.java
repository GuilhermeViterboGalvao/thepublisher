package br.com.clubetatame.service;

import java.util.Collection;
import java.util.Date;

import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface ContractService<E> extends Service<E> {
	
    Collection<E> list(int page, int pageSize);
    
    Collection<E> list(int page, int pageSize, String orderBy, String order);
    
    Collection<E> list(int page, int pageSize, String orderBy, String order, Date end);
    
    ResultList<E> search(String query, int page, int pageSize);
}