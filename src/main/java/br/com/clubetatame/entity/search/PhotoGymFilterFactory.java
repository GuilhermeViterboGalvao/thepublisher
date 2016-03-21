package br.com.clubetatame.entity.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;

public class PhotoGymFilterFactory {

	private Boolean isGym = false;
	
	public void setIsGym(Boolean isGym) {
		this.isGym = isGym;
	}

    @Factory
    public Filter getPublishedFilter() {
        return new QueryWrapperFilter(new TermQuery(new Term("isGym", isGym.toString())));
    }	
}