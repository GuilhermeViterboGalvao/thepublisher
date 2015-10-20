<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="/frameworks/tinymce/tiny_mce.js"></script>
<script type="text/javascript" src="/frameworks/tinymce/plugins/photogallery/editor_plugin_src.js"></script>
<script type="text/javascript">
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
	});
</script>

<style type="text/css">
	img.photoselector { float: left; margin: 8px 20px 8px 0; }
	.tinyMCEEditor { margin: 10px 1px 10px 10px; padding: 0; width: 863px; }
</style>

<nav class="ym-hlist">
	<ul>
		<li class="active"><strong>Matéria Padrão</strong></li>
		<li class="active"><strong>Total de Visualizações - <s:property value="views"/></strong></li>
	</ul>
</nav>

<s:fielderror cssStyle="color: red;" />

<s:form action="article-save" onsubmit="checkPermanentLink();">

	<div class="ym-form">

		<div class="ym-fbox-text" style="margin-bottom: 0">
		
			<s:hidden name="id" />
			<s:hidden name="type" />
			<p:photoSelector 
				name="photoId" 
				cssStyle="float: left;"
				display="'/img/'+photoId+'_150x100.jpg'"
				url="publisher/ac-photo"
				imagePattern="/img/[value]_150x100.jpg"
				minLength="5" 
				delay="500" 
				pageSize="60" 
				showToolTip="true"
				initialUrl="publisher/ac-photo"
				loadingImage="../system/publisher/img/loader.gif" 
			/>
				
			<label style="overflow: hidden;">Cabeçalho:</label> 
			<s:textfield name="header" cssStyle="width: 659px;" />

			<label style="overflow: hidden;">Título:</label>
			<s:textfield name="title" cssStyle="width: 659px;" />

			<label style="clear: left">Categoria:</label>
			<s:hidden name="categoryName"/>			
			<s:if test="categoryName.equals('')">
				<p:autoComplete 
					name="categoryId" 
					display="-vazio-" 
					url="publisher/ac-category" 
					initial="[
						{label:'Waves',value: 1},
						{label:'Fluir',value: 1005},
						{label:'SupClub',value: 963},
						{label:'Tatame',value: 1094}
					]"
				/>			
			</s:if>
			<s:else>
				<p:autoComplete 
					name="categoryId" 
					display="%{categoryName}" 
					url="publisher/ac-category" 
					initial="[
						{label:'Waves',value: 1},
						{label:'Fluir',value: 1005},
						{label:'SupClub',value: 963},
						{label:'Tatame',value: 1094}
					]" 
				/>			
			</s:else>

			<label>Autor:</label>			
			<s:hidden id="authorDescription" name="authorDescription"/>
			<s:if test="authorDescription.equals('')">
				<p:autoComplete 
					id="authorId" 
					name="authorId" 
					display="-vazio-" 
					url="publisher/ac-collaborator" 
					initial="[
						{label:'Redação Fluir',value: 11146}, 
						{label:'Redação SupClub',value: 11472},
						{label:'Redação Waves',value: 2384},
						{label:'Revista Tatame',value: 12038}
					]"  
				/>
			</s:if>
			<s:else>
				<p:autoComplete 
					id="authorId" 
					name="authorId" 
					display="%{authorDescription}" 
					url="publisher/ac-collaborator" 
					initial="[
						{label:'Redação Fluir',value: 11146}, 
						{label:'Redação SupClub',value: 11472},
						{label:'Redação Waves',value: 2384},
						{label:'Revista Tatame',value: 12038}
					]"  
				/>
			</s:else>
			<img alt="Adicionar colaborador" src="/system/publisher/img/add.png" onclick="showCollaboratorDialog('authorId')">

			<label>Resenha:</label>
			<s:textarea name="note" rows="3" />

			<label>Chaves:</label>
			<s:textarea name="tags" rows="2" />
			
			<label>Link Permanent:</label>
			<s:hidden id="link" name="link" value="%{permanentLink}"/>
			<s:textfield id="permanentLink" name="permanentLink" />
			
			<label>Template Padrão:</label>
			<s:hidden name="templateDescription"/>
			<s:if test="templateDescription.equals('')">
				<p:autoComplete 
					name="templateId" 
					display="-vazio-"	
					url="publisher/ac-skin"  
					initial="[
						{label:'Waves - Padrão',value: 1},
						{label:'Padrão - SupClub',value: 179},
						{label:'Fluir - Padrão ',value: 164},
						{label:'Tatame - Padrão',value: 214}
					]"
				/>
			</s:if>
			<s:else>
				<p:autoComplete 
					name="templateId" 
					display="%{templateDescription}"	
					url="publisher/ac-skin"  
					initial="[
						{label:'Waves - Padrão',value: 1},
						{label:'Padrão - SupClub',value: 179},
						{label:'Fluir - Padrão ',value: 164},
						{label:'Tatame - Padrão',value: 214}
					]"
				/>
			</s:else>			
						
			<label>Data:</label>
			<s:textfield name="publishedAt" value="%{getText('date.format',{publishedAt})}" cssStyle="margin-right: 20px; width: 150px; float: left;" />
			
			<s:div cssStyle="float: right; top: -20px; margin-right: 50px;">				
				<s:if test="id > -1">
					<h4><s:a href="javascript:void(0);" onclick="window.open('/article?id=%{id}&preview=true','_blank','height=768,width=1024,scrollbars=yes');">Preview</s:a></h4>
				</s:if>				
			</s:div>			
			
			<div class="ym-fbox-check" style="margin: 0 230px;">

				<label>Forum:</label>
				<s:checkbox name="forumEnabled" label="Forum" cssStyle="margin-right: 50px;" />

				<label>Publicado:</label>
				<s:checkbox id="published" name="published" label="Publicado" cssStyle="margin-right: 50px;"/>

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
		
			<p style="margin: 10px 0px">
				Criado por
				<s:property value="createdBy" />
				em
				<s:property value="created" />
			</p>
			
		</s:if>
		
		<s:if test="lastModifiedBy!=null">
		
			<p style="margin: 10px 0px">
				Modificado por
				<s:property value="lastModifiedBy" />
				em
				<s:property value="lastModified" />
			</p>
			
		</s:if>

	</div>

</s:form>
<script type="text/javascript" src="/manager/js/PermanentLinkSelectorDialog.js"></script>
<%-- <script type="text/javascript" src="/manager/js/collaboratorDialog.js"></script> --%>
<script type="text/javascript" src="/manager/js/SwfDialog.js"></script>
<script type="text/javascript">
	PermanentLinkSelectorDialog({
		'element_target': 'published',
		'on_confirm' : function(permanentLink){	
			$('#permanentLink').val(convertToPermanentLink(permanentLink)); 
		},
		'on_show' : function(element, permanentLinkSelectorDialog){	
			if(element.checked) permanentLinkSelectorDialog.show($('#permanentLink').val());
		}
	});	
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
	SWFDialog({
		sessionId    : '<s:property value="sessionId"/>',
		targetButton : 'swfDialog'
	});
</script>