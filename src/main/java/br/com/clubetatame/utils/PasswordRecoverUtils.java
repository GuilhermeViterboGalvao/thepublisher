package br.com.clubetatame.utils;

import java.util.Date;
import java.util.HashMap;

public final class PasswordRecoverUtils {

	private PasswordRecoverUtils() { }
	
	private static PasswordRecoverUtils instance;
	
	public static PasswordRecoverUtils getInstance() {
		if (instance == null) {
			synchronized (PasswordRecoverUtils.class) {
				if (instance == null) {
					PasswordRecoverUtils passwordRecoverUtils = new PasswordRecoverUtils();
					instance = passwordRecoverUtils;
				}
			}
		}
		return instance;
	}
	
	private static final HashMap<String, HashMap<String, Date>> keys = new HashMap<String, HashMap<String, Date>>();
	
	public String createCode(Long id) {
		String hash = null;
		if (id != null && id > 0) {
			Double code = new Double(System.currentTimeMillis() * id);
			hash = String.valueOf(code.hashCode());			
		}
		return hash;
	}	
	
	public boolean confirm(String hash, String email) {
		if (hash != null && !hash.isEmpty() && email != null && !email.isEmpty()) {
			HashMap<String, Date> map = keys.get(hash);
			if (map != null) {
				Date date = map.get(email);
				if (date != null) {
					Date today = new Date();
					long expirationTime = 24 * 60 * 60 * 1000;//24h
					return (date.getTime() - today.getTime()) <= expirationTime;				
				}
			}	
		}
		return false;
	}
	
	public void removeHash(String hash) {
		keys.remove(hash);
	}
	
	public void addEmailAndCode(String hash, String email) {
		if (hash != null && !hash.isEmpty() && email != null && !email.isEmpty()) {
			HashMap<String, Date> map = new HashMap<String, Date>();
			map.put(email, new Date());
			keys.put(hash, map);
		}
	}
}