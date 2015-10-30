<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Novo template</strong>
		</li>					
	</ul>		
</nav>
<s:if test="isAdmin">
	<s:form action="skin-save" cssClass="ym-form">
		<s:hidden name="id"/>	
		<s:fielderror cssStyle="color: red;"/>
		<div class="ym-fbox-text">		
			<label for="name">Nome</label>
			<s:textfield name="name"/>
	
			<label for="path">Template</label>
			<s:textfield name="path"/>
	
			<label for="contentFolder">Pasta de conteúdo</label>
			<s:textfield name="contentFolder"/>		
			
			<div class="ym-fbox-button">
				<s:submit value="Enviar" align="left"/>
			</div>
		</div>
	</s:form>
</s:if>
<s:else>
	<div class="ym-form">
		<div class="ym-fbox-text">		
			<label for="name">Nome</label>
			<s:textfield name="name"/>
			<label for="path">Template</label>
			<s:textfield name="path"/>
			<label for="contentFolder">Pasta de conteúdo</label>
			<s:textfield name="contentFolder"/>
		</div>	
	</div>
</s:else>