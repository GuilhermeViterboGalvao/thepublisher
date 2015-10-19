package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.HiddenTag;

import com.opensymphony.xwork2.util.ValueStack;


public class AutoCompleteTag  extends HiddenTag {

	private static final long serialVersionUID = 2876639904599778460L;

	private String display;
	private String url;
	private String delay;
	private String pageSize;
	private String minLength;
	private String evaluator;
	private String initial;
	

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
		return new AutoComplete(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();
		AutoComplete autocomplete = (AutoComplete)this.component;
		autocomplete.setDisplay(display);
		autocomplete.setUrl(url);
		autocomplete.setDelay(delay);
		autocomplete.setPageSize(pageSize);
		autocomplete.setMinLength(minLength);
		autocomplete.setEvaluator(evaluator);
		autocomplete.setInitial(initial);
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	
	public void setEvaluator(String evaluator) {
		this.evaluator = evaluator;
	}
}
