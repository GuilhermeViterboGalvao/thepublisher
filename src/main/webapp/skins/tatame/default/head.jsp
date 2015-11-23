<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="refresh" content="1200">
<s:if test="title != null || note != null || tags != null">
	<title>${title}</title>	
	<meta name="title" content="${title}"/>
	<meta name="description" content="${note}" />
	<meta name="keywords" content="${tags}" lang="pt-BR"/>
</s:if>
<s:else>
	<title>${titlePage}</title>
	<jsp:include page="/skins/tatame/default/meta-tags.jsp"/>
</s:else>
<s:if test="photo != null">
	<meta content="http://cdn-tatame.trrsf.com/img/${photo.id}_210x140.jpg" name="og:image" title="${photo.description}"/>	
	<meta property="og:description" content="<s:property value='note'/>"/>
	<meta property="og:title" content="<s:property value='title'/>" />
	<meta property="og:image" content="http://cdn-tatame.trrsf.com/img/<s:property value='photo.id'/>_670x418.jpg"/>
	<meta property="og:url" content="http://www.tatame.com.br/<s:property value='permanentLink.uri'/>"/>		
	<link href="http://cdn-tatame.trrsf.com/img/${photo.id}_210x140.jpg" rel="image_src" title="${photo.description}"/>
</s:if>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Metrophobic" type="text/css"/>
<link rel="stylesheet" href="/skins/tatame/css/main.css" type="text/css"/>
<s:if test="model != null && model instanceof com.publisher.entity.Article">
	<link rel="stylesheet" href="/skins/tatame/css/ArticleMain.css" type="text/css"/>
	<s:if test="model instanceof com.publisher.entity.PhotoGallery">
		<link rel="stylesheet" href="/skins/tatame/css/PhotoGallery.css" type="text/css"/>		
	</s:if>
</s:if>
<s:elseif test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<link rel="stylesheet" href="/skins/tatame/css/Home.css?4" type="text/css" />
</s:elseif>