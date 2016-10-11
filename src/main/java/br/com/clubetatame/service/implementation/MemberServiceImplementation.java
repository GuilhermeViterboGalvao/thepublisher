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

import com.publisher.service.implementation.AbstractServiceImplementation;
import com.publisher.utils.HibernateSearchUtils;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class MemberServiceImplementation extends AbstractServiceImplementation<Member> implements MemberService {

	private static Log log = LogFactory.getLog(MemberServiceImplementation.class);

	@Override
	public Class<Member> getServiceClass() {
		return Member.class;
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
				if (!member.getHash().equals(password)) {
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
	@SuppressWarnings("unchecked")
	public Member getByDocument(String document) {
		Query query = entityManager.createQuery("from Member where document = :document").setParameter("document", document);
		List<Member> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	@Override
	public Collection<Member> list(Boolean active) {
		return list(active, 0, 0);
	}

	@Override
	public Collection<Member> list(Boolean active, int page, int pageSize) {
		return list(active, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Member> list(Boolean active, int page, int pageSize, String orderBy, String order)  {
		StringBuilder sql = new StringBuilder();
		sql.append("from Member m ");
		if (active != null) {
			sql.append("where m.active=:active ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("m." + orderBy + " " + order);	
		} else {
			sql.append("m.id desc");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (active != null) {
			query.setParameter("active", active);
		}
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
	public ResultList<Member> search(String query, int page, int pageSize, Boolean isActive) {
		long t = System.currentTimeMillis();
		FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Member.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "email", "document", "address", "cep").createQuery();
		FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Member.class);
		if (pageSize > 0) {
			fullTextQuery.setMaxResults(pageSize);
		}
		if (page > 0 && pageSize > 0) {        	
			fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}        
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


	@SuppressWarnings("unchecked")
	public Member getByFacebookId(Long fbid) {
		if (fbid == null) return null;
		Member member = null;
		Query query = entityManager.createQuery("from Member where fbid=:fbid").setParameter("fbid", fbid);
		List<Member> result = query.getResultList();
		if (!result.isEmpty()) member = result.iterator().next();
		return member;
	}
}