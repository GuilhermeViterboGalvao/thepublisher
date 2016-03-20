package br.com.clubetatame.service.implementation;

import java.util.Collection;

import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.CompanyContract;
import br.com.clubetatame.service.CompanyContractService;

public class CompanyContractServiceImplementation extends AbstractContractServiceImplementation<CompanyContract> implements CompanyContractService{
	
	CompanyContractServiceImplementation(){
		genericClass = CompanyContract.class;
		entityName = CompanyContract.class.getName();
	}
	
	@Override
	public Collection<CompanyContract> list(Company company){
		return null;
	}

}
