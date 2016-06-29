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
	
	private static String resetPasswordTemplate;
	
	static {
		memberTemplate  = "<html>";
		memberTemplate += "   <head>";
		memberTemplate += "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>";
		memberTemplate += "      <style type=\"text/css\">";
		memberTemplate += "         .html, body { float: left; width: 100%; }";
		memberTemplate += "         .title { float: left; width: 100%; margin: 5% 0; font-size: 2em; line-height: 2.2em; color: #000; text-align: center; }";
		memberTemplate += "         .message { float: left; width: 100%; margin: 2% 0; }";
		memberTemplate += "         .message p { float: left; width: 100%; font-size: 1.5em; line-height: 2em; color: #000; text-align: center; }";
		memberTemplate += "         .message p a { text-decoration: none; color: red; }";
		memberTemplate += "      </style>";
		memberTemplate += "   </head>";
		memberTemplate += "   <body>";
		memberTemplate += "      <p class=\"title\">Clube Tatame - Confirmação de cadastro</p>";
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
		gymTemplate += "         .title { float: left; width: 100%; margin: 5% 0; font-size: 2em; line-height: 2.2em; color: #000; text-align: center; }";
		gymTemplate += "         .message { float: left; width: 100%; margin: 2% 0; }";
		gymTemplate += "         .message p { float: left; width: 100%; font-size: 1.5em; line-height: 2em; color: #000; text-align: center; }";
		gymTemplate += "         .message p a { text-decoration: none; color: red; }";
		gymTemplate += "      </style>";
		gymTemplate += "   </head>";
		gymTemplate += "   <body>";
		gymTemplate += "      <p class=\"title\">Clube Tatame - Confirmação de cadastro</p>";
		gymTemplate += "      <section class=\"message\">";
		gymTemplate += "         <p>Para finalizar o seu cadastro clique <a href=\"{confirmationLink}\">AQUI.</a></p>";
		gymTemplate += "      </section>";
		gymTemplate += "   </body>";
		gymTemplate += "</html>";
		
		resetPasswordTemplate  = "<html>";
		resetPasswordTemplate += "   <head>";
		resetPasswordTemplate += "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>";
		resetPasswordTemplate += "      <style type=\"text/css\">";
		resetPasswordTemplate += "         .html, body { float: left; width: 100%; }";
		resetPasswordTemplate += "         .title { float: left; width: 100%; margin: 5% 0; font-size: 2em; line-height: 2.2em; color: #000; text-align: center; }";
		resetPasswordTemplate += "         .message { float: left; width: 100%; margin: 2% 0; }";
		resetPasswordTemplate += "         .message p { float: left; width: 100%; font-size: 1.5em; line-height: 2em; color: #000; text-align: center; }";
		resetPasswordTemplate += "         .message p a { text-decoration: none; color: red; }";
		resetPasswordTemplate += "      </style>";
		resetPasswordTemplate += "   </head>";
		resetPasswordTemplate += "   <body>";
		resetPasswordTemplate += "      <p class=\"title\">Clube Tatame - Alteração de senha</p>";
		resetPasswordTemplate += "      <section class=\"message\">";
		resetPasswordTemplate += "         <p>Para alterar a sua senha, clique <a href=\"{confirmationLink}\">AQUI.</a></p>";
		resetPasswordTemplate += "      </section>";
		resetPasswordTemplate += "   </body>";
		resetPasswordTemplate += "</html>";		
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
	
	public void removeCode(String hash) {
		EmailUtils.codes.remove(hash);
	}
	
	public boolean validateCode(String hash) {
		if (hash != null && hash.isEmpty()) {
			Date currentDate = new Date();
			Date date = EmailUtils.codes.get(hash);
			long expirationTime = 24 * 60 * 60 * 1000;//24h
			return (date.getTime() - currentDate.getTime()) <= expirationTime;
		}	
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
				String confirmationLink = "http://tatame.com.br/clube/emailConfirmation";
				confirmationLink += "?memberId=" + member.getId();
				confirmationLink += "&code=" + hash;
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(member.getEmail()));
				message.setSubject("Clube Tatame - Confirmação de cadastro de membro");
				message.setContent(memberTemplate.replace("{confirmationLink}", confirmationLink), "text/html; charset=utf-8");
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
				String confirmationLink = "http://tatame.com.br/clube/emailConfirmation";
				confirmationLink += "?gymId=" + gym.getId();
				confirmationLink += "&code=" + hash;
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gym.getEmail()));
				message.setSubject("Clube Tatame - Confirmação de cadastro de academia");
				message.setContent(gymTemplate.replace("{confirmationLink}", confirmationLink), "text/html; charset=utf-8");
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
	
	public void sendEmailResetPasswordToMember(String hash, String email) {
		if (hash != null && !hash.isEmpty() && email != null && !email.isEmpty()) {
			final String userName = "confirmacao.clube@tatame.com.br";
			final String password = "club3t4t4m3";
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			try {
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(userName, password);
					}
				});
				String confirmationLink = "http://tatame.com.br/clube/forgotPassword-fromConfirm";
				confirmationLink += "?isMember=true";
				confirmationLink += "&hash=" + hash;
				confirmationLink += "&email=" + email;
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Clube Tatame - Alteração de senha");
				message.setContent(resetPasswordTemplate.replace("{confirmationLink}", confirmationLink), "text/html; charset=utf-8");
				Transport.send(message);
				log.info("E-mail send to " + email + " with code " + hash + ".");
			} catch (Exception e) {
				log.error(e);
			}	
		} else {
			if (hash == null || hash.isEmpty()) {
				log.error("Hash is null or empty.");
			} else if (email == null || email.isEmpty()) {
				log.error("E-mail is null or empty.");
			}
		}		
	}
	
	public void sendEmailResetPasswordToGym(String hash, String email) {
		if (hash != null && !hash.isEmpty() && email != null && !email.isEmpty()) {
			final String userName = "confirmacao.clube@tatame.com.br";
			final String password = "club3t4t4m3";
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			try {
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(userName, password);
					}
				});
				String confirmationLink = "http://tatame.com.br/clube/forgotPassword-fromConfirm";
				confirmationLink += "?isGym=true";
				confirmationLink += "&hash=" + hash;
				confirmationLink += "&email=" + email;
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Clube Tatame - Alteração de senha");
				message.setContent(resetPasswordTemplate.replace("{confirmationLink}", confirmationLink), "text/html; charset=utf-8");
				Transport.send(message);
				log.info("E-mail send to " + email + " with code " + hash + ".");
			} catch (Exception e) {
				log.error(e);
			}	
		} else {
			if (hash == null || hash.isEmpty()) {
				log.error("Hash is null or empty.");
			} else if (email == null || email.isEmpty()) {
				log.error("E-mail is null or empty.");
			}
		}		
	}	
}