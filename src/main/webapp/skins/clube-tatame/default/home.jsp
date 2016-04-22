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
		</div>
		
		<div class="content">
			<table>
				<tr>
					<td><img src="/skins/clube-tatame/img/vm-c1.png"></td>
					<td>- Ganhe desconto para frequentar academias por todo o território nacional</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/vm-c2.png"></td>
					<td>- Compre com desconto em diversas lojas virtuais do segmento</td>
				</tr>
				<tr>
					<td><img src="/skins/clube-tatame/img/vm-c3.png"></td>
					<td>- Receba avisos da agenda atualizada dos eventos nacionais</td>
				</tr>
				<tr class="no-border">
					<td><img src="/skins/clube-tatame/img/vm-c4.png"></td>
					<td>- Assine a revista TATAME com brindes</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<p:tile xml="clube/home/indica"/>