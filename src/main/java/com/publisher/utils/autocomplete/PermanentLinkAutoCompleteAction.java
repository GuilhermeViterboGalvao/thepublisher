package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;

import com.publisher.entity.PermanentLink;
import com.publisher.service.PermanentLinkService;

public class PermanentLinkAutoCompleteAction extends AutoCompleteAction {

	private PermanentLinkService permanentLinkService;
	
	public void setPermanentLinkService(PermanentLinkService permanentLinkService){
		this.permanentLinkService = permanentLinkService;
	}
	
	@Override
	public void populate() {
		PermanentLink permanentLink = null;
		if(getTerm() != null) {
			while(getTerm().charAt(0) == '/' && getTerm().length() > 0) {				
				setTerm(getTerm().substring(1));			
			}
			permanentLink = permanentLinkService.get(getTerm());
		}
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		collection.add(new LabelValue("url", permanentLink != null ? permanentLink.getUri() : ""));
		setResult(collection);
	}
}