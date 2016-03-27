package br.com.clubetatame.view.member;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.view.ViewAction;

public class CupomAction extends ActionSupport implements ViewAction, SessionAware, MemberAware {

	private static final long serialVersionUID = -5064749246217739955L;

	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	private Member member;

	@Override
	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public Member getMember() {
		return member;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/member/cupom.jsp";
	}
}