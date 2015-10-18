package com.publisher.ehcache.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.attribute.AttributeExtractor;

public class XMLAttributeExtractor implements AttributeExtractor {

	private static final long serialVersionUID = 8309584689171660934L;
	
	private static Log log = LogFactory.getLog(XMLAttributeExtractor.class);
    
	public Object attributeFor(Element element, String attributeName) {
          if (attributeName.equals("xml")) {
        	  int i = ((String)element.getObjectKey()).indexOf(":");
        	  if (i<0) {
        		  log.debug("xml not found");
        		  return null;
        	  }
        	  log.debug("atribute xml for " + element.getObjectKey() + ":" + ((String)element.getObjectKey()).subSequence(0, i));
              return ((String)element.getObjectKey()).subSequence(0, i);
          }  
          return null;
      }
}