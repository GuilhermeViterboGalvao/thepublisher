<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Contratos de Membros</strong>
		</li>
		<li class="active">
			<a href="contracts_member-edit">Novo</a>
		</li>
	</ul>
	<form class="ym-searchform">
		<input class="ym-searchfield" type="search" name="search" value="<s:property value="search"/>" placeholder="Procurar..." /> 
		<input class="ym-searchbutton" type="submit" value="Procurar" />
	</form>
</nav>
<table class="list">
	<thead>
		<tr>
			<th>
				<a href="contracts_member-list?orderBy=id&orderly=<s:property value="!orderly"/>">Id</a>
			</th>
			<th>
				<a href="contracts_member-list?orderBy=name&orderly=<s:property value="!orderly"/>">Nome</a>
			</th>
			<th>
				<a href="contracts_member-list?orderBy=start&orderly=<s:property value="!orderly"/>">Data de inicio</a>
			</th>
			<th>
				<a href="contracts_member-list?orderBy=end&orderly=<s:property value="!orderly"/>">Data de fim</a>
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
					<s:property value="name"/>
				</td>
				<td>
					<s:date name="start" format="dd/MM/yyyy HH:mm"/>
				</td>
				<td>
					<s:date name="end" format="dd/MM/yyyy HH:mm"/>
				</td>
				<td class="td">
					<a href="contracts_member-edit?id=<s:property value="id"/>">Editar</a>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<s:div cssClass="ym-grid" cssStyle='padding-top: 10px; margin-left: 10px; width: 98.8%;'> 
	<s:if test="currentPage > 1">
	    <s:url id="url" action="contracts_member-list">
	    	<s:param name="search"      value="search"/>
	    	<s:param name="pageSize"    value="pageSize"/>
	        <s:param name="currentPage" value="%{currentPage - 1}"/>
	        <s:param name="orderBy"     value="orderBy"/>
	        <s:param name="orderly"     value="orderly"/>
	    </s:url>
	    <s:a href="%{url}" cssClass="ym-g33 ym-gl" >P&aacute;gina Anterior</s:a>
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
	            <s:url id="url" action="contracts_member-list">
	            	<s:param name="search"      value="search"/>
	                <s:param name="currentPage" value="top"/>
	                <s:param name="pageSize"    value="pageSize"/>
	              	<s:param name="orderBy"     value="orderBy"/>
	        		<s:param name="orderly"     value="orderly"/>
	            </s:url>
	            <s:a href="%{url}"><s:property/></s:a>            
	        </s:else>
	    </s:iterator>
	</s:div>
	<s:if test="currentPage < pages">	
	     <s:url id="url" action="contracts_member-list">
	     	<s:param name="search"      value="search"/>
	     	<s:param name="pageSize"    value="pageSize"/>
	        <s:param name="currentPage" value="%{currentPage + 1}"/>
	        <s:param name="orderBy"     value="orderBy"/>
	        <s:param name="orderly"     value="orderly"/>	         
	     </s:url>
	     <s:a href="%{url}" cssClass="%{gridSize} ym-gl" cssStyle='text-align: end;'>Pr&oacute;xima página</s:a>
	</s:if>
</s:div>