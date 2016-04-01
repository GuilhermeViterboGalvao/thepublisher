package br.com.clubetatame.view;

import java.util.Collection;
import java.util.Date;

import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;

public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private String orderBy = "value";
	
	private boolean orderly = true;
	
	private Collection<GymContract> contracts;
	
	private GymContractService gymContractService;
	
	public void setGymContractService(GymContractService gymContractService) {
		this.gymContractService = gymContractService;
	}
    
	public Collection<GymContract> getContracts(){
		setPages((int) Math.floor(gymContractService.count() * 1f / getPageSize()) + 1);
		contracts = gymContractService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc", new Date());
      
        return contracts;
	}

	@Override
	public GymContract getEntity(long id) {
		return gymContractService.get(id);
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/gym/contracts.jsp";
	}
}