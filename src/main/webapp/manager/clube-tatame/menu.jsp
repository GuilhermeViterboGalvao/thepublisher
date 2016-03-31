<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="ym-vlist">
	<h6 class="ym-vtitle">Ediar capa do site</h6>
	<ul>
		<li><a href="/manager/clube-tatame/home">Editar</a></li>
	</ul>
	<h6 class="ym-vtitle">Academias</h6>
	<ul>
		<li><a href="/manager/clube-tatame/gym-input">Cadastrar</a></li>
		<li><a href="/manager/clube-tatame/gym-list">Listar</a></li>
		<li><a href="/manager/clube-tatame/contracts_gym-list">Contratos</a></li>
		<li><a href="/manager/clube-tatame/photoGym-list">Fotos</a></li>
	</ul>	
	<h6 class="ym-vtitle">Cupons</h6>
	<ul>
		<li><a href="/manager/clube-tatame/gxml-edit?path=clube/cupom/free-gym">Para academias gratuitas</a></li>
		<li><a href="/manager/clube-tatame/gxml-edit?path=clube/cupom/paying-gym">Para academias pagantes</a></li>
		<li><a href="/manager/clube-tatame/gxml-edit?path=clube/cupom/free-member">Para membros gratuitas</a></li>
		<li><a href="/manager/clube-tatame/gxml-edit?path=clube/cupom/paying-member">Para membros pagantes</a></li>
	</ul>			
	<h6 class="ym-vtitle">Empresas</h6>
	<ul>
		<li><a href="/manager/clube-tatame/company-input">Cadastrar</a></li>
		<li><a href="/manager/clube-tatame/company-list">Listar</a></li>
		<li><a href="/manager/clube-tatame/contracts_company-list">Contratos</a></li>
	</ul>
	<h6 class="ym-vtitle">Eventos</h6>
	<ul>
		<li><a href="/manager/clube-tatame/event-input">Cadastrar</a></li>
		<li><a href="/manager/clube-tatame/event-list">Listar</a></li>
		<li><a href="/manager/clube-tatame/photoEvent-list">Fotos</a></li>
	</ul>
	<h6 class="ym-vtitle">Fotos</h6>
	<ul>
		<li><a href="/manager/clube-tatame/photo-input">Cadastrar</a></li>
		<li><a href="/manager/clube-tatame/photo-list">Listar</a></li>
	</ul>	
	<h6 class="ym-vtitle">Matérias</h6>	
	<ul>
		<li><a href="/manager/clube-tatame/article-input">Cadastrar</a></li>
		<li><a href="/manager/clube-tatame/article-list?cid=37">Listar</a></li>		
	</ul>	
	<h6 class="ym-vtitle">Membros</h6>	
	<ul>
		<li><a href="/manager/clube-tatame/member-list">Listar</a></li>
		<li><a href="/manager/clube-tatame/contracts_member-list">Contratos</a></li>
	</ul>
	<h6 class="ym-vtitle">Produtos</h6>
	<ul>
		<li><a href="/manager/clube-tatame/product-input">Cadastrar</a></li>
		<li><a href="/manager/clube-tatame/product-list">Listar</a></li>
	</ul>
	<h6 class="ym-vtitle">Outros</h6>
	<ul>
		<s:if test="isAdmin()">
			<li><a href="/manager/clube-tatame/indexAllFacade-execute">Indexar entidades do sistema</a></li>
		</s:if>	
		<li><a href="/manager/clube-tatame/logout">Sair</a></li>
	</ul>
</nav>