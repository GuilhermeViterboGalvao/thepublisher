package com.publisher.view;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.utils.IPUtils;

public class TestAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = -2552987488306669088L;

	private HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private List<String> result;
	
	public List<String> getResult() {
		return result;
	}
	
	@Override
	public String execute() throws Exception {
		result = IPUtils.getIP(request);
		return SUCCESS;
	}
}