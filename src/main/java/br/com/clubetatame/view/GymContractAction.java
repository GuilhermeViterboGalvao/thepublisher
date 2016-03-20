package br.com.clubetatame.view;

import java.util.Collection;
import java.util.Date;

import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;

public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private String orderBy = "value";
	
	private boolean orderly = true;
	
	private Collection<GymContract> list;
	
	private GymContractService gymContractService;
	
	public void setGymContractService(GymContractService gymContractService) {
		this.gymContractService = gymContractService;
	}
	
    private Date start;
    
    public Date getStart(){
    	return start;
    }
    
    public void setStart(Date start) {
    	this.start = start;
    }    
    
    private Date end;
    
    public Date getEnd (){
    	return end;
    }
    
    public void setEnd(Date end) {
    	this.end = end;
    }
    
	public Collection<GymContract> getList(){
		return getList(0);
	}

	public Collection<GymContract> getList(int i){
		setPageSize(i);
		setPages((int) Math.floor(gymContractService.count() * 1f / getPageSize()) + 1);
		list = gymContractService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
      
        return list;
	}

	@Override
	public GymContract getEntity(long id) {
		return gymContractService.get(id);
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube/gym/contracts.jsp";
	}
}