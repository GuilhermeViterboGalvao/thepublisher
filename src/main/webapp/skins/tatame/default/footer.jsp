<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="logo">
	<img alt="tatame_white.png" src="http://cdn-tatame.trrsf.com/skins/tatame/img/tatame_white.png"/>
</div>	
<div class="pages">
	<div class="title">PÁGINAS</div>
	<p>Assinatura</p>
	<p>Fale Conosco</p>
	<p>Contrato de Assinaturas</p>
	<p>Política de Privacidade</p>
</div>
<div class="address">
	<div class="title">EDITORA TATAME</div>
	<p>Av. Marechal Floriano 38/307</p>
	<p>Centro – Rio de Janeiro</p>
	<p>Cep 20080-004</p>
	<p>Tel: +55 (21) 2233-7755 / 2516-8595</p>
</div>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<div id="tatame_1680x912_home"></div>
</s:if>
<s:else>
	<div id="tatame_1680x912_ros"></div>
</s:else>