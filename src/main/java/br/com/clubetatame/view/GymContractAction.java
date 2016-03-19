package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.ContractService;

public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private String orderBy = "value";
	
	private boolean orderly = true;
	
	private Collection<GymContract> list;
	
	private ContractService<GymContract> contractService;
	
	public void setContractService(ContractService<GymContract> contractService) {
		this.contractService = contractService;
		this.contractService.setGenericClass(GymContract.class);
		this.contractService.setEntityName(GymContract.class.getName());
	}
	
	public Collection<GymContract> getList(){
		return getList(0);
	}

	public Collection<GymContract> getList(int i){
		setPageSize(i);
		setPages((int) Math.floor(contractService.count() * 1f / getPageSize()) + 1);
		list = contractService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
      
        return list;
	}

	@Override
	public GymContract getEntity(long id) {
		return contractService.get(id);
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return (getId() > 0 ? "/skins/clube/gym/contract.jsp" : "/skins/clube/gym/contracts.jsp");
	}
}