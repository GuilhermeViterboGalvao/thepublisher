package com.publisher.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

	private IPUtils() { }
	
	@SuppressWarnings("unchecked")
	public static List<String> getIP(HttpServletRequest request) {
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
}