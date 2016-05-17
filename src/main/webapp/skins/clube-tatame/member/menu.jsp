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
	<s:else>
		<li>
			<a href="http://www.tatameshop.com.br/">Loja Virtual</a>
		</li>
	</s:else>
	<li>
		<a href="/clube/membros/cupom">Cupons</a>
	</li>	
	<li>
		<a href="/clube/membros/logout">Sair</a>
	</li>
</ul>