package br.com.clubetatame.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;

import com.publisher.entity.Photo;
import com.publisher.utils.ResultList;
import com.publisher.utils.autocomplete.LabelValue;
import com.publisher.utils.autocomplete.PhotoAutoCompleteAction;
import br.com.clubetatame.service.PhotoGymService;

public class PhotoGymAutoCompleteAction extends PhotoAutoCompleteAction {

	private PhotoGymService photoGymService;
	
	public void setPhotoGymService(PhotoGymService photoGymService) {
		this.photoGymService = photoGymService;
	}
	
	@Override
	public void populate() {
		ResultList<Photo> result = new ResultList<Photo>();
		if (getTerm() == null) {			
			result.setResult(photoGymService.listGymsPhotos(null, getPage(), getPagesize()));
		} else {
			result = photoGymService.searchGymsPhotos(addWildcards(getTerm()), getPage(), getPagesize());
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
			p.setDescription(photo.getDescription());
			if (photo.getCredits() != null && !photo.getCredits().isEmpty()) {
				p.setPhotographer(photo.getCredits());
			}
			p.setHeight(photo.getHeight());
			p.setWidth(photo.getWidth());
			photo.getWidth();
			collection.add(new LabelValue(sb.toString(), Long.toString(photo.getId()), p));
			sb.setLength(0);
		}
		setResult(collection);
	}
}