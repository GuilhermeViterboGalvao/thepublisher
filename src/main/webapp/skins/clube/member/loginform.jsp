<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<style type="text/css">
	.erro-messages { float: left; }
	.erro-messages .error { float: left; }
</style>
<div class="erro-messages">
	<s:fielderror cssClass="error"/>
</div>
<form action="/clube/membros/login" method="post">
	<label for="email">E-mail</label>
	<input type="email" name="email"/>
	<label for="password">Senha</label>
	<input type="password" name="password"/>
	<input type="submit" value="Enviar"/>	
</form>