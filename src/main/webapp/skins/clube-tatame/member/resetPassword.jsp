<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              								 %>
<s:include value="/skins/clube-tatame/member/menu.jsp"/>
<s:if test="hasFieldErrors()">
	<section class="errors-messages">
		<s:fielderror cssClass="error"/>
	</section>
</s:if>
<s:if test="resetWithSuccess">
	<section class="resetPassword-message">
		<p class="text">Senha atualizada com sucesso!</p>
	</section>
</s:if>
<s:else>
	<form class="resetPasswordForm" action="/clube/membros/resetPassword" method="post">
		<label for="oldPassword">Senha atual</label>
		<input type="password" name="oldPassword"/>
		<label for="password">Nova senha</label>
		<input type="password" name="password"/>
		<label for="password2">Repetir nova senha</label>
		<input type="password" name="password2"/>
		<input type="submit" value="Enviar"/>				
	</form>
</s:else>