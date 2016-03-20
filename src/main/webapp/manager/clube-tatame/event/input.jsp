<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">#map_canvas { width: 100%; height: 200px; margin-bottom: 15px; }</style>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar evento</strong>
		</li>					
	</ul>		
</nav>
<form action="/manager/clube-tatame/event-save" method="post" onsubmit="checkLatLon();">
	<div class="ym-form">		
		<s:fielderror cssClass="error"/>		
		<s:if test="lat != null && lon != null">
			<div id="map_canvas"></div>
		</s:if>			
		<div class="ym-fbox-text">
			<s:hidden name="id"/>
			<p:photoeventselector 
				name="photoId" 
				cssStyle="float: left;"
				display="'/img/' + photoId + '_150x100.jpg'"
				url="/manager/clube-tatame/ac-photo-event"
				imagePattern="/img/[value]_150x100.jpg"
				minLength="5" 
				delay="500" 
				pageSize="60" 
				showToolTip="true"
				initialUrl="/manager/clube-tatame/ac-photo-event"
				loadingImage="/manager/img/loader.gif" 
			/>				
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="description">Descrição</label>
			<s:textarea name="description"/>			
			<label for="contact">Contato</label>
			<s:textfield name="contact"/>
			<label for="state">Estado</label>
			<s:textfield name="state"/>
			<label for="city">Cidade</label>
			<s:textfield name="city"/>
			<label for="address">Endereço</label>
			<s:textfield name="address"/>
			<label for="start">Data de início</label>
			<s:textfield id="start" name="start" value="%{getText('date.format', {start})}"/>			
			<label for="end">Data de término</label>
			<s:textfield id="end" name="end" value="%{getText('date.format', {end})}"/>
			<label for="lat">Latitude</label>
			<s:textfield id="lat" name="lat"/>
			<label for="lon">Longitude</label>
			<s:textfield id="lon" name="lon"/>
			<label for="zoomGoogleMaps">Zoom do Google Maps</label>
			<s:textfield id="zoomGoogleMaps" name="zoomGoogleMaps"/>
	        <label for="company.id">Empresa</label>
	        <s:select name="company.id" list="companys" listKey="id" listValue="name" headerKey="0" headerValue="Selecione uma empresa"/>						
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
		<p style="margin: 10px 0px">Criado por <s:property value="createdBy.name" /> em <s:property value="%{getText('date.format', {created})}"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="%{getText('date.format', {lastModified})}"/>.</p>			
	</s:if>	
</form>
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
	$("#start").datepicker({
	    dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Próximo',
	    prevText: 'Anterior'
	});
	$("#end").datepicker({
	    dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Próximo',
	    prevText: 'Anterior'
	});
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