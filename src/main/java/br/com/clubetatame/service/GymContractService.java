package br.com.clubetatame.service;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;

public interface GymContractService extends ContractService<GymContract>{
	
	Collection<GymContract> list(Gym gym);
}
