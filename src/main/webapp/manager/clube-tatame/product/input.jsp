<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar produto</strong>
		</li>					
	</ul>	
</nav>
<s:fielderror cssClass="error"/>
<form action="/manager/clube-tatame/product-save" method="post" onsubmit="checkValue();">
	<div class="ym-form">
		<div class="ym-fbox-text">
			<s:hidden name="id"/>
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="value">Valor</label>
			<s:textfield id="value" name="value"/>
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
		<p style="margin: 10px 8px">Criado por: <s:property value="createdBy.name"/> em <s:property value="%{getText('date.format', {created})}"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 8px">Modificado por: <s:property value="lastModifiedBy.name"/> em <s:property value="%{getText('date.format', {lastModified})}"/>.</p>			
	</s:if>	
</form>
<script type="text/javascript" src="/frameworks/jquery/plugins/MaskMoney/jquery.maskMoney.min.js"></script>	
<script type="text/javascript">	
	var checkValue = function() {
		var value = $("#value");
		value.val(value.maskMoney("unmasked")[0]);
	};
	$("#value").maskMoney({prefix:"R$ ", allowNegative: true, thousands:".", decimal:",", affixesStay: false}).maskMoney("mask");
</script>