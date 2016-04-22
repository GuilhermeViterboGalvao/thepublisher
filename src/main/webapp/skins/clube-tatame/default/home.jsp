<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<p:tile xml="clube/home/destaque"/>
<p:tile xml="clube/home/chamadas"/>
<section class="guide-gym">
	<span>Quer saber onde encontrar uma academia associada?</span>
	<form action="/clube/academias">
		<input type="text" name="query" placeholder="Estado, Cidade ou Modalidade"/>
		<input type="submit" value="Buscas"/>
	</form>	
</section>

<div class="boxs">
	<div class="club">
		<div class="header">
			<span class="title">O que é o clube?</span>
			<span>É uma área de convivência para quem gosta de luta com diversas vantagens para o associado. Marcas, academias, eventos, entidades, praticantes e aficionados vão participar, de graça, desse novo conceito de obter informação, descontos, prêmios, conteúdo exclusivo etc.</span>
		</div>
		
		<div class="content">
			<table>
				<tr>
					<td><img src="/skins/clube-tatame/img/oc-c1.png"></td>
					<td>- Ganhe desconto para frequentar academias por todo o território nacional</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/oc-c2.png"></td>
					<td>- Compre com desconto em diversas lojas virtuais do segmento</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/oc-c3.png"></td>
					<td>- Receba avisos da agenda atualizada dos eventos nacionais</td>
				</tr>
				<tr class="no-border">
					<td><img src="/skins/clube-tatame/img/oc-c4.png"></td>
					<td>- Assine a revista TATAME com brindes</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="advantages">
		<div class="header">
			<span class="title">vantagens dos membros</span>
			<span>América Latina seu pioneirismo e imparcialidade fazem dela um produto sem igual é mundialmente respeitada e consino mercado.​</span>
			
			<span class="member-free">membro free</span>
			<span class="member-premium">membro premium</span>
			
			<img class="grey-arrow" src="/skins/clube-tatame/img/grey-arrow.png" />
		</div>
		
		<div class="content">
			<table>
				<tr>
					<td><img src="/skins/clube-tatame/img/vm-c1.png"></td>
					<td class="member-premium">- Ganhe desconto para frequentar academias por todo o território nacional</td>
					<td class="member-free">- Ganhe desconto para frequentar academias por todo o território nacional 2</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/vm-c2.png"></td>
					<td class="member-premium">- Compre com desconto em diversas lojas virtuais do segmento</td>
					<td class="member-free">- Compre com desconto em diversas lojas virtuais do segmento 2</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/vm-c3.png"></td>
					<td class="member-premium">- Receba avisos da agenda atualizada dos eventos nacionais</td>
					<td class="member-free">- Receba avisos da agenda atualizada dos eventos nacionais 2</td>
				</tr>
				<tr class="no-border">
					<td><img src="/skins/clube-tatame/img/vm-c4.png"></td>
					<td class="member-premium">- Assine a revista TATAME com brindes</td>
					<td class="member-free">- Assine a revista TATAME com brindes 2</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<p:tile xml="clube/home/indica"/>