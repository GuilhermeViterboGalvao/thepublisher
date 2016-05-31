<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="/skins/tatame/default/scope.jsp"/>

<s:set var="customHeadPathJSP" value="'/skins/tatame/special-categories/custom-head.jsp'" />
<s:set var="customHeaderImagePath" value="'/skins/tatame/special-categories/2016/jiu-jitsu-world-championship/img/banner-top.jpg'" />

<s:if test="model != null && model instanceof com.publisher.entity.Article">
	<s:set var="customDfpPathJSP" value="'/skins/tatame/special-categories/2016/jiu-jitsu-world-championship/google-dfp-script.jsp'" />
</s:if>

<s:if test="model != null && model instanceof com.publisher.entity.Category && model.id == 44">
	<s:set var="customDfpPathJSP" value="'/skins/tatame/special-categories/2016/jiu-jitsu-world-championship/google-dfp-script.jsp'" />
</s:if>

<jsp:include page="/skins/tatame/special-categories/template.jsp"/>