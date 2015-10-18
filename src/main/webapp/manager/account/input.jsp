<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 			  %>
<nav class="ym-hlist">
	<ul>
		<li class="active"><strong>Novo usuário</strong></li>					
	</ul>		
</nav>
<s:form action="account-save" cssClass="ym-form" >
	
	<s:hidden name="id"/>

	<s:fielderror cssStyle="color: red;"/>
		
	<div class="ym-fbox-text">
	
		<label for="name">Nome</label>
		<s:textfield name="name"/>
		
		<label for="email">E-mail</label>
		<s:textfield name="email"/>
		
		<label for="securityHole">Restrição de acesso</label>
		<s:textfield name="securityHole"/>		
	
		<label for="password">Senha</label>
		<s:password name="password"/>
		
		<label for="password2">Senha</label>
		<s:password name="password2"/>		
		
		<div class="ym-fbox-select" style="padding-top: 10px;">
			<label for="timezone">Fuso</label>
			<s:select list="timezones" name="timezone" listKey="value" listValue="key"/>
		</div>
		
		<div class="ym-fbox-check" style="padding-top: 10px;;">
			<input type="checkbox" name="active" />
			<label for="active">Ativo</label>				
   		</div>
					
		<div class="ym-fbox-button">
			<s:submit value="Enviar"/>
		</div>
	
	</div>	
		
</s:form>