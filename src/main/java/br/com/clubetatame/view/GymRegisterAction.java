package br.com.clubetatame.view;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymRegisterAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = 3555680572941192709L;

	private GymService gymService;
	
	public void setGymService(GymService gymService) { 
		this.gymService = gymService;
	}
	
	@Override
	public String execute() throws Exception {
		Gym gym = new Gym();
		gym.setName(name);
		gym.setDocument(document);
		gym.setAddress(address);
		gym.setCep(cep);
		gym.setCity(city);
		gym.setState(state);
		gym.setSite(site);
		gym.setEmail(email);
		gym.setActive(false);
		gym.setHash(gymService.hash(password));
		gym.setPhone(phone);
		gymService.persist(gym);
		EmailUtils.getInstance().sendEmailConfirmationToGym(gym);	
		createWithSuccess = true;
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
		if (name == null || name.isEmpty()) {
			addFieldError("name", "O campo 'nome' é obrigatório.");
		}
		if (document == null || document.isEmpty()) {
			addFieldError("document", "O campo 'cnpj' é obirgtório.");
		} else {
			Gym gym = gymService.getByDocument(document);
			if (gym != null) {
				addFieldError("document", "Já existe um usuário cadastrado com esse cnpj.");		
			}
		}
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo 'e-mail' é obrigatório.");		
		} else {
			Gym gym = gymService.getByEmail(email);
			if (gym != null) {
				addFieldError("email", "Já existe um usuário cadastrado com esse e-mail.");	
			}
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo 'senha' é obrigatório.");
		}
		if (password2 == null || password2.isEmpty()) {
			addFieldError("password2", "O campo 'redigite a senha' é obrigatório.");
		}
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			addFieldError("password", "O campo 'senha' e 'repetir senha' precisam ser iguais.");
		}		
	}
	
	//POJO
	
	private String name;
	
	private String document;
	
	private String address;
	
	private String cep;
	
	private String city;
	
	private String state;
	
	private String site;
	
	private String email;
	
	private String password;
	
	private String password2;
	
	private String phone;
	
	private boolean fromMenu = false;
	
	private boolean createWithSuccess = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isFromMenu() {
		return fromMenu;
	}

	public void setFromMenu(boolean fromMenu) {
		this.fromMenu = fromMenu;
	}
	
	public boolean isCreateWithSuccess() {
		return createWithSuccess;
	}

	public void setCreateWithSuccess(boolean createWithSuccess) {
		this.createWithSuccess = createWithSuccess;
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/pages/GymRegister.jsp";
	}	
}