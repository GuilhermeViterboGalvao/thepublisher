package br.com.clubetatame.view;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.service.MemberService;

public class EmailConfirmationAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = 6726728988730128126L;
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	private boolean expiredCode = false;
	
	public boolean isExpiredCode() {
		return expiredCode;
	}
	
	private String newCode;
	
	public String getNewCode() {
		return newCode;
	}
	
	@Override
	public String execute() throws Exception {
		if (memberId > 0) {
			if (code != null && !code.isEmpty()) {
				expiredCode = EmailUtils.getInstance().validateCode(code);
				if (!expiredCode) {
					Member member = memberService.get(memberId);
					if (member != null) {
						if (!member.isActive()) {
							member.setActive(true);
							memberService.update(member);
							EmailUtils.getInstance().removeCode(code);
						} else {
							errorMessage = "Membro já está ativo.";
						}
					} else {
						errorMessage = "Membro não encontrado.";	
					}
				} else {
					newCode = EmailUtils.getInstance().createCode(memberId);
					errorMessage = "Código expirado.";
				}
			} else {
				errorMessage = "Código inválido.";
			}
		} else if (gymId > 0) {
			if (code != null && !code.isEmpty()) {
				expiredCode = EmailUtils.getInstance().validateCode(code);
				if (!expiredCode) {
					Gym gym = gymService.get(gymId);
					if (gym != null) {
						if (!gym.isActive()) {
							gym.setActive(true);
							gymService.update(gym);
							EmailUtils.getInstance().removeCode(code);
						} else {
							errorMessage = "Academia já está ativa.";	
						}
					} else {						
						errorMessage = "Academia não encontrada.";
					}
				} else {
					newCode = EmailUtils.getInstance().createCode(gymId);
					errorMessage = "Código expirado.";
				}
			} else {
				errorMessage = "Código inválido.";
			}
		} else {
			errorMessage = "Parâmetros inválidos.";
		}
		return SUCCESS;
	}
	
	//Action properties
	
	private long memberId;

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	
	private long gymId;

	public long getGymId() {
		return gymId;
	}

	public void setGymId(long gymId) {
		this.gymId = gymId;
	}
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/email/info.jsp";
	}
}