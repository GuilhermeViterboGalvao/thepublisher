package br.com.clubetatame.view.gym;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.view.ViewAction;

public class ResetPasswordAction extends ActionSupport implements ViewAction, GymAware, SessionAware, ModelDriven<Gym> {

	private static final long serialVersionUID = -6524385497733332474L;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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
	
	@Override
	public String execute() throws Exception {
		if (!hasFieldErrors()) {
			gym.setHash(gymService.hash(password));
			gymService.update(gym);
			resetWithSuccess = true;	
		}
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
		//Security validation - end
		
		if (oldPassword == null || oldPassword.isEmpty()) {
			addFieldError("oldPassword", "O campo \"senha\" não pode ser nulo ou vazio!");
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo \"nova senha\" não pode ser nulo ou vazio!");
		}
		if (password2 == null || password2.isEmpty()) {
			addFieldError("password2", "O campo \"repetir senha\" não pode ser nulo ou vazio!");
		}
		if (gym != null && oldPassword != null && !oldPassword.isEmpty() && !gymService.hash(oldPassword).equals(gym.getHash())) {
			addFieldError("oldPassword", "Senha atual errada!");
		}
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			addFieldError("password", "Senhas diferentes!");
		}
	}
	
	//Action properties
	
	private String oldPassword;
	
	private String password;
	
	private String password2;
	
	private boolean resetWithSuccess = false;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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
	
	public boolean isResetWithSuccess() {
		return resetWithSuccess;
	}

	public void setResetWithSuccess(boolean resetWithSuccess) {
		this.resetWithSuccess = resetWithSuccess;
	}	

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/gym/resetPassword.jsp";
	}
}