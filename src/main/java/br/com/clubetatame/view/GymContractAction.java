package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;
import br.com.clubetatame.service.GymService;

public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private GymContractService service;
	
	public void setGymContractService(GymContractService service) {
		this.service = service;
	}
	
	@Override
	public GymContract getEntity(long id) {
		return service.get(id);
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		String content = "/skins/clube/gym/list.jsp";
		if(getId() > 0) content = "/skins/clube/gym/info.jsp"; 
		return content;
	}
	
	private Collection<GymContract> list;

	public Collection<GymContract> getList(int i){
		if (i > 0) {
    		setPageSize(i);
    		//setPages((int) Math.floor(service.count(true) * 1f / getPageSize()) + 1);
    		//list = service.list(true, getCurrentPage(), getPageSize(), null, null);
    	}        
        return list;
	}
}