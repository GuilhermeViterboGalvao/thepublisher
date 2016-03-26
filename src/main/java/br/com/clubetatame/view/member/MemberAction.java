package br.com.clubetatame.view.member;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.view.ViewAction;

public class MemberAction extends ActionSupport implements ViewAction, ModelDriven<Member>, MemberAware, SessionAware {

	private static final long serialVersionUID = 4208571918779924631L;
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
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
	
	private Member model;
	
	@Override
	public Member getModel() {
		if (model == null && member != null) {
			model = new Member();
			model.setId(member.getId());
			model.setActive(member.isActive());
			model.setAddress(member.getAddress());
			model.setBirth(member.getBirth());
			model.setCep(member.getCep());
			model.setCreated(member.getCreated());
			model.setCreatedBy(member.getCreatedBy());
			model.setDocument(member.getDocument());
			model.setEmail(member.getEmail());
			model.setFacebookAccessToken(member.getFacebookAccessToken());
			model.setFacebookAccessTokenExpiration(member.getFacebookAccessTokenExpiration());
			model.setFbid(member.getFbid());
			model.setGender(member.getGender());
			model.setHash(member.getHash());
			model.setLastModified(member.getLastModified());
			model.setLastModifiedBy(member.getLastModifiedBy());
			model.setName(member.getName());
		} else if (model == null) {
			model = new Member();
		}
		return model;
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/member/input.jsp";
	}
	
	@Override
	@SkipValidation
	public String input() {
		return SUCCESS;
	}
	
	public String save() {
		memberService.update(model);
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
		//Security validation - start
		if (member == null) {
			session.clear();
			return;
		}
		if (member.getId().longValue() != model.getId().longValue()) {
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
			addFieldError("document", "O campo \"cpf\" é obrigatório!");
		} else {
			Member member = memberService.getByDocument(model.getDocument());
			if (member != null && !this.member.getDocument().equals(member.getDocument())) {
				addFieldError("document", "CPF já cadastrado!");	
			}
		}
		if (model.getEmail() == null || model.getEmail().isEmpty()) {
			addFieldError("email", "O campo \"e-mail\" é obrigatório!");
		} else if (model.getEmail() != null && !model.getEmail().contains("@")) {
			addFieldError("email", "E-mail inválido.");
		} else if (model.getEmail() != null && model.getEmail().contains("@")) {
			Member member = memberService.getByEmail(model.getEmail());
			if (member != null && !this.member.getEmail().equals(member.getEmail())) {
				addFieldError("email", "Já existe um usuário cadastrado com esse e-mail.");
			}
		}
		//Default validation - end
	}
}