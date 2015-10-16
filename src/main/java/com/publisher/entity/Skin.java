package com.publisher.entity;

import java.io.File;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skin implements Serializable {

	private static final long serialVersionUID = 2897101824927558288L;

	@Id
	@DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Field
	private String name;
	
	@Field
	private String path;
	
	@Field
	private String contentFolder;
	
	public String getLayoutPath() {
		String path = normalizePath(this.path);
    	File file = new File(ServletActionContext.getServletContext().getRealPath(path));
    	if (file.exists() && !file.isDirectory()) {
    		return path;
    	}
    	if (file.isDirectory()) {
    		file = new File(file, "layout.jsp");
    	}
    	if (file.exists() && !file.isDirectory()) {
    		return path + "/layout.jsp";
    	}
    	return null;
    }
    
    public String getContentPath(String actionName) {
    	File file = null;
    	String path = normalizePath(this.path);
    	file = new File(ServletActionContext.getServletContext().getRealPath(path));
    	if (file.isDirectory()) {
    		file = new File(file, "pages/" + actionName + ".jsp");
    		if (file.exists() && !file.isDirectory()) {
    			return path + "/pages/" + actionName + ".jsp";
    		}
    	}
    	return null;
    }
    
    private String normalizePath(String path) {
    	if (path == null || path.length() == 0) {
    		return path;
    	}
    	while(path.charAt(path.length() - 1) == '/') {
    		path = path.substring(0, path.length() - 1);
    	}
    	if (path.charAt(0) != '/') {
    		path = "/" + path;
    	}
    	return path;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContentFolder() {
		return contentFolder;
	}

	public void setContentFolder(String contentFolder) {
		this.contentFolder = contentFolder;
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Skin)) {
            return false;
        }
        Skin other = (Skin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.publisher.entity.Skin#" + String.valueOf(id);
    }	
}