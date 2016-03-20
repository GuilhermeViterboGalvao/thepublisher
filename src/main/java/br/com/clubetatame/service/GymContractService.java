package br.com.clubetatame.service;

import java.util.Collection;
import java.util.Date;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;

public interface GymContractService extends ContractService<GymContract>{
	
	Collection<GymContract> list(Gym gym, Date end, Double value, int page, int pageSize, String orderBy, String order);
}
