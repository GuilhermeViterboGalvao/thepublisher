<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Novo Token</strong>
		</li>					
	</ul>		
</nav>
<s:if test="isAdmin()">
	<s:form action="authToken-save" cssClass="ym-form">	
		<s:hidden name="id"/>
		<s:fielderror cssStyle="color: red;"/>		
		<div class="ym-fbox-text">	
			<label>IPs:</label>
			<s:textarea name="IPs" rows="2"/>	
			
			<label>DNSs:</label>
			<s:textarea name="DNSs" rows="2"/>
			
			<label>Descrição:</label>
			<s:textarea name="description" rows="5"/>
			
			<s:if test="token != null">
				<label>Chave de acesso: <s:property value="token"/> </label>
			</s:if>


			<div class="ym-fbox-check" style="padding-top: 10px;">
				<label for="active">Ativo</label>		
				<s:checkbox id="active" name="active"/>
	   		</div>
			<div class="ym-fbox-button">
				<s:submit value="Enviar"/>
			</div>
		</div>
	</s:form>
</s:if>