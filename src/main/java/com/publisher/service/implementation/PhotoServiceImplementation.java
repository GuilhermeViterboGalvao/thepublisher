package com.publisher.service.implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.PhotoUtils;

public class PhotoServiceImplementation extends AbstractServiceImplementation<Photo> implements PhotoService, ApplicationContextAware {

	private static Log log = LogFactory.getLog(PhotoServiceImplementation.class);

	private File imageFolder;

	private File imageTempFolder;

	private File uploadFolder;

	private File uploadTempFolder;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		WebApplicationContext webApplicationContext = (WebApplicationContext)applicationContext;
		ServletContext servletContext = webApplicationContext.getServletContext();
		imageFolder = new File((String)servletContext.getAttribute("image-folder"));
		imageTempFolder = new File((String)servletContext.getAttribute("image-temp-folder"));
		uploadFolder = new File((String)servletContext.getAttribute("upload-folder"));
		uploadTempFolder= new File((String)servletContext.getAttribute("upload-temp-folder"));
	}

	@Override
	public void persist(Photo photo, File picture) {
		if (picture != null) {
			updateSize(photo, picture);
		}
		entityManager.persist(photo);
		if (picture != null) {
			updateFile(photo, picture);    	
		}
	}

	@Override
	public void update(Photo photo, File picture) {
		if (picture != null) {
			updateSize(photo, picture);        
		}
		entityManager.merge(photo);
		if (picture != null) {
			updateFile(photo, picture);
		}
		clearTempFolder(photo.getId());    	
	}

	@Override
	public Collection<Photo> list(int page, int pageSize, Date publishedUntil) {
		return list(page, pageSize, publishedUntil, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Photo> list(int page, int pageSize, Date publishedUntil, Boolean published) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Photo p ");
		if (publishedUntil != null) {
			sql.append("where p.date<=:publishedUntil ");
		}
		if (publishedUntil != null && published != null) {
			sql.append("and p.published=:published ");
		} else if (publishedUntil == null && published != null) {
			sql.append("where p.published=:published ");
		}
		sql.append("order by p.id desc");
		Query query = entityManager.createQuery(sql.toString());
		if (publishedUntil != null) {
			query.setParameter("publishedUntil", publishedUntil);
		}
		if (published != null) {
			query.setParameter("published", published);
		}
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		if (page > 0 && pageSize > 0) {            
			query.setFirstResult((page - 1) * pageSize);			
		}
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	@Override
	public long count(Boolean published) {
		Query query = null;
		if (published != null) {
			query = entityManager.createQuery("select count(p) from Photo p where p.published=:published");
			query.setParameter("published", published);
		} else {
			query = entityManager.createQuery("select count(p) from Photo p");
		}
		return (Long)query.getSingleResult();
	}

	@Override
	public long setPictureToUploadFolder(File picture) {
		return PhotoUtils.getInstance().setPictureToUploadFolder(picture, uploadFolder);
	}

	@Override
	public long setPictureToUploadFolder(File picture, File dir) {
		return PhotoUtils.getInstance().setPictureToUploadFolder(picture, dir);
	}

	@Override
	public File getPictureFromUploadFolder(long id) {
		return PhotoUtils.getInstance().getPictureFromUploadFolder(id, uploadFolder);
	}

	@Override
	public File getPictureFromUploadFolder(long id, File dir) {
		return PhotoUtils.getInstance().getPictureFromUploadFolder(id, dir);
	}

	@Override
	public void removePictureFromUploadFolder(long id) {
		PhotoUtils.getInstance().removePictureFromUploadFolder(id, uploadFolder);
	}

	@Override
	public void removePictureFromUploadFolder(long id, File dir) {
		PhotoUtils.getInstance().removePictureFromUploadFolder(id, dir);
	}

	@Override
	public void removePictureFromUploadTempFolder(long id) {
		PhotoUtils.getInstance().removePictureFromUploadTempFolder(id, uploadTempFolder);
	}

	@Override
	public void removePictureFromUploadTempFolder(long id, File dir) {
		PhotoUtils.getInstance().removePictureFromUploadTempFolder(id, dir);
	}

	private void updateSize(Photo photo, File picture) {
		if (picture == null) {
			return;
		}
		BufferedImage image = null;
		try { 
			image = ImageIO.read(picture); 
		} catch (Exception e) { 
			log.error(e);
			e.printStackTrace(); 
		}
		if (image != null) {
			photo.setWidth(image.getWidth());
			photo.setHeight(image.getHeight());
		}
	}

	private void updateFile(Photo photo, File picture) {    	
		if (picture == null) return;
		long id = photo.getId();
		File folder = new File(imageFolder, Long.toString(id - id % 1000));
		if (!folder.exists()) {
			if (!folder.mkdirs()) {
				log.error("Failed to create for folder " + folder.getAbsolutePath());
			}
		}
		File dest = new File(folder, id + ".jpg");
		if (dest.exists()) {
			if (!dest.delete()) {
				log.error("Failed to delete file " + dest.getAbsolutePath());
			}
		}
		log.info("moving " + picture.getAbsolutePath() + " to " + dest.getAbsolutePath());
		try {
			FileUtils.moveFile(picture, dest);
		} catch (Exception e) {
			log.error("Failed to move " + picture.getAbsolutePath() + " to " + dest.getAbsolutePath(), e);
		}
	} 

	private void clearTempFolder(long id){
		log.info("PhotoServiceImplementation.clearTempFolder(" + id + ")");
		String prefix = id + "_";
		for(File file : imageTempFolder.listFiles()) {
			if (file.getName().startsWith(prefix) || file.getName().equals(id + ".jpg")) {
				file.delete();
			}
		}
	}
}