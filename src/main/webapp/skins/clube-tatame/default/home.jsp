<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>

<p:tile xml="clube/home/destaque"/>
<p:tile xml="clube/home/chamadas"/>

<div class="boxs">
	<div class="club">
		<div class="header">
			<span class="title">O que é o clube?</span>
			<span>É uma área de convivência para quem gosta de luta com diversas vantagens para o associado. Marcas, academias, eventos, praticantes e fãs vão participar, de graça, desse novo conceito de obter informação, descontos, prêmios, conteúdo exclusivo e muito mais.</span>
		</div>
		
		<div class="content">
			<table>
				<tr>
					<td><img src="/skins/clube-tatame/img/icon-weight.png"></td>
					<td class="margin">Tenha acesso a academias em todo o território nacional de várias modalidades</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/icon-shorts.png"></td>
					<td class="margin">Compre com desconto no TATAMEShop e nas lojas parceiras do Clube</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/icon-mmagloves.png"></td>
					<td class="margin">Fique sabendo antes sobre os eventos mais importantes do Brasil</td>
				</tr>
				<tr class="no-border">
					<td><img src="/skins/clube-tatame/img/icon-money.png"></td>
					<td class="margin">Participe de concursos e promoções exclusivas para membros</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="advantages">
		<div class="header">
			<span class="title">vantagens dos membros</span>
			<span>Escolha sua forma de cadastro e faça parte desse grupo especial. Faça seu cadastro de graça ou assine a revista.​</span>
			
			<span class="member-free">membro free</span>
			<span class="member-premium">membro premium</span>
			
			<img class="grey-arrow" src="/skins/clube-tatame/img/grey-arrow.png" />
		</div>
		
		<div class="content">
			<table>
				<tr>
					<td class="member-premium"><img src="/skins/clube-tatame/img/icon-people.png"></td>
					<td class="member-premium margin">Grandes descontos no TATAMEShop e em diversas lojas parceiras</td>
					<td class="member-free"><img src="/skins/clube-tatame/img/icon-medal.png"></td>
					<td class="member-free margin">Desconto em produtos em diversas lojas virtuais</td>
				</tr>
				<tr>
					<td class="member-premium"><img src="/skins/clube-tatame/img/icon-kimono.png"></td>
					<td class="member-premium margin">Concurso cultural valendo uma passagem para o Abu Dhabi Pro</td>
					<td class="member-free"><img src="/skins/clube-tatame/img/icon-trophy.png"></td>
					<td class="member-free margin">Concursos culturais com produtos exclusivos para os membros do Clube</td>
				</tr>
				<tr>
					<td class="member-premium"><img src="/skins/clube-tatame/img/icon-medal.png"></td>
					<td class="member-premium margin">Treine grátis em diversas academias afiliadas ao Clube</td>
					<td class="member-free"><img src="/skins/clube-tatame/img/icon-weight-black.png"></td>
					<td class="member-free margin">Desconto para frequentar academias em todo o território nacional</td>
				</tr>
				<tr class="no-border">
					<td class="member-premium"><img src="/skins/clube-tatame/img/icon-magazine.png"></td>
					<td class="member-premium margin">Receba a revista em casa e tenha muito mais vantagens</td>
					<td class="member-free"><img src="/skins/clube-tatame/img/icon-people.png"></td>
					<td class="member-free margin">Entreviste seu lutador preferido formulando e enviando perguntas</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<div class="guide-title">
	<div class="line"></div>
	<span>Guia de Academias</span>
	<div class="line"></div>
</div>

<section class="guide-gym">
	<span>Quer saber onde encontrar uma academia associada?</span>
	<form action="/clube/academias">
		<input type="text" name="query" placeholder="Estado, Cidade ou Modalidade"/>
		<input type="submit" value="Buscas"/>
	</form>	
</section>

<p:tile xml="clube/home/note"/>

<p:tile xml="clube/home/indica"/>