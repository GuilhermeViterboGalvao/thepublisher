<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar academia</strong>
		</li>					
	</ul>		
</nav>
<s:fielderror cssClass="error"/>
<form action="/manager/clube-tatame/gym-save" method="post">
	<div class="ym-form">
		<div class="ym-fbox-text">
			<s:hidden name="id"/>	
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="contact">Contato</label>
			<s:textfield name="contact"/>			
			<label for="document">CNPJ</label>
			<s:textfield name="document"/>
			<label for="email">E-mail</label>
			<s:textfield name="email"/>			
			<label for="phone">Telefone</label>
			<s:textfield name="phone"/>			
			<label for="address">Endereço</label>
			<s:textfield name="address"/>
			<label for="cep">CEP</label>
			<s:textfield name="cep"/>			
			<label for="instagram">Instagram</label>
			<s:textfield name="instagram"/>			
			<label for="lat">Latitude</label>
			<s:textfield name="lat"/>
			<label for="lon">Longitude</label>
			<s:textfield name="lon"/>
			<div class="ym-fbox-check" style="padding-top: 10px;">
				<label for="active">Ativo</label>
				<s:checkbox id="active" name="active"/>
	   		</div>
			<div class="ym-g50 ym-gl ym-fbox-button">
				<s:submit value="Enviar"/>
			</div>
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 0px">Criado por <s:property value="createdBy.name" /> em <s:property value="created"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="lastModified"/>.</p>			
	</s:if>	
</form>