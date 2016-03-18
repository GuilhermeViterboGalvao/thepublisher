<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="box box-1024 box-shadow">
	<span class="category-name">Academias</span>
	

		
	<s:iterator value="list" status="st">
		<p class="gym.title"><s:property value="gym.name"/></p>
		<s:if test="end.after(CurrentDate) && value > 0">
			<span>Premium</span>
		</s:if>
	</s:iterator>

</div>
	