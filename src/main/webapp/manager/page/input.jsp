<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Nova capa</strong>
		</li>					
	</ul>
</nav>
<s:form action="page-save" cssClass="ym-form" onsubmit="checkPermanentLink();">
	<s:hidden name="id"/>
	<s:fielderror cssStyle="color: red;"/>
	<div class="ym-fbox-text">

		<label for="name">Nome</label>
		<s:textfield name="name"/>
			
		<label>Link Permanent:</label>
		<s:hidden id="link" name="link" value="%{permanentLink}"/>
		<s:textfield id="permanentLink" name="permanentLink" />		
		
		<label for="contentFile">Arquivo</label>
		<s:textfield name="contentFile"/>		
				
		<label for="skinName">Template</label>
		<s:hidden name="skinName"/>
		<s:if test="skinName != null && !skinName.equals('')">
			<p:autocomplete name="skinId" display="skinName" url="manager/ac-skin" />
		</s:if>
		<s:else>
			<p:autocomplete name="skinId" url="manager/ac-skin" />
		</s:else>
				
		<div class="ym-fbox-button">			
			<s:submit value="Enviar" align="left"/>
		</div>
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