package com.publisher.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface ArticleService extends Service<Article> {
	
    List<Article> get(int page, int pageSize, Date start, Date end, Boolean publishedOnly);
    
    List<Article> get(Category category, int page, int pageSize, Date start, Date end, Boolean publishedOnly);
    
    List<Article> get(Category category, int page, int pageSize, Date start, Date end, Boolean publishedOnly, String orderBy, String order);	

    Collection<Article> list(int page, int pageSize);

    Collection<Article> list(Boolean published, int page, int pageSize);
    
    Collection<Article> list(Boolean published, int page, int pageSize, Date publishedUntil);
    
    Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil);
    
    Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil, Boolean orderByDate);
    
    Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil, String orderBy, String order);
    
    ResultList<Article> search(String query, int page, int pageSize);

    ResultList<Article> search(String query, int page, int pageSize, Boolean published);
    
    ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil);
    
    ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil, String categoryName);
    
    long count(Category category);
    
    long count(Date start, Date end);
    
    long count(Boolean publishedOnly);
    
    long count(Category category, Date start, Date end);
    
    long count(Category category, Date start, Date end, Boolean publishedOnly);
}