package com.publisher.service;

import java.util.Collection;

import org.apache.lucene.search.Sort;

import com.publisher.utils.ResultList;

public interface Service<T> {

	T get(Long id);
	
	void persist(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	Collection<T> list();
	
	Collection<T> list(int page, int pageSize, String orderBy, String order);
	
	Collection<T> search(String query);
	
	ResultList<T> search(String query, int page, int pageSize);
	
	ResultList<T> search(String query, int page, int pageSize, Sort sort);
	
	long count();
	
	void indexAll();
}