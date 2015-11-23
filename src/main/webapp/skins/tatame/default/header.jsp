<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="terra-bar">
	<iframe scrolling="no" src="http://s1.trrsf.com/navbar/superslim/index.html?id=1&amp;format=superslim&amp;itemMenu=esp"></iframe>
</div>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<div class="ads">
		<div id="TerraAdvertising" data-keyvalues="" data-clicktag=""></div>
		<script type="text/javascript" src="http://p2.trrsf.com/tagmanfe/ShowArea.aspx?key=br.cobranded_tatame.home.master1&direct=1"></script>
	</div>
</s:if>
<s:else>
	<div class="ads">
		<div id="tatame_728x90_ros"></div>
	</div>
</s:else>