<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<div id="statistics" style="display: none" title="Resultado da enquete"></div>


<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Enquetes</strong>
		</li>
		<li>
			<a href="poll-edit">Adicionar</a>
		</li>		
	</ul>
	<form class="ym-searchform">
		<input class="ym-searchfield"  type="search" name="search" value="<s:property value="search"/>" placeholder="Procurar..." /> 
		<input class="ym-searchbutton" type="submit" value="Procurar" />
	</form>
</nav>
<table class="list">
	<thead>
		<tr>
			<th>
				<a href="poll-list?orderBy=id&orderly=<s:property value="!orderly"/>">id</a>
			</th>
			<th>
				<a href="poll-list?orderBy=question&orderly=<s:property value="!orderly"/>">Pergunta</a>
			</th>	
			<th>
				<a href="poll-list?orderBy=publishedAt&orderly=<s:property value="!orderly"/>">Data</a>
			</th>		
			<th>
				<a href="poll-list?orderBy=published&orderly=<s:property value="!orderly"/>">Publicado</a>
			</th>
			<th></th>
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
					<s:property value="question"/>
				</td>
				<td>
					<s:property value="%{getText('date.format',{publishedAt})}"/>
				</td>
				<td>
					<s:if test="published">Sim</s:if><s:else>Não</s:else>
				</td>				
				<td class="td">
					<a href="poll-edit?id=<s:property value="id"/>">Editar</a>
				</td>
				<td class="td">
					<a href="javascript:showStatistics(polls[<s:property value="#i.index"/>])">Resultado</a>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>


<s:div cssClass="ym-grid" cssStyle='padding-top: 10px; margin-left: 10px; width: 98.8%;'> 
	<s:if test="currentPage > 1">
	    <s:url id="url" action="poll-list">
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
	        <s:param name="selectedPage"  value="currentPage"/>
	        <s:param name="numberOfPages" value="pages"/>
	    </s:bean>
	    <s:iterator value="counter" >
	        <s:if test="top == currentPage">
	        		<s:property value="currentPage"/> 
	        </s:if>
	        <s:else>
	            <s:url id="url" action="poll-list">
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
	     <s:url id="url" action="poll-list">
	     	<s:param name="search"      value="search"/>
	     	<s:param name="pageSize"    value="pageSize"/>
	        <s:param name="currentPage" value="%{currentPage + 1}"/>
	        <s:param name="orderBy"     value="orderBy"/>
	        <s:param name="orderly"     value="orderly"/>	         
	     </s:url>
	     <s:a href="%{url}" cssClass="%{gridSize} ym-gl" cssStyle='text-align: end;'>Pr&oacute;xima página</s:a>
	</s:if>
</s:div>


<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});

	var showStatistics = function(poll) {
		
		if (poll == null) return;
	
		var data = new Array();
	
		data[0] = [ 'Alternativa', 'Votos' ];
	
		for ( var i = 0; i < poll.alternatives.length; i++) {
			data[i + 1] = [ poll.alternatives[i].text, poll.alternatives[i].votes ];
		}
		
		console.log(data);
	
		var dataTable = google.visualization.arrayToDataTable(data);
	
		var options = {
			title : poll.question,
			backgroundColor : "white",
			height: 400,
			width: 1200
		};
		
		var chart = new google.visualization.PieChart($('#statistics').empty()[0]);
		
		chart.draw(dataTable, options);
		
		$('#statistics').dialog({
			height: 370,
			maxHeight: 370,
			minHeight: 370,
			width: 720,
			maxWidth: 720,
			minWidth: 720
		});		
	};

	var polls = [
		<s:iterator value="list" status="i">
			{
				id: <s:property value="id"/>,
				date: '<s:property value="date.toString()"/>',
				question: '<s:property value="escapeToJson(question)" escape="false"/>',
				alternatives: [
					<s:iterator value="alternatives" status="j">
						{
							id: <s:property value="id"/>,
							text: '<s:property value="escapeToJson(text)" escape="false"/>',
							votes: <s:if test="votes != null"><s:property value="votes"/></s:if><s:else>0</s:else> 
						}<s:if test="#j.index < (alternatives.size() - 1)">,</s:if>
					</s:iterator>
				]
			}<s:if test="#i.index < (list.size() - 1)">,</s:if>
		</s:iterator>
	];
</script>