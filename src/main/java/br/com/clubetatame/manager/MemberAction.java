package br.com.clubetatame.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.publisher.entity.Account;
import com.publisher.utils.Option;
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
			
			if(entity.getBirth() != null){
				this.birth = getDate(entity.getBirth());
			}

			this.address = entity.getAddress();
			this.cep = entity.getCep();
			this.answer = entity.getAnswer();
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
			entity.setBirth(getDate(birth));
			entity.setAddress(address);
			entity.setCep(cep);
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
		return memberService.list(null, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
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
	
	private String getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String d = null;
		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day   = calendar.get(Calendar.DAY_OF_MONTH);
		d  = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
		d += "/" + (month < 10 ? "0" + String.valueOf(month) : String.valueOf(month));
		d += "/" + String.valueOf(year);
		return d;
	}
	
	private Date getDate(String date) {
		if (date != null && !date.isEmpty() && date.length() == 10) {
			String[] d = date.split("/");
			if (d != null && d.length == 3) {
				int year  = 0;
				int month = 0;
				int day   = 0;
				try {
					year  = Integer.parseInt(d[2]);
					month = Integer.parseInt(d[1]) - 1;
					day   = Integer.parseInt(d[0]);
				} catch (Exception e) { }
				if (year > 0 && month >= 0 && day > 0) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR,        year);
					calendar.set(Calendar.MONTH,      month);
					calendar.set(Calendar.DAY_OF_MONTH, day);
					calendar.set(Calendar.HOUR_OF_DAY,    0);
					calendar.set(Calendar.MINUTE,         0);
					calendar.set(Calendar.SECOND,         0);
					calendar.set(Calendar.MILLISECOND,    0);
					return calendar.getTime();
				}
			}
		}
		return null;
	}
	
	//Only for Select of genders	
	public List<Option> getListGenders() {
		List<Option> options = new ArrayList<Option>();
		options.add(new Option("Masculino", "Masculino"));
		options.add(new Option("Feminino", "Feminino"));
		return options;
	}
	
	//Action properties
	
	private String orderBy = "created";
	
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
	
	private String birth;
	
	private String address;
	
	private String cep;
	
	private String answer;
	
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

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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