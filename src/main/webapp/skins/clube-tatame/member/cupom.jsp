<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<%@ taglib prefix="p" uri="/publisher-tags" 										 %>
<s:include value="/skins/clube-tatame/member/menu.jsp"/>

<section class="message">
	<p class="title">Bem vindo a sua área de descontos.</p>
	<p class="text">Aqui você encontra todos os descontos que o Clube Tatame lhe oferece.</p>
</section>

<s:if test="freeMember">
	<div class="content">
		<div class="avatar">
			<img src="/skins/clube-tatame/img/avatar-membro-free.jpg">
			<span class="name"><s:property value="member.name"/></span>
			<span class="date"> membro desde <s:date name="member.created" format="dd/MM/yyyy"/></span>
			<span class="description">Quer descontos maiores e ainda mais vantagens? torne-se um membro premium <a href="#">aqui</a> </span>
		</div>
		<p:tile xml="clube/cupom/free-member"/>
	</div>	
</s:if>
<s:else>
	<div class="content">
		<div class="avatar">
			<img src="/skins/clube-tatame/img/avatar-membro-premium.jpg">
			<span class="name"><s:property value="member.name"/></span>
			<span class="date"> membro desde <s:date name="member.created" format="dd/MM/yyyy"/></span>
		</div>
		<p:tile xml="clube/cupom/paying-member"/>
	</div>
</s:else>