<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
	#map_canvas { width: 100%; height: 200px; margin-bottom: 15px; }
	
	img.photoselector { float: left; margin: 8px 20px 8px 0; }
	.tinyMCEEditor { margin: 10px 1px 10px 10px; padding: 0; width: 863px; }
	.errors { color: red; }
	div.fields { margin-bottom: 0; }
	label.header { overflow: hidden; } 
	input.header { width: 659px; }
	label.title { overflow: hidden; } 
	input.title { width: 659px; }
	label.categoryName { clear: left; }
	input.publishedAt { margin-right: 20px; width: 150px; float: left; }
	div.preview { float: right; top: -20px; margin-right: 50px; }
	div.check-box { margin: 0 230px; }
	input.forumEnabled { margin-right: 50px; }
	input.published {}	
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

<script type="text/javascript" src="/frameworks/tinymce/tiny_mce.js"></script>
<script type="text/javascript" src="/frameworks/tinymce/plugins/photogallery/editor_plugin_src.js"></script>
<script type="text/javascript" src="/manager/js/PermanentLinkSelectorDialog.js?1"></script>
<script type="text/javascript" src="/manager/js/SwfDialog.js?1"></script>

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
	
	function checkPermanentLink() {
		var value = $('#link').val();
		var permanentLink = $('#permanentLink').val();
		if ((value == undefined || value == null || value == "") 
		&& (permanentLink == undefined || permanentLink == null || permanentLink == "")) {
			$('#permanentLink').val(convertToPermanentLink(suggestPermanentLink()));	
		} else if (permanentLink != value) {
			$('#permanentLink').val(convertToPermanentLink($('#permanentLink').val()));
		}
	}
	
	$(function() {
		tinymce.init({
			selector: "#tinyMCEEditor",
			plugins: "inlinepopups,fullscreen,autosave,paste,photogallery",
			content_css: "/manager/css/article.css",
			theme : "advanced",
			relative_urls: false,
			width: 863,
			height: 500,
			theme_advanced_toolbar_location: "top",
			theme_advanced_toolbar_align: "left",
			theme_advanced_statusbar_location: "bottom",		
			theme_advanced_buttons1: "bold,italic,underline,separator,justifyleft,justifycenter,justifyright,justifyfull,separator,formatselect,separator,bullist,numlist,separator,outdent,indent,separator,undo,redo",
			theme_advanced_buttons2: "sub,sup,charmap,hr,separator,link,unlink,anchor,separator,photogallery,separator,cleanup,removeformat,separator,pastetext,pasteword,separator,code,separator,fullscreen,forecolor,backcolor",
			theme_advanced_buttons3: "",
			theme_advanced_buttons4: ""		
		});
		PermanentLinkSelectorDialog({
			'element_target': 'published',
			'on_confirm' : function(permanentLink){	
				$('#permanentLink').val(convertToPermanentLink(permanentLink)); 
			},
			'on_show' : function(element, permanentLinkSelectorDialog){	
				if(element.checked) permanentLinkSelectorDialog.show($('#permanentLink').val());
			}
		});
		SWFDialog({
			sessionId    : '<s:property value="sessionId"/>',
			targetButton : 'swfDialog'
		});		
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