package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymAction extends AbstractAction<Gym> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private String content = "/skins/clube/gym/list.jsp";
	
	private GymService service;
	
	public void setGymService(GymService service) {
		this.service = service;
	}

	public Collection<Gym> getList(){
		return service.list();
	}

	@Override
	public Gym getEntity(long id) {
		return service.get(id);
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		if(getId() > 0) content = "/skins/clube/gym/info.jsp"; 
		return content;
	}
}