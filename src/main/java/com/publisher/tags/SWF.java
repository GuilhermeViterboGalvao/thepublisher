package com.publisher.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Hidden;
import com.opensymphony.xwork2.util.ValueStack;

public class SWF extends Hidden {

	private String flashUrl;
	private String alternativePath;
	private String uploadUrl;
	private String deleteUrl;
	private String sessionid;
	private String idDivBtn;	
	private String idDivThumbnail;	
	private String idDivFileProgress;	
	private String alternativePathSWFHandler;
	
	public SWF(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return "swf";
	}

	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (flashUrl != null) {
			addParameter("flashUrl", flashUrl);
		} else {
			addParameter("flashUrl", "../frameworks/swfupload/swfupload.swf");
		}
		if (alternativePath != null) {
			addParameter("alternativePath", alternativePath);
		}
		if (uploadUrl != null) {
			addParameter("uploadUrl", uploadUrl);
		}
		if (deleteUrl != null) {
			addParameter("deleteUrl", deleteUrl);
		}
		if (sessionid != null) {
			addParameter("sessionid", findValue(sessionid));
		}
		if (!idDivBtn.equals("default")) {
			addParameter("idDivBtn", findString(idDivBtn));
		} else {
			addParameter("idDivBtn", "submitPhotos_"+Long.toString((long)(Math.random()*4294967296L)));
		}		
		if (!idDivThumbnail.equals("default")) {
			addParameter("idDivThumbnail", findString(idDivThumbnail));
		} else {
			addParameter("idDivThumbnail", "thumbnails_"+Long.toString((long)(Math.random()*4294967296L)));
		}
		if (!idDivFileProgress.equals("default")) {
			addParameter("idDivFileProgress", findString(idDivFileProgress));
		} else {
			addParameter("idDivFileProgress", "divFileProgressContainer_"+Long.toString((long)(Math.random()*4294967296L)));
		}
		if (alternativePathSWFHandler != null) {
			addParameter("alternativePathSWFHandler", alternativePathSWFHandler);
		}
	}

	public String getAlternativePath() {
		return alternativePath;
	}
	public void setAlternativePath(String alternativePath) {
		this.alternativePath = alternativePath;
	}
	public String getFlashUrl() {
		return flashUrl;
	}
	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public String getDeleteUrl() {
		return deleteUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getIdDivBtn() {
		return idDivBtn;
	}
	public void setIdDivBtn(String idDivBtn) {
		this.idDivBtn = idDivBtn;
	}
	public String getIdDivThumbnail() {
		return idDivThumbnail;
	}
	public void setIdDivThumbnail(String idDivThumbnail) {
		this.idDivThumbnail = idDivThumbnail;
	}	
	public String getIdDivFileProgress() {
		return idDivFileProgress;
	}
	public void setIdDivFileProgress(String idDivFileProgress) {
		this.idDivFileProgress = idDivFileProgress;
	}	
	public String getAlternativePathSWFHandler() {
		return alternativePathSWFHandler;
	}
	public void setAlternativePathSWFHandler(String alternativePathSWFHandler) {
		this.alternativePathSWFHandler = alternativePathSWFHandler;
	}
}