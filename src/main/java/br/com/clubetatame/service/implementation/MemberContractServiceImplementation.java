package br.com.clubetatame.service.implementation;

import java.util.Collection;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.entity.MemberContract;
import br.com.clubetatame.service.MemberContractService;


public class MemberContractServiceImplementation extends AbstractContractServiceImplementation<MemberContract> implements MemberContractService{
	
	MemberContractServiceImplementation(){
		genericClass = MemberContract.class;
		entityName = MemberContract.class.getSimpleName();
	}
	
	@Override
	public Collection<MemberContract> list(Member member){
		return null;
	}
	
}