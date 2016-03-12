<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<p:tile xml="clube/home/destaques"/>
<p:tile xml="clube/home/chamadas"/>
<section class="guide-gym">
	<span>Quer saber onde encontrar uma academia assiciada?</span>
	<input type="text" name="query" placeholder="Estado, Cidade ou Modalidade"/>
	<input type="submit" value="Buscas"/>	
</section>
<p:tile xml="clube/home/indica"/>