package com.publisher.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

	private IPUtils() { }
	
	@SuppressWarnings("unchecked")
	public static List<String> getHeaders(HttpServletRequest request) {
		if (request != null) {
			Enumeration<String> headerNames = request.getHeaderNames();
			if (headerNames != null) {
				List<String> headers = new ArrayList<String>();
				while (headerNames.hasMoreElements()) {
					String headerName = (String)headerNames.nextElement();
					String headerValue = request.getHeader(headerName);
					headers.add("[" + headerName + "] - [" + headerValue + "]");
				}
				return headers;
			}
		}
		return null;
	}
	
	public static String getClientIP(HttpServletRequest request) {
		if (request != null) {
	        String ip = request.getHeader("X-Forwarded-For");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }			
		}
		return null;
	}
}