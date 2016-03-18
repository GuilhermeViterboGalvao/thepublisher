package br.com.clubetatame.view;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymAction extends AbstractAction<Gym> {

	private static final long serialVersionUID = -6496746559789779954L;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
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