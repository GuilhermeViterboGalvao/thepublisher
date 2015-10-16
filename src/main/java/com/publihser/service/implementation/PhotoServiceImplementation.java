package com.publihser.service.implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.PhotoUtils;
import com.publisher.utils.ResultList;

public class PhotoServiceImplementation extends TransactionalService implements PhotoService, ApplicationContextAware {

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
	public Photo get(Long id) {
		return id != null ? entityManager.find(Photo.class, id) : null;
	}

	@Override
	public void persist(Photo entity) {
		if (entity != null) {
			entityManager.persist(entity);
		}
	}

	@Override
	public void update(Photo entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Photo entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	@Override
	public Collection<Photo> list() {
		return list(0, 0);
	}

	@Override
	public Collection<Photo> search(String query) {
		return search(query, 0, 0).getResult();
	}

	@Override
	public long count() {
		return count(null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void indexAll() {
        try {
            Query query = entityManager.createQuery("select max(p.id) from Photo p");
            long total = (Long) query.getSingleResult();
            FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
            ft.purgeAll(Photo.class);
            for (long i = 0; i < total / 100 + 1; i++) {
                query = ft.createQuery("select p from Photo p where p.id>=? and p.id<=? order by p.id");
                query.setParameter(1, i * 100 + 1);
                query.setParameter(2, (i + 1) * 100);
				List<Photo> list = query.getResultList();
                for (Photo photo : list) {                    
                    ft.index(photo);
                    log.info(photo.getId() + ": " + photo.getDescription());
                }
                ft.flushToIndexes();
                ft.clear();
            }
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
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
	public Collection<Photo> list(int page, int pageSize) {
		return list(page, pageSize, null);
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
	@SuppressWarnings("unchecked")	
	public ResultList<Photo> search(String query, int page, int pageSize) {
    	long t = System.currentTimeMillis();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(Photo.class).get();
		BooleanJunction<?> junction = qb.bool().should(qb.phrase().onField("description").sentence(query).createQuery());
						   junction = junction.should(qb.phrase().onField("tags").sentence(query).createQuery());		
		org.apache.lucene.search.Query luceneQuery = junction.createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, Photo.class);
        fullTextQuery.setSort(new Sort(new SortField("date", SortField.Type.LONG, true)));
        if (pageSize > 0) {
        	fullTextQuery.setMaxResults(pageSize);
        }
        if (page > 0 && pageSize > 0) {        	
        	fullTextQuery.setFirstResult((page - 1) * pageSize);			
		}
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultList<Photo> result = new ResultList<Photo>();
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int) (System.currentTimeMillis() - t));
        log.info("PHOTO SEARCH=[" + luceneQuery + "] - TimeElapsed=" + result.getTimeElapsed());
        return result;
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