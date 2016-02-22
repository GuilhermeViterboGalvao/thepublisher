<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
	$(function(){
		var isComma = false;		
		var lat     = '${lat != null ? lat : -23.9923}';
		var lon     = '${lon != null ? lon : -46.3479}';
		var mapZoom = '${zoomGoogleMaps > 0 ? zoomGoogleMaps : 10}';		
		if (lat.indexOf(',') > 0) {
			isComma = true;
		}		
		if (isComma) {
			lat = lat.replace(',', '.');
			lon = lon.replace(',', '.');
		}		
		var latlng = new google.maps.LatLng(Number(lat), Number(lon));
		var myOptions = {
	  		zoom      : Number(mapZoom),
	  		center    : latlng,
	  		mapTypeId : google.maps.MapTypeId.SATELLITE
		};	
		var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
		window.map = map;
		var wavesMarker = new google.maps.Marker({
			position  : latlng,
			map       : map,
			draggable : true
		});
		window.wavesMarker = wavesMarker;
		google.maps.event.addListener(map, 'zoom_changed', function(){
			document.getElementById("zoomGoogleMaps").value = map.getZoom();
		});		
		google.maps.event.addListener(wavesMarker, 'dragend', function(){		
			document.getElementById("lat").value = wavesMarker.getPosition().lat(); 
			if (isComma) {
				document.getElementById("lat").value = document.getElementById("lat").value.replace('.', ',');
			}
			document.getElementById("lon").value = wavesMarker.getPosition().lng();
			if (isComma) {
				document.getElementById("lon").value = document.getElementById("lon").value.replace('.', ',');
			}
		});
	});
	
	function validate() {
		$("#lat").val($("#lat").val().replace(".",","));
		$("#lon").val($("#lon").val().replace(".",","));
		checkPermanentLink();
	}	
</script>

<style type="text/css">
	.map { float:left; width: 100%; height: 275px; }
	.map #map_canvas { width: 100%; height: 200px; margin-bottom: 15px;}
	.map label { float: left; position:relative;  margin: 5px 0 0 3%; z-index: 99;}
	.map input { float: left; position:relative; width: 18%; margin: 5px !important; margin-top: 0; z-index: 99; }
</style>


<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar empresa</strong>
		</li>					
	</ul>		
</nav>
<s:if test="isAdmin()">
	<s:fielderror cssClass="error"/>
	<form action="/manager/clube-tatame/company-save" method="post" onsubmit="validate();">
		<div class="ym-form">
			<div class="ym-fbox-text">
				<s:hidden name="id"/>
				
				<div class="map">
					<div id="map_canvas"></div>
					<label for="lat">Latitude:</label>
					<s:textfield id="lat" name="lat"/>
					<label for="lon">Longitude:</label>
					<s:textfield id="lon" name="lon"/>
					<label for="zoomGoogleMaps">Map Zoom:</label>
					<s:textfield id="zoomGoogleMaps" name="zoomGoogleMaps"/>
				</div>
				
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
			<p style="margin: 10px 8px">Criado por <s:property value="createdBy.name" /> em <s:property value="created"/>.</p>			
		</s:if>		
		<s:if test="lastModifiedBy != null">		
			<p style="margin: 10px 8px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="lastModified"/>.</p>			
		</s:if>	
	</form>
</s:if>
<s:else>
	<div class="ym-form">
		<div class="ym-fbox-text">		
			<s:hidden name="id"/>
			
			<div class="map">
				<div id="map_canvas"></div>
				<label for="lat">Latitude:</label>
				<s:textfield id="lat" name="lat"/>
				<label for="lon">Longitude:</label>
				<s:textfield id="lon" name="lon"/>
				<label for="zoomGoogleMaps">Map Zoom:</label>
				<s:textfield id="zoomGoogleMaps" name="zoomGoogleMaps"/>
			</div>	
			
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
			
			<div class="ym-fbox-check" style="padding-top: 10px;">
				<label for="active">Ativo</label>
				<s:checkbox id="active" name="active"/>
	   		</div>		
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 8px">Criado por <s:property value="createdBy.name" /> em <s:property value="created"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 8px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="lastModified"/>.</p>			
	</s:if>
</s:else>