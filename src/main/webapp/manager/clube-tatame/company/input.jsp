<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

<script type="text/javascript" src="/manager/js/GoogleMaps.js"></script>
<script type="text/javascript"> initGoogleMap('${lat != null ? lat : -23.9923}', '${lon != null ? lon : -46.3479}', '${zoomGoogleMaps > 0 ? zoomGoogleMaps : 10}') </script>

<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar empresa</strong>
		</li>					
	</ul>		
</nav>
<s:if test="isAdmin()">
	<form action="/manager/clube-tatame/company-save" method="post" onsubmit="validate();">
		<div class="ym-form">
			<div class="ym-fbox-text">
				<div id="map_canvas" style="width: 100%; height: 200px;"></div>
			
				<s:hidden name="id"/>	
				<label for="name">Nome</label>
				<s:textfield name="name"/>
				<label for="document">CNPJ</label>
				<s:textfield name="document"/>				
				<label for="email">E-mail</label>
				<s:textfield name="email"/>		
				<label for="contact">Contato</label>
				<s:textfield name="contact"/>
				<label for="phone">Telefone</label>
				<s:textfield name="phone"/>
				<label for="address">Endereço</label>
				<s:textfield name="address"/>
				<label for="cep">CEP</label>
				<s:textfield name="cep"/>
				<label for="facebook">Facebook</label>
				<s:textfield name="facebook"/>
				<label for="instagram">Instagram</label>
				<s:textfield name="instagram"/>
				
				<label for="lat">Latitude</label>
				<s:textfield id="lat" name="lat"/>
				
				<label for="lon">Longitude</label>
				<s:textfield id="lon" name="lon"/>
				
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
			<p style="margin: 10px 0px">Criado por <s:property value="createdBy" /> em <s:property value="created"/>.</p>			
		</s:if>		
		<s:if test="lastModifiedBy != null">		
			<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy" /> em <s:property value="lastModified"/>.</p>			
		</s:if>	
	</form>
</s:if>
<s:else>
	<div class="ym-form">
		<div class="ym-fbox-text">
			<div id="map_canvas" style="width: 100%; height: 200px;"></div>
		
			<s:hidden name="id"/>	
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="document">CNPJ</label>
			<s:textfield name="document"/>				
			<label for="email">E-mail</label>
			<s:textfield name="email"/>		
			<label for="contact">Contato</label>
			<s:textfield name="contact"/>
			<label for="phone">Telefone</label>
			<s:textfield name="phone"/>
			<label for="address">Endereço</label>
			<s:textfield name="address"/>
			<label for="cep">CEP</label>
			<s:textfield name="cep"/>
			<label for="facebook">Facebook</label>
			<s:textfield name="facebook"/>
			<label for="instagram">Instagram</label>
			<s:textfield name="instagram"/>
			
			<label for="lat">Latitude</label>
			<s:textfield id="lat" name="lat"/>
			
			<label for="lon">Longitude</label>
			<s:textfield id="lon" name="lon"/>
			
			<div class="ym-fbox-check" style="padding-top: 10px;">
				<label for="active">Ativo</label>
				<s:checkbox id="active" name="active"/>
	   		</div>		
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 0px">Criado por <s:property value="createdBy" /> em <s:property value="created"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy" /> em <s:property value="lastModified"/>.</p>			
	</s:if>
</s:else>