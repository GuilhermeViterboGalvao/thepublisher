<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<style type="text/css">
	.error-message { float: left; }
	.error-message p { float: left; }
	
	.expired-code { float: left; }
	.expired-code p { float: left; }
	.expired-code a { float: left; }
	
	.message { float: left; }
	.message p { float: left; }
</style>
<s:if test="errorMessage != null && !errorMessage.isEmpty()">
	<div class="error-message">
		<p><s:property value="errorMessage"/></p>
	</div>
	<s:if test="expiredCode">
		<div class="expired-code">
			<p>Seu código expirou!</p>
			<s:if test="memberId > 0">
				<a href="http://clube.tatame.com.br/emailConfirmation?memberId=${memberId}&code=${newCode}">Clique aqui para confirmar seu cadastro.</a>
			</s:if>
			<s:else>
				<a href="http://clube.tatame.com.br/emailConfirmation?gymId=${gymId}&code=${newCode}">Clique aqui para confirmar seu cadastro.</a>
			</s:else>			
		</div>
	</s:if>
</s:if>
<s:else>
	<div class="message">
		<p>Cadastro confirmado com sucesso!</p>
	</div>
</s:else>