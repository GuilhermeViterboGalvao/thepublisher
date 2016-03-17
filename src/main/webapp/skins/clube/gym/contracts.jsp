<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="box box-1024 box-shadow">
	<span class="category-name">Academias</span>
	

		
	<s:iterator value="list" status="st">
		<p class="title"><s:property value="name"/></p>
	</s:iterator>

</div>
	