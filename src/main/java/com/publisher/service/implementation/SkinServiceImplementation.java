package com.publisher.service.implementation;

import com.publisher.entity.Skin;
import com.publisher.service.SkinService;

public class SkinServiceImplementation extends AbstractServiceImplementation<Skin> implements SkinService {
	
	@Override
	public Class<Skin> getServiceClass() {
		return Skin.class;
	}
}