package com.publisher.utils.swf;

import java.io.File;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.service.PhotoService;

public abstract class AbstractSWFAction<T> extends ActionSupport {

	private static final long serialVersionUID = 4224364080892297667L;

	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService){
		this.photoService = photoService;
	}
	
	public String upload(){
		if (picture == null) {
			return ERROR;
		}
		if (path != null && !path.equals("")) {
			if(path.contains(";")) {
				path = path.substring(0, path.indexOf(";"));
			}
			if(!path.endsWith(File.separator)) {
				path += File.separator;
			}
			id = photoService.setPictureToUploadFolder(picture, new File(new File((String)ServletActionContext.getServletContext().getAttribute("upload-folder")), path));
		} else {
			id = photoService.setPictureToUploadFolder(picture);
		}
		return SUCCESS;
	}
	
	public String delete(){
		if (id <= 0) {
			return ERROR;
		}
		if (path != null && !path.equals("")) {
			if(path.contains(";")) {
				path = path.substring(0, path.indexOf(";"));
			}
			if(!path.endsWith(File.separator)) {
				path += File.separator;
			}
			photoService.removePictureFromUploadFolder( id, new File(new File((String)ServletActionContext.getServletContext().getAttribute("upload-folder") ), path));
			photoService.removePictureFromUploadTempFolder(id, new File((String)ServletActionContext.getServletContext().getAttribute("upload-temp-folder"), path));
		} else {
			photoService.removePictureFromUploadFolder(id);
			photoService.removePictureFromUploadTempFolder(id);
		}
		return SUCCESS;
	}	
	
	//Action properties
	
	private File picture;
	
	public void setPicture(File picture){
		this.picture = picture;
	}
	
	private long id;
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}
	
	private String path;
	
	public void setPath(String path){
		this.path = path;
	}
	
	public abstract T getResult();
}