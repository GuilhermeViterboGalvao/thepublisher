<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="count" value="0"/>

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
				</div>
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
</div>
	