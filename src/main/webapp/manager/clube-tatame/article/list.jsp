<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="ym-hlist">
	<ul>
		<li class="active"><strong>Matérias</strong></li>
		<li><a href="/manager/clube-tatame/article-edit">Nova matéria padrão</a></li>
	</ul>
	<form class="ym-searchform">
		<input class="ym-searchfield" type="search" name="search" value="<s:property value="search"/>" placeholder="Procurar..." /> 
		<input class="ym-searchbutton" type="submit" value="Procurar" />
		<input type="hidden" name="cid" value="${cid}"/>
	</form>
</nav>
<table class="list">
	<thead>
		<tr>
			<th>
				<a href="/manager/clube-tatame/article-list?orderBy=id&orderly=<s:property value="!orderly"/>">Id</a>
			</th>
			<th>
				<a href="/manager/clube-tatame/article-list?orderBy=publishedAt&orderly=<s:property value="!orderly"/>">Data</a>
			</th>
			<th>
				<a href="/manager/clube-tatame/article-list?orderBy=header&orderly=<s:property value="!orderly"/>">Chamada</a>
			</th>
			<th>
				<a href="/manager/clube-tatame/article-list?orderBy=title&orderly=<s:property value="!orderly"/>">Título</a>
			</th>
			<th>
				<a href="/manager/clube-tatame/article-list?orderBy=published&orderly=<s:property value="!orderly"/>">Publicado</a>
			</th>					
			<th></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="list">
			<tr>
				<td>
					<s:property value="id"/>
				</td>
				<td>
					<s:date name="publishedAt" format="dd/MM/yyyy hh:mm"/>
				</td>
				<td>
					<s:property value="header"/>
				</td>
				<td>
					<s:property value="title"/>
				</td>
				<td>
					<s:if test="published">
						<img src="/manager/img/on.png" alt="Publicado"/>
					</s:if>
					<s:else>
						<img src="/manager/img/off.png" alt="Despublicado"/>
					</s:else>
				</td>		
				<s:url id="url" action="article-edit">
					<s:param name="id" value="id"/>
				</s:url>
				<td class="td"><s:a href="%{url}">Editar</s:a></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

<s:div cssClass="ym-grid" cssStyle='padding-top: 10px; margin-left: 10px; width: 98.8%;'> 
	<s:if test="currentPage > 1">
	    <s:url id="url" action="article-list">
	    	<s:param name="search" value="search"/>
	    	<s:param name="pageSize" value="pageSize"/>
	        <s:param name="currentPage" value="%{currentPage - 1}"/>
	        <s:param name="orderBy" value="orderBy"/>
	        <s:param name="orderly" value="orderly"/>
	        <s:param name="cid" value="cid"/>
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
	    <s:bean name="com.publisher.utils.PageList" id="counter">
	        <s:param name="selectedPage" value="currentPage"/>
	        <s:param name="numberOfPages" value="pages"/>
	    </s:bean>
	    <s:iterator value="counter" >
	        <s:if test="top == currentPage">
	        	<s:property value="currentPage"/>
	        </s:if>
	        <s:else>
	            <s:url id="url" action="article-list">
	            	<s:param name="search" value="search"/>
	                <s:param name="currentPage" value="top"/>
	                <s:param name="pageSize" value="pageSize"/>
			        <s:param name="orderBy" value="orderBy"/>
			        <s:param name="orderly" value="orderly"/>
			        <s:param name="cid" value="cid"/>	                
	            </s:url>
	            <s:a href="%{url}"><s:property/></s:a>            
	        </s:else>
	    </s:iterator>
	</s:div>
	
	<s:if test="currentPage < pages">	
	     <s:url id="url" action="article-list">
	     	<s:param name="search" value="search"/>
	     	<s:param name="pageSize" value="pageSize"/>
	        <s:param name="currentPage" value="%{currentPage + 1}"/>
	        <s:param name="orderBy" value="orderBy"/>
	        <s:param name="orderly" value="orderly"/>
	        <s:param name="cid" value="cid"/>	         
	     </s:url>
	     <s:div cssClass="%{gridSize} ym-gl" cssStyle='text-align: end;'>
	     	<s:a href="%{url}" >Pr&oacute;xima página</s:a>
	     </s:div>
	</s:if>
</s:div>