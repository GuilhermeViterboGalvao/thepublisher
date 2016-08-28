package com.publisher.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = 7843634121883842191L;
	
	private static Log log = LogFactory.getLog(IndexAction.class);
	
	@Override
	public String execute() throws Exception {
		log.info("Loading index.jsp...");
		return SUCCESS;
	}
}