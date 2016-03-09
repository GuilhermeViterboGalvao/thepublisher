<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes"/>
<s:if test="#metaTagsPathJSP != null">
	<s:include value="%{metaTagsPathJSP}"/>
</s:if>
<s:if test="#titlePage != null">
	<title><s:property value="%{titlePage}"/></title>
</s:if>
<link href="/skins/clube/css/main.css" rel="stylesheet" type="text/css"/>
<link href="/skins/clube/css/home.css" rel="stylesheet" type="text/css"/>