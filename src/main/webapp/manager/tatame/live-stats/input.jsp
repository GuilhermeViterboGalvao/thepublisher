<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Novo evento (LiveStats)</strong>
		</li>					
	</ul>
</nav>
<s:form action="liveStats-save" cssClass="ym-form" onsubmit="checkPermanentLink();">
	<s:hidden name="id"/>
	<s:fielderror cssStyle="color: red;"/>
	<div class="ym-fbox-text">

		<label for="eventName">Nome do evento</label>
		<s:textfield name="eventName"/>
		
		<label for="description">Descrição</label>
		<s:textfield name="description"/>			

		<label for="tags">Palavras chaves</label>
		<s:textarea name="tags" rows="2"/>

		<label for="code">Código LiveStats</label>
		<s:textarea name="code" rows="5"/>

		<label for="permanentLink">Link Permanent:</label>
		<s:hidden id="link" name="link" value="%{permanentLink}"/>
		<s:textfield id="permanentLink" name="permanentLink" />		
				
		<label for="skinName">Template</label>
		<s:hidden name="skinName"/>
		<s:if test="skinName != null && !skinName.isEmpty()">
			<p:autocomplete name="skinId" display="skinName" url="/manager/ac-skin" />
		</s:if>
		<s:else>
			<p:autocomplete name="skinId" url="/manager/ac-skin" />
		</s:else>
		
		<label for="pollName">Enquete</label>
		<s:hidden name="pollQuestion"/>
		<s:if test="pollQuestion != null && !pollQuestion.isEmpty()">
			<p:autocomplete name="pollId" display="pollQuestion" url="/manager/ac-poll" />
		</s:if>
		<s:else>
			<p:autocomplete name="pollId" url="/manager/ac-poll" />
		</s:else>

		<label for="publishedAt">Data de publicação:</label>
		<s:textfield name="publishedAt" value="%{getText('date.format',{publishedAt})}" cssClass="publishedAt"/>
		
		<div class="ym-fbox-check check-box">
			<label for="forumEnabled">Forum:</label>
			<s:checkbox name="forumEnabled" cssClass="forumEnabled"/>
		</div>
		
		<div class="ym-fbox-check check-box">
			<label for="published">Publicado:</label>
			<s:checkbox id="published" name="published" cssClass="published"/>			
		</div>

		<div class="ym-fbox-button">			
			<s:submit value="Enviar" align="left"/>
		</div>
		
		<s:if test="createdBy != null">		
			<p style="margin: 10px 0px">Criado por <s:property value="createdBy" /> em <s:property value="created"/></p>			
		</s:if>
				
		<s:if test="lastModifiedBy != null">		
			<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy" /> em <s:property value="lastModified"/></p>			
		</s:if>
	</div>	
</s:form>
<script type="text/javascript">
	function checkPermanentLink() {  	
		var name = $('input[name=name]').get(0).value;
		var link = $('#permanentLink').val();
		if (name != undefined && name != null && name != ""
				&& (link == undefined || link == null || link == "")) {
			$('#permanentLink').val(convertToPermanentLink(name));
		} else if (link != undefined && link != null && link != "") {
			$('#permanentLink').val(convertToPermanentLink($('#permanentLink').val()));
		}
	}	
	function convertToPermanentLink(text) {
		return text
		   .replace(new RegExp(' - ',            'gi'), '/')		
		   .replace(new RegExp('[áàâÁÀÂãÃ]',     'gi'), 'a')
	       .replace(new RegExp('[óòôÓÒÔõÕ]',     'gi'), 'o')
	       .replace(new RegExp('[úùÚÙ]',         'gi'), 'u')
	       .replace(new RegExp('[íìÍÌ]',         'gi'), 'i')
	       .replace(new RegExp('[éèêÉÈÊ]',       'gi'), 'e')
	   	   .replace(new RegExp('[çÇ]',           'gi'), 'c')		
		   .replace(new RegExp('[^a-zA-Z0-9/ -]', 'gi'),  '')	
		   .trim()
		   .replace(new RegExp(' ',              'gi'), '-')
		   .toLowerCase();
	}
</script>