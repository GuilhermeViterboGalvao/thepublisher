<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="count" value="0"/>

<div class="boxs">
	<span class="title">EVENTOS</span>

	<s:iterator value="events" status="st">
		<div class="event">
			<img class="logo" src="/img/${photo.id}_220x130.jpg" />
			
			<div class="info">
				<span class="name"><s:property value="name"/></span>
				<span class="description"><s:property value="description"/></span>
			</div>
			<div class="detail">
				<a href="/${permanentLink.uri}">
					<strong>Detalhes e fotos como chegar</strong> (maps)
				</a>
			</div>
		</div>
			
		<s:if test="#count == 0 && #st.count > 2">
			<div class="promo">
				<span>Cadastre seu evento. Clique aqui e faça parte do clube tatame</span>
			</div>
			
			<s:set name="count" value="#count+1"/>
		</s:if>
	</s:iterator>
	
	<div class="page-list">
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
		         <s:url id="url" value="/clube/eventos">
		             <s:param name="currentPage" value="top"/>
		             <s:param name="pageSize" value="pageSize"/>
		         </s:url>
		         <s:a cssClass="inactive" href="%{url}"><s:property/></s:a>
		     </s:else>
		 </s:iterator>													
	</div>	
</div>
	