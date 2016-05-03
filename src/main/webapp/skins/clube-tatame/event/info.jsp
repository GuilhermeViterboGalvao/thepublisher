<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="boxs">
	<div class="info">
		<span class="name">
			<s:property value="name"/>
		</span>
   		<div class="end">
  			<span class="black">DATA:</span>
  			<span><s:date name="end" format="dd/MM/yyyy"/></span>
  		</div>
   		<div class="address">
  			<span class="black">LOCAL:</span>
  			<span><s:property value="address"/></span>
  		</div>
  		<span class="description">
   			<s:property value="description"/>
   		</span>
   		
   		<s:hidden id="name" name="name"/>
	  	<s:hidden id="lat" name="lat"/>
		<s:hidden id="lon" name="lon"/>
	  	<div class="map">
	  	<span class="map-title">Como chegar</span>
	  		<div id="google-maps-canvas"></div>
	  	</div>
  	</div>
   	<div class="sub-info">
   		<img class="photo" src="/img/<s:property value="photo.id"/>_360x504.jpg" alt="<s:property value="photo.description"/>"/>
   		
   		<div class="detail">
   			<span class="start">
   				INSCRIÇÕES ATÉ: <s:date name="deadline" format="dd/MM/yyyy"/>
   			</span>
   			<span class="link">
   				Para fazer sua inscrição <a href="/${link}">clique aqui</a> 
   			</span>
   		</div>
   	</div>
 </div>


