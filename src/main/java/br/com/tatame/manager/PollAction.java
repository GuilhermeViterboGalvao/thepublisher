package br.com.tatame.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.publisher.manager.AbstractAction;
import com.publisher.utils.ResultList;

import br.com.tatame.entity.Alternative;
import br.com.tatame.entity.Poll;
import br.com.tatame.service.PollService;

public class PollAction extends AbstractAction<Poll> {

	private static final long serialVersionUID = 717763812970698375L;

	private PollService pollService;
	
	public void setPollService(PollService pollService) {
		this.pollService = pollService;
	}
	
	@Override
	protected void indexAll() {
		pollService.indexAll();		
	}

	@Override
	protected void populateForm(Poll entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.question = entity.getQuestion();
			this.alternatives = entity.getAlternatives();
			this.publishedAt = entity.getPublishedAt();
			this.published = entity.isPublished();
		}
	}

	@Override
	protected Poll updateObject(Poll entity) {
		if (entity != null) {
			entity.setQuestion(question);
			entity.setAlternatives(alternatives);
			entity.setPublishedAt(publishedAt);
			entity.setPublished(published);
			entity.setLastModified(new Date());
			entity.setLastModifiedBy(getAccount());
			if (entity.getCreated() == null) {
				entity.setCreated(new Date());	
			}
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(getAccount());	
			}			
		}
		return entity;
	}

	@Override
	protected Poll createEmptyInstance() {
		return new Poll();
	}

	@Override
	protected void saveObject(Poll entity, boolean isNew) {
		if (isNew) {
			pollService.persist(entity);
		} else {
			pollService.update(entity);		
		}
	}

	@Override
	protected Collection<Poll> generateList() {
		setPages((int)Math.floor(1f * pollService.count() / getPageSize()) + 1);		
		return pollService.list(getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
	}

	@Override
	protected Collection<Poll> generateSearch() {
		ResultList<Poll> result = pollService.search(getSearch(), getCurrentPage(), getPages());
		setPages((int)Math.floor(1f * result.getResultSize() / getPageSize()) + 1);
		return result.getResult();
	}

	@Override
	protected Poll getObject() {
		return pollService.get(id);
	}
	
	
	@Override
	public void validate() {
		super.validate();
		
		if (publishedAt == null) {
			addFieldError("publishedAt", "Você deve entrar com a data de publicação.");
		}
	}
	
	
	//PollAction properties
	
	private String orderBy = "publishedAt";
	
	private boolean orderly = true;

	//POJO
	
	private long id = 0;
	
	private String question;
	
	private List<Alternative> alternatives;
	
	private Date publishedAt;
	
	private boolean published;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Alternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}
	
	public Date getPublishedAt() {
		return publishedAt == null ? new Date() : publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
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
}