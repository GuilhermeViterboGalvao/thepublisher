package com.publisher.utils;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhotoUtils {

	private PhotoUtils() { }
	
	private static Log log = LogFactory.getLog(PhotoUtils.class);
	
	private static PhotoUtils instance;
	
	public static PhotoUtils getInstance() {
		if (instance == null) {
			synchronized (PhotoUtils.class) {
				if (instance == null) {
					PhotoUtils photoUtils = new PhotoUtils();
					instance = photoUtils;
				}
			}
		}
		return instance;
	}
	
	public synchronized long setPictureToUploadFolder(File picture, File folder) {
		if (folder == null) {
			return 0l;
		}
		if (!folder.exists()) {
			if (!folder.mkdirs()) {
				log.error("Failed to create for folder " + folder.getAbsolutePath());	
			}
		}
		if (!folder.isDirectory()) {
			log.error(folder.getAbsolutePath() + " is not a directory");
			return 0l;
		}
		Integer id = folder.listFiles().length + 1;
		id = (int)(id.hashCode() * Math.random() * 1000);
		File dest = new File(folder, id + ".jpg");
		if (log.isInfoEnabled()) {
			log.info("Moving " + picture.getAbsolutePath() + " to " + dest.getAbsolutePath());
		}
        try {
			FileUtils.moveFile(picture, dest);
		} catch (Exception e) {
			log.error("Failed to move " + picture.getAbsolutePath() + " to " + dest.getAbsolutePath(), e);
		}
		return id;
	}
	
	public synchronized File getPictureFromUploadFolder(long id, File dir) {
		if (dir == null) {
			return null;
		}
		if (!dir.isDirectory()) {
			return null;
		}
		for(File file : dir.listFiles()) {			
			if (file.getName().equals(String.valueOf(id) + ".jpg")) {
				return file;
			}	
		}		
		return null;
	}
	
	public synchronized void removePictureFromUploadFolder(long id, File dir) {
		if (dir == null) {
			return;
		}
		if (!dir.isDirectory()) {
			return;
		}
		for(File file : dir.listFiles()) {
			if (file.isFile()) {
				if (file.getName().equals(String.valueOf(id) + ".jpg")) {
					file.delete();
					break;
				}
			}
		}
	}
	
	public synchronized void removePictureFromUploadTempFolder(long id, File dir) {
		if (dir == null) {
			return;
		}
		if (!dir.isDirectory()) {
			return;
		}
		for(File file : dir.listFiles()) {
			if (file.isFile()) {				
				String fileName = file.getName().substring(0, file.getName().indexOf("."));				
				if (fileName.equals(String.valueOf(id)) || fileName.startsWith(String.valueOf(id) + "_")) {
					file.delete();
					break;
				}
			}
		}
	}
}