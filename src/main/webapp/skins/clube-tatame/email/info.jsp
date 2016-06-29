<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<style type="text/css">
	.error-message { float: left; width: 100%; margin: 5% 0; }
	.error-message p { float: left; width: 100%; text-align: center; font-size: 1.2em; line-height: 1.4em; color: red; }
	
	.expired-code { float: left; width: 100%; margin: 5% 0; }
	.expired-code p { float: left; width: 100%; text-align: center; font-size: 1.2em; line-height: 1.4em; color: #000; }
	.expired-code a { float: left; text-decoration: none; font-size: 1.2em; line-height: 1.4em; color: red;}
	
	.message { float: left; width: 100%; margin: 5% 0; }
	.message p { float: left; width: 100%; text-align: center; font-size: 1.2em; line-height: 1.4em; color: #000; }
</style>
<s:if test="errorMessage != null && !errorMessage.isEmpty()">
	<div class="error-message">
		<p><s:property value="errorMessage"/></p>
	</div>
	<s:if test="expiredCode">
		<div class="expired-code">
			<p>Seu código expirou!</p>
			<s:if test="memberId > 0">
				<a href="http://tatame.com.br/emailConfirmation?memberId=${memberId}&code=${newCode}">Clique aqui para confirmar seu cadastro.</a>
			</s:if>
			<s:else>
				<a href="http://tatame.com.br/emailConfirmation?gymId=${gymId}&code=${newCode}">Clique aqui para confirmar seu cadastro.</a>
			</s:else>			
		</div>
	</s:if>
</s:if>
<s:else>
	<div class="message">
		<p>Cadastro confirmado com sucesso!</p>
	</div>
</s:else>