package br.com.clubetatame.view.member;

import java.util.Collection;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.entity.MemberContract;
import br.com.clubetatame.service.MemberContractService;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.view.ViewAction;

public class CupomAction extends ActionSupport implements ViewAction, SessionAware, MemberAware, ModelDriven<Member> {

	private static final long serialVersionUID = -5064749246217739955L;

	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	private Member model;
	
	@Override
	public Member getModel() {
		if (model == null && member != null) {
			model = memberService.get(member.getId());
		} else if (model == null) {
			model = new Member();
		}
		return model;
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
	
	private MemberContractService memberContractService;
	
	public void setMemberContractService(MemberContractService memberContractService) {
		this.memberContractService = memberContractService;
	}
	
	@Override
	public String execute() throws Exception {
		if (member == null) {
			session.clear();
			return LOGIN;
		}
		return SUCCESS;
	}
	
	public boolean isFreeMember() {
		Collection<MemberContract> contracts = null;
		try {
			contracts = memberContractService.list(member);
		} catch (Exception e) { }
		
		if (contracts != null && contracts.size() > 0) {
			return memberContractService.validateContract(contracts.iterator().next()) ? false : true;
		}		
		return true;
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