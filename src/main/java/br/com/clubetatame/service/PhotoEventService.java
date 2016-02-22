package br.com.clubetatame.service;

import java.util.Collection;
import java.util.List;
import com.publisher.entity.Photo;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface PhotoEventService {

	List<Photo> listEventsPhotos();
	
	List<Photo> listEventsPhotos(Boolean published);
	
	List<Photo> listEventsPhotos(Boolean published, int page, int pageSize);
		
	List<Photo> listEventsPhotos(Boolean published, int page, int pageSize, String orderBy, String order);
		
	long countEventsPhotos();
	
	long countEventsPhotos(Boolean published);
	
    Collection<Photo> searchEventsPhotos(String query);
    
    ResultList<Photo> searchEventsPhotos(String query, int page, int pageSize);	
}