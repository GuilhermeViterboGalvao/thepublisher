package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.HiddenTag;

import com.opensymphony.xwork2.util.ValueStack;


public class PhotoSelectorTag  extends HiddenTag {

	private static final long serialVersionUID = 2876639904599778460L;

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

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
		return new PhotoSelector(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();
		PhotoSelector photoselector = (PhotoSelector)this.component;
		photoselector.setDisplay(display);
		photoselector.setUrl(url);
		photoselector.setDelay(delay);
		photoselector.setInitialUrl(initialUrl);
		photoselector.setPageSize(pageSize);
		photoselector.setShowToolTip(showToolTip);
		photoselector.setImagePattern(imagePattern);
		photoselector.setMinLength(minLength);
		photoselector.setLoadingImage(loadingImage);
		photoselector.setEvaluator(evaluator);
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