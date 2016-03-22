package com.publisher.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import br.com.clubetatame.entity.Gym;
import br.com.clubetatame.entity.Member;

public final class EmailUtils {

	private EmailUtils() { }
	
	private static EmailUtils instance;
	
	private static Log log = LogFactory.getLog(EmailUtils.class);
	
	private static HashMap<String, Date> codes = new HashMap<String, Date>();
	
	private static String memberTemplate;
	
	private static String gymTemplate;
	
	static {
		memberTemplate  = "<html>";
		memberTemplate += "   <head>";
		memberTemplate += "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>";
		memberTemplate += "      <style type=\"text/css\">";
		memberTemplate += "         .html, body { float: left; width: 100%; }";
		memberTemplate += "         .message { float: left; width: 100%; margin: 2% 0; }";
		memberTemplate += "         .message p { float: left; width: 100%; font-size: 1.5em; line-height: 2em; color: #000; }";
		memberTemplate += "         .message p a { float: left; width: 100%; text-decoration: none; color: red; }";
		memberTemplate += "      </style>";
		memberTemplate += "   </head>";
		memberTemplate += "   <body>";
		memberTemplate += "      <section class=\"message\">";
		memberTemplate += "         <p>Para finalizar o seu cadastro clique <a href=\"{confirmationLink}\">AQUI.</a></p>";
		memberTemplate += "      </section>";
		memberTemplate += "   </body>";
		memberTemplate += "</html>";
		
		gymTemplate  = "<html>";
		gymTemplate += "   <head>";
		gymTemplate += "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>";
		gymTemplate += "      <style type=\"text/css\">";
		gymTemplate += "         .html, body { float: left; width: 100%; }";
		gymTemplate += "         .message { float: left; width: 100%; margin: 2% 0; }";
		gymTemplate += "         .message p { float: left; width: 100%; font-size: 1.5em; line-height: 2em; color: #000; }";
		gymTemplate += "         .message p a { float: left; width: 100%; text-decoration: none; color: red; }";
		gymTemplate += "      </style>";
		gymTemplate += "   </head>";
		gymTemplate += "   <body>";
		gymTemplate += "      <section class=\"message\">";
		gymTemplate += "         <p>Para finalizar o seu cadastro clique <a href=\"{confirmationLink}\">AQUI.</a></p>";
		gymTemplate += "      </section>";
		gymTemplate += "   </body>";
		gymTemplate += "</html>";		
	}
	
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
	
	public String createCode(Long id) {
		String hash = null;
		if (id != null && id > 0) {
			Double code = new Double(System.currentTimeMillis() * id);
			hash = String.valueOf(code.hashCode());
			Date date = new Date();
			EmailUtils.codes.put(hash, date);
			log.info("Code " + hash + " created.");			
		} else {
			log.warn("Can not create code, ID is null!");
		}
		return hash;
	}
	
	public boolean validateCode(String hash) {
		//TODO
		return false;
	}
	
	public void sendEmailConfirmationToMember(Member member) {
		if (member != null && member.getEmail() != null && !member.getEmail().isEmpty() && member.getEmail().contains("@")) {
			final String userName = "confirmacao.clube@tatame.com.br";
			final String password = "club3t4t4m3";
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			final String hash = createCode(member.getId());
			try {
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(userName, password);
					}
				});
				String confirmationLink = "http://clube.tatame.com.br/clube/emailConfirmation";
				confirmationLink += "?memberId=" + member.getId();
				confirmationLink += "&code=" + hash;
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(member.getEmail()));
				message.setSubject("Clube Tatame - Confirmação de cadastro de membro");
				message.setText(memberTemplate.replace("{confirmationLink}", confirmationLink));
				Transport.send(message);
				log.info("E-mail send to " + member.getEmail() + " with code " + hash + ".");
			} catch (Exception e) {
				log.error(e);
			}	
		} else {
			if (member == null) {
				log.error("Member is null.");	
			} else {
				log.error("Member " + member.getId() + " has an empty e-mail, or has an invalid e-mail.");
			}			
		}
	}
	
	public void sendEmailConfirmationToGym(Gym gym) {
		if (gym != null && gym.getEmail() != null && !gym.getEmail().isEmpty() && gym.getEmail().contains("@")) {
			final String userName = "confirmacao.clube@tatame.com.br";
			final String password = "club3t4t4m3";
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			final String hash = createCode(gym.getId());
			try {
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(userName, password);
					}
				});
				String confirmationLink = "http://clube.tatame.com.br/clube/emailConfirmation";
				confirmationLink += "?gymId=" + gym.getId();
				confirmationLink += "&code=" + hash;
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gym.getEmail()));
				message.setSubject("Clube Tatame - Confirmação de cadastro de academia");
				message.setText(gymTemplate.replace("{confirmationLink}", confirmationLink));
				Transport.send(message);
				log.info("E-mail send to " + gym.getEmail() + " with code " + hash + ".");
			} catch (Exception e) {
				log.error(e);
			}	
		} else {
			if (gym == null) {
				log.error("Gym is null.");	
			} else {
				log.error("Gym " + gym.getId() + " has an empty e-mail, or has an invalid e-mail.");
			}			
		}
	}
}