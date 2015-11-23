<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<div class="box-1024">
	<div class="box-714">
		<div class="articles">
			<div class="title">
				<a href="/<s:property value="permanentLink.uri"/>">
					<s:property value="name"/>
				</a>			
			</div>
			<s:iterator value="getArticles(6)" status="i">
				<div class="article">
					<a href="/${permanentLink.uri}">
						<img alt="${photo.id}" src="http://cdn-tatame.trrsf.com/img/${photo.id}_270x180.jpg"/>
					</a>					
					<div class="info">
						<div class="title">
							<a href="/${permanentLink.uri}">${title}</a>
						</div>
						<div class="author">
							<a href="mailto:${createdBy.email}">${createdBy.name}</a>
						</div>
						<div class="date"><s:date name="publishedAt" format="dd/MM/yyyy hh:mm"/></div>
					</div>
				</div>
			</s:iterator>
		</div>
		<div class="footer">
			<s:if test="currentPage != 1"> 
				<s:url id="url" value="/%{permanentLink.uri}">				            
			           <s:param name="pageSize" value="pageSize"/>            
			           <s:param name="currentPage" value="currentPage - 1"/>
				</s:url>
				<s:a href="%{url}" cssClass="previous">Anterior</s:a>
			</s:if>
			
			<s:bean name="com.publisher.utils.PageList" id="counter">
			    <s:param name="selectedPage" value="currentPage"/>
			    <s:param name="numberOfPages" value="pages"/>
			</s:bean>
			<s:iterator value="counter" >											    	
			     <s:if test="top == currentPage">
			         <span class="current">
			         	<s:property value="currentPage"/>
			         </span> 
			     </s:if>
			     <s:else>
			         <s:url id="url" value="/%{permanentLink.uri}">
			             <s:param name="currentPage" value="top"/>
			             <s:param name="pageSize" value="pageSize"/>
			         </s:url>
			         <s:a cssClass="inactive" href="%{url}"><s:property/></s:a>
			     </s:else>
			 </s:iterator>											
					
			<s:if test="currentPage < pages">	
				<s:url id="url" value="/%{permanentLink.uri}">
			     	<s:param name="pageSize" value="pageSize"/>
			        <s:param name="currentPage" value="%{currentPage + 1}"/>
			     </s:url>
			    <s:a href="%{url}" cssClass="next">Próximo</s:a>
			</s:if>			
		</div>		
	</div>
	<div class="box-300 margin-left-10">
		<p:tile xml="home/revista"/>	
	</div>	
</div>