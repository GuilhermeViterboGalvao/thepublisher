package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Hidden;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name="autocomplete", tldTagClass="br.com.fluir.website.tags.AutoCompleteTag", description="Coloca campo de edição auto completável", allowDynamicAttributes=true)
public class AutoComplete extends Hidden {

	private String display;
	private String url;
	private String delay;
	private String pageSize;
	private String minLength;
	private String evaluator;	
	private String initial;	
	
	public AutoComplete(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return "autocomplete";
	}

	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (this.url != null) {
			addParameter("url", this.url);
		}
		if (this.display != null) {
			addParameter("display", findValue(this.display));
		}
		if (this.delay != null) {
			addParameter("delay", this.delay);
		}
		if (this.pageSize != null) {
			addParameter("pageSize", this.pageSize);
		}
		if (this.minLength != null) {
			addParameter("minLength", this.minLength);
		}
		if (getId()==null) {
			addParameter("id","photoselector_"+Long.toString((long)(Math.random()*4294967296L)));
		}
		if (this.evaluator != null) {			
			addParameter("evaluator", findString(this.evaluator));
		}
		if (this.initial != null) {			
			addParameter("initial", this.initial);
		}
	}	

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getDisplay() {
		return display;
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
	
	public String getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(String evaluator) {
		this.evaluator = evaluator;
	}
}