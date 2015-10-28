package com.publisher.service.implementation;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import com.publisher.entity.Article;
import com.publisher.entity.Category;
import com.publisher.entity.PermanentLink;
import com.publisher.service.ArticleService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ArticleServiceImplementation extends TransactionalService implements ArticleService {

	private static Log log = LogFactory.getLog(ArticleServiceImplementation.class);
	
	@Override
	public Article get(Long id) {
		return id != null ? entityManager.find(Article.class, id) : null;
	}

	@Override
	public void persist(Article entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Article entity) {
		if (entity != null) {
			entityManager.merge(entity);
			cleanCache(entity.getPermanentLink());
		}
	}

	@Override
	public void delete(Article entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}	

	@Override
	public Collection<Article> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Article> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
		return count(null, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(a.id) from Article a");
            long total = (Long) query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Article.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select a from Article a where a.id>=? and a.id<=? order by a.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
                List<Article> list = query.getResultList();
                for (Article article : list) {                    
                    ft.index(article);
                    log.info(article.getId() + ": " + article.getTitle());
                }
                ft.flushToIndexes();
                ft.clear();
            }
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
	}
	
	@Override
	public void update(Article entity, PermanentLink oldPermanentLink) {
		entityManager.merge(entity);
		cleanCache(oldPermanentLink);
	}
	
	private void cleanCache(PermanentLink permanentLink) {
		if (permanentLink != null && permanentLink.getUri() != null) {
			try {
				Cache cache = CacheManager.getInstance().getCache("pageCache");
				if (cache != null) {
					cache.remove(permanentLink.getUri());	
				}	
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	@Override
	public List<Article> get(int page, int pageSize, Date start, Date end, Boolean publishedOnly) {
		return get(null, page, pageSize, start, end, publishedOnly);
	}

	@Override
	public List<Article> get(Category category, int page, int pageSize, Date start, Date end, Boolean publishedOnly) {
		return get(category, page, pageSize, start, end, publishedOnly, null, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> get(Category category, int page, int pageSize, Date start, Date end, Boolean publishedOnly, String orderBy, String order) {
        Set<Category> categories = new HashSet<Category>();
        if (category != null) {
            categories.add(category);
            addChilds(categories, category, 0, 3);
            for (Category c : category.getChilds()) {
                categories.add(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean where = false;
        sb.append("select a from Article a");
        if (categories.size() > 0) {
            where = true;
            sb.append(" where a.category in ( ");
            for (int i = 1; i <= categories.size(); i++) {
                sb.append("?");
                if (i != categories.size()) {
                    sb.append(", ");
                } else {
                    sb.append(" )");
                }
            }
        }
        if (start != null) {
            if (where) {
                sb.append(" and ");
            } else {
                sb.append(" where ");
                where = true;
            }
            sb.append("a.publishedAt >= ?");
        }
        if (end != null) {
            if (where) {
                sb.append(" and ");
            } else {
                sb.append(" where ");
                where = true;
            }
            sb.append("a.publishedAt <= ?");
        }
        if (publishedOnly != null && publishedOnly) {
            if (where) {
                sb.append(" and ");
            } else {
                sb.append(" where ");
                where = true;
            }
            sb.append("a.published = true");
        }
        sb.append(" order by ");
		if (orderBy != null && !orderBy.equals("")
				&& order != null && !order.equals("")) {
			sb.append("	a." + orderBy + " " + order);	
		} else {
			sb.append("	a.publishedAt desc");	
		}     
        Query query = entityManager.createQuery(sb.toString());
        int i = 1;
        for (Category c : categories) {
            query.setParameter(i, c);
            i++;
        }
        if (start != null) {
            query.setParameter(i, start);
            i++;
        }
        if (end != null) {
            query.setParameter(i, end);
            i++;
        }
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		if (page > 0 && pageSize > 0) {
			query.setFirstResult((page - 1) * pageSize);
		}
        query.setHint("org.hibernate.cacheable", true);
        return query.getResultList();
	}

	@Override
	public Collection<Article> list(int page, int pageSize) {
		return list(null, page, pageSize);
	}

	@Override
	public Collection<Article> list(Boolean published, int page, int pageSize) {
		return list(published, page, pageSize, null);
	}

	@Override
	public Collection<Article> list(Boolean published, int page, int pageSize, Date publishedUntil) {
		return list(published, null, page, pageSize, publishedUntil);
	}

	@Override
	public Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil) {
		return list(published, category, page, pageSize, publishedUntil, null);
	}

	@Override
	public Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil, Boolean orderByDate) {
		return list(published, category, page, pageSize, publishedUntil, orderByDate != null && orderByDate ? "publishedAt" : null, "desc");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Article> list(Boolean published, Category category, int page, int pageSize, Date publishedUntil, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Article a ");		
		if (published != null) {
			sql.append("where ");
			sql.append("	a.published=:published ");
		}		
		if (category != null && published != null) {
			sql.append("	and ");
			sql.append("	a.category=:category  ");
		} else if (category != null){
			sql.append("where ");
			sql.append("	a.category=:category  ");
		}		
		if (publishedUntil != null && (category != null || published != null)) {
			sql.append("	and ");
			sql.append("	a.publishedAt<=:publishedUntil ");
		} else if (publishedUntil != null) {
			sql.append("where ");
			sql.append("	a.publishedAt<=:publishedUntil ");
		}		
		sql.append("order by ");
		if (orderBy != null && !orderBy.equals("")
				&& order != null && !order.equals("")) {
			sql.append("	a." + orderBy + " " + order);	
		} else {
			sql.append("	a.id desc");	
		}
		Query query = entityManager.createQuery(sql.toString());
		if (published != null) {
			query.setParameter("published", published);
		}
		if (category != null) {
			query.setParameter("category", category);
		}
		if (publishedUntil != null) {
			query.setParameter("publishedUntil", publishedUntil);
		}		
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		if (page > 0 && pageSize > 0) {
			query.setFirstResult((page - 1) * pageSize);
		}
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize) {
		return search(query, page, pageSize, null);
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published) {
		return search(query, page, pageSize, published, null);
	}

	@Override
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil) {
		return search(query, page, pageSize, published, publishedUntil, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Article> search(String query, int page, int pageSize, Boolean published, Date publishedUntil, String categoryName) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();				
		BooleanJunction<?> junction = HibernateSearchUtils.createQuery(query, qb, "title", "header", "note", "tags", "createdBy.name");
		if (categoryName != null && !categoryName.isEmpty()) {
			junction = junction.must(
				qb.phrase().onField("category.name").sentence(categoryName).createQuery()
			);
		}
		if (publishedUntil != null) {						
			junction = junction.must(
				qb.range().onField("publishedAt").from(
					DateTools.round(0l, DateTools.Resolution.DAY)
				).to(
					DateTools.round(publishedUntil.getTime(), DateTools.Resolution.DAY)
				).excludeLimit().createQuery()
			);
		}		
		org.apache.lucene.search.Query luceneQuery = junction.createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Article.class);
        if (published != null && published) {
        	fullTextQuery.enableFullTextFilter("published").setParameter("isPublished", true);
        }
        fullTextQuery.setSort(
        	new Sort(
        		new SortField("publishedAt", SortField.Type.LONG, true)
        	)
        );
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {
        	fullTextQuery.setFirstResult((page - 1) * pageSize);        	
        }
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Article> result = new ResultList<Article>();                    
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        log.info("ARTICLE SEARCH=[" + luceneQuery.toString() + "] - TimeElapsed=" + result.getTimeElapsed());        
        return result;
	}

	@Override
	public long count(Category category) {
		return count(category, null, null);
	}

	@Override
	public long count(Date start, Date end) {
		return count(null, start, end);
	}

	@Override
	public long count(Boolean publishedOnly) {
		return count(null, null, null, publishedOnly);
	}

	@Override
	public long count(Category category, Date start, Date end) {
		return count(category, start, end, null);
	}

	@Override
	public long count(Category category, Date start, Date end, Boolean publishedOnly) {
        Set<Category> categories = new HashSet<Category>();
        if (category != null) {
            categories.add(category);
            addChilds(categories, category, 0, 3);
            for (Category c : category.getChilds()) {
                categories.add(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean where = false;
        sb.append("select count(a) from Article a");
        if (categories.size() > 0) {
            where = true;
            sb.append(" where a.category in ( ");
            for (int i = 1; i <= categories.size(); i++) {
                sb.append("?");
                if (i != categories.size()) {
                    sb.append(", ");
                } else {
                    sb.append(" )");
                }
            }
        }
        if (start != null) {
            if (where) {
                sb.append(" and ");
            } else {
                sb.append(" where ");
                where = true;
            }
            sb.append("a.publishedAt>=?");
        }
        if (end != null) {
            if (where) {
                sb.append(" and ");
            } else {
                sb.append(" where ");
                where = true;
            }
            sb.append("a.publishedAt<=?");
        }
        if (publishedOnly != null && publishedOnly) {
            if (where) {
                sb.append(" and ");
            } else {
                sb.append(" where ");
                where = true;
            }
            sb.append("a.published<=true");
        }
        Query query = entityManager.createQuery(sb.toString());
        int i = 1;
        for (Category c : categories) {
            query.setParameter(i, c);
            i++;
        }
        if (start != null) {
            query.setParameter(i, start);
            i++;
        }
        if (end != null) {
            query.setParameter(i, end);
            i++;
        }
        query.setHint("org.hibernate.cacheable", true);
        return (Long) query.getSingleResult();
	}
	
   private void addChilds(Set<Category> categories, Category category, int counter, int max) {
    	counter++;
    	for(Category currentCategory : category.getChilds()) {
    		categories.add(currentCategory);
    		if (counter < max) {
    			addChilds(categories, currentCategory, counter, max);
    		}
    	}
    }
}