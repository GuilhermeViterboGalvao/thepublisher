<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Nova enquete</strong>
		</li>					
	</ul>
</nav>

<div id="popup" style="display: none" title="Insira uma alternativa"></div>

<s:form action="poll-save" cssClass="ym-form">
	<s:hidden name="id"/>
	<s:fielderror cssStyle="color: red;"/>
	<div class="ym-fbox-text">

		<label for="question">Pergunta</label>
		<s:textfield name="question"/>
		
		<label for="publishedAt">Data de publicação:</label>
		<s:textfield name="publishedAt" value="%{getText('date.format',{publishedAt})}" cssClass="publishedAt"/>
		
		<div class="ym-fbox-check check-box">
			<label for="published">Publicado:</label>
			<s:checkbox id="published" name="published" cssClass="published"/>			
		</div>
		
		<div class="ym-fbox-text">
			<label style="float: left;">Alternativas:</label>
			<s:a cssClass="ym-button ym-add" cssStyle="float: left; margin-top: 0px; margin-left: 10px;" id="addAlternatives" name="addAlternatives" href="javascript:add();">Adicionar alternativa</s:a>
		</div>
		
		<s:if test="alternatives != null">
			<div id="alternatives" class="ym-fbox-text" style="background-color: rgb(191, 191, 191);">
				<s:iterator value="alternatives" status="st">
					<div id="alternative<s:property value="#st.count"/>" class="ym-fbox-text">
						<label for="alternative"><s:property value="#st.count"/>-)alternativa:</label>
						<s:hidden name="alternatives[%{#st.count - 1}].id" value="%{id}"/>
						<s:hidden name="alternatives[%{#st.count - 1}].votes" value="%{votes}"/>
						<s:textfield name="alternatives[%{#st.count - 1}].text" value="%{text}"/>
						<s:a cssClass="ym-button ym-delete" cssStyle="float: right; margin-top: 2px;" href="javascript:del(%{#st.count});">excluir</s:a>
					</div>
				</s:iterator>
			</div>
		</s:if>
		<s:else>
			<div id="alternatives" class="ym-fbox-text"></div>
		</s:else>
	
		<div class="ym-fbox-text ym-error">
			<s:fielderror cssStyle="color: red;"/>
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
	var i = 0;
	
	function removeTextNodes(element){
		for (var i = 0; i < element.childNodes.length; i++) {
			if (element.childNodes[i].nodeType === 3) {
				element.removeChild(element.childNodes[i]);
			}	
		}
	}
	
	function del(id){	
		$('#alternative'+id).remove();
		i--;
		if (i == 0) {
			$('#alternatives')[0].css('background-color', '');
		} else {
		    for (var count = 0; count < i; count++) {
		    	removeTextNodes($('#alternatives').children()[count]);
		    	$('#alternatives').children()[count].firstChild.innerHTML = (count + 1) + "-) alternativa: ";
		    }
		}
	}
	
	function confirm(){
		$('#alternatives').css('backgroundColor', '#bfbfbf').append(
				$('<div>').attr('id', 'alternative' + (i+1)).addClass('ym-fbox-text').append(
						$('<label>').html((i+1) + '-) alternativa: ').css('color', '#666666')
				).append(
						$('<input>').attr('type', 'text').attr('name', 'alternatives[' + i + '].text').val($('#textArea').val())
				).append(
						$('<a>').addClass('ym-button ym-delete').css('float', 'right').css('marginTop', '2px').html('excluir').attr('href', 'javascript:del(' + (i+1)  + ');')
				)
		);
		
		i++;
		
		$('#popup').dialog('close');
	}
	
	function add(){
		$('#popup').empty().addClass('ym-form').append(			
				$('<div>').addClass('ym-fbox-text').append(
						$('<label>').html((i+1) + "-) alternativa: ")
				).append(
						$('<textarea>').attr('id', 'textArea').css('rows', 5).css('cols', 100)
				)
		).append(
				$('<div>').css('padding', '0.2em 11em').append(					
						$('<a>').addClass('ym-button ym-save').click(confirm).html('Confirmar')
				)
		).dialog({ 
			height: 350,
			maxHeight: 350,
			minHeight: 350,
			width: 550,
			maxWidth: 550,
			minWidth: 550
		});
	}
</script>

<s:if test="alternatives != null && alternatives.size() > 0">
	<script type="text/javascript">
		i = <s:property value="alternatives.size()"/>;
	</script>
</s:if>