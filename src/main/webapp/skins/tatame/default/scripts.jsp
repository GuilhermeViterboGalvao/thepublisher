<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">terra_stats_metrics();</script>
<script type="text/javascript" src="http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-1.7.2-min.js"></script>
<script type="text/javascript" src="/frameworks/jquery/plugins/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/skins/tatame/js/main.js?1"></script>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<script type="text/javascript" src="/skins/tatame/js/Home.js?15"></script>
</s:if>
<s:if test="model != null && model instanceof com.publisher.entity.Article">
	<script type="text/javascript" src="/skins/tatame/js/Article.js"></script>
	<s:if test="model instanceof com.publisher.entity.PhotoGallery">
		<script type="text/javascript" src="/skins/tatame/js/PhotoGallery.js"></script>
	</s:if>
</s:if>
<!-- FB Rmkt – MMA 2x2 -->
<script type="text/javascript">
	(function() {
 		var _fbq = window._fbq || (window._fbq = []);
 		if (!_fbq.loaded) {
   			var fbds = document.createElement("script");
   			fbds.async = true;
   			fbds.src = "//connect.facebook.net/en_US/fbds.js";
   			var s = document.getElementsByTagName("script")[0];
   			s.parentNode.insertBefore(fbds, s);
   			_fbq.loaded = true;
 		}
 		_fbq.push(["addPixelId", "1411343469184877"]);
	})();
	window._fbq = window._fbq || [];
	window._fbq.push(["track", "PixelInitialized", {}]);
</script>
<!-- end FB Rmkt -->
<!-- Facebook Pixel Code - Tatame Geral -->
<script type="text/javascript">
	!function(f,b,e,v,n,t,s){if(f.fbq)return;n=f.fbq=function(){n.callMethod?
	n.callMethod.apply(n,arguments):n.queue.push(arguments)};if(!f._fbq)f._fbq=n;
	n.push=n;n.loaded=!0;n.version="2.0";n.queue=[];t=b.createElement(e);t.async=!0;
	t.src=v;s=b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t,s)}(window,
	document,"script","//connect.facebook.net/en_US/fbevents.js");
	fbq("init", "641946129239872");
	fbq("track", "PageView");
</script>
<noscript>
	<img height="1" width="1" style="display: none;" src="https://www.facebook.com/tr?id=641946129239872&ev=PageView&noscript=1"/>
</noscript>
<!-- End Facebook Pixel Code -->
<jsp:include page="/skins/tatame/default/google-analytics-script.jsp"/>