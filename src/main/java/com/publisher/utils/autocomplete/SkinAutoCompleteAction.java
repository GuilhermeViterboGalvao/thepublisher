package com.publisher.utils.autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import com.publisher.entity.Skin;
import com.publisher.service.SkinService;

public class SkinAutoCompleteAction extends AutoCompleteAction{
	
	private SkinService skinService;
	
	public void setSkinService(SkinService skinService){
		this.skinService = skinService;
	}
	
	@Override
	public void populate() {
		if (getTerm() == null) {
			return;
		}
		Collection<Skin> skins = skinService.search(addWildcards(getTerm()));
		Collection<LabelValue> collection = new ArrayList<LabelValue>();
		for (Skin skin: skins) {
			collection.add(new LabelValue(skin.getName(), Long.toString(skin.getId())));
		}				
		setResult(collection);		
	}
}