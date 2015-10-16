package com.publisher.service;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import com.publisher.entity.PermanentLink;
import net.bull.javamelody.MonitoredWithSpring;

@MonitoredWithSpring
public interface PermanentLinkService extends Service<PermanentLink> {

	PermanentLink get(long id);
	
	PermanentLink get(String uri);
	
	ActionMapping getActionMapping(String url);
	
	void change(PermanentLink oldPermanentLink, PermanentLink newPermanentLink);
	
	void removeFromCacheIfIsNotPermanent(String uri);
}