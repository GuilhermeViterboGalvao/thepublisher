package br.com.clubetatame.manager;

import java.util.Collection;
import com.publisher.entity.Photo;
import com.publisher.manager.PhotoAction;
import com.publisher.utils.ResultList;
import br.com.clubetatame.service.PhotoEventService;

public class PhotoEventAction extends PhotoAction {

	private static final long serialVersionUID = 2731203967689082360L;

	private PhotoEventService photoEventService;
	
	public void setPhotoEventService(PhotoEventService photoEventService) {
		this.photoEventService = photoEventService;
	}
	
	@Override
	protected Photo updateObject(Photo entity) {
		entity = super.updateObject(entity);
		entity.setEvent(true);
		return entity;
	}
	
	@Override
	protected Collection<Photo> generateList() {
		setPages((int)Math.floor(1f * photoEventService.countEventsPhotos() / getPageSize()) + 1);
		Collection<Photo> list = photoEventService.listEventsPhotos(null, getCurrentPage(), getPageSize());
		return list;
	}

	@Override
	protected Collection<Photo> generateSearch() {
		ResultList<Photo> result = photoEventService.searchEventsPhotos(getSearch(), getCurrentPage(), getPageSize());
        setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}	
}