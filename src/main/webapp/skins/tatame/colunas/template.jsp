<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/skins/tatame/default/head.jsp"/>
		<s:if test="#customHeadPathJSP != null">
			<s:include value="%{customHeadPathJSP}"/>
		</s:if>		
	</head>
	<body>
		<script type="text/javascript">var windowWidth = screen.width || document.body.offsetWidth;</script>	
		<div class="page">
			<div id="fb-root"></div>
			<script type="text/javascript">
				(function(d, s, id) {
				  var js, fjs = d.getElementsByTagName(s)[0];
				  if (d.getElementById(id)) {
					  return;
				  }
				  js = d.createElement(s); 
				  js.id = id;
				  js.src = "//connect.facebook.net/pt_BR/sdk.js#xfbml=1&version=v2.5&appId=312287075567167";
				  fjs.parentNode.insertBefore(js, fjs);
				}(document, "script", "facebook-jssdk"));
			</script>
			<div class="header">
				<s:if test="#headerPathJSP != null">
					<s:include value="%{headerPathJSP}"/>
				</s:if>
				<s:if test="#menuPathJSP != null">
					<s:include value="%{menuPathJSP}"/>
				</s:if>
				<s:if test="#customHeaderImagePath != null && model != null && model instanceof com.publisher.entity.Article">
					<div class="header-colunas"><img src="${customHeaderImagePath}"></div>
				</s:if>
			</div>
			<div class="content">
				<tiles:insertAttribute name="content"/>
			</div>
			<div class="footer box-shadow">
				<s:if test="#footerPathJSP != null">
					<s:include value="%{footerPathJSP}"/>
				</s:if>
			</div>
			<s:if test="#scriptsPathJSP != null">
				<s:include value="%{scriptsPathJSP}"/>
			</s:if>							
			<s:if test="#dfpPathJSP != null">
				<s:include value="%{dfpPathJSP}"/>
			</s:if>		
		</div>		
	</body>
</html>