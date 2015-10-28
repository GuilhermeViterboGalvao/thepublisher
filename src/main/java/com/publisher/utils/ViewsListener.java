package com.publisher.utils;

import java.util.HashMap;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.publisher.entity.Article;
import com.publisher.service.EntityManagerFactoryService;

public class ViewsListener implements Runnable {

	private static Log log = LogFactory.getLog(ViewsListener.class);
	
	private static ViewsListener instance;
	
	public static ViewsListener getInstace() {
		return instance;
	}
	
	private ServletContext context;
	
	public static void start(ServletContext context) {
		if (instance == null) {
			synchronized (ViewsListener.class) {
				if (instance == null) {
					ViewsListener instance = new ViewsListener(context);
					ViewsListener.instance = instance;
					new Thread(instance, "ViewsListener").start();
				}
			}
		}
	}
	
	private HashMap<Long, Long> mapOfViews;
	
	private ViewsListener(ServletContext context) {
		this.mapOfViews = new HashMap<Long, Long>();
		this.context = context;
		this.running = true;		
	}
	
	private boolean running = false;
	
	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized void finish() {
		running = false;
	}
	
	@Override
	public void run() {
		while (running) {				
			try {
				//5 min.
				Thread.sleep(5*60*1000);
			} catch (Exception e) {
				log.error(e);
			}
			update();
		}
		update();
	}
	
	public void count(Long id) {
		synchronized (mapOfViews) {
			if (id != null) {
				Long views = mapOfViews.get(id);
				if (views == null || views <= 0l) {
					mapOfViews.put(id, 1l);
				} else {
					mapOfViews.put(id, ++views);
				}	
			}
		}
	}
	
	private EntityManagerFactoryService entityManagerFactoryService;
	
	private EntityManagerFactoryService getEntityManagerFactoryService() {
		if (entityManagerFactoryService == null) {
			synchronized (ViewsListener.class) {
				if (entityManagerFactoryService == null) {
					WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
					Object obj = webApplicationContext.getBean("entityManagerFactoryService");
					if (obj != null && obj instanceof EntityManagerFactoryService) {
						entityManagerFactoryService = (EntityManagerFactoryService)obj;
					}
				}
			}
		}
		return entityManagerFactoryService;
	}
	
	private void update() {
		synchronized (mapOfViews) {
			if (mapOfViews.size() > 0) {
				Set<Long> articles = mapOfViews.keySet();				
				EntityManager entityManager = getEntityManagerFactoryService().getEntityManager();
				if (entityManager != null) {
					try {
						entityManager.getTransaction().begin();
						for (Long articleId : articles) {
							Long views = mapOfViews.get(articleId);					
							Article article = entityManager.find(Article.class, articleId);
							article.setViews(article.getViews() + views);
							entityManager.merge(article);					
							log.info("UPDATING ARTICLE VIEWS ON ARTICLE.ID=" + articleId + " VIEWS=" + views + " TOTAL=" + article.getViews());
						}
						entityManager.getTransaction().commit();	
					} catch (Exception e) {
						if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
							entityManager.getTransaction().rollback();
						}
						log.error(e);
					} finally {
						entityManager.close();				
						log.info("UPDATING ARTICLE VIEWS: entityManager closed");
					}	
				}
				mapOfViews.clear();
			}
		}
	}
}