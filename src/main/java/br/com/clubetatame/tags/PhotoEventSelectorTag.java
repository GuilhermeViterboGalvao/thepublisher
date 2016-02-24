package br.com.clubetatame.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import com.opensymphony.xwork2.util.ValueStack;
import com.publisher.tags.PhotoSelectorTag;

public class PhotoEventSelectorTag extends PhotoSelectorTag {

	private static final long serialVersionUID = -4156028198517471734L;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
		return new PhotoEventSelector(stack, req, res);
	}	
	
	protected void populateParams() {
		super.populateParams();
		PhotoEventSelector photoEventSelector = (PhotoEventSelector)this.component;
		photoEventSelector.setDisplay(display);
		photoEventSelector.setUrl(url);
		photoEventSelector.setDelay(delay);
		photoEventSelector.setInitialUrl(initialUrl);
		photoEventSelector.setPageSize(pageSize);
		photoEventSelector.setShowToolTip(showToolTip);
		photoEventSelector.setImagePattern(imagePattern);
		photoEventSelector.setMinLength(minLength);
		photoEventSelector.setLoadingImage(loadingImage);
		photoEventSelector.setEvaluator(evaluator);
	}
}