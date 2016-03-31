package br.com.clubetatame.service;

import java.util.Collection;
import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.Company;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface CompanyService extends Service<Company> {
	
	Collection<Company> list(Boolean active);
	
    Collection<Company> list(Boolean active, int page, int pageSize);
    
    Collection<Company> list(Boolean active, int page, int pageSize, String orderBy, String order);
    
    Collection<Company> list(float lat, float lon, float distanceInKM);
    
    Collection<Company> list(float lat, float lon, float distanceInKM, Boolean active);
    
    ResultList<Company> search(String query, int page, int pageSize);
    
    ResultList<Company> search(String query, int page, int pageSize, Boolean active);
}