package com.publisher.service;


import com.publisher.entity.AuthToken;

import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface AuthTokenService extends Service<AuthToken> {

	String generateToken();
	
	AuthToken get(String token, Boolean active);
}