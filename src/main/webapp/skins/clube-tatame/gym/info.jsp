<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="validContracts.size > 0">
	<div class="boxs">
		<div class="info">
			<span class="name">
				<s:property value="name"/>
			</span>
	   		<span class="description">
	   			<s:property value="description"/>
	   		</span>
	   		<span class="site">
	   			<a href="${site}" target="_blank">
	   				<s:property value="site"/>
	   			</a>
	   		</span>
   		</div>
   		<div class="gallery">
	   		<s:if test="photos != null && photos.size() > 0">
			    <div class="bigphotoholder">
			        <div class="holder">
			        	<img class="bigphoto" src="/img/<s:property value="photos[0].photo.id"/>_459x290.jpg" alt="<s:property value="photos[0].description"/>"/>
			        </div>
			    </div>	    
			    <div class="bigphotoholder-thumbs">		    	
			        <div class="left">		        
			        	<a href="javascript:void(0);">
			        		<img src="/skins/tatame/img/arrow-left.png" alt="esquerda"/>
			        	</a>
			        </div>
			        <div class="right">
			        	<a href="javascript:void(0);">
			        		<img src="/skins/tatame/img/arrow-right.png" alt="direita"/>
			        	</a>
			        </div>
			        <div class="photoholder">
			            <s:iterator value="photos" status="st">
			                <a class="<s:if test="#st.count>3">hide</s:if><s:else>show</s:else>" id="<s:property value="photo.id"/>_459x290" href="javascript:void(0);">
			                	<img class="<s:if test="#st.count==0 || #st.count%3==1">no-margin</s:if>" src="/img/<s:property value="photo.id"/>_151x114.jpg" alt="<s:property value="description"/>"/>
			                </a>
			            </s:iterator>
			        </div>
			    </div>		    		
	   		</s:if>
   		</div>
   	</div>
   	
   	<div class="boxs border-top">
   		<div class="sub-info">
   			<s:if test="operation != null && !operation.isEmpty()">
	   			<div class="operation">
	   				<span class="red">FUNCIONAMENTO:</span>
	   				<span><s:property value="operation"/></span>
	   			</div>
   			</s:if>
   			<s:if test="modality != null && !modality.isEmpty()">
	   			<div class="modality">
	   				<span class="red">MODALIDADES:</span>
	   				<span><s:property value="modality"/></span>
	   			</div>
   			</s:if>
   			<s:if test="address != null && !address.isEmpty()">
	   			<div class="address">
	   				<span class="red">LOCAL:</span>
	   				<span><s:property value="address"/></span>
	   			</div>
   			</s:if>
   		</div>
   	</div>
   	
   	<div class="boxs">
   		<s:hidden id="name" name="name"/>
   		<s:hidden id="lat" name="lat"/>
		<s:hidden id="lon" name="lon"/>
   		<div class="map">
   			<span class="map-title">Como chegar</span>
   			<div id="google-maps-canvas"></div>
   		</div>
 
		<s:if test="facebook != null && !facebook.isEmpty()">
			<div class="facebook">
				<div id="fb-root"></div>
				<script type="text/javascript">
					(function(d, s, id) {
				  		var js, fjs = d.getElementsByTagName(s)[0];
				  		if (d.getElementById(id)) return;
				  		js = d.createElement(s); 
				  		js.id = id;
				  		js.src = "//connect.facebook.net/pt_BR/sdk.js#xfbml=1&version=v2.5&appId=312287075567167";
				  		fjs.parentNode.insertBefore(js, fjs);
					}(document, "script", "facebook-jssdk"));
				</script>	
				<div 
					class="fb-page" 
					data-href="${facebook}" 
					data-tabs="timeline" 
					data-width="432" 
					data-height="369" 
					data-small-header="false" 
					data-adapt-container-width="true" 
					data-hide-cover="false" 
					data-show-facepile="true">
				</div>
			</div>	
		</s:if>	
	</div>
</s:if>