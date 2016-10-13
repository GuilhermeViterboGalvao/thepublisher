package br.com.tatame.view;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.publisher.view.ViewAction;

import br.com.tatame.entity.LiveStats;
import br.com.tatame.service.LiveStatsService;

public class LiveStatsAction extends ActionSupport implements ModelDriven<LiveStats>, Preparable, ViewAction {

	private static final long serialVersionUID = -5257533673652155735L;

	private LiveStatsService liveStatsService;
	
	public void setLiveStatsService(LiveStatsService liveStatsService) {
		this.liveStatsService = liveStatsService;
	}
	
	private LiveStats model;
	
	@Override
	public LiveStats getModel() {
		return model;
	}
	
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public void prepare() throws Exception {
		if (id > 0) {
			model = liveStatsService.get(id);
		}
	}
	
	@Override
	public String getLayoutPath() {
		return model != null && model.getSkin() != null ? model.getSkin().getLayoutPath() : "";
	}

	@Override
	public String getContentPath() {
		return model != null && model.getSkin() != null ? model.getSkin().getContentPath(model.getClass().getSimpleName()) : "/skins/tatame/pages/LiveStats.jsp";
	}
}