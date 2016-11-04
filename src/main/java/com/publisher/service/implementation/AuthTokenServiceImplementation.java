package com.publisher.service.implementation;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

import javax.persistence.Query;

import com.publisher.entity.AuthToken;
import com.publisher.service.AuthTokenService;

public class AuthTokenServiceImplementation extends AbstractServiceImplementation<AuthToken> implements AuthTokenService {

	@Override
	public String generateToken() {
        String hash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String salt = String.format("%s%s", new Date().toString(), "ThePublisher##");
            md5.update(salt.getBytes());
            hash = new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return hash;
	}
	
	@Override
	public AuthToken get(String token, Boolean active){
		if(token == null || token.isEmpty()) return null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AuthToken a where token=:token");
		
		if(active != null){
			sb.append(" and a.active=:active");
		}
		
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("token", token);
		
		if(active != null){
			query.setParameter("active", active);
		}
		
		query.setHint("org.hibernate.cacheable", true);
		try{ 
			return (AuthToken) query.getResultList().get(0); 
		} catch(Exception e){ 
			return null; 
		}
	}
}