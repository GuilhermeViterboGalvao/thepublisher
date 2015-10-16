package com.publisher.entity.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.filter.impl.CachingWrapperFilter;

public class PublishedFilter {

	private Boolean published = false;
	
	public void setIsPublished(Boolean published) {
		this.published = published;
	}
	
    @Factory
    public Filter getPublishedFilter() {
        return new CachingWrapperFilter(
        	new QueryWrapperFilter(
        		new TermQuery(
        			new Term("published", published.toString())
        		)
        	)
        );
    }
}