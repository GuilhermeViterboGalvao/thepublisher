package br.com.clubetatame.view;


import java.util.Collection;
import java.util.Date;

import br.com.clubetatame.entity.Event;
import br.com.clubetatame.service.EventService;

public class EventAction extends AbstractAction<Event> {

	private static final long serialVersionUID = 2980875638255151314L;

	private String orderBy = "end";
	
	private boolean orderly = false;
	
	private Collection<Event> events;
	
	private EventService eventService;
	
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	
    private Date start;
    
    public Date getStart(){
    	return start;
    }
    
    public void setStart(Date start) {
    	this.start = start;
    }    
    
    private Date end;
    
    public Date getEnd (){
    	return end;
    }
    
    public void setEnd(Date end) {
    	this.end = end;
    }
	
	public Collection<Event> getEvents(){
		return getEvents(0);
	}

	public Collection<Event> getEvents(int i){
		setPageSize(i);
		setPages((int) Math.floor(eventService.count() * 1f / getPageSize()) + 1);
		events = eventService.listByDate(start, end, true, getCurrentPage(), getPageSize(), orderBy, orderly ? "desc" : "asc");
      
        return events;
	}
	
	@Override
	public Event getEntity(long id) {
		return eventService.get(id);
	}
	
	@Override
	public String getLayoutPath() {
		return "/skins/clube-tatame/default/layout.jsp";
	}

	@Override
	public String getContentPath() {
		return ( getId() > 0 ? "/skins/clube-tatame/event/info.jsp" : "/skins/clube-tatame/event/list.jsp");
	}
}