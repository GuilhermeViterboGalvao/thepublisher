<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<s:include value="/skins/clube-tatame/member/menu.jsp"/>
<section class="message">
	<p class="title">Seja bem vindo <s:property value="name"/>.</p>
	<p class="text">Aqui é o seu local onde você pode alterar as suas informações, e pegar o seu</p>
	<p class="text">cumpo de desconto para aproveitar as vantagens de um membro do Clube Tatame.</p>
</section>
<s:if test="hasFieldErrors()">
	<section class="errors-messages">
		<s:fielderror cssClass="error"/>
	</section>
</s:if>
<form class="memberForm" action="/clube/membros/membro-save" method="post">
	<s:hidden name="id"/>	
	<label for="name">Nome</label>
	<s:textfield name="name"/>	
	<label for="email">E-mail</label>
	<s:textfield name="email"/>	
	<label for="document">CPF</label>
	<s:textfield name="document"/>	
	<label for="gender">Sexo</label>
	<s:select name="gender" list="listGenders" listKey="id" listValue="value" headerKey="0" headerValue="Selecione seu sexo"/>
	<label for="birth">Data de nascimento</label>
	<s:textfield id="birth" name="birth"/>	
	<label for="address">Endereço</label>
	<s:textfield name="address"/>	
	<label for="cep">CEP</label>
	<s:textfield name="cep"/>	
	<s:submit value="Enviar"/>		
</form>