package com.publisher.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import com.publisher.service.AccountService;
import com.publisher.service.ArticleService;
import com.publisher.service.CategoryService;
import com.publisher.service.PageService;
import com.publisher.service.PermanentLinkService;
import com.publisher.service.PhotoService;
import com.publisher.service.SkinService;

public class IndexAllFacade extends ActionSupport implements AccountAware {

	private static final long serialVersionUID = -193803987154138591L;

	protected static Log log = LogFactory.getLog(IndexAllFacade.class);
	
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private PageService pageService;

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	
	private PermanentLinkService permanentLinkService;

	public void setPermanentLinkService(PermanentLinkService permanentLinkService) {
		this.permanentLinkService = permanentLinkService;
	}
	
	private PhotoService photoService;

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	private SkinService skinService;

	public void setSkinService(SkinService skinService) {
		this.skinService = skinService;
	}
	
	private String resultMessage;
	
	public String getResult() {
		return resultMessage;
	}
	
	public String indexAccount() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade ACCOUNT");
				accountService.indexAll();
				log.info("Fim da indexação da entidade ACCOUNT");
				resultMessage = "Entidade ACCOUNT indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade ACCOUNT!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-account";
	}
	
	public String indexArticle() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade ARTICLE");
				articleService.indexAll();
				log.info("Fim da indexação da entidade ARTICLE");
				resultMessage = "Entidade ARTICLE indexada com sucesso.";
			} catch (Exception e) {
				log.error(e);
				resultMessage = "Erro ao indexar a entidade ARTICLE!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-article";
	}
	
	public String indexCategory() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade CATEGORY");
				categoryService.indexAll();
				log.info("Fim da indexação da entidade CATEGORY");
				resultMessage = "Entidade CATEGORY indexada com sucesso.";
			} catch (Exception e) {
				log.error(e);
				resultMessage = "Erro ao indexar a entidade CATEGORY!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-category";
	}
	
	public String indexPage() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade PAGE");
				pageService.indexAll();
				log.info("Fim da indexação da entidade PAGE");
				resultMessage = "Entidade PAGE indexada com sucesso.";			
			} catch (Exception e) {
				log.error(e);
				resultMessage = "Erro ao indexar a entidade PAGE!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-page";
	}
	
	public String indexPermanentLink() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade PERMANENTLINK");
				permanentLinkService.indexAll();
				log.info("Fim da indexação da entidade PERMANENTLINK");
				resultMessage = "Entidade PERMANENTLINK indexada com sucesso.";	
			} catch (Exception e) {
				log.error(e);
				resultMessage = "Erro ao indexar a entidade PERMANENTLINK!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-permanentLink";
	}
	
	public String indexPhoto() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade PHOTO");
				photoService.indexAll();
				log.info("Fim da indexação da entidade PHOTO");
				resultMessage = "Entidade PHOTO indexada com sucesso.";
			} catch (Exception e) {
				log.error(e);
				resultMessage = "Erro ao indexar a entidade PHOTO!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-photo";
	}
	
	public String indexSkin() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade SKIN");
				skinService.indexAll();
				log.info("Fim da indexação da entidade SKIN");
				resultMessage = "Entidade SKIN indexada com sucesso.";
			} catch (Exception e) {
				log.error(e);
				resultMessage = "Erro ao indexar a entidade SKIN!\nVerifique o log no servidor.";
			}
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-skin";
	}

	private Account account;
	
	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Account getAccount() {
		return account;
	}

	@Override
	public boolean isAdmin() {
		if (account != null && account.getSecurityHole() != null && !account.getSecurityHole().isEmpty() && account.getSecurityHole().equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}
}