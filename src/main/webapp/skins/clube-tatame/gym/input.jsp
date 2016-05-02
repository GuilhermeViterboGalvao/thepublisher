<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<s:include value="/skins/clube-tatame/gym/menu.jsp"/>
<section class="message">
	<p class="title">Seja bem vindo <s:property value="name"/>.</p>
	<p class="text">Aqui é o seu local onde você pode alterar as suas informações, e pegar o seu</p>
	<p class="text">cumpo de desconto para aproveitar as vantagens de uma academia parceira do Clube Tatame.</p>
</section>
<s:if test="hasFieldErrors()">
	<section class="errors-messages">
		<s:fielderror cssClass="error"/>
	</section>
</s:if>
<form class="memberForm" action="/clube/academias/gym-save" method="post">
	<s:hidden name="id"/>	
	<label for="name">Nome</label>
	<s:textfield name="name"/>	
	<label for="description">Descrição</label>
	<s:textarea name="description" rows="4"/>
	<label for="site">Site</label>
	<s:textfield name="site"/>	
	<label for="contact">Contato</label>
	<s:textfield name="contact"/>
	<label for="document">CNPJ</label>
	<s:textfield name="document"/>
	<label for="email">E-mail</label>
	<s:textfield name="email"/>
	<label for="phone">Telefone</label>
	<s:textfield name="phone"/>	
	<label for="city">Cidade</label>
	<s:textfield name="city"/>
	<label for="state">Estado</label>
	<s:textfield name="state"/>
	<label for="address">Endereço</label>
	<s:textfield name="address"/>
	<label for="cep">CEP</label>
	<s:textfield name="cep"/>											
	<s:submit value="Enviar"/>		
</form>