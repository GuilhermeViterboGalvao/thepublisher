package com.publisher.service;

import java.util.Collection;

public interface Service<T> {

	T get(Long id);
	
	void persist(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	Collection<T> list();
	
	Collection<T> search(String query);
	
	long count();
	
	void indexAll();
}