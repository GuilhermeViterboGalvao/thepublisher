<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>		
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/core/base.min.css" />
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/forms/gray-theme.css" />
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/screen/typography.css" />
		<link rel="stylesheet" type="text/css" href="/system/publisher/css/default.css" />
		<style type="text/css">.login { top: 50%; left: 50%; margin-top: -135px; margin-left: -215px; height: 270px; width: 430px; position: absolute; }</style>
	</head>
	<body>
		<s:form action="login" namespace="/publisher" cssClass="ym-form ym-g25 login" >
			<h2 style="padding-left: 10px;">Identifique-se</h2>
			<div class="ym-fbox-text" >
				<label for="email">Email</label>	    
				<s:textfield name="email"/>	    
				<label for="password">Senha</label>
				<s:password name="password"/>			
				<div class="ym-fbox-button">
					<s:submit value="Enviar" cssClass="ym-button"/>
				</div>    	
			</div>
		</s:form>
	</body>
</html>