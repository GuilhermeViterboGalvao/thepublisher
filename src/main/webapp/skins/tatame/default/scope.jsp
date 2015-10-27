<%@ page contentType="text/html" pageEncoding="UTF-8"			  %>
<%@ taglib prefix="s" uri="/struts-tags"						  %>
<s:set var="titlePage"    	value="'TATAME - Tudo sobre o mundo das lutas'" />
<s:set var="headerPathJSP"  value="'/skins/tatame/default/header.jsp'" />
<s:set var="menuPathJSP"  	value="'/skins/tatame/default/menu.jsp'" />
<s:set var="sidePathJSP"  	value="'/skins/tatame/default/side.jsp'" />
<s:set var="dfpPathJSP"  	value="'/skins/tatame/default/google-dfp-script.jsp'" />
<s:if test="model instanceof com.system.entity.Page">
	<s:set var="bodyClasses" value="'home blog'"/>
	<s:set var="tgmKey" value="'br.cobranded_tatame.home'"/>
</s:if>
<s:elseif test="model instanceof com.system.entity.Article">
	<s:set var="bodyClasses" value="'single single-post single-format-standard'"/>
	<s:set var="tgmKey" value="'br.cobranded_tatame.articles'"/>
</s:elseif>
<s:else>
	<s:set var="bodyClasses" value="'archive tag'"/>
	<s:set var="tgmKey" value="'br.cobranded_tatame.articles'"/>
</s:else>