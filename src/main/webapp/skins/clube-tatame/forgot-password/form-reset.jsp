<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<style class="text/css">
	.errors-messages { float: left; width: 50%; margin: 10% 25%; }
	.errors-messages .error { float: left; width: 100%; }
	.errors-messages .error li { float: left; width: 100%; }
	.errors-messages .error li span { float: left; width: 100%; font-size: 1em; text-align: center; color: red; line-height: 1.2em; }

	.forgot-password-form { float: left; width: 50%; margin: 10% 25%; }
	.forgot-password-form label { float: left; width: 100%; font-size: 1em; line-height: 1.2em; margin: 0 0 1% 0; }
	.forgot-password-form input[type="email"] { float: left; width: 98%; border: 0.01em solid #000; padding: 1%; margin: 0 0 1.5% 0; }
	.forgot-password-form .isMember { float: left; width: 50%; margin: 0 0 1.5% 0; }
	.forgot-password-form .isMember label { float: right; width: 95%; }
	.forgot-password-form .isMember input[type="checkbox"] { float: left; width: 5%; border: 0; padding: 0; margin: 0; }
	.forgot-password-form .isGym { float: left; width: 50%; margin: 0 0 1.5% 0; }
	.forgot-password-form .isGym label { float: right; width: 95%; }
	.forgot-password-form .isGym input[type="checkbox"] { float: left; width: 5%; border: 0; padding: 0; margin: 0; }
	.forgot-password-form input[type="submit"] { float: left; width: 100%; padding: 1%; background-color: #fff; font-size: 1em; line-height: 1.2em; text-align: center; font-weight: 800; color: #414142; border: 0.01em solid #414142; }
	
	.message { float: left; width: 50%; margin: 10% 25%; }
	.message p { float: left; width: 100%; font-size: 1em; text-align: center; color: #000; line-height: 1.2em; }
</style>
<s:if test="hasFieldErrors()">
	<section class="errors-messages">
		<s:fielderror cssClass="error"/>
	</section>
</s:if>
<s:if test="!success">
	<form class="forgot-password-form" action="/clube/forgotPassword-reset" method="post">
		<label for="email">Informe seu e-mail</label>
		<input type="email" name="email"/>
		<div class="isMember">
			<label for="isMember">Sou um membro</label>
			<input id="isMember" type="checkbox" name="isMember"/>	
		</div>
		<div class="isGym">
			<label for="isMember">Sou uma academia</label>
			<input id="isGym" type="checkbox" name="isGym"/>	
		</div>
		<input type="submit" value="Enviar"/>	
	</form>
	<script type="text/javascript">
		//TODO
		$("#isMember");
		$("#isGym");
	</script>	
</s:if>
<s:else>
	<section class="message">
		<p>Você irá receber um e-mail para recadastrar a sua senha.</p>
	</section>
</s:else>