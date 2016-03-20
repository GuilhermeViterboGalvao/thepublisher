package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymAction extends AbstractAction<Gym> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private String orderBy = "id";
	
	private boolean orderly = true;
	
	private Collection<Gym> list;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	public Collection<Gym> getList(){
		return getList(0);
	}

	public Collection<Gym> getList(int i){
		setPageSize(i);
		setPages((int) Math.floor(gymService.count() * 1f / getPageSize()) + 1);
		list = gymService.list(true, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
      
        return list;
	}

	@Override
	public Gym getEntity(long id) {
		return gymService.get(id);
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube/gym/info.jsp";
	}
}