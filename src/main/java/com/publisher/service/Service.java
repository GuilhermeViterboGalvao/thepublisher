package com.publisher.service;

import java.util.Collection;

import com.publisher.utils.ResultList;

public interface Service<T> {

	T get(Long id);
	
	void persist(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	Collection<T> list();
	
	Collection<T> search(String query);
	
	ResultList<T> search(String query, int page, int pageSize);
	
	long count();
	
	void indexAll();
}