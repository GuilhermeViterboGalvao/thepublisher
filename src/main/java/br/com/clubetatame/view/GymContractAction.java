package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.ContractService;

public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private ContractService<GymContract> contractService;
	
	public void setContractService(ContractService<GymContract> contractService) {
		this.contractService = contractService;
		this.contractService.setGenericClass(GymContract.class);
		this.contractService.setEntityName(GymContract.class.getName());
	}
	
	@Override
	public GymContract getEntity(long id) {
		return contractService.get(id);
	}

	private Collection<GymContract> list;

	public Collection<GymContract> getList(int i){
		setPageSize(i);
		setPages((int) Math.floor(contractService.count() * 1f / getPageSize()) + 1);
		list = contractService.list(getCurrentPage(), getPageSize());
      
        return list;
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		String content = "/skins/clube/gym/contracts.jsp";
		if(getId() > 0) content = "/skins/clube/gym/contract.jsp"; 
		return content;
	}
}