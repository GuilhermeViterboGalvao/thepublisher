package br.com.clubetatame.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.publisher.entity.Account;
import br.com.clubetatame.service.CompanyContractService;
import br.com.clubetatame.service.CompanyService;
import br.com.clubetatame.service.EventService;
import br.com.clubetatame.service.GymContractService;
import br.com.clubetatame.service.GymService;
import br.com.clubetatame.service.MemberContractService;
import br.com.clubetatame.service.MemberService;
import br.com.clubetatame.service.ProductService;

public class IndexAllFacade extends ActionSupport implements AccountAware {

	private static final long serialVersionUID = 4360826367610393246L;

	protected static Log log = LogFactory.getLog(IndexAllFacade.class);

	private CompanyService companyService;

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	private CompanyContractService companyContractService;

	public void setCompanyContractService(CompanyContractService companyContractService) {
		this.companyContractService = companyContractService;
	}
	
	private EventService eventService;

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	
	private GymService gymService;
	
	public void setGymService(GymService gymService) {
		this.gymService = gymService;
	}
	
	private GymContractService gymContractService;
	
	public void setGymContractService(GymContractService gymContractService) {
		this.gymContractService = gymContractService;
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	private MemberContractService memberContractService;
	
	public void setMemberContractService(MemberContractService memberContractService) {
		this.memberContractService = memberContractService;
	}
	
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	private String resultMessage;
	
	public String getResult() {
		return resultMessage;
	}
	
	public String indexCompany() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade COMPANY");
				companyService.indexAll();
				log.info("Fim da indexação da entidade COMPANY");
				resultMessage = "Entidade COMPANY indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade COMPANY!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-company";
	}
	
	public String indexCompanyContract() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade COMPANYCONTRACT");
				companyContractService.indexAll();
				log.info("Fim da indexação da entidade COMPANYCONTRACT");
				resultMessage = "Entidade COMPANYCONTRACT indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade COMPANYCONTRACT!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-company-contract";
	}
	
	public String indexEvent() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade EVENT");
				eventService.indexAll();
				log.info("Fim da indexação da entidade EVENT");
				resultMessage = "Entidade EVENT indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade EVENT!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-event";
	}
	
	public String indexGym() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade GYM");
				gymService.indexAll();
				log.info("Fim da indexação da entidade GYM");
				resultMessage = "Entidade GYM indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade GYM!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-gym";
	}
	
	public String indexGymContract() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade GYMCONTRACT");
				gymContractService.indexAll();
				log.info("Fim da indexação da entidade GYMCONTRACT");
				resultMessage = "Entidade GYMCONTRACT indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade GYMCONTRACT!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-gym-contract";
	}
	
	public String indexMember() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade MEMBER");
				memberService.indexAll();
				log.info("Fim da indexação da entidade MEMBER");
				resultMessage = "Entidade MEMBER indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade MEMBER!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-member";
	}
	
	public String indexMemberContract() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade MEMBERCONTRACT");
				memberContractService.indexAll();
				log.info("Fim da indexação da entidade MEMBERCONTRACT");
				resultMessage = "Entidade MEMBERCONTRACT indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade MEMBERCONTRACT!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-member-contract";
	}
	
	public String indexProduct() {
		if (isAdmin()) {
			try {
				log.info("Iniciando a indexação da entidade PRODUCT");
				productService.indexAll();
				log.info("Fim da indexação da entidade PRODUCT");
				resultMessage = "Entidade PRODUCT indexada com sucesso.";
			} catch (Exception e) {			
				log.error(e);
				resultMessage = "Erro ao indexar a entidade PRODUCT!\nVerifique o log no servidor.";
			}	
		} else {
			resultMessage = "Você não tem permissão para executar essa ação!";
		}
		return "index-product";
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