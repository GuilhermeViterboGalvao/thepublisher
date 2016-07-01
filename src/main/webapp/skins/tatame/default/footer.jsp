<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="logo">
	<img alt="tatame_white.png" src="http://cdn-tatame.trrsf.com/skins/tatame/img/tatame_white.png"/>
</div>	
<div class="pages">
	<div class="title">PÁGINAS</div>
	<p><a href="http://www.tatameshop.com.br">Assinatura</a></p>
	<p><a href="/tatame/contato/info">Fale Conosco</a></p>
	<p><a href="/tatame/assinaturas/info">Contrato de Assinaturas</a></p>
	<p><a href="/tatame/politica-privacidade-/info">Política de Privacidade</a></p>
</div>
<div class="address">
	<div class="title">EDITORA TATAME</div>
	<p>Av. Almirante Barroso, 6 sala 1004</p>
	<p>Centro – Rio de Janeiro</p>
	<p>Cep 20031-000</p>
	<p>Tel: +55 (21) 2233-7755 ou 2516-8595</p>
</div>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<div id="tatame_1680x912_home"></div>
</s:if>
<s:else>
	<div id="tatame_1680x912_ros"></div>
</s:else>

<div id="tatame_codigo_ros"></div>