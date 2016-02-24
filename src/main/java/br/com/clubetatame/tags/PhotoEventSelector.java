package br.com.clubetatame.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;
import com.opensymphony.xwork2.util.ValueStack;
import com.publisher.tags.PhotoSelector;

@StrutsTag(name="photoeventselector", tldTagClass="br.com.clubetatame.tags.PhotoSelector", description="Seleciona as fotos dos eventos cadastradas no sistema através das tags.", allowDynamicAttributes=true)
public class PhotoEventSelector extends PhotoSelector {

	public PhotoEventSelector(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	
	protected String getDefaultTemplate() {
		return "photoeventselector";
	}
	
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("isEvent", "true");
	}
}