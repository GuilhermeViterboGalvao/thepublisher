package com.publisher.service.implementation;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

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
}