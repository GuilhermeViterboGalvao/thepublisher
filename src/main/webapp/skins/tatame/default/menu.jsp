<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="menu box-shadow">
	<a href="/" class="logo">
		<img alt="Tatame" src="http://cdn-tatame.trrsf.com/skins/tatame/img/tatame_white.png">
	</a>
	<ul class="categories">
		<li>
			<a href="/tatame/noticias">NOTÍCIAS</a>
			<ul>
				<li>
					<a href="/search?query=mma">MMA</a>
				</li>
				<li>
					<a href="/tatame/jiujitsu">JIU-JITSU</a>
				</li>			
			</ul>
		</li>					
	</ul>
	<a href="#" class="diary">AGENDA</a>
	<a href="#" class="columns">COLUNAS</a>
	<a href="/tatame/videos" class="videos">VÍDEOS</a>
	<a href="http://www.tatameshop.com.br/" target="_blank" class="tatameshop">TATAMESHOP</a>
	<a href="http://www.tatameshop.com.br/tatame-f65/" target="_blank" class="subscribe">ASSINE</a>
	<div class="search">
		<form action="/search" method="get">
			<input type="text" name="query" placeholder="Buscar...">
		</form>
	</div>
</div>
