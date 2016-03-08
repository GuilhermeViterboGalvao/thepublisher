<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<s:if test="#headPathJSP != null">
			<s:include value="%{headPathJSP}"/>
		</s:if>
	</head>
	<body>
		<div class="page">
			<s:if test="#headerPathJSP != null">
				<s:include value="%{headerPathJSP}"/>
			</s:if>
			<tiles:insertAttribute name="content"/>
			<s:if test="#footerPathJSP != null">
				<s:include value="%{footerPathJSP}"/>
			</s:if>
			<s:if test="#scriptsPathJSP != null">
				<s:include value="%{scriptsPathJSP}"/>
			</s:if>
		</div>
	</body>
</html>