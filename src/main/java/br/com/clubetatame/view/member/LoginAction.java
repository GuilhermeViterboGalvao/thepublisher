package br.com.clubetatame.view.member;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.view.ViewAction;

public class LoginAction extends ActionSupport implements ViewAction, SessionAware {

	private static final long serialVersionUID = 4492211315899505188L;

	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}	
	
	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public String execute() throws Exception {
		Member member = memberService.authenticate(email, memberService.hash(password));
		if (member == null || !member.isActive()) {
			return LOGIN;
		}
		session.put("member", member);
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo \"E-mail\" é obrigatório.");
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo \"Senha\" é obrigatório.");
		}
	}
	
	//Action properties
	
	private String email;
	
	private String password;

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

	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube/member/loginform.jsp";
	}
}