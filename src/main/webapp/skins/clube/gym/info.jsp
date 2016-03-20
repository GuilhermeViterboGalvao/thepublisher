<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="validContracts.size > 0">
   		<s:property value="name"/>
   		<s:property value="description"/>
   		<s:property value="operation"/>
   		<s:property value="address"/>
</s:if>