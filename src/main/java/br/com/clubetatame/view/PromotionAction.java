package br.com.clubetatame.view;

import java.util.Date;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;
import com.publisher.view.ViewAction;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class PromotionAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = 3778857598157600315L;

	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@Override
	public String execute() throws Exception {
		Member member = new Member();
		member.setName(memberName);
		member.setActive(false);
		member.setEmail(email);		
		member.setAnswer(answer);
		member.setDocument(document);
		member.setCreated(new Date());
		member.setHash(memberService.hash(password));
		memberService.persist(member);
		EmailUtils.getInstance().sendEmailConfirmationToMember(member);	
		success = true;
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
		if (memberName == null || memberName.isEmpty()) {
			addFieldError("name", "O campo \"Nome\" não pode ser nulo ou vazio.");
		}
		if (document == null || document.isEmpty()) {
			addFieldError("document", "O campo \"Cpf\" não pode ser nulo ou vazio.");
		}
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo \"E-mail\" não pode ser nulo ou vazio.");
		} else if (!email.contains("@")) {
			addFieldError("email", "\"E-mail\" inválido.");
		} else {
			Member member = memberService.getByEmail(email);
			if (member != null) {
				addFieldError("email", "Já existe um membro cadastrado com esse e-mail.");	
			}
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo \"Senha\" não pode ser nulo ou vazio.");
		}
		if (password2 == null || password2.isEmpty()) {
			addFieldError("password2", "O campo \"Repetir senha\" não pode ser nulo ou vazio.");
		}
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			addFieldError("password", "As senha não coincidem.");
		}
		if (answer == null || answer.isEmpty()) {
			addFieldError("answer", "O campo \"Resposta\" não pode ser nulo ou vazio.");
		}
	}
	
	private boolean success = false;
	
	public boolean isSuccess() {
		return success;
	}
	
	//Action properties
	
	private String memberName;
	
	private String document;
	
	private String email;
	
	private String email2;
	
	private String password;
	
	private String password2;
	
	private String answer;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String getLayoutPath() {
		return "/skins/tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/tatame/promocoes/2016/minotauro-lenda-do-mma/form.jsp";
	}
}