package br.com.clubetatame.service;

import java.util.Collection;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.entity.MemberContract;

public interface MemberContractService extends ContractService<MemberContract> {
	
	Collection<MemberContract> list(Member member);
}