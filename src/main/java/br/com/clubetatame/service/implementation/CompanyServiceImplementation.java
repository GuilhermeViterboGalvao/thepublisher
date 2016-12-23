package br.com.clubetatame.service.implementation;

import java.util.ArrayList;
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
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.service.CompanyService;

public class CompanyServiceImplementation extends AbstractServiceImplementation<Company> implements CompanyService {

	private static Log log = LogFactory.getLog(CompanyServiceImplementation.class);

	@Override
	public Collection<Company> list(Boolean active) {
		return list(active, 0, 0);
	}

	@Override
	public Collection<Company> list(Boolean active, int page, int pageSize) {
		return list(active, page, pageSize, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Company> list(Boolean active, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Company c ");
		if (active != null) {
			sql.append("where c.active=:active ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("c." + orderBy + " " + order);	
		} else {
			sql.append("c.id desc");
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
	public Collection<Company> list(float lat, float lon, float distanceInKM) {
		return list(lat, lon, distanceInKM, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Company> list(float lat, float lon, float distanceInKM, Boolean active) {
		StringBuilder sql = new StringBuilder();
		sql.append("select c from Company c ");
		List<Float> area = null;
		if (distanceInKM > 0.0f) {
			area = this.getArea(lat, lon, distanceInKM);
			sql.append("where ");
			sql.append("c.lat>=:lat1 ");
			sql.append("and ");
			sql.append("c.lat<=:lat2 ");
			sql.append("and ");
			sql.append("c.lon<=:lon2 ");
			sql.append("and ");
			sql.append("c.lon>=:lon1 ");
		}
		if (area != null && active != null) {
			sql.append("and ");
			sql.append("c.active=:active ");
		} else if (area == null && active != null) {
			sql.append("where ");
			sql.append("c.active=:active ");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (area != null) {
			query.setParameter("lat1", area.get(0));
			query.setParameter("lon1", area.get(1));
			query.setParameter("lat2", area.get(2));
			query.setParameter("lon2", area.get(3));
		}
		if (active != null) {
			query.setParameter("active", active);
		}
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	private List<Float> getArea(float lat, float lon, float distanceInKM) {
		int earthInKM = 40075;
		float oneKMinGrau = 360.0f / earthInKM;
		float distance = distanceInKM * oneKMinGrau;
		float lat1 = lat - distance;
		float lon1 = lon - distance;
		float lat2 = lat + distance;
		float lon2 = lon + distance;
		ArrayList<Float> result = new ArrayList<Float>(4);
		result.add(Float.valueOf(lat1));
		result.add(Float.valueOf(lon1));
		result.add(Float.valueOf(lat2));
		result.add(Float.valueOf(lon2));
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResultList<Company> search(String query, int page, int pageSize, Boolean active) {
		long t = System.currentTimeMillis();
		FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Company.class).get();
		org.apache.lucene.search.Query luceneQuery = HibernateSearchUtils.createQuery(query, qb, "name", "contact", "document", "email", "phone", "address", "cep").createQuery();
		FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Company.class);
		if (pageSize > 0) {
			fullTextQuery.setMaxResults(pageSize);
		}
		if (page > 0 && pageSize > 0) {        	
			fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}        
		if (active != null) fullTextQuery.enableFullTextFilter("activeCompany").setParameter("isActive", active);
		fullTextQuery.setHint("org.hibernate.cacheable", true);
		ResultList<Company> result = new ResultList<Company>();
		result.setResult(fullTextQuery.getResultList());
		result.setResultSize(fullTextQuery.getResultSize());
		result.setTimeElapsed((int)(System.currentTimeMillis() - t));
		result.setPage(page);
		result.setPageSize(pageSize);
		log.info("COMPANY SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
		return result;
	}
}