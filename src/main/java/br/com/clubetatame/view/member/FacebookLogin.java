package br.com.clubetatame.view.member;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.service.implementation.MemberServiceImplementation;
import br.com.clubetatame.view.ViewAction;


public class FacebookLogin extends ActionSupport implements  ViewAction, SessionAware {

	private static final long serialVersionUID = 7982474673172814773L;
	
	private static Log log = LogFactory.getLog(MemberServiceImplementation.class);
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService){
		this.memberService = memberService;
	}
	
	private Map<String, Object> session;
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	private String appSecret;
	
	private String appId;
	
	private String myFacebookUrl;
	
	private String code;
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String execute() throws Exception {
		String returnType = LOGIN;
		
        if (code != null){
	        Member smember = (Member)session.get("member");
	        
	        if (smember == null) {
	        	Member member = null;
	        	
	        	synchronized (memberService) {
	        		myFacebookUrl = "http://homolog.tatame.terra.com.br/clube/membros/facebookLogin";
	        		appSecret = "2e797a2f2dd5de7cbe8865d2816744ad";
	        		appId = "515364881999582";        		        		
	        		member = facebookAuthentication(code);
				}
	            if (member != null && member.isActive()){
		            session.put("member", member);
		            returnType = SUCCESS;
	            }
	        }else{
	        	returnType = SUCCESS;
	        }
        }
        return returnType;
	}

	
	public Member facebookAuthentication(String authCode) {
        Member member = null;
        try {
        	
            URL url = new URL(getFacebookAuthURL(authCode));
            if (log.isInfoEnabled()) {
            	log.info("FacebookAuth URL " + url);	
            }
            
            String result = readURL(url);
            if (log.isInfoEnabled()) {
            	log.info("FacebookAuth Result " + result);	
            }
            
            String accessToken = null;
            Integer expires    = null;
            String[] pairs     = result.split("&");
            
            for (String pair : pairs) {
                String[] keyAndValue = pair.split("=");
                if (keyAndValue.length != 2) {
                    throw new RuntimeException("Unexpected auth response");
                } else {
                    if (keyAndValue[0].equals("access_token")) {
                    	accessToken = keyAndValue[1];
                    }
                    if (keyAndValue[0].equals("expires"))      {
                    	expires = Integer.valueOf(keyAndValue[1]);
                    }
                }
            }
            
            if (log.isInfoEnabled()) {
            	log.info("FacebookAuth access_token " + accessToken);
            	log.info("FacebookAuth expires "      + expires);            	
            }

            if (accessToken == null) {
            	return null;
            }
            
            member = getFacebookUser(accessToken, expires);            
        } catch (Exception e) { }
        
        return member;
	}
	
	public Member getFacebookUser(String accessToken, Integer expires) {
        Member member = null;
        
        try {
        	if (log.isInfoEnabled()) {
        		log.info("Facebook Login: " + accessToken + " [" + expires + "]");	
        	}
        	
            URL url = new URL("https://graph.facebook.com/me?access_token=" + accessToken + "&fields=name,gender,email");
            
            JSONObject json = new JSONObject(readURL(url));
            
            if (log.isInfoEnabled()) {
            	log.info(json.toString());	
            }
            
            long fbid = 0;            
            if (!json.isNull("id")) {
            	fbid = json.getLong("id");
            }
            
            if (fbid > 0) {
            	member = memberService.getByFacebookId(fbid);
            	if (member == null) {
            		member = new Member();
            		member.setActive(true);
            		member.setCreated(new Date());
            	}
            	member.setFbid(fbid);
        		if (!json.isNull("name")) {
        			member.setName(json.getString("name"));
        		}
        		if (!json.isNull("email")) {
        			member.setEmail(json.getString("email"));
        		}       
        		if (!json.isNull("gender")) {
        			member.setGender((json.getString("gender").equals("male") ? "Masculino" : "Feminino"));
        		} 

            	if (accessToken != null && !accessToken.isEmpty()) {
            		member.setFacebookAccessToken(accessToken);	
            	}        	
                if (expires != null) {
                    Date expiration = new Date();
                    expiration.setTime(System.currentTimeMillis() + expires * 1000);
                    member.setFacebookAccessTokenExpiration(expiration);
                }
            	if (member.getId() != null && member.getId() > 0) {
            		memberService.update(member);
            	} else {
            		memberService.persist(member);
            	}
            }
        } catch (Exception e) { }        
        return member;
	}
	
    private String readURL(URL url) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = url.openStream();
        int read = 0;
        while ((read = in.read()) != -1) {
        	baos.write(read);
        }
        in.close();
        return new String(baos.toByteArray());
    }
    
    private String getFacebookAuthURL(String authCode) {
    	String url = "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + myFacebookUrl + "&client_secret=" + appSecret + "&code=" + authCode;
    	if (log.isInfoEnabled()) {
    		log.info(url);
    	}
        return url;
    }
    
    @Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return "/skins/clube-tatame/member/loginform.jsp";
	}
}