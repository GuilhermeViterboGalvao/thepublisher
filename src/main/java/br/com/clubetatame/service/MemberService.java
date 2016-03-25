package br.com.clubetatame.service;

import java.util.Collection;
import java.util.List;
import com.publisher.service.Service;
import com.publisher.utils.ResultList;

import br.com.clubetatame.entity.Member;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface MemberService extends Service<Member> {

	Member authenticate(String email, String password);
	
	Member getByEmail(String email);
	
	Member getByDocument(String document);
	
	List<Member> getByName(String name);
    
    Collection<Member> list(Boolean active);
	
    Collection<Member> list(Boolean active, int page, int pageSize);
    
    Collection<Member> list(Boolean active, int page, int pageSize, String orderBy, String order);
    
    ResultList<Member> search(String query, int page, int pageSize);
    
    ResultList<Member> search(String query, int page, int pageSize, Boolean isActive);
    
    String hash(String password);
}