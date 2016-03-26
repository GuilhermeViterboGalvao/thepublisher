<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<style type="text/css">
	.erro-messages { float: left; width: 50%; margin: 5% 25% 0 25%; }
	.erro-messages .error { float: left; width: 100%; }
	.erro-messages .error li { float: left; width: 100%; }
	.erro-messages .error li span { float: left; width: 100%; text-align: center; color: red; font-size: 1.2em; line-height: 1.4em; margin: 0 0 1% 0; }	
	.form { float: left; width: 50%; margin: 5% 25% 20% 25%; }
	.form label { float: left; width: 100%; font-size: 1.2em; margin: 0 0 1% 0; line-height: 1.4em; }
	.form input[type="email"],
	.form input[type="password"] { float: left; width: 98%; margin: 0 0 5% 0; border: none; font-size: 1em; padding: 1%; line-height: 1.2em; background-color: rgb(250, 255, 189); }
	.form input[type="submit"] { float: left; width: 100%; font-size: 1.2em; padding: 1%; margin: 0; line-height: 1.4em; font-weight: 600; background-color: #fff; border: 0.1em solid #000; }
</style>
<s:if test="hasFieldErrors()">
	<div class="erro-messages">
		<s:fielderror cssClass="error"/>
	</div>
</s:if>
<form class="form" action="/clube/membros/login" method="post">
	<label for="email">E-mail</label>
	<input type="email" name="email"/>
	<label for="password">Senha</label>
	<input type="password" name="password"/>
	<input type="submit" value="Enviar"/>	
</form>