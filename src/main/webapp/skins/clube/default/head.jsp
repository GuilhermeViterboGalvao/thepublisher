<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes"/>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,700,400" rel="stylesheet" type="text/css"/>
<s:if test="#metaTagsPathJSP != null">
	<s:include value="%{metaTagsPathJSP}"/>
</s:if>
<s:if test="#titlePage != null">
	<title><s:property value="%{titlePage}"/></title>
</s:if>
<link href="/skins/clube/css/main.css" rel="stylesheet" type="text/css"/>

<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 6">
	<link href="/skins/clube/css/home.css" rel="stylesheet" type="text/css"/>
</s:if>

<s:if test="model != null && model instanceof com.publisher.entity.Article">
	<link href="/skins/clube/css/article.css" rel="stylesheet" type="text/css"/>	
</s:if>

<s:if test="model != null && model instanceof br.com.clubetatame.entity.Gym">
	<link href="/skins/clube/css/gym.css" rel="stylesheet" type="text/css"/>	
</s:if>

<s:if test="contracts != null">
	<link href="/skins/clube/css/gymContracts.css" rel="stylesheet" type="text/css"/>	
</s:if>