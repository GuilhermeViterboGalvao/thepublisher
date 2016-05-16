<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>

<ul class="member-menu">
	<li>
		<a href="/clube/membros/membro-input">Minha Home</a>
	</li>
	<s:if test="member.fbid == null">
		<li>
			<a href="/clube/membros/resetPassword">Alterar senha</a>
		</li>
	</s:if>
	<li>
		<a href="/clube/membros/cupom">Cupons</a>
	</li>	
	<li>
		<a href="/clube/membros/logout">Sair</a>
	</li>
</ul>