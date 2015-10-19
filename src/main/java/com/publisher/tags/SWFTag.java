package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.HiddenTag;
import com.opensymphony.xwork2.util.ValueStack;

public class SWFTag extends HiddenTag {
	
	private static final long serialVersionUID = -5858126736545830785L;
	
	private String flashUrl;
	private String alternativePath;
	private String uploadUrl;
	private String deleteUrl;
	private String sessionid;
	private String idDivBtn;
	private String idDivThumbnail;
	private String idDivFileProgress;
	private String alternativePathSWFHandler;	
	
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
		return new SWF(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();
		SWF swf = (SWF)component;
		swf.setFlashUrl(flashUrl);
		swf.setUploadUrl(uploadUrl);
		swf.setDeleteUrl(deleteUrl);
		swf.setSessionid(sessionid);
		swf.setIdDivBtn(idDivBtn);
		swf.setIdDivThumbnail(idDivThumbnail);
		swf.setIdDivFileProgress(idDivFileProgress);
		swf.setAlternativePathSWFHandler(alternativePathSWFHandler);
		swf.setAlternativePath(alternativePath);
	}
	
	public void setAlternativePath(String alternativePath) {
		this.alternativePath = alternativePath;
	}
	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public void setIdDivBtn(String idDivBtn) {
		this.idDivBtn = idDivBtn;
	}	
	public void setIdDivThumbnail(String idDivThumbnail) {
		this.idDivThumbnail = idDivThumbnail;
	}
	public void setIdDivFileProgress(String idDivFileProgress) {
		this.idDivFileProgress = idDivFileProgress;
	}
	public void setAlternativePathSWFHandler(String alternativePathSWFHandler) {
		this.alternativePathSWFHandler = alternativePathSWFHandler;
	}
}