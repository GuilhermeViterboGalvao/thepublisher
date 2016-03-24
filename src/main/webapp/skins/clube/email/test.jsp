<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<style type="text/css">
	.message { float: left; width: 100%; margin: 5% 0; }
	.message p { float: left; width: 100%; font-size: 1.2em; line-height: 1.4em; color: #000; text-align: center; }
</style>
<s:if test="success">
	<div class="message">
		<p>E-mail enviado com sucesso.</p>
	</div>
</s:if>
<s:else>
	<div class="message">
		<p>Ocorreu um problema ao enviar o e-mail, por favor olhe o log da aplicação para mais informações.</p>
	</div>
</s:else>