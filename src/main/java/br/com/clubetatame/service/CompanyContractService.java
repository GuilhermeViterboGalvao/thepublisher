package br.com.clubetatame.service;

import java.util.Collection;

import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.CompanyContract;

public interface CompanyContractService extends ContractService<CompanyContract> {
	
	Collection<CompanyContract> list(Company company);
}