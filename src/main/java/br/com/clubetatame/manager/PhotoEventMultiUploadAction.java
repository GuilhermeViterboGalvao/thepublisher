package br.com.clubetatame.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.publisher.entity.Photo;
import com.publisher.manager.PhotoMultiUploadAction;

public class PhotoEventMultiUploadAction extends PhotoMultiUploadAction {

	private static final long serialVersionUID = 5324081238150583383L;
	
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
                photo.setEvent(true);
                photoService.persist(photo, picture);
                photoService.removePictureFromUploadFolder(pictureId[i]);
                photoService.removePictureFromUploadTempFolder(pictureId[i]);
            }
		}
    	return "redirect";
    }
}