package br.com.clubetatame.view.gym;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.view.ViewAction;

public class LoginAction extends ActionSupport implements ViewAction, SessionAware {

	private static final long serialVersionUID = 4492211315899505188L;

	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}	
	
	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public String execute() throws Exception {
		String returnType = LOGIN;
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo \"E-mail\" é obrigatório.");
			returnType = INPUT;
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo \"Senha\" é obrigatório.");
			returnType = INPUT;
		}
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			Gym gym = gymService.authenticate(email, gymService.hash(password));
			if (gym != null && gym.isActive()) {
				session.put("gym", gym);
				returnType = SUCCESS;
			}
		}
		return returnType;		
	}
	
	//Action properties
	
	private String email;
	
	private String password;
	
	private boolean fromMenu = false;

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
	
	public boolean isFromMenu() {
		return fromMenu;
	}

	public void setFromMenu(boolean fromMenu) {
		this.fromMenu = fromMenu;
	}	

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/gym/loginform.jsp";
	}
}