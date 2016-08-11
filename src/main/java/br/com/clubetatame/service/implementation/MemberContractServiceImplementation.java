package br.com.clubetatame.service.implementation;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Query;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.entity.MemberContract;
import br.com.clubetatame.service.MemberContractService;

public class MemberContractServiceImplementation extends AbstractContractServiceImplementation<MemberContract> implements MemberContractService{
	
	public MemberContractServiceImplementation() {
		genericClass = MemberContract.class;
		entityName = MemberContract.class.getSimpleName();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public Collection<MemberContract> list(Member member){
		if (member != null) {
			StringBuilder sql = new StringBuilder();
			sql.append("from MemberContract c where c.member=:member ");
			sql.append("order by c.start desc");
			Query query = entityManager.createQuery(sql.toString());
			query.setParameter("member", member);
			return query.getResultList();
		}
		return null;
	}

	@Override
	public boolean validateContract(MemberContract contract) {
		if (contract != null) {
			Date today = new Date();
			return contract.getEnd() != null && contract.getEnd().after(today);
		}
		return false;
	}	
}