<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Editar produto</strong>
		</li>					
	</ul>		
</nav>
<s:if test="isAdmin()">
	<s:fielderror cssClass="error"/>
	<form action="/manager/clube-tatame/product-save" method="post">
		<div class="ym-form">
			<div class="ym-fbox-text">
				<s:hidden name="id"/>
				<label for="name">Nome</label>
				<s:textfield name="name"/>
				<label for="value">Valor</label>
				<s:textfield name="value"/>
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
			<p style="margin: 10px 0px">Criado por <s:property value="createdBy.name" /> em <s:property value="created"/>.</p>			
		</s:if>		
		<s:if test="lastModifiedBy != null">		
			<p style="margin: 10px 0px">Modificado por <s:property value="lastModifiedBy.name" /> em <s:property value="lastModified"/>.</p>			
		</s:if>	
	</form>
</s:if>
<s:else>
	<div class="ym-form">
		<div class="ym-fbox-text">		
			<s:hidden name="id"/>
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="value">Valor</label>
			<s:textfield name="value"/>
			
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