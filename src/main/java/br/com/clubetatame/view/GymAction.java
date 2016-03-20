package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;
import br.com.clubetatame.service.GymService;

public class GymAction extends AbstractAction<Gym> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private String orderBy = "value";
	
	private boolean orderly = true;
	
	private Double value = 0.0;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private GymContractService gymContractService;
	
	public void setGymContractService(GymContractService gymContractService) {
		this.gymContractService = gymContractService;
	}
	
	public Collection<GymContract> getValidContracts(){
		Collection<GymContract> contracts = gymContractService.list(getModel(), getCurrentDate(), value, 0, 0, orderBy, orderly ? "desc" : "asc");
		return gymContractService.list(getModel(), getCurrentDate(), value, 0, 0, orderBy, orderly ? "desc" : "asc");
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