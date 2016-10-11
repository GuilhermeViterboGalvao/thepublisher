package br.com.tatame.service.implementation;

import com.publisher.service.implementation.AbstractServiceImplementation;

import br.com.tatame.entity.LiveStats;
import br.com.tatame.service.LiveStatsService;

public class LiveStatsServiceImplementation extends AbstractServiceImplementation<LiveStats> implements LiveStatsService {

	@Override
	public Class<LiveStats> getServiceClass() {
		return LiveStats.class;
	}
}