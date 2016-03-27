<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Novas fotos</strong>
		</li>					
	</ul>		
</nav>
<s:form method="post" action="photoGymMulti-save" onsubmit="return validateForm();" cssClass="ym-form">   		
	<div class="ym-fbox-text" >
		<label for="description">Descrição</label>
		<s:textarea id="description" name="description" />			
	    
		<label for="tags">Palavras chave:</label>
		<s:textarea id="tags" name="tags" />
				        
		<label for="credits">Fotógrafo:</label>
		<s:textfield name="credits"/>
			           
	    <label for="date">Data:</label>
	    <s:textfield id="date" name="date" value="%{getText('date.format',{date})}"/>    
	    
		<div class="ym-fbox-check">							
			<label for="published">Publicado</label>
			<s:checkbox name="published"/>
	    </div>
	    
	    <div class="ym-fbox-check">		    
		    <label for="useFilename">Acrescentar nas palavras chaves o nome dos arquivos</label>
		    <s:checkbox name="useFilename" />
		</div>
			
		<div class="ym-fbox-button" style="margin-top: 5px; margin-bottom: 20px;">            
	        <s:submit value="Salvar" align="left"/>
		</div>
		
		<p:swf 
			sessionid="%{sessionId}" 
			uploadUrl="/manager/photo_swf-upload" 
			deleteUrl="/manager/photo_swf-delete"
			idDivBtn="default"
			idDivFileProgress="default"
			idDivThumbnail="default"
		/>		
		<s:fielderror cssStyle="color: red;"/>		
	</div>			          
</s:form>
<script type="text/javascript" src="/manager/clube-tatame/photo-gym/js/multi.js"></script>