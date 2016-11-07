package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;

import com.publisher.entity.Poll;
import com.publisher.service.PollService;

public class PollAutoCompleteAction extends AutoCompleteAction {

	private PollService pollService;
	
	public void setPollService(PollService pollService){
		this.pollService = pollService;
	}
	
	@Override
	public void populate() {
		if (getTerm() == null) {
			return;
		}
		Collection<Poll> list = pollService.search(addWildcards(getTerm()));
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		for (Poll object: list) {
			collection.add(new LabelValue(object.getQuestion(), Long.toString(object.getId())));
		}				
		setResult(collection);		
	}
}