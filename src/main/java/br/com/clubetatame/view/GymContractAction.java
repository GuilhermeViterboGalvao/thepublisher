package br.com.clubetatame.view;

import java.util.Collection;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.ContractService;
import br.com.clubetatame.service.GymContractService;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.service.ProductService;

public class GymContractAction extends AbstractAction<GymContract> {

	private static final long serialVersionUID = -5054133042718478840L;
	
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
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