<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/skins/tatame/default/head.jsp"/>
	</head>
	<body class="${bodyClasses}">
		<div class="cabeceira">
			<s:if test="#headerPathJSP != null"> 
				<s:include value="%{headerPathJSP}"/>
			</s:if>
		</div>
		<div id="fb-root"></div>
		<script type="text/javascript">
			window.fbAsyncInit = function() {
				FB.init({
	  				appId  : "302747973157904",// APP ID
	  				status : true,// check login status
	  				cookie : true,// enable cookies to allow the server to access the session
	  				xfbml  : true//parse XFBML
				});
			};
		</script>
		<div class="boxed" id="wrapper">
			<div class="header-v1">
				<s:if test="#menuPathJSP != null"> 
					<s:include value="%{menuPathJSP}"/>
				</s:if>			
			</div>
			<br class="clear" />
			<div id="main" class="container">
				<div>
					<div class="content-wrapper">
						<tiles:insertAttribute name="content"/>
					</div>
					<div class="sidebar">
						<s:if test="#sidePathJSP != null"> 
							<s:include value="%{sidePathJSP}"/>
						</s:if>
					</div>
				</div>
				<div class="clear"></div>	
			</div>
			<div class="clear"></div>
			<jsp:include page="/skins/tatame/default/footer.jsp"/>
		</div>			
	</body>
</html>