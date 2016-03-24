package br.com.clubetatame.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.view.EmailUtils;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.service.MemberService;

public class EmailTestAction extends ActionSupport implements ViewAction {

	private static final long serialVersionUID = 8995978816219688276L;
	
	private static Log log = LogFactory.getLog(EmailTestAction.class);
	
	private static final String password = "!!!112233123456789445566!!!";
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public String execute() throws Exception {
		if (hash != null && !hash.isEmpty() && hash.equals(password)) {
			if (memberId > 0) {
				Member member = memberService.get(memberId);
				if (member != null) {
					if (customEmail != null && !customEmail.isEmpty()) {
						member.setEmail(customEmail);
						EmailUtils.getInstance().sendEmailConfirmationToMember(member);
						log.info("MEMBER - Send custom e-mail to " + customEmail + ", faking member ID " + memberId + ".");
					} else {
						EmailUtils.getInstance().sendEmailConfirmationToMember(member);
						log.info("MEMBER - Send custom e-mail to " + customEmail + ",  member ID " + memberId + ".");
					}
					success = true;
				}
			} else if (gymId > 0) {
				Gym gym = gymService.get(gymId);
				if (gym != null) {
					if (customEmail != null && !customEmail.isEmpty()) {
						gym.setEmail(customEmail);
						EmailUtils.getInstance().sendEmailConfirmationToGym(gym);
						log.info("GYM - Send custom e-mail to " + customEmail + ", faking gym ID " + gymId + ".");
					} else {
						EmailUtils.getInstance().sendEmailConfirmationToGym(gym);
						log.info("GYM - Send custom e-mail to " + customEmail + ",  gym ID " + gymId + ".");
					}
					success = true;
				}				
			} else {
				log.error("Missing parameters!");	
			}
		} else {
			log.error("Wrong password!");
		}
		return SUCCESS;
	}
	
	private boolean success = false;
	
	public boolean isSuccess() {
		return success;
	}
	
	//Action properties
	
	private String hash;
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	private String customEmail;
	
	public void setCustomEmail(String customEmail) {
		this.customEmail = customEmail;
	}
	
	private long gymId;
	
	public void setGymId(long gymId) {
		this.gymId = gymId;
	}
	
	private long memberId;
	
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String getLayoutPath() {
		return "/skins/clube/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube/email/test.jsp";
	}
}