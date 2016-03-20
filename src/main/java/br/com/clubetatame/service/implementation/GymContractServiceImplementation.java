package br.com.clubetatame.service.implementation;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;

public class GymContractServiceImplementation extends AbstractContractServiceImplementation<GymContract> implements GymContractService {
	
	GymContractServiceImplementation(){
		genericClass = GymContract.class;
		entityName = GymContract.class.getName();
	}
	
	@Override
	public Collection<GymContract> list(Gym gym){
		return null;
	}

}
