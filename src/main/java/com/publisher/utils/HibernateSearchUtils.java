package com.publisher.utils;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

public class HibernateSearchUtils {

	private HibernateSearchUtils() { }
	
	public static BooleanJunction<?> createQuery(String query, QueryBuilder builder, String... fields) {
		if (builder != null && query != null && !query.isEmpty() && fields != null) {
			String[] matches = query.split(" ");
			BooleanJunction<?> junction = builder.bool();
			for (String match : matches) {
				if (match.length() > 2) {
					junction.must(builder.keyword().onFields(fields).matching(match).createQuery());	
				}				
			}
			return junction;
		}
		return null;
	}
}