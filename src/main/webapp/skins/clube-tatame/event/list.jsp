<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="boxs">
	<span class="title">Eventos</span>

	<s:iterator value="events" status="st">
		<span class="name"><s:property value="name"/></span>
			
	</s:iterator>
</div>
	