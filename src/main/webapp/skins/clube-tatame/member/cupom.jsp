<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<%@ taglib prefix="p" uri="/publisher-tags" 										 %>
<s:include value="/skins/clube-tatame/member/menu.jsp"/>
<section class="message">
	<p class="title">Bem vindo a sua área de descontos.</p>
	<p class="text">Aqui você encontra todos os descontos que o Clube Tatame lhe oferece.</p>
</section>
<s:if test="freeMember">
	<p:tile xml="clube/cupom/free-member"/>
</s:if>
<s:else>
	<p:tile xml="clube/cupom/paying-member"/>
</s:else>