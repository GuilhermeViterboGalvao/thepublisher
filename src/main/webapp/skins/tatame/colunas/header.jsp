<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="terra-bar">
	<iframe scrolling="no" src="http://s1.trrsf.com/navbar/superslim/index.html?id=1&amp;format=superslim&amp;itemMenu=esp"></iframe>
</div>

<div class="ads">
	<div id="tatame_728x90_ros"></div>
</div>
<s:if test="#customHeaderImagePath != null && model != null && model instanceof com.publisher.entity.Article">
	<div class="header-colunas">
		<img class="box-shadow" src="${customHeaderImagePath}">
	</div>
</s:if>
