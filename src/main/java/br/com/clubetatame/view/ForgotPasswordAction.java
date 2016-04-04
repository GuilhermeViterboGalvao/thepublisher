package br.com.clubetatame.view;

import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;

import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.utils.PasswordRecoverUtils;

public class ForgotPasswordAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = -2446722743863909835L;
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@SkipValidation
	public String formReset() {
		return (result = "form-reset");
	}
		
	public String reset() {
		String hash = null;
		if (isMember) {
			Member member = memberService.getByEmail(email);
			hash = PasswordRecoverUtils.getInstance().createCode(member.getId());
		} else if (isGym) {
			Gym gym = gymService.getByEmail(email);
			hash = PasswordRecoverUtils.getInstance().createCode(gym.getId());
		}
		if (hash != null && !hash.isEmpty()) {
			PasswordRecoverUtils.getInstance().addEmailAndCode(hash, email);
			if (isMember) {
				EmailUtils.getInstance().sendEmailResetPasswordToMember(hash, email);
			} else if (isGym) {
				EmailUtils.getInstance().sendEmailResetPasswordToGym(hash, email);
			}
		}
		success = true;
		return (result = "form-reset");
	}
	
	@SkipValidation
	public String fromConfirm() {
		return (result = "form-confirm");
	}
	
	@SkipValidation
	public String confirm() {
		if (hash == null || hash.isEmpty()) {
			return (result = ERROR);
		}
		if (email == null || email.isEmpty()) {
			return (result = ERROR);
		}
		if (!isMember && !isGym) {
			return (result = ERROR);
		}
		if (isMember) {
			Member member = memberService.getByEmail(email);
			if (member == null) {
				return (result = ERROR);	
			}
		}
		if (isGym) {
			Gym gym = gymService.getByEmail(email);
			if (gym == null) {
				return (result = ERROR);
			}
		}
		if(!PasswordRecoverUtils.getInstance().confirm(hash, email)) {
			return (result = "redirect");
		}		
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo \"senha\" não pode ser nulo ou vazio!");
		}
		if (password2 == null || password2.isEmpty()) {
			addFieldError("password", "O campo \"repetir senha\" não pode ser nulo ou vazio!");
		} 
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			addFieldError("password", "O campo \"senha\" e \"repetir senha\" precisam ter os mesmos valores.");
		}
		if (hasFieldErrors()) {
			return (result = "form-confirm");
		}
		if (isMember) {
			Member member = memberService.getByEmail(email);
			member.setHash(memberService.hash(password));
			memberService.update(member);
		} else if (isGym) {
			Gym gym = gymService.getByEmail(email);
			gym.setHash(gymService.hash(password));
			gymService.update(gym);
		}
		PasswordRecoverUtils.getInstance().removeHash(hash);
		success = true;
		return (result = "form-confirm");
	}
	
	@Override
	public void validate() {
		super.validate();
		if (!isMember && !isGym) {
			addFieldError("isMember", "É necessário selecionar uma das opções \"sou membro\" ou \"sou uma academia\".");
		}
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo e-mail não pode ser nulo ou vazio!");
		}
		if (isMember && email != null && !email.isEmpty()) {
			if (memberService.getByEmail(email) == null) {
				addFieldError("email", "Membro não encontrado com o e-mail \"" + email + "\".");	
			}
		}
		if (isGym && email != null && !email.isEmpty()) {
			if (gymService.getByEmail(email) == null) {
				addFieldError("email", "Academia não encontrada com o e-mail \"" + email + "\".");
			}
		}
	}
	
	private String result = "form-reset";
	
	private boolean success = false;
	
	public boolean isSuccess() {
		return success;
	}
	
	//Action propterties
	
	private String email;
	
	private boolean isMember = false;
	
	private boolean isGym = false;
	
	private String hash;
	
	private String password;
	
	private String password2;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setIsMember(boolean isMember) {
		this.isMember = isMember;
	}

	public boolean isGym() {
		return isGym;
	}

	public void setIsGym(boolean isGym) {
		this.isGym = isGym;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		if (result.equals(INPUT)) {
			return "/skins/clube-tatame/forgot-password/form-reset.jsp";
		} else if (result.equals("form-reset")) {
			return "/skins/clube-tatame/forgot-password/form-reset.jsp";			
		} else if (result.equals("form-confirm")) {
			return "/skins/clube-tatame/forgot-password/form-confirm.jsp";
		} else if (result.equals(ERROR)) {
			return "/skins/clube-tatame/forgot-password/error.jsp"; 
		}	
		return "/skins/clube-tatame/forgot-password/form-reset.jsp";
	}
}