<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="refresh" content="1200"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes"/>
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
<link href="/frameworks/jquery/plugins/perfect-scrollbar.min.css" rel="stylesheet" type="text/css"/>
<link rel="shortcut icon" href="http://cdn-tatame.trrsf.com/skins/tatame/img/favicon_16x16-ok.png" type="image/x-icon"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Metrophobic" type="text/css"/>
<link rel="stylesheet" href="/skins/tatame/css/main.css?19" type="text/css"/>
<s:if test="model != null && model instanceof com.publisher.entity.Article">
	<link rel="stylesheet" href="/skins/tatame/css/ArticleMain.css?1" type="text/css"/>
	<s:if test="model instanceof com.publisher.entity.PhotoGallery">
		<link rel="stylesheet" href="/skins/tatame/css/PhotoGallery.css" type="text/css"/>		
	</s:if>
</s:if>
<s:elseif test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<link rel="stylesheet" href="/skins/tatame/css/Home.css?11" type="text/css"/>
</s:elseif>
<s:elseif test="model != null && model instanceof com.publisher.entity.Category">
	<link rel="stylesheet" href="/skins/tatame/css/Category.css" type="text/css"/>
</s:elseif>
<s:elseif test="articles != null">
	<link rel="stylesheet" href="/skins/tatame/css/Search.css" type="text/css"/>
</s:elseif>