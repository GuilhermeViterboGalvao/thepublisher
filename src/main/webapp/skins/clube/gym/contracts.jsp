<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="box box-1024 box-shadow">
	<span class="category-name">ACADEMIAS PREMIUM</span>
	

		
	<s:iterator value="list" status="st">
		<s:if test="end.after(CurrentDate) && value > 0">
			<p><s:property value="gym.name"/></p>
			<p><s:property value="gym.description"/></p>
		
		</s:if>
		<s:else>
			<p><s:property value="gym.name"/></p>
			<p>
				<s:property value="gym.address"/>
				<s:property value="gym.city"/>
				<s:property value="gym.state"/>
			</p>
		</s:else>
		
	</s:iterator>

</div>
	