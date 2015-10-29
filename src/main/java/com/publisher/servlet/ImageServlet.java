package com.publisher.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.ImageMagickFacade;

public class ImageServlet extends HttpServlet {	

	private static final long serialVersionUID = -1776399655031893443L;
	
	private static Log log = LogFactory.getLog(ImageServlet.class);
	
	private Float quality;
	
	private File homeFolder;
	
	private File imageFolder;
	
	private File imageTempFolder;
	
	private String imageMagickPath;
	
	private int folderSize = 1000;
	
	private PhotoService photoService;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try { 
			folderSize = Integer.parseInt(config.getInitParameter("folder-size")); 
		} catch (Exception e) { 
			log.error(e);
		}
		homeFolder = new File((String)config.getServletContext().getAttribute("home-folder"));		
		imageFolder = new File((String)config.getServletContext().getAttribute(config.getInitParameter("image-folder")));
		if (imageFolder == null) {
			imageFolder = new File(config.getInitParameter("image-folder"));
			if (!imageFolder.exists()) {
				imageFolder.mkdirs();
			}
		}
		imageTempFolder = new File((String)config.getServletContext().getAttribute(config.getInitParameter("image-temp-folder")));
		if (imageTempFolder == null) {
			imageTempFolder = new File(config.getInitParameter("image-temp-folder"));
			if (!imageTempFolder.exists()) {
				imageTempFolder.mkdirs();
			}
		}
		imageMagickPath = (String)config.getServletContext().getAttribute("bin-path");
		imageMagickPath = !imageMagickPath.endsWith(File.separator) ? imageMagickPath + File.separator : imageMagickPath;
		log.info("Image folder: " + imageFolder);
		log.info("Image temporary folder: " + imageTempFolder);
		log.info("imageMagick folder path: " + imageMagickPath);		
		try { 
			quality = Float.parseFloat(config.getInitParameter("quality")); 
		} catch (Exception e) { 
			log.error(e);
		}		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File ifolder = request.getParameter("path") != null ? new File(imageFolder, request.getParameter("path")) : imageFolder;
		if (!ifolder.exists()) {
			ifolder.mkdirs();
		}	
		File tfolder = request.getParameter("path") != null ? new File(imageTempFolder, request.getParameter("path")) : imageTempFolder;
		if (!tfolder.exists()) {
			tfolder.mkdirs();
		}		
		String uri = request.getRequestURI();		
		File image = getImage(uri, ifolder, tfolder);
		if (image == null || !image.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String path = image.getAbsolutePath().replace(homeFolder.getAbsolutePath(), "");
		if (!path.startsWith(File.separator)) {
			path = File.separator + path;
		}		
		log.info(path);		
		request.getRequestDispatcher(path).forward(request, response);		
	}
	
	private File getImage(String uri, File imageFolder, File tempFolder) {
		String fileName = null;		
		try {
			fileName = uri.substring(uri.lastIndexOf(File.separator) + 1, uri.length());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		File resizedFile = new File(tempFolder, fileName);
		if (!resizedFile.exists()) {
			long id = -1;
			int width = -1;
			int height = -1;
			int i = fileName.indexOf("_");
			if (i != -1) {
				try { 
					id = Long.parseLong(fileName.substring(0, i)); 
				} catch (Exception e) { 
					log.error(e.getMessage(),e); 
				}
			} else {
				try { 
					id = Long.parseLong(fileName.substring(0, fileName.lastIndexOf('.'))); 
				} catch (Exception e) { 
					log.error(e.getMessage(),e); 
				}
			}
			if (id == -1) {
				log.debug("Bad image name format: " + fileName);				
				return null;
			}
			String size = null;
			if (i != -1) {
				try {
					size = fileName.substring(i + 1, fileName.indexOf("."));
					width = Integer.parseInt(size.substring(0, size.indexOf("x")));
					height = Integer.parseInt(size.substring(size.indexOf("x") + 1, size.length()));
				} catch (Exception e) {
					size = null;
				}
				if (size == null) {
					log.debug("Bad image name format (size not found): " + fileName);
					return null;
				}
			}
			File customCutFile = new File(imageTempFolder, id + "_" + width + "x" + height + ".jpg");
			if (customCutFile.exists()) {
				log.debug("Using custom cut for " + fileName);
				resizedFile = customCutFile;
			} else {
				File originalFile = new File(imageFolder, (folderSize > 0 ? (id - id % folderSize) + File.separator : "") + id + ".jpg");
				if (!originalFile.exists()) {
					log.debug("Image does not exist (file not found) " + fileName);
					return null;
				}
				Photo photo = null;
				BufferedImage bimage = null;				
				try {
					bimage = ImageIO.read(originalFile);
				} catch (Exception e) { 
					log.error(e);
					e.printStackTrace();
				}				
				photo = getPhoto(id);				
				if (bimage != null) {					
					photo.setWidth(bimage.getWidth());
					photo.setHeight(bimage.getHeight());
				}
				if (photo == null) {
					log.debug("Image does not exist (database entry not found) " + fileName);
					return null;
				}
				if (!((photo.getWidth() == width) && (photo.getHeight() == height))) {
					if (log.isDebugEnabled()) log.debug("Resizing " + fileName);
					ImageMagickFacade.getInstance().resize(
						imageMagickPath, 
						originalFile, 
						photo.getWidth(), 
						photo.getHeight(), 
						resizedFile, 
						width, 
						height, 
						quality, 
						photo.getHorizontalCenter(), 
						photo.getVerticalCenter()
					);
				} else {
					if (log.isDebugEnabled()) log.debug("Using original size "+fileName);
					resizedFile = originalFile;
				}
			}
		}		
		return resizedFile;
	}
	
    private Photo getPhoto(long id) {
    	if (photoService == null) {
    		synchronized (ImageServlet.class) {
    			if (photoService == null) {
    				WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    				if (webContext != null) {
    					Object service = webContext.getBean("photoService");
    					if (service != null && service instanceof PhotoService) {
    						photoService = (PhotoService)service;    						
    					}
    				}		
    			}
			}   		
    	}
    	Photo photo = null;
    	if (photoService != null && id > 0) {
    		photo = photoService.get(id);	
    	}
        return photo != null ? photo : (photo = new Photo());
    }	
}