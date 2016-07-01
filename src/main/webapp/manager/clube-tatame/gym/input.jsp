<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
	#map_canvas { width: 100%; height: 200px; margin-bottom: 15px; }
	img.photoselector { float: left; margin: 8px 20px 8px 0; }
	div.fields { margin-bottom: 0; }
	label.name { overflow: hidden; } 
	input.name { width: 659px; }
	label.site { overflow: hidden; } 
	input.site { width: 659px; }
</style>

<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar academia</strong>
		</li>					
	</ul>		
</nav>
<form action="/manager/clube-tatame/gym-save" method="post" onsubmit="checkPermanentLink(); checkLatLon();">
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
				url="/manager/clube-tatame/ac-photo-gym"
				imagePattern="/img/[value]_150x100.jpg"
				minLength="5" 
				delay="500" 
				pageSize="60" 
				showToolTip="true"
				initialUrl="/manager/clube-tatame/ac-photo-gym"
				loadingImage="/manager/img/loader.gif" 
			/>	
			<label class="name" for="name">Nome</label>
			<s:textfield name="name" cssClass="name"/>
			<label class="site" for="site">Site</label>
			<s:textfield name="site" cssClass="site"/>
			<label for="description">Descrição</label>
			<s:textarea name="description" rows="3"/>
			<label for="operation">Funcionamento</label>
			<s:textfield name="operation"/>
			<label for="modality">Modalidades</label>
			<s:textfield name="modality"/>
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
			<label for="twitter">Twitter</label>
			<s:textfield name="twitter"/>		
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
	   		
			<div class="ym-grid">
				<div class="ym-g33 ym-gl ym-fbox-button">
					<input id="btnShow" type="button" value="Selecionar fotos"/>
				</div>						
				<div class="ym-g33 ym-gl ym-fbox-button">
					<input type="button" id="swfDialog" name="swfDialog" value="Subir imagens"/>
				</div>	
				<div class="ym-g33 ym-gl ym-fbox-button">
					<s:submit value="Enviar" />
				</div>			
			</div>
			
			<div id="popup" style="display: none;" title="Selecione uma foto"></div>						
			<div id="sortable">				
				<s:iterator value="photos" status="itStatus">
					<s:div id="draggable" name="foto%{#itStatus.count-1}" cssClass="d-photo">						
						<s:hidden id="fotoid%{#itStatus.count-1}" name="photos[%{#itStatus.count-1}].photo.id" value="%{photo.id}" />
						<img 
							src="<s:url value="/img/%{photo.id}_130x80.jpg" includeParams="none" />" 
							class="d-photo"
							id="fotoimg<s:property value="%{#itStatus.count-1}"/>" 
							alt="" 
						/>
						<img src="/manager/img/close.gif" onclick="window.photoSelectorDialog.remove(this.parentNode)" class="d-photo-nav"/>						
						<s:textarea cssClass="d-photo" value="%{description}" id="fotolegenda%{#itStatus.count-1}" name="photos[%{#itStatus.count-1}].description" />						
					</s:div>					
				</s:iterator>				
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

<script type="text/javascript" src="/frameworks/jquery/plugins/jquery.xmldom-1.0.min.js"></script>
<script type="text/javascript" src="/manager/js/PermanentLinkSelectorDialog.js?1"></script>
<script type="text/javascript" src="/manager/js/PhotoSelectorDialog.js"></script>
<script type="text/javascript" src="/manager/js/SwfDialog.js"></script>

<script type="text/javascript">
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
		$("#sortable").sortable({
	        revert: false,
	        stop: function () {
		    	var childrens = $("#sortable").children();
		    	for(var i = 0; i < childrens.length; i++){
		    		var nodes = removeTextNodes(childrens[i]).childNodes;
		    		nodes[0].name = "photos["+i+"].photo.id";
		    		nodes[0].id = "fotoid"+i;
		    		nodes[3].name = "photos["+i+"].description";
		    		nodes[3].id = "fotolegenda"+i;
		    		childrens[i].attributes["name"].value = "foto"+i; 
		    		childrens[i].style.position = "relative";
		    		childrens[i].className = "d-photo ui-draggable";
		    	}
	        }
	    });
	    $("#draggable").draggable({
	        connectToSortable: "#sortable",
	        revert: "invalid"
	    });
		PhotoSelectorDialog({
			'currentPage' : 1,
			'pageSize' : 50,
			'pages' : 0,
			'query' : '',
			'popup' : 'popup',
			'button' : 'btnShow', 
			'addedImages' : <s:if test="photos.size > 0"><s:property value="photos.size"/></s:if><s:else>0</s:else> 
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
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyBt2XicrROjhdH9ytrn3n9rTW6LKc2CkkA"></script>
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