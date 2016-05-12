package br.com.clubetatame.view;

import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class MemberSimpleRegister extends ActionSupport {

	private static final long serialVersionUID = -1950447773626035536L;

	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) { 
		this.memberService = memberService;
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
			result += "O campo 'cpf' é obirgtório.\n";
			hasError = true;
		} else {
			Member member = memberService.getByDocument(document);
			if (member != null) {
				result += "Já existe um usuário cadastrado com esse cpf.";
				hasError = true;				
			}
		}
		if (email == null || email.isEmpty()) {
			result += "O campo 'e-mail' é obrigatório.\n";
			hasError = true;
		} else {
			Member member = memberService.getByEmail(email);
			if (member != null) {
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
			Member member = new Member();
			member.setName(name);
			member.setEmail(email);
			member.setDocument(document);
			member.setActive(false);
			member.setHash(memberService.hash(password));
			member.setCreated(new Date());
			memberService.persist(member);
			EmailUtils.getInstance().sendEmailConfirmationToMember(member);
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