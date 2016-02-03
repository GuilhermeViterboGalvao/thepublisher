package br.com.clubetatame.service;

import java.util.Collection;
import java.util.Date;

import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.MemberContract;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface MemberContractService extends Service<MemberContract> {
	
    Collection<MemberContract> list(int page, int pageSize);
    
    Collection<MemberContract> list(int page, int pageSize, String orderBy, String order);
    
    Collection<MemberContract> list(int page, int pageSize, String orderBy, String order, Date end);
    
    ResultList<MemberContract> search(String query, int page, int pageSize);
}