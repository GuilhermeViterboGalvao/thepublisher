package br.com.clubetatame.service;

import java.util.Collection;
import java.util.List;
import com.publisher.entity.Photo;
import com.publisher.utils.ResultList;

public interface PhotoEventService {

	List<Photo> listEventsPhotos();
	
	List<Photo> listEventsPhotos(Boolean isActive);
	
	List<Photo> listEventsPhotos(Boolean isActive, int page, int pageSize);
	
	int countEventsPhotos();
	
	int countEventsPhotos(Boolean isActive);
	
    Collection<Photo> searchEventsPhotos(String query);
    
    ResultList<Photo> searchEventsPhotos(String query, int page, int pageSize);	
}