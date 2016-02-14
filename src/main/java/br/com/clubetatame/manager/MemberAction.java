package br.com.clubetatame.manager;

import java.util.Collection;
import java.util.Date;
import com.publisher.entity.Account;
import com.publisher.utils.ResultList;
import br.com.clubetatame.entity.Member;
import br.com.clubetatame.service.MemberService;

public class MemberAction extends AbstractAction<Member> {

	private static final long serialVersionUID = -3113931084212676293L;
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	protected void indexAll() {
		memberService.indexAll();
	}

	@Override
	protected void populateForm(Member entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.document = entity.getDocument();
			this.email = entity.getEmail();
			this.gender = entity.getGender();
			this.birth = entity.getBirth();
			this.address = entity.getAddress();
			this.cep = entity.getCEP();
			this.active = entity.isActive();
			this.createdBy = entity.getCreatedBy();
			this.created = entity.getCreated();
			this.lastModified = entity.getLastModified();
			this.lastModifiedBy = entity.getLastModifiedBy();
		}		
	}

	@Override
	protected Member updateObject(Member entity) {
		if (entity != null) {
			entity.setName(name);
			entity.setDocument(document);
			entity.setEmail(email);
			entity.setGender(gender);
			entity.setBirth(birth);
			entity.setAddress(address);
			entity.setCEP(cep);
			entity.setActive(active);
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(getAccount());
			}
			if (entity.getCreated() == null) {
				entity.setCreated(new Date());
			}
			entity.setLastModifiedBy(getAccount());
			entity.setLastModified(new Date());
		}
		return entity;
	}

	@Override
	protected Member createEmptyInstance() {
		return new Member();
	}

	@Override
	protected void saveObject(Member entity, boolean isNew) {
		if (isNew) {
			memberService.persist(entity);
		} else {
			memberService.update(entity);
		}
	}

	@Override
	protected Collection<Member> generateList() {
		setPages((int)Math.floor(1f * memberService.count() / getPageSize()) + 1);		
		return memberService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Member> generateSearch() {
		ResultList<Member> result = memberService.search(getSearch(), getCurrentPage(), getPageSize());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result != null ? result.getResult() : null;
	}

	@Override
	protected Member getObject() {
		return memberService.get(id);
	}
	
	@Override
	public void validate() {
		super.validate();
		if (name == null || name.isEmpty()) {
			addFieldError("name", "O campo 'nome' é obrigatório.");
		}
		if (document == null || document.isEmpty()) {
			addFieldError("document", "O campo 'R.G.' é obrigatório.");
		}
		if (email == null || email.isEmpty()) {
			addFieldError("email", "O campo 'e-mail' é obrigatório.");
		}
		if (gender == null || gender.isEmpty()) {
			addFieldError("gender", "O campo 'sexo' é obrigatório.");
		}
		if (birth == null) {
			addFieldError("birth", "O campo 'data de nascimento' é obrigatório.");
		}
		if (address == null || address.isEmpty()) {
			addFieldError("address", "O campo 'endereço' é obrigatório.");
		}
		if (cep == null || cep.isEmpty()) {
			addFieldError("cep", "O campo 'CEP' é obrigatório.");
		}		
	}
	
	//Action properties
	
	private String orderBy = "publishedAt";
	
	private boolean orderly = true;
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderly() {
		return orderly;
	}

	public void setOrderly(boolean orderly) {
		this.orderly = orderly;
	}	
	
	//POJO

	private long id = -1;
	
	private String name;
	
	private String document;
	
	private String email;
	
	private String gender;
	
	private Date birth;
	
	private String address;
	
	private String cep;
	
	private boolean active;
	
	private Account createdBy;
	
	private Date created;
	
	private Account lastModifiedBy;
	
	private Date lastModified;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Account getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Account lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}