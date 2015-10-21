<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-vlist">
	<h6 class="ym-vtitle">Matérias</h6>
	<ul>
		<li><a href="/manager/article-edit">Matéria padrão</a></li>
		<li><a href="/manager/article-edit?type=gallery">Galeria de fotos</a></li>
		<li><a href="/manager/article-list">Listar</a></li>
	</ul>
	<h6 class="ym-vtitle">Usuários</h6>
	<ul>
		<li><a href="/manager/account-edit">Cadastrar</a></li>
		<li><a href="/manager/account-list">Listar</a></li>
	</ul>	
	<h6 class="ym-vtitle">Outros</h6>
	<ul>
		<li><a href="/manager/logout">Sair</a></li>
	</ul>	
</nav>