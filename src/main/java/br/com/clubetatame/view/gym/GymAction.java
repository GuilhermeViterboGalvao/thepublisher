package br.com.clubetatame.view.gym;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.view.ViewAction;

public class GymAction extends ActionSupport implements ViewAction, ModelDriven<Gym>, GymAware, SessionAware {

	private static final long serialVersionUID = 4208571918779924631L;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/gym/input.jsp";
	}
	
	@Override
	@SkipValidation
	public String input() {
		return SUCCESS;
	}
	
	public String save() {
		gymService.update(model);
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
		//Security validation - start
		if (gym == null) {
			session.clear();
			return;
		}
		if (gym.getId().longValue() != model.getId().longValue()) {
			addFieldError("id", "Valor inválido para o campo ID.");
		}
		//Security validation - end
		
		//Default validation - start
		if (model.getName() == null || model.getName().isEmpty()) {
			addFieldError("name", "O campo \"nome\" é obrigatório!");
		} else if (model.getName() != null && model.getName().length() <= 3) {
			addFieldError("name", "O campo \"nome\" deve ter mais que três caracteres.");
		}
		if (model.getDocument() == null || model.getDocument().isEmpty()) {
			addFieldError("document", "O campo \"cnpj\" é obrigatório!");
		} else {
			Gym gym = gymService.getByDocument(model.getDocument());
			if (gym != null && !this.gym.getDocument().equals(gym.getDocument())) {
				addFieldError("document", "CNPJ já cadastrado!");	
			}
		}
		if (model.getEmail() == null || model.getEmail().isEmpty()) {
			addFieldError("email", "O campo \"e-mail\" é obrigatório!");
		} else if (model.getEmail() != null && !model.getEmail().contains("@")) {
			addFieldError("email", "E-mail inválido.");
		} else if (model.getEmail() != null && model.getEmail().contains("@")) {
			Gym gym = gymService.getByEmail(model.getEmail());
			if (gym != null && !this.gym.getEmail().equals(gym.getEmail())) {
				addFieldError("email", "Já existe um usuário cadastrado com esse e-mail.");
			}
		}
		if (model.getDescription() == null || model.getDescription().isEmpty()) {
			addFieldError("email", "O campo \"descrição\" é obrigatório!");
		}
		if (model.getSite() == null || model.getSite().isEmpty()) {
			addFieldError("email", "O campo \"site\" é obrigatório!");
		}
		if (model.getContact() == null || model.getContact().isEmpty()) {
			addFieldError("email", "O campo \"contato\" é obrigatório!");
		}
		if (model.getPhone() == null || model.getPhone().isEmpty()) {
			addFieldError("email", "O campo \"telefone\" é obrigatório!");
		}
		if (model.getCity() == null || model.getCity().isEmpty()) {
			addFieldError("email", "O campo \"cidade\" é obrigatório!");
		}
		if (model.getState() == null || model.getState().isEmpty()) {
			addFieldError("email", "O campo \"estado\" é obrigatório!");
		}
		if (model.getAddress() == null || model.getAddress().isEmpty()) {
			addFieldError("email", "O campo \"endereço\" é obrigatório!");
		}
		if (model.getCEP() == null || model.getCEP().isEmpty()) {
			addFieldError("email", "O campo \"cep\" é obrigatório!");
		}
		//Default validation - end
	}
}