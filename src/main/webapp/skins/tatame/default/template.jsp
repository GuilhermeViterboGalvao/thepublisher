<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/skins/tatame/default/head.jsp"/>
	</head>
	<body>
		<jsp:include page="/skins/tatame/default/terra-counter-head.jsp"/>
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
		<s:if test="#headerPathJSP != null"> 
			<s:include value="%{headerPathJSP}"/>
		</s:if>	
		<div class="header">
			<s:include value="/skins/tatame/default/header.jsp"/>
			<s:include value="/skins/tatame/default/menu.jsp"/>
		</div>
		<div class="content">
			<div class="page">
				<tiles:insertAttribute name="content"/>
			</div>			
		</div>
		<div class="footer">
			<s:include value="/skins/tatame/default/footer.jsp"/>
		</div>
		<s:include value="/skins/tatame/default/scripts.jsp"/>							
		<s:if test="#dfpPathJSP != null">
			<s:include value="%{dfpPathJSP}"/>
		</s:if>		
	</body>
</html>