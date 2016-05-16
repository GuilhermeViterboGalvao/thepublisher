<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar membro</strong>
		</li>					
	</ul>		
</nav>
<s:if test="isAdmin()">
	<s:fielderror cssClass="error"/>
	<form action="/manager/clube-tatame/member-save" method="post">
		<div class="ym-form">
			<div class="ym-fbox-text">
				<s:hidden name="id"/>	
				<label for="name">Nome</label>
				<s:textfield name="name"/>
				<label for="document">R.G.</label>
				<s:textfield name="document"/>				
				<label for="email">E-mail</label>
				<s:textfield name="email"/>		
				<label for="gender">Gênero</label>
				<s:select name="gender" list="listGenders" listKey="id" listValue="value" headerKey="0" headerValue="Selecione seu sexo"/>
				<label for="birth">Data de nascimento</label>
				<s:textfield name="birth" id="birth" />		
				<label for="address">Endereço</label>
				<s:textfield name="address"/>
				<label for="cep">CEP</label>
				<s:textfield name="cep"/>		
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
			<p style="margin: 10px 0px">Criado por <s:property value="createdBy.name" /> em <s:property value="%{getText('date.format', {created})}"/>.</p>			
		</s:if>		
		<s:if test="lastModifiedBy != null">		
			<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="%{getText('date.format', {lastModified})}"/>.</p>			
		</s:if>	
	</form>
	
	<script type="text/javascript">
		$("#birth").datepicker({
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
</s:if>
<s:else>
	<div class="ym-form">
		<div class="ym-fbox-text">
			<s:hidden name="id"/>	
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="document">R.G.</label>
			<s:textfield name="document"/>				
			<label for="email">E-mail</label>
			<s:textfield name="email"/>		
			<label for="gender">Gênero</label>
			<select name="gender">
				<option value="masculino">Masculino</option>
				<option value="feminino">Feminino</option>
			</select>
			<label for="birth">Data de nascimento</label>
			<s:textfield name="birth" value="%{getText('date.format',{birth})}"/>		
			<label for="address">Endereço</label>
			<s:textfield name="address"/>
			<label for="cep">CEP</label>
			<s:textfield name="cep"/>		
			<div class="ym-fbox-check" style="padding-top: 10px;">
				<label for="active">Ativo</label>
				<s:checkbox id="active" name="active"/>
	   		</div>	
		</div>
	</div>
	<s:if test="createdBy != null">		
		<p style="margin: 10px 0px">Criado por <s:property value="createdBy.name" /> em <s:property value="created"/>.</p>			
	</s:if>		
	<s:if test="lastModifiedBy != null">		
		<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="lastModified"/>.</p>			
	</s:if>
</s:else>