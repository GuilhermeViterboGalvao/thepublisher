<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="count" value="0"/>

<div class="boxs">
	<div class="background-gym">
		<img src="/skins/clube-tatame/img/background-gym.jpg">
	</div>
	<div class="country-map">
		<img  src="/skins/clube-tatame/img/country-map.png">
	</div>
</div>

<div class="boxs">
	<span class="title">ACADEMIAS PREMIUM</span>

	<s:iterator value="contracts" status="st">
		<s:if test="end.after(CurrentDate) && value > 0">
			<div class="premium">
				<img class="logo" src="/img/${gym.logo.id}_220x130.jpg" />
				
				<div class="info">
					<span class="name"><s:property value="gym.name"/></span>
					<span class="description"><s:property value="gym.description"/></span>
				</div>
				<div class="detail">
					<a href="/${gym.permanentLink.uri}">
						<strong>Detalhes e fotos como chegar</strong> (maps)
					</a>
					<div class="social-media">
						<s:if test="facebook != null">
							<a class="icon" href="https://www.facebook.com/${facebook}">
								<img src="/skins/clube-tatame/img/icon-facebook.png">
							</a>
						</s:if>
						<s:if test="twitter != null">
							<a class="icon" href="https://twitter.com/${twitter}">
								<img src="/skins/clube-tatame/img/icon-twitter.png">
							</a>
						</s:if>
						<s:if test="instagram != null">
							<a class="icon" href="https://www.instagram.com/${instagram}">
								<img src="/skins/clube-tatame/img/icon-instagram.png">
							</a>
						</s:if>
					</div>
				</div>
				<div class="arrow-black2"><img src="/skins/clube-tatame/img/arrow-black2.png"> </div>
			</div>
		</s:if>
		<s:else>
			<s:if test="#count == 0 && value == 0">
				<div class="promo">
					<span>Indique sua Academia para fazer parte do clube e ganhe uma assinatura VIP</span>
				</div>
				
				<s:set name="count" value="#count+1"/>
			</s:if>
			<div class="free">
				<span class="name"><s:property value="gym.name"/></span>
	
				<span class="location">
					<s:property value="gym.address"/>, <s:property value="gym.city"/> - <s:property value="gym.state"/>
				</span>
				
				<span class="modality">MODALIDADES: <s:property value="gym.modality"/></span>
			</div>
		</s:else>
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
		         <s:url id="url" value="/clube/academias">
		             <s:param name="currentPage" value="top"/>
		             <s:param name="pageSize" value="pageSize"/>
		         </s:url>
		         <s:a cssClass="inactive" href="%{url}"><s:property/></s:a>
		     </s:else>
		 </s:iterator>													
	</div>	
</div>
	