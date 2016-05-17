<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<link href="/skins/clube-tatame/css/loginFormMember.css" rel="stylesheet" type="text/css"/>
<s:if test="hasFieldErrors() && !fromMenu">
	<div class="erro-messages">
		<s:fielderror cssClass="error"/>
	</div>
</s:if>
<div class="info">
	<label>Área restria aos membros do Clube Tatame.</label>
</div>
<form class="form" action="/clube/membros/login" method="post">
	<label for="email">E-mail</label>
	<input type="email" name="email"/>
	<label for="password">Senha</label>
	<input type="password" name="password"/>
	<input type="submit" value="Enviar"/>	
</form>
<div class="info2">
	<a href="https://www.facebook.com/dialog/oauth?client_id=515364881999582&display=page&scope=email&redirect_uri=http://homolog.tatame.terra.com.br/clube/membros/facebookLogin">
		<img class="btnFacebook" alt="" src="/skins/clube-tatame/img/btnFacebookLarge.png">
	</a>
	<label>Esqueceu a sua senha? Clique <a href="/clube/forgotPassword-formReset">aqui</a> para recuperá-la.</label>
	<label>Ainda não possui cadastro? Clique <a href="/clube/memberRegister?fromMenu=true">aqui</a>, e cadastre-se gratuitamente.</label>
</div>