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
				<li>
					<a href="/tatame/jiu-jitsu/pan-2016">PAN JJ 2016</a>
				</li>
				<li>
					<a href="/tatame/jiu-jitsu/abu-dhabi-2016">Abu Dhabi Pro</a>
				</li>			
			</ul>
		</li>					
	</ul>
	<a href="/tatame/agenda" class="diary">AGENDA</a>
	<a href="/tatame/colunas" class="columns">COLUNAS</a>
	<a href="/tatame/videos" class="videos">VÍDEOS</a>
	<a href="http://www.tatameshop.com.br/" target="_blank" class="subscribe">ASSINE</a>
	<div class="search">
		<form action="/search" method="get">
			<input type="text" name="query" placeholder="Buscar...">
		</form>
	</div>
</div>