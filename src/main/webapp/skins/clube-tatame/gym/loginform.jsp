<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<link href="/skins/clube-tatame/css/loginFormGym.css" rel="stylesheet" type="text/css"/>
<s:if test="hasFieldErrors() && !fromMenu">
	<div class="erro-messages">
		<s:fielderror cssClass="error"/>
	</div>
</s:if>
<div class="info">
	<label>Área restria à academias parceiras do Clube Tatame.</label>
</div>
<form class="form" action="/clube/academias/login" method="post">
	<label for="email">E-mail</label>
	<input type="email" name="email"/>
	<label for="password">Senha</label>
	<input type="password" name="password"/>
	<input type="submit" value="Enviar"/>	
</form>
<div class="info2">
	<label>Esqueceu a sua senha? Clique <a href="/clube/forgotPassword-formReset">aqui</a> para recuperá-la.</label>
	<label>Ainda não possui cadastro? Clique <a href="#">aqui</a>, e cadastre-se gratuitamente.</label>
</div>