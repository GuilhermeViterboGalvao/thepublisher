package br.com.clubetatame.entity.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;

public class PhotoEventFilterFactory {

	private Boolean isEvent = false;
	
	public void setIsEvent(Boolean isEvent) {
		this.isEvent = isEvent;
	}

    @Factory
    public Filter getPublishedFilter() {
        return new QueryWrapperFilter(new TermQuery(new Term("isEvent", isEvent.toString())));
    }	
}