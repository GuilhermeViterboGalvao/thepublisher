package br.com.clubetatame.view;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class AbstractAction<T> extends ActionSupport implements ModelDriven<T>, Preparable, ViewAction {

	private static final long serialVersionUID = -843169202944052227L;

	private T model;
	
	@Override
	public T getModel() {
		return model;
	}
	
	public void setModel(long id){
		this.model = getEntity(id);
	}
	
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	@Override
	public void prepare() throws Exception {
		if (id > 0) {
			setModel(id);
		}
	}
	
	public abstract T getEntity(long id);
	
	@Override
	public abstract String getLayoutPath();

	@Override
	public abstract String getContentPath();
}