package br.com.clubetatame.service;

import java.util.Collection;
import java.util.Date;

import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.GymContract;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface GymContractService extends Service<GymContract> {
	
    Collection<GymContract> list(int page, int pageSize);
    
    Collection<GymContract> list(int page, int pageSize, String orderBy, String order);
    
    Collection<GymContract> list(int page, int pageSize, String orderBy, String order, Date end);
    
    ResultList<GymContract> search(String query, int page, int pageSize);
}