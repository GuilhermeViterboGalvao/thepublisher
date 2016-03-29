package br.com.clubetatame.service;

import java.util.Date;
import java.util.List;

import com.publisher.entity.PermanentLink;
import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.Event;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface EventService extends Service<Event> {
	
	void update (Event entity, PermanentLink oldPermanentLink);
	
	ResultList<Event> search(String query, int page, int pageSize, Boolean isActive);

	List<Event> list();
	
	List<Event> list(Boolean isActive);
	
	List<Event> list(Boolean isActive, int page, int pageSize);
	
	List<Event> list(Boolean isActive, int page, int pageSize, String orderBy, String order);
	
	List<Event> listByCompany(Company company);
	
	List<Event> listByCompany(Company company, Boolean isActive);	
	
	List<Event> listByCompany(Company company, Boolean isActive, int page, int pageSize);
	
	List<Event> listByCompany(Company company, Boolean isActive, int page, int pageSize, String orderBy, String order);
	
	List<Event> listByDate(Date start);
	
	List<Event> listByDate(Date start, Date end);
	
	List<Event> listByDate(Date start, Date end, Boolean isActive);
	
	List<Event> listByDate(Date start, Date end, Boolean isActive, int page, int pageSize);
	
	List<Event> listByDate(Date start, Date end, Boolean isActive, int page, int pageSize, String orderBy, String order);
	
	long count(Boolean isActive);
	
	long countByCompany(Company company);
	
	long countByCompany(Company company, Boolean isActive);
	
	long countByDate(Date Start);
	
	long countByDate(Date Start, Date end);
	
	long countByDate(Date Start, Date end, Boolean isActive);
}