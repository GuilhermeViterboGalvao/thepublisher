<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="end.after(CurrentDate) && value > 0">
   		<s:property value="gym.name"/>
   		<s:property value="gym.description"/>
   		<s:property value="gym.operation"/>
   		<s:property value="gym.address"/>
</s:if>