package br.com.clubetatame.service.implementation;

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
import com.publisher.service.implementation.TransactionalService;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class MemberServiceImplementation extends TransactionalService implements MemberService {

	private static Log log = LogFactory.getLog(MemberServiceImplementation.class);
	
	@Override
	public Member get(Long id) {
		return id != null ? entityManager.find(Member.class, id) : null;
	}

	@Override
	public void persist(Member entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Member entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Member entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Member> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Member> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
        Query query = entityManager.createQuery("select count(m) from Member m");
        return query != null ? (Long)query.getSingleResult() : 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(m.id) from Member m");
            long total = (Long)query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Member.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select m from Member m where m.id>=? and m.id<=? order by m.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Member> list = query.getResultList();
                for (Member member : list) {                	
                    ft.index(member);
                    log.info(member.getId() + ": " + member.getName());
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
	public Member authenticate(String email, String password) {
        Member member = null;
        Query query = entityManager.createQuery("from Member where email=:email").setParameter("email", email);
        if (query != null) {
            List<Member> result = query.getResultList();
            if (result != null && !result.isEmpty()) {
            	member = result.iterator().next();
                if (!member.getHash().equals(hash(password))) {
                	member = null;
                }
            }        	
        }
        return member;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Member getByEmail(String email) {
        Query query = entityManager.createQuery("from Member where email = :email").setParameter("email", email);
		List<Member> result = query.getResultList();
        if (result == null || result.isEmpty()) {
        	return null;
        }
        return result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> getByName(String name) {
		Query query = entityManager.createQuery("from Member m where m.name like :name order by m.name");
		query.setParameter("name", "%" + name + "%");
		List<Member> result = query.getResultList();
        if (result == null || result.isEmpty()) {
        	return null;
        }
        return result;
	}

	@Override
	public Collection<Member> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Member> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Member m ");
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("m." + orderBy + " " + order);	
		} else {
			sql.append("m.id desc");
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
	public ResultList<Member> search(String query, int page, int pageSize) {
		return search(query, page, pageSize, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Member> search(String query, int page, int pageSize, Boolean isActive) {
        long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Member.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "email", "document", "address", "cep").createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Member.class);
        if (isActive != null) {
        	fullTextQuery.enableFullTextFilter("activeMember").setParameter("isActive", isActive);
        }        
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Member> result = new ResultList<Member>();
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
        result.setPage(page);
        result.setPageSize(pageSize);
        log.info("MEMBER SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
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