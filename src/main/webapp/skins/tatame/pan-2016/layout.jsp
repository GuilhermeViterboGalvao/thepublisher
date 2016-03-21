<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="/skins/tatame/default/scope.jsp"/>

<s:set var="customHeadPathJSP" value="'/skins/tatame/pan-2016/custom-head.jsp'" />
<s:set var="customHeaderImagePath" value="'/skins/tatame/img/pan-2016-banner.jpg'" />

<s:if test="model != null && model instanceof com.publisher.entity.Article">
	<s:set var="customDfpPathJSP" value="'/skins/tatame/pan-2016/google-dfp-script.jsp'" />
</s:if>

<s:if test="model != null && model instanceof com.publisher.entity.Category && model.id == 41">
	<s:set var="customDfpPathJSP" value="'/skins/tatame/pan-2016/google-dfp-script.jsp'" />
</s:if>

<jsp:include page="/skins/tatame/pan-2016/template.jsp"/>