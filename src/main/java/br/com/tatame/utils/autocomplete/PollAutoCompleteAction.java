package br.com.tatame.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;


import com.publisher.utils.autocomplete.AutoCompleteAction;
import com.publisher.utils.autocomplete.LabelValue;

import br.com.tatame.entity.Poll;
import br.com.tatame.service.PollService;

public class PollAutoCompleteAction extends AutoCompleteAction{
	
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