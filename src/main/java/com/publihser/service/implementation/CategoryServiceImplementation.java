package com.publihser.service.implementation;

import java.util.Collection;
import java.util.List;

import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.service.CategoryService;
import com.publisher.utils.ResultList;

public class CategoryServiceImplementation extends TransactionalService implements CategoryService {

	@Override
	public Category get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(Category entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Category entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Category entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Category> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Category> search(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void indexAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Category> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Category category, PermanentLink oldPermanentLink) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Category> list(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Category> list(int page, int pageSize, String orderBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultList<Category> search(String query, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
