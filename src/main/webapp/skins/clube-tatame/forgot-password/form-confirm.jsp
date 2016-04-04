<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<style class="text/css">
	.errors-messages { float: left; width: 100%; margin: 0 0 5% 0; }
	.errors-messages .error { float: left; width: 100%; }
	.errors-messages .error li { float: left; width: 100%; margin: 0 0 0.5% 0; }
	.errors-messages .error li span { float: left; width: 100%; font-size: 1em; text-align: center; color: red; line-height: 1.2em; }
	
	.confirm-password-form { float: left; width: 50%; margin: 10% 25%; }
	.confirm-password-form label { float: left; width: 100%; font-size: 1em; line-height: 1.2em; margin: 0 0 1% 0; }
	.confirm-password-form input[type="password"] { float: left; width: 98%; border: 0.01em solid #000; padding: 1%; margin: 0 0 1.5% 0; border-radius: 5px; }
	.confirm-password-form input[type="submit"] { float: left; width: 100%; padding: 1%; background-color: #ffefa3; font-size: 1em; line-height: 1.2em; text-align: center; font-weight: 800; color: #414142; border: 0.01em solid #414142; border-radius: 5px; }
	
	.message { float: left; width: 50%; margin: 15% 25%; }
	.message p { float: left; width: 100%; font-size: 1em; text-align: center; color: #000; line-height: 1.2em; }	
</style>
<s:if test="!success">
	<form class="confirm-password-form" action="/clube/forgotPassword-confirm" method="post">
		<s:if test="hasFieldErrors()">
			<section class="errors-messages">
				<s:fielderror cssClass="error"/>
			</section>
		</s:if>	
		<input type="hidden" name="hash" value="${hash}"/>
		<input type="hidden" name="email" value="${email}"/>
		<input type="hidden" name="isMember" value="${member}"/>
		<input type="hidden" name="isGym" value="${gym}"/>
		<label for="password">Nova senha</label>
		<input type="password" name="password"/>
		<label for="password2">Repetir senha</label>
		<input type="password" name="password2"/>
		<input type="submit" value="Enviar"/>
	</form>
</s:if>
<s:else>
	<section class="message">
		<p>Senha alterada com sucesso.</p>
	</section>
</s:else>