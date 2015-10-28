package com.publisher.service.implementation;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import com.publisher.entity.Account;
import com.publisher.service.AccountService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;

public class AccountServiceImplementation extends TransactionalService implements AccountService {

	private static Log log = LogFactory.getLog(AccountServiceImplementation.class);
	
	@Override
	public Account get(Long id) {
		return id != null ? entityManager.find(Account.class, id ) : null;
	}

	@Override
	public void persist(Account entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Account entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Account entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}
	
	@Override
	public Collection<Account> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Account> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(a) from Account a");
        return (Long)query.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(a.id) from Account a");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Account.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select a from Account a where a.id>=? and a.id<=? order by a.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Account> list = query.getResultList();
                for (Account account : list) {                	
                    ft.index(account);
                    log.info(account.getId() + ": " + account.getName());
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
	@SuppressWarnings("unchecked")
	public Account authenticate(String email, String password) {
        Account account = null;
        Query query = entityManager.createQuery("from Account where email=:email").setParameter("email", email);
        List<Account> result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            account = result.iterator().next();
            if (!account.getHash().equals(hash(password))) {
                account = null;
            }
        }
        return account;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Account getByEmail(String email) {
        Query query = entityManager.createQuery("from Account where email = :email").setParameter("email", email);
		List<Account> result = query.getResultList();
        if (result == null || result.isEmpty()) {
        	return null;
        }
        return result.get(0);
	}

	@Override
	public Collection<Account> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Account> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Account a ");
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("a." + orderBy + " " + order);	
		} else {
			sql.append("a.id desc");
		}
        Query query = entityManager.createQuery(sql.toString());
        if (pageSize > 0) {
        	query.setMaxResults(pageSize);	
        }
        if (pageSize > 0 && page > 0) {
        	query.setFirstResult((page - 1) * pageSize);
        }        
        return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Account> search(String query, int page, int pageSize) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Account.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "email").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Account.class);        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Account> result = new ResultList<Account>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("ACCOUNT SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
	}

	@Override
	public String hash(String password) {
        String newPassword = "ThePublisher" + password;
        String hash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(newPassword.getBytes(), 0, newPassword.length());
            hash = new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return hash;
	}
}