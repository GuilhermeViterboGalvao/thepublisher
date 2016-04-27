<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="count" value="0"/>

<div class="boxs">
	<img class="gym-header" src="/skins/clube-tatame/img/gym-header.jpg" />
</div>

<div class="boxs">
	<section class="guide-gym">
		<span>Quer saber onde encontrar uma academia associada?</span>
		<form action="/clube/academias">
			<input type="text" name="query" placeholder="Estado, Cidade ou Modalidade"/>
			<input type="submit" value="BUSCAR"/>
		</form>	
	</section>
</div>

<div class="boxs">
	<span class="title">ACADEMIAS PREMIUM</span>

	<s:iterator value="contracts" status="st">
		<s:if test="end.after(CurrentDate) && value > 0">
			<div class="premium">
				<div class="logo">
					<img src="/img/${gym.logo.id}_220x130.jpg" />
				</div>
				<div class="info">
					<span class="name"><s:property value="gym.name"/></span>
					<span class="description"><s:property value="gym.description"/></span>
				</div>
				<div class="detail">
					<a href="/${gym.permanentLink.uri}">
						<strong>Detalhes e fotos como chegar</strong> (maps)
					</a>
					<div class="social-media">
						<s:if test="gym.facebook != null">
							<a class="icon" href="https://www.facebook.com/${gym.facebook}">
								<img src="/skins/clube-tatame/img/icon-facebook.png">
							</a>
						</s:if>
						<s:if test="gym.twitter != null">
							<a class="icon" href="https://twitter.com/${gym.twitter}">
								<img src="/skins/clube-tatame/img/icon-twitter.png">
							</a>
						</s:if>
						<s:if test="gym.instagram != null">
							<a class="icon" href="https://www.instagram.com/${gym.instagram}">
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
	