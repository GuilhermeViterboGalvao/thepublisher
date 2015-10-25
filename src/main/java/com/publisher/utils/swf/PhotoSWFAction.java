package com.publisher.utils.swf;

public class PhotoSWFAction extends AbstractSWFAction<Long> {

	private static final long serialVersionUID = 4938260760523660907L;

	@Override
	public Long getResult() {
		return getId();
	}
}