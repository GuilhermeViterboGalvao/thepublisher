package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Hidden;

import com.opensymphony.xwork2.util.ValueStack;

public class ArticleSelectorDialog extends Hidden {
	
	private String popup;
	private String onArticleClick;
	private String url;
	private int width;
	private int height;
	private String text;
	private String category;
	
	public ArticleSelectorDialog(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	
	protected String getDefaultTemplate() {
		return "articleselectordialog";
	}
	
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (popup != null) {
			addParameter("popup", popup);
		}
		if (onArticleClick != null) {
			addParameter("onArticleClick", findString(onArticleClick));
		}
		if (url != null) {
			addParameter("url", url);
		}
		if (width > 0) {
			addParameter("width", width);
		}
		if (height > 0) {
			addParameter("height", height);
		}
		if (id != null) {
			addParameter("id", id);
		} else {
			addParameter("id", "selectArticle" + Long.toString((long)(Math.random()*4294967296L)));
		}
		if (text != null) {
			addParameter("text", text);
		}		
		if (cssStyle != null) {
			addParameter("cssStyle", findString(cssStyle));
		}
		if (category != null && !category.isEmpty()) {
			addParameter("category", category);
		}
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public void setOnArticleClick(String onArticleClick) {
		this.onArticleClick = onArticleClick;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}	
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}	
}