<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="terra-bar">
	<iframe scrolling="no" src="http://s1.trrsf.com/navbar/superslim/index.html?id=1&amp;format=superslim&amp;itemMenu=esp"></iframe>
</div>
<s:if test="model != null && ((model instanceof com.publisher.entity.Page && model.id == 1) || model instanceof br.com.tatame.entity.LiveStats)">
	<div class="ads">
		<jsp:include page="/skins/tatame/default/advertiser-zone1.jsp"/>
	</div>
</s:if>
<s:else>
	<div class="ads">
		<div id="tatame_728x90_ros"></div>
	</div>
</s:else>