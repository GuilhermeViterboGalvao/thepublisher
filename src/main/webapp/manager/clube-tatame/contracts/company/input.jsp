<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar contrato</strong>
		</li>					
	</ul>		
</nav>
<s:fielderror cssClass="error"/>
<form action="/manager/clube-tatame/contracts_company-save" method="post" onsubmit="checkValue();">
	<div class="ym-form">
		<div class="ym-fbox-text">
			<s:hidden name="id"/>
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="value">Valor</label>
			<s:textfield id="value" name="value"/>
			<label for="description">Descrição</label>
			<s:textarea name="description" rows="15"/>			
			<label for="start">Data de início</label>
			<s:textfield id="start" name="start" />
			<label for="end">Data de término</label>
			<s:textfield id="end" name="end" />			
			<div class="ym-fbox-select" >
				<label for="company">Empresa</label>
				<s:select name="company" list="listCompanys" listKey="id" listValue="name" headerKey="0" headerValue="Selecione uma empresa"/>
			</div>		
			<div class="ym-fbox-select" >
				<label for="products">Produtos</label>
				<s:select id="products" name="products" list="listProducts" listKey="id" listValue="name" multiple="true" size="8" />
			</div>
			<div class="ym-g50 ym-gl ym-fbox-button">
				<s:submit value="Enviar"/>
			</div>   		
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 8px">Criado por <s:property value="createdBy.name" /> em <s:property value="%{getText('date.format', {created})}"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 8px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="%{getText('date.format', {lastModified})}"/>.</p>			
	</s:if>	
</form>
<script type="text/javascript">
	var products = [
		<s:iterator value="listProducts" status="i">
			{
				id: ${id},
				value: ${value}
			}<s:if test="#i.index < listProducts.size() - 1">,</s:if>
		</s:iterator>
	];
	function getProductValue(id) {
		var product = null;
		for (var i = 0; i < products.length; i++) {
			product = products[i];
			if (product.id == id) {
				break;
			}
		}
		return product != null ? product.value : 0;
	}
	$("#products").change(function() {
		var newValue = 0;
		 $("#products option:selected").each(function() {
			 newValue += getProductValue(this.value);
		 });
		 $("#value").val(parseFloat(newValue.toFixed(2)));
	});	
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
			value.val(0.0);
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
	$("#start").datepicker({
	    dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Próximo',
	    prevText: 'Anterior'
	});
	$("#end").datepicker({
	    dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Próximo',
	    prevText: 'Anterior'
	});
</script>