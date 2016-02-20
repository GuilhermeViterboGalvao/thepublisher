package br.com.clubetatame.service;

import java.util.Date;
import java.util.List;
import com.publisher.service.Service;
import br.com.clubetatame.entity.Company;
import br.com.clubetatame.entity.Event;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface EventService extends Service<Event> {

	List<Event> list();
	
	List<Event> list(Boolean isActive);
	
	List<Event> list(Boolean isActive, int page, int pageSize);
	
	List<Event> listByCompany(Company company);
	
	List<Event> listByCompany(Company company, Boolean isActive);	
	
	List<Event> listByCompany(Company company, Boolean isActive, int page, int pageSize);
	
	List<Event> listByDate(Date start);
	
	List<Event> listByDate(Date start, Date end);
	
	List<Event> listByDate(Date start, Date end, Boolean isActive);
	
	List<Event> listByDate(Date start, Date end, Boolean isActive, int page, int pageSize);
	
	int count(Boolean isActive);
	
	int countByCompany(Company company);
	
	int countByCompany(Company company, Boolean isActive);
	
	int countByDate(Date Start);
	
	int countByDate(Date Start, Date end);
	
	int countByDate(Date Start, Date end, Boolean isActive);
}