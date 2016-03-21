package br.com.clubetatame.view;

import com.opensymphony.xwork2.ActionSupport;

import br.com.clubetatame.service.MemberService;

public class MemberSimpleRegister extends ActionSupport {

	private static final long serialVersionUID = -1950447773626035536L;

	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) { 
		this.memberService = memberService;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
}