package br.com.clubetatame.entity.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.filter.impl.CachingWrapperFilter;

public class ActiveFilterFactory {

	private Boolean active = false;
	
	public void setIsActive(Boolean active) {
		this.active = active;
	}
	
    @Factory
    public Filter getActiveFilter() {
        return new CachingWrapperFilter(new QueryWrapperFilter(new TermQuery(new Term("active", active.toString()))));
    }
}