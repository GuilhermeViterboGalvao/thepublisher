package br.com.clubetatame.service;

import java.util.Collection;
import java.util.List;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface PhotoGymService extends PhotoService {

	List<Photo> listGymsPhotos();
	
	List<Photo> listGymsPhotos(Boolean published);
	
	List<Photo> listGymsPhotos(Boolean published, int page, int pageSize);
		
	List<Photo> listGymsPhotos(Boolean published, int page, int pageSize, String orderBy, String order);
		
	long countGymsPhotos();
	
	long countGymsPhotos(Boolean published);
	
    Collection<Photo> searchGymsPhotos(String query);
    
    ResultList<Photo> searchGymsPhotos(String query, int page, int pageSize);	
}