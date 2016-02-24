package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Hidden;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name="photoselector", tldTagClass="com.publisher.tags.PhotoSelector", description="Seleciona as fotos cadastradas no sistema através das tags.", allowDynamicAttributes=true)
public class PhotoSelector extends Hidden {

	private String display;
	private String url;
	private String delay;
	private String initialUrl;
	private String pageSize;
	private String showToolTip;
	private String imagePattern;
	private String minLength;
	private String loadingImage;
	private String evaluator;

	public PhotoSelector(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return "photoselector";
	}

	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (this.url != null) {
			addParameter("url", this.url);
		}
		if (this.display != null) {
			addParameter("display", findValue(this.display));
		}
		if (this.imagePattern != null) {
			addParameter("imagePattern", this.imagePattern);
		}
		if (this.delay != null) {
			addParameter("delay", this.delay);
		}
		if (this.initialUrl != null) {
			addParameter("initialUrl", this.initialUrl);
		}
		if (this.pageSize != null) {
			addParameter("pageSize", this.pageSize);
		}
		if (this.showToolTip != null) {
			addParameter("showToolTip", this.showToolTip);
		}
		if (this.minLength != null) {
			addParameter("minLength", this.minLength);
		}
		if (this.loadingImage != null) {
			addParameter("loadingImage", this.loadingImage);
		}		
		if (this.evaluator != null) {
			addParameter("evaluator", findString(this.evaluator));
		}
		if (getId()==null) {
			addParameter("id","photoselector_"+Long.toString((long)(Math.random()*4294967296L)));
		}
	}	

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImagePattern(String imagePattern) {
		this.imagePattern = imagePattern;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public void setInitialUrl(String initialUrl) {
		this.initialUrl = initialUrl;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setShowToolTip(String showToolTip) {
		this.showToolTip = showToolTip;
	}
	
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public void setLoadingImage(String loadingImage) {
		this.loadingImage = loadingImage;
	}

	public String getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(String evaluator) {
		this.evaluator = evaluator;
	}
}