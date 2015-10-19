package com.publisher.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.publisher.utils.TileRepository;

public class TileTag implements Tag {
	
	private String xml;
	private String xsl;	
	private PageContext pageContext;
	private Tag parent;
	
	public int doStartTag() throws JspException {
		try {
			if (xml != null) {
				if (xsl == null) {
					xsl = xml;
				}
				pageContext.getOut().print(TileRepository.getInstance().get(xml, xsl));
				release();
			}
		} catch (Exception ex) {
			throw new JspTagException("TileTag: " + ex.getMessage());
		}
		return SKIP_BODY;
	}
	public int doEndTag() {
		return EVAL_PAGE;
	}
	
	public void setXml(String xml) {
		this.xml = xml;
	}
	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public void setParent(Tag parent) {
		this.parent = parent;
	}

	public Tag getParent() {
		return parent;
	}

	public void release() {
		xml = null;
		xsl = null;		
	}
}