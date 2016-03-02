package br.com.clubetatame.service;

import java.util.Collection;
import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.Gym;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface GymService extends Service<Gym> {

	Gym authenticate(String email, String password);
	
	Collection<Gym> list(Boolean active);
	
    Collection<Gym> list(Boolean active, int page, int pageSize);
    
    Collection<Gym> list(Boolean active, int page, int pageSize, String orderBy, String order);
    
    Collection<Gym> list(float lat, float lon, float distanceInKM);
    
    Collection<Gym> list(float lat, float lon, float distanceInKM, Boolean active);
    
    ResultList<Gym> search(String query, int page, int pageSize);
    
    ResultList<Gym> search(String query, int page, int pageSize, Boolean active);
    
    String hash(String password);
}