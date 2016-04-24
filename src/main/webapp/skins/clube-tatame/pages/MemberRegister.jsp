<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<style type="text/css">
	.erro-messages { float: left; width: 50%; margin: 2.5% 25% 0 25%; }
	.erro-messages .error { float: left; width: 100%; }
	.erro-messages .error li { float: left; width: 100%; }
	.erro-messages .error li span { float: left; width: 100%; text-align: center; color: red; font-size: 1.2em; line-height: 1.4em; margin: 0 0 1% 0; }
	
	.success { float: left; width: 50%; margin: 2.5% 25% 0 25%; }
	.success label { float: left; width: 100%; font-size: 1.2em; line-height: 1.4em; text-align: center; }
	
	form { float: left; width: 50%; margin: 2.5% 25%; }
	form label { float: left; width: 100%; font-size: 1.2em; margin: 0 0 1% 0; line-height: 1.4em; }
	form input[type="text"],
	form input[type="email"],
	form input[type="password"] { float: left; width: 98%; margin: 0 0 5% 0; border: none; font-size: 1em; padding: 1%; line-height: 1.2em; background-color: rgb(250, 255, 189); }
	form input[type="submit"] { float: left; width: 100%; padding: 1%; background-color: #ffefa3; font-size: 1em; line-height: 1.2em; text-align: center; font-weight: 800; color: #414142; border: 0.01em solid #414142; border-radius: 5px; }
	
	@media screen and (max-width: 800px) {
		.erro-messages { width: 98%; margin: 5% 1% 0 1%; }
		.erro-messages .error li span { font-size: 1em; line-height: 1.2em; }
		.success { width: 98%; margin: 5% 1% 0 1%; }
		.success label { font-size: 1em; line-height: 1.2em; }	
		form { width: 98%; margin: 5% 1%; }
		form label { font-size: 1em; line-height: 1.2em; }  
	}	
</style>
<s:if test="hasFieldErrors() && !fromMenu">
	<div class="erro-messages">
		<s:fielderror cssClass="error"/>
	</div>
</s:if>
<s:if test="createWithSuccess">
	<div class="success">
		<label>Dados enviados com sucesso. Para ativar seu cadastro, acesse seu e-mail, abra o e-mail de confirmação e clique no link de confirmação.</label>
	</div>
</s:if>
<s:else>
	<form action="/clube/memberRegister" method="post">
		<label for="name">Nome completo</label>
		<input type="text" name="name"/>
		<label for="document">CPF (apenas n&uacute;meros sem traços e pontos)</label>
		<input type="text" name="document"/>
		<label for="email">E-mail</label>
		<input type="text" name="email"/>
		<label for="password">Senha</label>
		<input type="password" name="password"/>
		<label for="password2">Redigite a senha</label>
		<input type="password" name="password2"/>					
		<input type="submit" value="Cadastrar"/>
	</form>
</s:else>