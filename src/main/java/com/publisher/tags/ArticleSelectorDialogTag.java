package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.HiddenTag;
import com.opensymphony.xwork2.util.ValueStack;

public class ArticleSelectorDialogTag extends HiddenTag {

	private static final long serialVersionUID = -1075421147572851609L;
	
	private String popup;
	private String onArticleClick;
	private String url;
	private int width;
	private int height;
	private String text;
	private String category;
	
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
		return new ArticleSelectorDialog(stack, req, res);
	}
	
	protected void populateParams() {
		super.populateParams();
		ArticleSelectorDialog articleSelectorDialog = (ArticleSelectorDialog)component;
		articleSelectorDialog.setPopup(popup);
		articleSelectorDialog.setOnArticleClick(onArticleClick);
		articleSelectorDialog.setUrl(url);
		articleSelectorDialog.setWidth(width);
		articleSelectorDialog.setHeight(height);
		articleSelectorDialog.setId(id);
		articleSelectorDialog.setText(text);
		articleSelectorDialog.setCategory(category);
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