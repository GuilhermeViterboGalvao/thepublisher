<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 			  %>
<link href="/manager/photo/css/list.css" type="text/css" rel="stylesheet"/>
<script src="/manager/photo/js/list.js" type="text/javascript"></script>
<nav class="ym-hlist">
	<ul>
		<li class="active"><strong>Fotos</strong></li>
		<li><a href="photo-edit">Nova foto</a></li>
		<li><a href="photomulti-add">Enviar várias fotos</a></li>		
	</ul>
	<form class="ym-searchform">
		<s:hidden name="isDefault"/>
		<input class="ym-searchfield" type="search" name="search" value="<s:property value="search"/>" placeholder="Procurar..." /> 
		<input class="ym-searchbutton" type="submit" value="Procurar" />
	</form>
</nav>
<div id="photos" style="margin: 10px;">
    <s:iterator value="list">    
        <s:url id="url" action="photo-edit">
            <s:param name="id" value="id"/>
        </s:url>        
        <s:a href="%{url}">
        	<%
        		int i = (int)(Math.random() * 1000000);
        		request.setAttribute("i", i);
        	%>
        	<img src="<s:url value="/img/%{id}_80x60.jpg"/>?${i}" alt="<s:property value="id"/>"/> 
        </s:a>
    </s:iterator>
</div>
<s:div cssClass="ym-grid" cssStyle='padding-top: 10px; margin-left: 10px; width: 98.8%;'> 
	<s:if test="currentPage > 1">
	    <s:url id="url" action="photo-list">
	    	<s:param name="search" value="search"/>
	    	<s:param name="pageSize" value="pageSize"/>
	    	<s:param name="isDefault" value="isDefault"/>
	        <s:param name="currentPage" value="%{currentPage - 1}"/>
	    </s:url>
	    <s:div cssClass="ym-g33 ym-gl">
	    	<s:a href="%{url}" >P&aacute;gina Anterior</s:a>
	    </s:div>
	</s:if>	
		
	<s:set var="textAlign" value="" />
	<s:set var="gridSize" value=""/>	
		
	<s:if test="currentPage == 1"> 
		<s:set var="textAlign" value="'left'" />
		<s:set var="gridSize"  value="'ym-g50'" />
	</s:if>
	<s:else>	
		<s:set var="textAlign" value="'center'" />
		<s:set var="gridSize" value="'ym-g33'" />
	</s:else>
	
	<s:div cssClass="%{gridSize} ym-gl" cssStyle='text-align: %{textAlign}; margin-top 5px;'>	
	    <s:bean name="utils.PageList" id="counter">	        
	        <s:param name="numberOfPages" value="pages"/>
	        <s:param name="isDefault" value="isDefault"/>
	        <s:param name="selectedPage" value="currentPage"/>	        
	    </s:bean>
	    <s:iterator value="counter" >
	        <s:if test="top == currentPage">
	        		<s:property value="currentPage"/> 
	        </s:if>
	        <s:else>
	            <s:url id="url" action="photo-list">
	            	<s:param name="search" value="search"/>
	                <s:param name="currentPage" value="top"/>
	                <s:param name="pageSize" value="pageSize"/>
	                <s:param name="isDefault" value="isDefault"/>
	            </s:url>
	            <s:a href="%{url}"><s:property/></s:a>            
	        </s:else>
	    </s:iterator>
	</s:div>
	
	<s:if test="currentPage < pages">	
	     <s:url id="url" action="photo-list">	     	
	     	<s:param name="search" value="search"/>
	     	<s:param name="pageSize" value="pageSize"/>
	     	<s:param name="isDefault" value="isDefault"/>
	        <s:param name="currentPage" value="%{currentPage + 1}"/>
	     </s:url>
	     <s:div cssClass="%{gridSize} ym-gl" cssStyle='text-align: end;'>
	     	<s:a href="%{url}" >Pr&oacute;xima página</s:a>
	     </s:div>
	</s:if>
</s:div>