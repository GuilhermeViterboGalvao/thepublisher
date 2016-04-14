<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header>
	<img class="logo" alt="Logo" src="/skins/clube-tatame/img/logo.jpg"/>
	<section class="menu">
		<form action="/clube/membros/login" method="post">
			<input type="text" name="email" placeholder="Usuário"/>
			<input type="password" name="password" placeholder="Senha"/>
			<input type="submit" value="ENTRAR"/>
		</form>
		<img class="btnFacebook" alt="Login com o Facebook" src="/skins/clube-tatame/img/btnFacebook.png"/>	
	</section>
	<nav>
		<ul>
			<li>
				<a href="#">O CLUBE</a>
			</li>
			<li>
				<a href="#">SEJA MEMBRO</a>
			</li>
			<li>
				<a href="/clube/academias">GUIA DE ACADEMIAS</a>
			</li>
			<li>
				<a href="/clube/parceiros">PARCEIROS</a>
			</li>
			<li>
				<a href="/clube/eventos">EVENTOS</a>
			</li>
			<li>
				<a href="http://www.tatameshop.com.br">LOJA VIRTUAL</a>
			</li>			
		</ul>
	</nav>
</header>