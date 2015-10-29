package com.publisher.manager;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Photo;
import com.publisher.service.PhotoService;
import com.publisher.utils.ResultList;

public class PhotoAction extends AbstractAction<Photo> {

	private static final long serialVersionUID = -2844682879310094840L;
	
	public PhotoAction() {
		setPageSize(90);
	}

	private PhotoService photoService;
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	@Override
	protected void indexAll() {
		photoService.indexAll();
	}

	@Override
	protected void populateForm(Photo entity) {
		if (entity == null) {
			return;
		}		
    	this.lastModifiedByName = entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getName() : "";
    	this.horizontalCenter = entity.getHorizontalCenter() != 0.0f ? entity.getHorizontalCenter() : 0.5f;
    	this.verticalCenter = entity.getVerticalCenter() != 0.0f ? entity.getVerticalCenter() : 0.5f;
    	this.createdByName = entity.getCreatedBy() != null ? entity.getCreatedBy().getName() : "";   	
    	this.lastModified = entity.getLastModified();
    	this.description = entity.getDescription();
    	this.published = entity.isPublished();
    	this.credits = entity.getCredits();
    	this.created = entity.getCreated();
    	this.height = entity.getHeight();
    	this.width = entity.getWidth();
    	this.tags = entity.getTags();
    	this.date = entity.getDate();    	
    	this.id = entity.getId();		
	}

	@Override
	protected Photo updateObject(Photo entity) {
		entity.setHorizontalCenter(horizontalCenter);
		entity.setVerticalCenter(verticalCenter);
		entity.setLastModifiedBy(getAccount());
		entity.setDescription(description);
		if (entity.getCreatedBy() == null) {
			entity.setCreatedBy(getAccount());	
		}		
		entity.setPublished(published);
		entity.setLastModified(new Date());
		if (entity.getCreated() == null) {
			entity.setCreated(date);	
		}
		entity.setCredits(credits);
		entity.setDate(date);
		entity.setTags(tags);
		return entity;
	}

	@Override
	protected Photo createEmptyInstance() {
		return new Photo();
	}

	@Override
	protected void saveObject(Photo entity, boolean isNew) {
		if (isNew) {
			photoService.persist(entity, picture);
		} else {
			photoService.update(entity, picture);
		}		
	}

	@Override
	protected Collection<Photo> generateList() {
		setPages((int)Math.floor(1f * photoService.count() / getPageSize()) + 1);
		Collection<Photo> list = photoService.list(getCurrentPage(), getPageSize());
		return list;
	}

	@Override
	protected Collection<Photo> generateSearch() {
		ResultList<Photo> result = photoService.search(getSearch(), getCurrentPage(), getPageSize());
        setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}

	@Override
	protected Photo getObject() {
		return photoService.get(id);
	}
	
	@Override
	public void validate() {	
		if (date == null) {
			addFieldError("date", "Digite uma data.");
		}			
		if (picture == null && id <= 0) {
			addFieldError("picture", "Escolha uma imagem.");
		}			
		if (credits == null || credits.isEmpty()) {
			addFieldError("credits", "Selecione o fotógrafo.");
		}
	}	
	
	//Action properties
	
	private File picture;
	
	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}
	
    public String getShowSize(int w, int h) {
    	Photo photo = photoService.get(id);
        if (photo.getId() == null || photo.getId() == 0) {
            return "400x266";
        }
        double x = 0;
        double y = 0;
        if (1d * photo.getWidth() / photo.getHeight() >= 1d * w / h) {
            if (photo.getWidth() > w) {
                x = w;
                y = photo.getHeight() * (1d * w / photo.getWidth());
            } else {
                x = photo.getWidth();
                y = photo.getHeight();
            }
        } else {
            if (photo.getHeight() > h) {
                y = h;
                x = photo.getWidth() * (1d * h / photo.getHeight());
            } else {
                x = photo.getWidth();
                y = photo.getHeight();
            }
        }
        return (int) x + "x" + (int) y;
    }

    public int getShowWidth(int w, int h) {
    	Photo photo = photoService.get(id);
        if (photo.getId() == null || photo.getId() == 0) {
            return 400;
        }
        double x = 0;
        if (1d * photo.getWidth() / photo.getHeight() >= 1d * w / h) {
            if (photo.getWidth() > w) {
                x = w;
            } else {
                x = photo.getWidth();
            }
        } else {
            if (photo.getHeight() > h) {
                x = photo.getWidth() * (1d * h / photo.getHeight());
            } else {
                x = photo.getWidth();
            }
        }
        return (int) x;
    }

    public int getShowHeight(int w, int h) {
    	Photo photo = photoService.get(id);
        if (photo.getId() == null || photo.getId() == 0) {
            return 266;
        }
        double y = 0;
        if (1d * photo.getWidth() / photo.getHeight() >= 1d * w / h) {
            if (photo.getWidth() > w) {
                y = photo.getHeight() * (1d * w / photo.getWidth());
            } else {
                y = photo.getHeight();
            }
        } else {
            if (photo.getHeight() > h) {
                y = h;
            } else {
                y = photo.getHeight();
            }
        }
        return (int) y;
    }	

	//POJO
	
	private String lastModifiedByName;
	
	private Collection<Photo> photos;
	
	private float horizontalCenter;
	
	private float verticalCenter;
	
	private String createdByName;
    
    private String description;
    
    private boolean published;
    
    private Date lastModified;
    
    private String credits;    
    
    private Date created;
    
    private String tags;
    
    private int height;
    
    private int width;
    
    private Date date;
    
	private long id;

	public String getLastModifiedByName() {
		return lastModifiedByName;
	}

	public void setLastModifiedByName(String lastModifiedByName) {
		this.lastModifiedByName = lastModifiedByName;
	}

	public Collection<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Collection<Photo> photos) {
		this.photos = photos;
	}

	public float getHorizontalCenter() {
		return horizontalCenter;
	}

	public void setHorizontalCenter(float horizontalCenter) {
		this.horizontalCenter = horizontalCenter;
	}

	public float getVerticalCenter() {
		return verticalCenter;
	}

	public void setVerticalCenter(float verticalCenter) {
		this.verticalCenter = verticalCenter;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
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

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Date getDate() {
		return date != null ? date : new Date();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}