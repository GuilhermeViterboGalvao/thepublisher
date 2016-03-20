<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
	#map_canvas { width: 100%; height: 200px; margin-bottom: 15px; }
	
	img.photoselector { float: left; margin: 8px 20px 8px 0; }
	div.d-photo { margin: 10px 0; background-color: #cccccc; height: 48px; position: relative; }
	img.d-photo { display: block; float: left; margin: 0; border: 0; width:80px; height: 48px; cursor: move; }
	img.d-photo-nav { width: 22px; height: 22px; float: right; margin: 3px; }
	div.d-photo > textarea.d-photo  { position: absolute; left: 85px; top: 5px; padding: 4px; width: 715px; height: 38px; margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0;	}
</style>

<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar academia</strong>
		</li>					
	</ul>		
</nav>
<form action="/manager/clube-tatame/gym-save" method="post" onsubmit="checkLatLon();">
	<div class="ym-form">		
		<s:fielderror cssClass="error"/>		
		<s:if test="lat != null && lon != null">
			<div id="map_canvas"></div>
		</s:if>			
		<div class="ym-fbox-text">
			<s:hidden name="id"/>	
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="description">Descrição</label>
			<s:textarea name="description"/>
			<label for="site">Site</label>
			<s:textfield name="site"/>
			<label for="operation">Funcionamento</label>
			<s:textfield name="operation"/>
			<label for="contact">Contato</label>
			<s:textfield name="contact"/>			
			<label for="document">CNPJ</label>
			<s:textfield name="document"/>
			<label for="email">E-mail</label>
			<s:textfield name="email"/>			
			<label for="phone">Telefone</label>
			<s:textfield name="phone"/>
			<label for="state">Estado</label>
			<s:textfield name="state"/>			
			<label for="city">Cidade</label>
			<s:textfield name="city"/>						
			<label for="address">Endereço</label>
			<s:textfield name="address"/>
			<label for="cep">CEP</label>
			<s:textfield name="cep"/>			
			<label for="instagram">Instagram</label>
			<s:textfield name="instagram"/>
			<label for="facebook">Facebook</label>
			<s:textfield name="facebook"/>			
			<label for="lat">Latitude</label>
			<s:textfield id="lat" name="lat"/>
			<label for="lon">Longitude</label>
			<s:textfield id="lon" name="lon"/>
			<label for="permanentLink">Link Permanent:</label>
			<s:hidden id="link" name="link" value="%{permanentLink}"/>
			<s:textfield id="permanentLink" name="permanentLink" />	
			<div class="ym-fbox-check" style="padding-top: 10px;">
				<label for="active">Ativo</label>
				<s:checkbox id="active" name="active"/>
	   		</div>
	   		
			<div class="ym-grid" style="padding-bottom: 5px;">
				<div class="ym-g33 ym-gl ym-fbox-button">
					<input id="btnShow" name="btnShow" type="button" value="Selecionar fotos"/>
				</div>
	
				<div class="ym-g33 ym-gl ym-fbox-button">
					<input type="button" id="swfDialog" name="swfDialog" value="Subir imagens"/>
				</div>
				
				<div class="ym-g33 ym-gl ym-fbox-button">
					<s:submit value="Enviar" />
				</div>
			</div>
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 0px">Criado por <s:property value="createdBy.name" /> em <s:property value="%{getText('date.format', {created})}"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="%{getText('date.format', {lastModified})}"/>.</p>			
	</s:if>	
</form>

<script type="text/javascript" src="/manager/js/PhotoSelectorDialog.js"></script>
<script type="text/javascript" src="/manager/js/SwfDialog.js"></script>

<script type="text/javascript">
	var lat = $("#lat");
	var lon = $("#lon");
	var checkLatLon = function() {		
		if (lat && lat.val() && lat.val().indexOf(".") > 0) {
			lat.val(lat.val().replace(".", ","));
		}		
		if (lon && lon.val()) {
			lon.val(lon.val().replace(".", ","));
		}
	};
	checkLatLon();

</script>
<s:if test="lat != null && lon != null">
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script type="text/javascript">
		var latlon = new google.maps.LatLng(Number(lat.val().replace(",", ".")), Number(lon.val().replace(",", ".")));
		var options = {
	  		zoom : 12,
	  		center : latlon,
	  		mapTypeId : google.maps.MapTypeId.SATELLITE
		};
		var map = new google.maps.Map(document.getElementById("map_canvas"), options);
		new google.maps.Marker({
			position : latlon,
			map : map,
			draggable : true
		});		
	</script>
</s:if>