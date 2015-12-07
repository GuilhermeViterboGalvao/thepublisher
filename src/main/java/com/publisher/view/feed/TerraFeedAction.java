package com.publisher.view.feed;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Article;
import com.publisher.service.ArticleService;

public class TerraFeedAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = -2116914199777614564L;
	
	private static final Logger log = Logger.getLogger(TerraFeedAction.class);

	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	private HttpServletRequest request;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public String execute() throws Exception {
		String ipRequested = request.getRemoteAddr();
		boolean executeAction = false;
		if (debug) {
			executeAction = true;
		} else {
			for (String releasedIP : releasedIPs) {
				releasedIP = releasedIP.replace(".*", "");
				if (ipRequested.contains(releasedIP)) {
					executeAction = true;
					break;
				}
			}	
		}
		if (executeAction && startdate != null && !startdate.isEmpty() && enddate != null && !enddate.isEmpty()) {
			try {
				//Unix Epoch Time
				Date start = new Date(Long.parseLong(startdate) * 1000);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, -30);
				Date minDate = calendar.getTime();
				if(start.compareTo(minDate) >= 0) {
					Date end = new Date(Long.parseLong(enddate) * 1000);
					if (end.compareTo(start) >= 0) {
						articles = articleService.get(0, 0, start, end, true);	
					}
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return SUCCESS;
	}
	
	//Action properties
	
	private String[] releasedIPs = { "200.53.94.*", "98.142.237.35" };//Terra Server IPs
	
	private String startdate;
	
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	private String enddate;
	
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	private List<Article> articles;
	
	public List<Article> getArticles() {
		return articles;
	}
	
	private boolean debug = false;
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}