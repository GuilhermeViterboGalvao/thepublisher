package br.com.clubetatame.view.gym;

import java.util.Collection;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.GymContract;
import br.com.clubetatame.service.GymContractService;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.view.ViewAction;

public class CupomAction extends ActionSupport implements ViewAction, SessionAware, GymAware, ModelDriven<Gym> {

	private static final long serialVersionUID = -5064749246217739955L;

	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private Gym model;
	
	@Override
	public Gym getModel() {
		if (model == null && gym != null) {
			model = gymService.get(gym.getId());
		} else if (model == null) {
			model = new Gym();
		}
		return model;
	}	
	
	private Gym gym;

	@Override
	public void setGym(Gym gym) {
		this.gym = gym;
	}

	@Override
	public Gym getGym() {
		return gym;
	}
	
	private GymContractService gymContractService;
	
	public void setGymContractService(GymContractService gymContractService) {
		this.gymContractService = gymContractService;
	}
	
	@Override
	public String execute() throws Exception {
		if (gym == null) {
			session.clear();
			return LOGIN;
		}
		return SUCCESS;
	}
	
	public boolean isFreeMember() {
		Collection<GymContract> contracts = gymContractService.list(gym);
		if (contracts != null && contracts.size() > 0) {
			return gymContractService.validateContract(contracts.iterator().next()) ? false : true;
		}		
		return true;
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/gym/cupom.jsp";
	}
}