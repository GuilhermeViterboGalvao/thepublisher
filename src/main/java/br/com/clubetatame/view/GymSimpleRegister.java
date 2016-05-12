package br.com.clubetatame.view;

import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;

public class GymSimpleRegister extends ActionSupport {

	private static final long serialVersionUID = -6576498704086736786L;

	private GymService gymService;
	
	public void setGymService(GymService gymService) { 
		this.gymService = gymService;
	}
	
	private String result = "";
	
	public String getResult() {
		return result;
	}
	
	@Override
	public String execute() throws Exception {
		boolean hasError = false;
		if (name == null || name.isEmpty()) {
			result = "O campo 'nome' é obrigatório.\n";
			hasError = true;
		}
		if (document == null || document.isEmpty()) {
			result += "O campo 'cnpj' é obirgtório.\n";
			hasError = true;
		} else {
			Gym gym = gymService.getByDocument(document);
			if (gym != null) {
				result += "Já existe um usuário cadastrado com esse cnpj.";
				hasError = true;				
			}
		}
		if (email == null || email.isEmpty()) {
			result += "O campo 'e-mail' é obrigatório.\n";
			hasError = true;
		} else {
			Gym gym = gymService.getByEmail(email);
			if (gym != null) {
				result += "Já existe um usuário cadastrado com esse e-mail.";
				hasError = true;
			}
		}
		if (password == null || password.isEmpty()) {
			result += "O campo 'senha' é obrigatório.\n";
			hasError = true;
		}
		if (password2 == null || password2.isEmpty()) {
			result += "O campo 'redigite a senha' é obrigatório.\n";
			hasError = true;
		}
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			result += "O campo 'senha' e 'repetir senha' precisam ser iguais.";
			hasError = true;
		}
		if (!hasError) {
			Gym gym = new Gym();
			gym.setName(name);
			gym.setEmail(email);
			gym.setDocument(document);
			gym.setActive(false);
			gym.setHash(gymService.hash(password));
			gym.setCreated(new Date());
			gymService.persist(gym);
			EmailUtils.getInstance().sendEmailConfirmationToGym(gym);
			result = SUCCESS;
		}
		return SUCCESS;
	}
	
	private String name;
	
	private String document;
	
	private String email;
	
	private String password;
	
	private String password2;

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
}	