package com.publisher.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;

public class PhotoMultiUploadAction extends ActionSupport implements ServletRequestAware, AccountAware {

	private static final long serialVersionUID = 76972787395291166L;

	protected PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	protected HttpServletRequest request;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}	
	
	protected Account account;
	
	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Account getAccount() {
		return account;
	}
	
	@Override
	public boolean isAdmin() {
		if (account != null && account.getSecurityHole() != null && !account.getSecurityHole().isEmpty() && account.getSecurityHole().equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}	
	
    @SkipValidation
    public String add() {
    	return INPUT;
    }
    
    public String save() {
    	List<File> pictures = new ArrayList<File>();    	
    	for (int i = 0; i < pictureId.length; i++) {
    		pictures.add(photoService.getPictureFromUploadFolder(pictureId[i]));
		}  	
    	if (!pictures.isEmpty()) {    		
    		Photo photo = null;
    		File picture = null;    		
    		for (int i = 0; i < pictures.size(); i++) {
    			picture = pictures.get(i);    			
				photo = new Photo();            
                photo.setDescription((useFilename ? pictureDescription[i] + " " : "") + description);
                photo.setTags((useFilename ? pictureTag[i] + " " : "") + tags);
                photo.setLastModified(new Date());
                photo.setLastModifiedBy(account);
                photo.setPublished(published);
                photo.setCreated(new Date());
                photo.setCreatedBy(account);                
                photo.setCredits(credits);
                photo.setDate(date);
                photoService.persist(photo, picture);
                photoService.removePictureFromUploadFolder(pictureId[i]);
                photoService.removePictureFromUploadTempFolder(pictureId[i]);
            }
		}
    	return "redirect";
    }
	
    @Override
    public void validate() {
    	if (description == null || description.equals("")) {
    		addFieldError("descriptionFieldErro", "Entre com a descriçãoo.");
    	}    		
    	if (tags == null || tags.equals("")) {
    		addFieldError("tagsFieldErro", "Digite as palavras chave.");
    	}    		
    	if (pictureId == null) {
    		addFieldError("picturesIdsFieldErro", "Escolha uma ou mais imagens.");
    	}    		
		if (credits == null || credits.isEmpty()) {
			addFieldError("creditsFieldErro", "Selecione um fotógrafo.");
		}			
    }	
	
	//Action properties
	
	public String getSessionId() {
    	return request.getSession().getId();
    }	
	
	//POJO properties
	
	protected boolean useFilename = false;
	
	protected String[] pictureDescription;
	
	protected String[] pictureTag;
	
	protected String description;
	
	protected boolean published;
	
	protected long[] pictureId;
	
	protected String credits;
	
	protected String tags;
	
	protected Date date;

	public boolean isUseFilename() {
		return useFilename;
	}

	public void setUseFilename(boolean useFilename) {
		this.useFilename = useFilename;
	}

	public String[] getPictureDescription() {
		return pictureDescription;
	}

	public void setPictureDescription(String[] pictureDescription) {
		this.pictureDescription = pictureDescription;
	}

	public String[] getPictureTag() {
		return pictureTag;
	}

	public void setPictureTag(String[] pictureTag) {
		this.pictureTag = pictureTag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public long[] getPictureId() {
		return pictureId;
	}

	public void setPictureId(long[] pictureId) {
		this.pictureId = pictureId;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getDate() {
		return date == null ? new Date() : date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}