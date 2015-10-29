<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<link href="/manager/photo/css/input.css" type="text/css" rel="stylesheet"/>
<script src="/manager/photo/js/input.js" type="text/javascript"></script>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Nova foto</strong>
		</li>
		<s:if test="id > 0">
			<li class="active">
				<s:a href="photocustom-list?id=%{id}">Fotos customizadas</s:a>
			</li>			
		</s:if>
	</ul>
</nav>
<div style="margin-top: 5px;">
	<s:if test="id > 0">
		<div style="float: left; margin: 0 10px 0 10px; width: 422px; height: 290px; border: 1px solid black; background-color: #cccccc;">	    
	        <div style="position: relative; border: 1px solid #888888; width: <s:property value="getShowWidth(400,268)"/>px; height: <s:property value="getShowHeight(400,268)"/>px; margin: <s:property value="(288-getShowHeight(400,268))/2"/>px <s:property value="(420-getShowWidth(400,268))/2"/>px;">
	            <div 
	            	id="marker" 
	            	style="position: absolute; top: -10px; left: -10px; border: 1px solid red; width: 20px; height: 20px; display: none;">
	            </div>	            
	            <img 
	            	id="picture" 
	            	src="<s:url value="/img/%{id}_%{getShowSize(400, 268)}.jpg"/>" 
	            	width="<s:property value="%{getShowWidth(400, 268)}"/>" 
	            	height="<s:property value="%{getShowHeight(400,268)}"/>" 
	            	alt="Foto"
	            />
	        </div>
	    </div>	
	</s:if>
    
    <s:form method="post" enctype="multipart/form-data" action="photo-save" cssClass="ym-form"  accept-charset="UTF-8">     			
		<div class="ym-fbox-text">		    	    
            <s:hidden key="id"/>            
            <s:fielderror cssStyle="color: red;"/>            
            <s:hidden key="horizontalCenter"/>            
            <s:hidden key="verticalCenter"/>
            
            <label for="description">Descrição</label>
			<s:textarea name="description"/>
            
            <label for="tags">Palavras chave</label>
            <s:textarea name="tags"/>
            
			<label for="credits">Créditos da foto</label>
			<s:textfield name="credits"/>
                        
            <label for="date">Data</label>
            <s:textfield name="date" value="%{getText('date.format', {date})}"/>
						
		    <label for="picture">Selecione a imagem:</label>
			<s:file name="picture"/>
                        
			<div class="ym-fbox-check">
				<label for="published">Publicado</label>
				<s:checkbox name="published"/>
			</div>
			                        
            <s:if test="id > 0">            
            	<a href="<s:url value="/img/%{id}.jpg"/>">Download</a>            	
				<label for="width">Tamanho: <s:property value="width"/>x<s:property value="height"/></label>
            	<p>Criado por: <s:property value="createdByName"/> em <s:property value="getText('date.format',{created})"/></p>            	
            	<p>Modificado por: <s:property value="lastModifiedByName"/> em <s:property value="getText('date.format',{lastModified})"/></p>			
            </s:if>

			<div class="ym-fbox-button">
				<s:submit key="Salvar" align="left"/>
			</div>			
		</div>
	</s:form>       
</div>
<script type="text/javascript">
	inicialize(<s:property value="horizontalCenter"/>, <s:property value="verticalCenter"/>);
</script>