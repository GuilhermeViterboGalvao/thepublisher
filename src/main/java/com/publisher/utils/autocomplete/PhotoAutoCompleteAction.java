package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.ResultList;

public class PhotoAutoCompleteAction extends AutoCompleteAction {
	
	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}	

	@Override
	public void populate() {
		ResultList<Photo> result = new ResultList<Photo>();
		if (getTerm() == null) {			
			result.setResult(photoService.list(getPage(), getPagesize()));
		} else {
			result = photoService.search(addWildcards(getTerm()), getPage(), getPagesize(), new Sort(new SortField("date", SortField.Type.LONG, true)));
		}
		Collection<Photo> photos = result.getResult();
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		StringBuilder sb = new StringBuilder();
		for (Photo photo : photos) {
			sb.append(photo.getDescription());
			if (photo.getCredits() != null && !photo.getCredits().isEmpty()) {
				sb.append(" <");
				sb.append(photo.getCredits());
				sb.append(">");
			}
			PhotoElement p = new PhotoElement();
			p.description = photo.getDescription();
			if (photo.getCredits() != null && !photo.getCredits().isEmpty()) {
				p.photographer = photo.getCredits();
			}
			p.height = photo.getHeight();
			p.width = photo.getWidth();
			photo.getWidth();
			collection.add(new LabelValue(sb.toString(), Long.toString(photo.getId()), p));
			sb.setLength(0);
		}
		setResult(collection);
	}
	
	public class PhotoElement {
		
		private int width;
		
		private int height;
		
		private String description;
		
		private String photographer;
				
		public void setWidth(int width) {
			this.width = width;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setPhotographer(String photographer) {
			this.photographer = photographer;
		}

		public String getPhotographer() { 
			return photographer; 
		}
		
		public String getDescription() { 
			return description; 
		}
		
		public int getWidth() { 
			return width; 
		}
		
		public int getHeight() { 
			return height; 
		}		
	}//PhotoElement
	
}//PhotoAutoCompleteAction