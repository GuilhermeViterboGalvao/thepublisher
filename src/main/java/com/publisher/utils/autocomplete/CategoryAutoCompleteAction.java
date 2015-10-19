package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import com.publisher.entity.Category;
import com.publisher.service.CategoryService;

public class CategoryAutoCompleteAction extends AutoCompleteAction {
	
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService){
		this.categoryService = categoryService;
	}
	
	@Override
	public void populate(){		
		if (getTerm() == null) {
			return;
		}
		Collection<Category> categories = categoryService.search(addWildcards(getTerm()));
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		for (Category category:categories) {
			collection.add(new LabelValue(category.getName(), Long.toString(category.getId())));
		}
		setResult(collection);	
	}
}