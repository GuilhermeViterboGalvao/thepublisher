<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
	img.photoselector { float: left; margin: 8px 20px 8px 0; }
	div.d-photo { margin: 10px 0; background-color: #cccccc; height: 48px; position: relative; }
	img.d-photo { display: block; float: left; margin: 0; border: 0; width:80px; height: 48px; cursor: move; }
	img.d-photo-nav { width: 22px; height: 22px; float: right; margin: 3px; }
	div.d-photo > textarea.d-photo  { position: absolute; left: 85px; top: 5px; padding: 4px; width: 715px; height: 38px; margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0;	}
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
			<strong>Matéria Padrão</strong>
		</li>
		<li class="active">
			<strong>Total de Visualizações - <s:property value="views"/></strong>
		</li>
	</ul>
</nav>
<s:fielderror cssClass="error"/>
<form action="/manager/article-save" method="post" onsubmit="checkPermanentLink();">
	<div class="ym-form">
		<div class="ym-fbox-text fields">		
			<s:hidden name="id"/>
			<s:hidden name="type"/>
			<p:photoselector 
				name="photoId" 
				cssStyle="float: left;"
				display="'/img/'+photoId+'_150x100.jpg'"
				url="/manager/ac-photo"
				imagePattern="/img/[value]_150x100.jpg"
				minLength="5" 
				delay="500" 
				pageSize="60" 
				showToolTip="true"
				initialUrl="/manager/ac-photo"
				loadingImage="/manager/img/loader.gif" 
			/>
				
			<label class="header">Cabeçalho:</label> 
			<s:textfield name="header" cssClass="header"/>

			<label class="title">Título:</label>
			<s:textfield name="title" cssClass="title"/>
			
			<label class="categoryName">Categoria:</label>
			<s:hidden name="categoryName"/>			
			<s:if test="categoryName.equals('')">
				<p:autocomplete 
					name="categoryId" 
					display="-vazio-" 
					url="/manager/ac-category" 
					initial="[{label:'Tatame',value: 1}]"
				/>			
			</s:if>
			<s:else>
				<p:autocomplete 
					name="categoryId" 
					display="%{categoryName}" 
					url="/manager/ac-category" 
					initial="[{label:'Tatame',value: 1}]"
				/>			
			</s:else>

			<label>Autor:</label>			
			<s:hidden id="createdByDescription" name="createdByDescription"/>
			<s:if test="createdByDescription.equals('')">
				<p:autocomplete 
					id="createdById" 
					name="createdById" 
					display="-vazio-" 
					url="/manager/ac-account" 
					initial="[{label:'Redação Tatame',value: 1}]"
				/>
			</s:if>
			<s:else>
				<p:autocomplete 
					id="createdById" 
					name="createdById" 
					display="%{createdByDescription}" 
					url="/manager/ac-account" 
					initial="[{label:'Redação Tatame',value: 1}]"  
				/>
			</s:else>

			<label>Resenha:</label>
			<s:textarea name="note" rows="3"/>

			<label>Chaves:</label>
			<s:textarea name="tags" rows="2"/>
			
			<label>Link Permanent:</label>
			<s:hidden id="link" name="link" value="%{permanentLink}"/>
			<s:textfield id="permanentLink" name="permanentLink" />
			
			<label>Template Padrão:</label>
			<s:hidden name="templateDescription"/>
			<s:if test="templateDescription.equals('')">
				<p:autocomplete 
					name="templateId" 
					display="-vazio-"	
					url="/manager/ac-skin"  
					initial="[{label:'Tatame - Padrão',value: 214}]"
				/>
			</s:if>
			<s:else>
				<p:autocomplete 
					name="templateId" 
					display="%{templateDescription}"	
					url="/manager/ac-skin"  
					initial="[{label:'Tatame - Padrão',value: 214}]"
				/>
			</s:else>
						
			<label>Data:</label>
			<s:textfield name="publishedAt" value="%{getText('date.format',{publishedAt})}" cssClass="publishedAt"/>
			
			<div class="preview">				
				<s:if test="id > -1">
					<h4><s:a href="javascript:void(0);" onclick="window.open('/article?id=%{id}&preview=true','_blank','height=768,width=1024,scrollbars=yes');">Preview</s:a></h4>
				</s:if>				
			</div>			
			
			<div class="ym-fbox-check check-box">
				<label>Forum:</label>
				<s:checkbox name="forumEnabled" cssClass="forumEnabled"/>

				<label>Publicado:</label>
				<s:checkbox id="published" name="published" cssClass="published"/>
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

	<div class="tinyMCEEditor">		
		<s:textarea id="tinyMCEEditor" name="content"></s:textarea>
		<s:if test="createdBy!=null">		
			<p style="margin: 10px 0px">Criado por <s:property value="createdBy" /> em <s:property value="created"/></p>			
		</s:if>		
		<s:if test="lastModifiedBy!=null">		
			<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy" /> em <s:property value="lastModified"/></p>			
		</s:if>
	</div>
</form>
<script type="text/javascript" src="/frameworks/jquery/plugins/jquery.xmldom-1.0.min.js"></script>
<script type="text/javascript" src="/frameworks/tinymce/tiny_mce.js"></script>
<script type="text/javascript" src="/frameworks/tinymce/plugins/photogallery/editor_plugin_src.js"></script>
<script type="text/javascript" src="/manager/js/PermanentLinkSelectorDialog.js"></script>
<script type="text/javascript" src="/manager/js/PhotoSelectorDialog.js"></script>
<script type="text/javascript" src="/manager/js/SwfDialog.js?1"></script>
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