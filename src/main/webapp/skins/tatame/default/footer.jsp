<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="logo">
	<img alt="tatame_white.png" src="/skins/tatame/img/tatame_white.png"/>
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
	<p>Rua do Carmo, 57 - 10&#186; andar</p>
	<p>Centro - Rio de Janeiro - RJ</p>
	<p>Cep 20011-020</p>
	<p>Tel: +55 (21) 3129-8031</p>
</div>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<div id="tatame_1680x912_home"></div>
</s:if>
<s:else>
	<div id="tatame_1680x912_ros"></div>
</s:else>

<div id="tatame_codigo_ros"></div>