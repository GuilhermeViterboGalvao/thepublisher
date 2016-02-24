package br.com.clubetatame.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;

import com.publisher.entity.Photo;
import com.publisher.utils.ResultList;
import com.publisher.utils.autocomplete.LabelValue;
import com.publisher.utils.autocomplete.PhotoAutoCompleteAction;
import br.com.clubetatame.service.PhotoEventService;

public class PhotoEventAutoCompleteAction extends PhotoAutoCompleteAction {

	private PhotoEventService photoEventService;
	
	public void setPhotoEventService(PhotoEventService photoEventService) {
		this.photoEventService = photoEventService;
	}
	
	@Override
	public void populate() {
		ResultList<Photo> result = new ResultList<Photo>();
		if (getTerm() == null) {			
			result.setResult(photoEventService.listEventsPhotos(null, getPage(), getPagesize()));
		} else {
			result = photoEventService.searchEventsPhotos(addWildcards(getTerm()), getPage(), getPagesize());
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