<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="active">
	<div class="padding-top-15">
		<div class="box-1024">	
    		<s:property value="name"/>
    		<s:property value="description"/>
    		<s:property value="operation"/>
    		<s:property value="address"/>
		</div>
	</div>
</s:if>