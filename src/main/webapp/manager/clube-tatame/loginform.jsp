<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>		
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/core/base.min.css" />
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/forms/gray-theme.css" />
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/screen/typography.css" />
		<link rel="stylesheet" type="text/css" href="/manager/css/default.css" />
		<style type="text/css">
			.login { top: 50%; left: 50%; margin-top: -135px; margin-left: -215px; height: 270px; width: 430px; position: absolute; }
			.login > h2 { padding-left: 10px; }
		</style>
	</head>
	<body>
		<form action="/manager/clube-tatame/login" method="post" class="ym-form ym-g25 login">
			<h2>Identifique-se</h2>
			<div class="ym-fbox-text" >
				<label for="email">Email</label>	    
				<input type="email" name="email"/>	    
				<label for="password">Senha</label>
				<input type="password" name="password"/>						
				<div class="ym-fbox-button">
					<input type="submit" value="Enviar" class="ym-button"/>
				</div>    	
			</div>		
		</form>
	</body>
</html>