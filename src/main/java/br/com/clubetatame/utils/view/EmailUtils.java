package br.com.clubetatame.utils.view;

import java.util.Date;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import br.com.clubetatame.entity.Member;

public final class EmailUtils {

	private EmailUtils() { }
	
	private static EmailUtils instance;
	
	private static Log log = LogFactory.getLog(EmailUtils.class);
	
	private static HashMap<String, Date> codes = new HashMap<String, Date>();
	
	public static EmailUtils getInstance() {
		if (instance == null) {
			synchronized (EmailUtils.class) {
				if (instance == null) {
					EmailUtils emailCache = new EmailUtils();
					instance = emailCache; 
				}
			}
		}
		return instance;
	}
	
	public String createCode(Member member) {
		String hash = null;
		if (member != null) {
			Double code = new Double(System.currentTimeMillis() * member.getId());
			hash = String.valueOf(code.hashCode());
			Date date = new Date();
			EmailUtils.codes.put(hash, date);
			log.info("Code " + hash + " created for Member " + member.getName() + "#" + member.getEmail() + "#" + String.valueOf(member.getId()) + " at " + date + ".");			
		} else {
			log.warn("Can not create code, Member is null!");
		}
		return hash;
	}
	
	public boolean validateCode(String hash) {
		//TODO
		return false;
	}
	
	public void sendEmailConfirmationToMember(String to) {
		//TODO Usar javax.mail.*
	}
	
	public void sendEmailConfirmationToGym(String to) {
		//TODO Usar javax.mail.*
	}
}