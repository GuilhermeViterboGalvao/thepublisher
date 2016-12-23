package br.com.tatame.view;

import java.util.Collection;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.publisher.entity.Article;
import com.publisher.entity.PermanentLink;
import com.publisher.service.ArticleService;
import com.publisher.view.ViewAction;

import br.com.tatame.entity.LiveStats;
import br.com.tatame.service.LiveStatsService;

public class LiveStatsAction extends ActionSupport implements ModelDriven<LiveStats>, Preparable, ViewAction {

	private static final long serialVersionUID = -5257533673652155735L;

	private LiveStatsService liveStatsService;
	
	public void setLiveStatsService(LiveStatsService liveStatsService) {
		this.liveStatsService = liveStatsService;
	}
	
	private ArticleService articleService;
	
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }	
	
	private LiveStats model;
	
	private Collection<Article> articles;
	
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
			
			if (model != null && model.getCategory() != null) {
            	articles = articleService.get(model.getCategory(), 1, 3, null, null, true, null, null);	
        	}
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
	
    public Collection<Article> getArticles() {
		return articles;  
    } 
    
    public PermanentLink getCategoryLink(){
    	return model.getCategory().getPermanentLink();
    }
}