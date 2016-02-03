package br.com.clubetatame.service;

import java.util.Collection;
import java.util.Date;

import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.CompanyContract;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface CompanyContractService extends Service<CompanyContract> {
	
    Collection<CompanyContract> list(int page, int pageSize);
    
    Collection<CompanyContract> list(int page, int pageSize, String orderBy, String order);
    
    Collection<CompanyContract> list(int page, int pageSize, String orderBy, String order, Date end);
    
    ResultList<CompanyContract> search(String query, int page, int pageSize);
}