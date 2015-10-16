package com.publisher.service;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Photo;
import com.publisher.utils.ResultList;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface PhotoService extends Service<Photo> {

	Collection<Photo> list(int page, int pageSize);
	
	Collection<Photo> list(int page, int pageSize, Date publishedUntil);
	
	Collection<Photo> list(int page, int pageSize, Date publishedUntil, Boolean published);
	
	ResultList<Photo> search(String query, int page, int pageSize);
	
	long count(Boolean published);
	
    long setPictureToUploadFolder(File picture);
    
    long setPictureToUploadFolder(File picture, File dir);
    
    File getPictureFromUploadFolder(long id);
    
    File getPictureFromUploadFolder(long id, File dir);
    
    void removePictureFromUploadFolder(long id);
    
    void removePictureFromUploadFolder(long id, File dir);
    
    void removePictureFromUploadTempFolder(long id);
    
    void removePictureFromUploadTempFolder(long id, File dir);	
}