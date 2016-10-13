package br.com.tatame.service.implementation;

import java.util.Date;

import com.publisher.service.implementation.AbstractServiceImplementation;

import br.com.tatame.entity.LiveStats;
import br.com.tatame.service.LiveStatsService;

public class LiveStatsServiceImplementation extends AbstractServiceImplementation<LiveStats> implements LiveStatsService {

	@Override
	public Class<LiveStats> getServiceClass() {
		return LiveStats.class;
	}
	
	@Override
	public void persist(LiveStats entity) {
		if (entity != null) {
			entityManager.persist(entity);
			if (entity.getPermanentLink() != null) {
				entityManager.flush();
				entity.getPermanentLink().setParam(entity.getId());
				entity.setCreated(new Date());
				entityManager.merge(entity.getPermanentLink());
				entityManager.flush();
			}	
		}
	}
}