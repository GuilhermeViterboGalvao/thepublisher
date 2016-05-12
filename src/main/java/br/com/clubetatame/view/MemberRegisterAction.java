package br.com.clubetatame.view;

import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class MemberRegisterAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = -493215736294584086L;

	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) { 
		this.memberService = memberService;
	}
	
	@Override
	public String execute() throws Exception {
		Member member = new Member();
		member.setName(name);
		member.setEmail(email);
		member.setDocument(document);
		member.setActive(false);
		member.setHash(memberService.hash(password));
		member.setCreated(new Date());
		memberService.persist(member);
		EmailUtils.getInstance().sendEmailConfirmationToMember(member);	
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
			addFieldError("document", "O campo 'cpf' é obirgtório.");
		} else {
			Member member = memberService.getByDocument(document);
			if (member != null) {
				addFieldError("document", "Já existe um usuário cadastrado com esse cpf.");		
			}
		}
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo 'e-mail' é obrigatório.");		
		} else {
			Member member = memberService.getByEmail(email);
			if (member != null) {
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
	
	private String email;
	
	private String password;
	
	private String password2;
	
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
		return "/skins/clube-tatame/pages/MemberRegister.jsp";
	}	
}