package br.com.clubetatame.service.implementation;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Query;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;

public class GymContractServiceImplementation extends AbstractContractServiceImplementation<GymContract> implements GymContractService {
	
	public GymContractServiceImplementation(){
		genericClass = GymContract.class;
		entityName 	 = GymContract.class.getName();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<GymContract> list(Gym gym, Date end, Double value, int page, int pageSize, String orderBy, String order){
		StringBuilder sql = new StringBuilder();
		sql.append("from GymContract c");
		if (gym != null) {
			sql.append(" where c.gym = :gym");			
		}
		if (end != null) {
			String clause = (sql.toString().contains("where") ? "and" : "where");
			sql.append(String.format(" %s c.end >= :end", clause));
		}
		if (value != null) {
			String clause = (sql.toString().contains("where") ? "and" : "where");
			sql.append(String.format(" %s c.value > :value", clause));
		}
		sql.append(" order by");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append(" c." + orderBy + " " + order);	
		} else {
			sql.append(" c.id desc");
		}
        Query query = entityManager.createQuery(sql.toString());
        if (gym != null) {
        	query.setParameter("gym", gym);	
        }
        if (end != null) {
        	query.setParameter("end", end);	
        }
        if (value != null) {
        	query.setParameter("value", value);	
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
