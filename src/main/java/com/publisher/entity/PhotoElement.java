package com.publisher.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Parent;

@Embeddable
public class PhotoElement implements Serializable {

	private static final long serialVersionUID = 6163373371597867963L;
	
	@Parent
	private PhotoGallery parent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Photo photo;

	private String description;

	public PhotoGallery getParent() {
		return parent;
	}

	public void setParent(PhotoGallery parent) {
		this.parent = parent;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}