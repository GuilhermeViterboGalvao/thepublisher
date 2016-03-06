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
<script type="text/javascript">	
	var checkValue = function() {
		var value = $("#value");
		try {
			if (value && value.val()) {
				var newValue;
				if (value.val().indexOf(",") > 0) {
					newValue = Number(value.val().replace(",", "."));	
				} else {
					newValue = Number(value.val());
				}
				value.val(parseFloat(newValue.toFixed(2)));
			}	
		} catch(exception) {
			value.val(0.00);
		}
	};
	checkValue();
	$("#value").keypress(function(event) {
		var value = this.value;
		var keyCode = event.keyCode;
		if ((keyCode >= 48 && keyCode <= 57) || keyCode == 46) {
			if (value.indexOf(".") > 0 && keyCode == 46) {
				return false;	
			}
			return true;
		}
		return false;
	});
</script>