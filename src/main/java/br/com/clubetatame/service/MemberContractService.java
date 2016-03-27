package br.com.clubetatame.service;

import java.util.Collection;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.entity.MemberContract;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface MemberContractService extends ContractService<MemberContract> {
	
	Collection<MemberContract> list(Member member);
	
	boolean validateContract(MemberContract contract);
}