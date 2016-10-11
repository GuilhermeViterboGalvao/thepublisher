package br.com.clubetatame.service.implementation;

import java.util.Collection;
import javax.persistence.Query;

import com.publisher.service.implementation.AbstractServiceImplementation;
import br.com.clubetatame.entity.Product;
import br.com.clubetatame.service.ProductService;

public class ProductServiceImplementation extends AbstractServiceImplementation<Product> implements ProductService {
	
	@Override
	public Class<Product> getServiceClass() {
		return Product.class;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Product> list(Boolean active, int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Product p ");
		if (active != null) {
			sql.append("where p.active=:active ");
		}
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append("p." + orderBy + " " + order);	
		} else {
			sql.append("p.id desc");
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
}