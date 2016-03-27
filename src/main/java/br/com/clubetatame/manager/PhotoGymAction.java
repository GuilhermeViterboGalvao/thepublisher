package br.com.clubetatame.manager;

import java.util.Collection;
import com.publisher.entity.Photo;
import com.publisher.manager.PhotoAction;
import com.publisher.utils.ResultList;
import br.com.clubetatame.service.PhotoGymService;

public class PhotoGymAction extends PhotoAction {

	private static final long serialVersionUID = 8127167197100868577L;
	
	private PhotoGymService photoGymService;
	
	public void setPhotoGymService(PhotoGymService photoGymService) {
		this.photoGymService = photoGymService;
	}
	
	@Override
	protected Photo updateObject(Photo entity) {
		entity = super.updateObject(entity);
		entity.setGym(true);
		return entity;
	}
	
	@Override
	protected Collection<Photo> generateList() {
		setPages((int)Math.floor(1f * photoGymService.countGymsPhotos() / getPageSize()) + 1);
		Collection<Photo> list = photoGymService.listGymsPhotos(null, getCurrentPage(), getPageSize());
		return list;
	}

	@Override
	protected Collection<Photo> generateSearch() {
		ResultList<Photo> result = photoGymService.searchGymsPhotos(getSearch(), getCurrentPage(), getPageSize());
        setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}	
}