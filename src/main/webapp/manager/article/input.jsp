<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
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
				<div class="ym-g50 ym-gl ym-fbox-button">
					<input type="button" id="swfDialog" name="swfDialog" value="Subir imagens"/>
				</div>	
				<div class="ym-g50 ym-gl ym-fbox-button">
					<s:submit value="Enviar" />
				</div>				
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
<script type="text/javascript" src="/frameworks/tinymce/tiny_mce.js"></script>
<script type="text/javascript" src="/frameworks/tinymce/plugins/photogallery/editor_plugin_src.js"></script>
<script type="text/javascript" src="/manager/js/PermanentLinkSelectorDialog.js"></script>
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
			content_css: "/manager/css/article.css?1",
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