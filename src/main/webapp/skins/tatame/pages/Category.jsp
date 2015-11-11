<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"			  %>
<div class="inner">
	<div class="box-title bottom20">
		<h2>
			<a href="/<s:property value="permanentLink.uri"/>">
				<b><s:property value="name"/></b>
			</a>			
		</h2>
	</div>
	<div class="home-blog home-small">
		<s:iterator value="getArticles(6)" status="i">		
			<article class="post type-post status-publish format-standard hentry post-item" id="${id}">
				<h2 class="post-title">
					<a href="/${permanentLink.uri}" rel="bookmark" title="${title}">${title}</a>
				</h2>
				<div class="clearfix"></div>
				<div class="post-meta-info">
					<div class="post_meta_author">
						<i class="icon-user"></i>
						<a href="mailto:${createdBy.email}" title="${createdBy.name}" rel="author">${createdBy.name}</a>
					</div>
					<div class="post_meta_date">${createdBy.name}, Rio de Janeiro</div>
					<div class="post_meta_date"><i class='icon-time'></i><s:date name="publishedAt" format="dd/MM/yyyy hh:mm"/></div>
				</div>
				<div class="post-image">
					<a href="/${permanentLink.uri}" title="${title}"> 
						<img class="lazy" data-original="http://cdn-tatame.trrsf.com/img/${photo.id}_270x180.jpg" width="270" height="180" alt="${title}" border="0" />
					</a>
				</div>
				<div class="post-entry">
					<p>${note}</p>
				</div>
				<div class="post-readmore">
					<a href="/${permanentLink.uri}" rel="bookmark" title="${title}"  class="btn btn-small">Leia mais</a>
				</div>
			</article>
		</s:iterator>		
		<div class="pagenavi clear">		
			<s:if test="currentPage != 1"> 
				<s:url id="url" value="/%{permanentLink.uri}">				            
			           <s:param name="pageSize" value="pageSize"/>            
			           <s:param name="currentPage" value="currentPage - 1"/>
				</s:url>
				<s:a href="%{url}" cssClass="pagenavi-prev"><i class="icon-chevron-left"></i> Anterior</s:a>
			</s:if>
			
			<s:bean name="com.publisher.utils.PageList" id="counter">
			    <s:param name="selectedPage" value="currentPage"/>
			    <s:param name="numberOfPages" value="pages"/>
			</s:bean>
			<s:iterator value="counter" >											    	
			     <s:if test="top == currentPage">
			         <span class="pagenavi-current">
			         	<s:property value="currentPage"/>
			         </span> 
			     </s:if>
			     <s:else>
			         <s:url id="url" value="/%{permanentLink.uri}">
			             <s:param name="currentPage" value="top"/>
			             <s:param name="pageSize" value="pageSize"/>
			         </s:url>
			         <s:a cssClass="pagenavi-inactive" href="%{url}"><s:property/></s:a>
			     </s:else>
			 </s:iterator>											
					
			<s:if test="currentPage < pages">	
				<s:url id="url" value="/%{permanentLink.uri}">
			     	<s:param name="pageSize" value="pageSize"/>
			        <s:param name="currentPage" value="%{currentPage + 1}"/>
			     </s:url>
			    <s:a href="%{url}" cssClass="pagenavi-next">Próximo <i class="icon-chevron-right"></i></s:a>
			</s:if>
		</div>
	</div>
</div>