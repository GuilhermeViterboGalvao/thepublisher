<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-1.7.2-min.js"></script>
<script type="text/javascript" src="/skins/clube/js/ImageLazyLoad.js"></script>
<script type="text/javascript">ImageLazyLoad.init();</script>
<script type="text/javascript" src="/skins/clube/js/Main.js"></script>
<s:if test="model != null">
	<s:if test="model instanceof com.publisher.entity.Page && model.id == 6">
		<script type="text/javascript" src="/skins/clube/js/Home.js"></script>
	</s:if>
	<s:elseif test="model instanceof com.publisher.entity.Article">
		<script type="text/javascript" src="/skins/clube/js/Article.js"></script>
	</s:elseif>
</s:if>