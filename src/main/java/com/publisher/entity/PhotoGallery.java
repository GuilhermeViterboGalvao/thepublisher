package com.publisher.entity;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OrderColumn;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ListIndexBase;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class PhotoGallery extends Article {

	private static final long serialVersionUID = 3531151064896885366L;

	@ListIndexBase(value=1)
	@OrderColumn(name="position")
	@ElementCollection(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)	
	private List<PhotoElement> photos;

	public List<PhotoElement> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoElement> photos) {
		this.photos = photos;
	}
}