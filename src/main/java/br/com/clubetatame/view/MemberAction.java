package br.com.clubetatame.view;

import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class MemberAction extends ActionSupport implements ViewAction, ModelDriven<Member> {

	private static final long serialVersionUID = -8560482733635952373L;
	
	private MemberService memberService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@SkipValidation
	public String input() {
		return "input";
	}
	
	public String save() {
		memberService.persist(model);		
		//TODO mandar e-mail de confirmação
		//Após a confirmação, colocar usuário como ativo.		
		return "save";
	}
	
	private String resultMessage;
	
	public String getResult() {
		return resultMessage;
	}
	
	@SkipValidation
	public String json() {
		//TODO
		return "json";
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube/member/input.jsp";
	}
	
	@Override
	public void validate() {
		super.validate();
		if (model.getName() == null || model.getName().isEmpty()) {
			addFieldError("name", "O campo \"nome\" é obrigatório!");
		} else if (model.getName() != null && model.getName().length() <= 3) {
			addFieldError("name", "O campo \"nome\" deve ter mais que três caracteres.");
		}
		if (model.getDocument() == null || model.getDocument().isEmpty()) {
			addFieldError("document", "O campo \"cpf\" é obrigatório!");
		}
		if (model.getEmail() == null || model.getEmail().isEmpty()) {
			addFieldError("email", "O campo \"e-mail\" é obrigatório!");
		} else if (model.getEmail() != null && !model.getEmail().contains("@")) {
			addFieldError("email", "E-mail inválido.");
		} else if (model.getEmail() != null && model.getEmail().contains("@")) {
			Member member = memberService.getByEmail(model.getEmail());
			if (member != null && member.isActive()) {
				addFieldError("email", "Já existe um usuário cadastrado com esse e-mail.");
			}
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", "O campo \"senha\" é obrigatório!");
		}
		if (password2 == null || password2.isEmpty()) {
			addFieldError("password2", "O campo \"redigite a senha\" é obrigatório!");
		}
		if (password != null && !password.isEmpty() && password2 != null && !password2.isEmpty() && !password.equals(password2)) {
			addFieldError("password", "As senhas estão diferentes.");
		}		
	}
	
	//Action properties
	
	private Member model;
	
	@Override
	public Member getModel() {
		if (id > 0) {
			model = memberService.get(id);
		} else {
			model = new Member();
		}
		return model;
	}
	
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}	
	
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private String password2;
	
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
}