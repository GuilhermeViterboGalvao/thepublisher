<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%-- <script type="text/javascript" src="http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-1.7.2-min.js"></script> --%>
<script type="text/javascript" src="/skins/tatame/js/jquery-1.7.2-min.js"></script>
<script type="text/javascript" src="/skins/clube-tatame/js/ImageLazyLoad.js"></script>
<script type="text/javascript">ImageLazyLoad.init();</script>
<script type="text/javascript" src="/skins/clube-tatame/js/Main.js"></script>
<s:if test="model != null">
	<s:if test="model instanceof com.publisher.entity.Page && model.id == 6">
		<script type="text/javascript" src="/skins/clube-tatame/js/Home.js"></script>
	</s:if>
	<s:elseif test="model instanceof com.publisher.entity.Article">
		<script type="text/javascript" src="/skins/clube-tatame/js/Article.js"></script>
	</s:elseif>
	<s:elseif  test="model instanceof br.com.clubetatame.entity.Gym">
		<script type="text/javascript" src="/skins/clube-tatame/js/maps.googleapis.js"></script>
		<script type="text/javascript" src="/skins/clube-tatame/js/Gym.js"></script>
	</s:elseif>
	<s:elseif test="model instanceof br.com.clubetatame.entity.Member">
		<script type="text/javascript" src="/frameworks/jquery/plugins/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/skins/clube-tatame/js/Member.js"></script>
	</s:elseif>
	<s:elseif  test="model instanceof br.com.clubetatame.entity.Event">
		<script type="text/javascript" src="/skins/clube-tatame/js/maps.googleapis.js"></script>
		<script type="text/javascript" src="/skins/clube-tatame/js/Event.js"></script>
	</s:elseif>
</s:if>