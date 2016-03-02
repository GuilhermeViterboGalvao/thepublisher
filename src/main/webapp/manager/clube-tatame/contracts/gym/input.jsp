<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	$(function() {
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
	});
</script>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar contrato</strong>
		</li>					
	</ul>		
</nav>
<s:fielderror cssClass="error"/>
<form action="/manager/clube-tatame/contracts_gym-save" method="post" >
	<div class="ym-form">
		<div class="ym-fbox-text">
			<s:hidden name="id"/>
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="value">Valor</label>
			<s:textfield name="value"/>
			<label for="description">Descrição</label>
			<s:textarea name="description" rows="15"/>	
			
			<label for="start">Data de início:</label>
			<s:textfield id="start" name="start" />
			<label for="end">Data de término:</label>
			<s:textfield id="end" name="end" />
			
			<div class="ym-fbox-select" >
				<label for="gym">Academia</label>
				<s:select name="gym" list="listGyms" listKey="id" listValue="name"/>
			</div>		
			<div class="ym-fbox-select" >
				<label for="spots">Produtos</label>
				<s:select name="products" list="listProducts" listKey="id" listValue="name" multiple="true" size="8" />
			</div>
			<div class="ym-g50 ym-gl ym-fbox-button">
				<s:submit value="Enviar"/>
			</div>   		
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 8px">Criado por <s:property value="createdBy.name" /> em <s:property value="created"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 8px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="lastModified"/>.</p>			
	</s:if>	
</form>