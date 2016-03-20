<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-vlist">
	<h6 class="ym-vtitle">Ediar capa do site</h6>
	<ul>
		<li><a href="/manager/home">Editar</a></li>
	</ul>
	<h6 class="ym-vtitle">Matérias</h6>
	<ul>
		<li><a href="/manager/article-edit">Matéria padrão</a></li>
		<li><a href="/manager/article-edit?type=gallery">Galeria de fotos</a></li>
		<li><a href="/manager/article-list">Listar</a></li>
	</ul>
	<h6 class="ym-vtitle">Fotos</h6>
	<ul>
		<li><a href="/manager/photo-edit">Nova foto</a></li>
		<li><a href="/manager/photomulti-add">Enviar várias fotos</a></li>
		<li><a href="/manager/photo-list">Listar</a></li>
	</ul>
	<h6 class="ym-vtitle">Categoria</h6>
	<ul>
		<s:if test="isAdmin()">
			<li><a href="/manager/category-edit">Nova categoria</a></li>
		</s:if>		
		<li><a href="/manager/category-list">Listar</a></li>
	</ul>
	<h6 class="ym-vtitle">Template</h6>
	<ul>
		<s:if test="isAdmin()">
			<li><a href="/manager/skin-edit">Nova template</a></li>
		</s:if>
		<li><a href="/manager/skin-list">Listar</a></li>
	</ul>
	<h6 class="ym-vtitle">Outras capas</h6>
	<ul>
		<s:if test="isAdmin()">
			<li><a href="/manager/page-edit">Nova capa</a></li>
		</s:if>
		<li><a href="/manager/page-list">Listar</a></li>
	</ul>	
	<h6 class="ym-vtitle">Usuários</h6>
	<ul>
		<s:if test="isAdmin()">
			<li><a href="/manager/account-edit">Cadastrar</a></li>
		</s:if>
		<li><a href="/manager/account-list">Listar</a></li>
	</ul>	
	<h6 class="ym-vtitle">Outros</h6>
	<ul>
		<s:if test="isAdmin()">
			<li><a href="/manager/indexAllFacade-execute">Indexar entidades do sistema</a></li>
		</s:if>
		<li><a href="/manager/logout">Sair</a></li>
	</ul>	
</nav>