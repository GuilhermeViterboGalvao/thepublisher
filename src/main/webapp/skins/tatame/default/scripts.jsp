<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">terra_stats_metrics();</script>
<script type="text/javascript" src="/skins/tatame/js/jquery-1.7.2-min.js"></script>
<script type="text/javascript" src="/frameworks/jquery/plugins/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/skins/tatame/js/main.js?2"></script>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<script type="text/javascript" src="/skins/tatame/js/Home.js?15"></script>
</s:if>
<s:elseif test="model != null && model instanceof com.publisher.entity.Article">
	<script type="text/javascript" src="/skins/tatame/js/Article.js"></script>
	<s:if test="model instanceof com.publisher.entity.PhotoGallery">
		<script type="text/javascript" src="/skins/tatame/js/PhotoGallery.js"></script>
	</s:if>
</s:elseif>
<s:elseif test="model != null && model instanceof br.com.tatame.entity.LiveStats">
	<script type="text/javascript" src="/skins/tatame/js/LiveStats.js"></script>
	<script type="text/javascript">
		window.twttr = (function(d, s, id) {
  			var js, fjs = d.getElementsByTagName(s)[0],
    		t = window.twttr || {};
  			if (d.getElementById(id)) return t;
  			js = d.createElement(s);
  			js.id = id;
  			js.src = "https://platform.twitter.com/widgets.js";
  			fjs.parentNode.insertBefore(js, fjs);
  			t._e = [];
  			t.ready = function(f) {
				t._e.push(f);
  			};
  		return t;
		}(document, "script", "twitter-wjs"));
	</script>	
</s:elseif>
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

<!-- clever-advertising -->
<script data-cfasync="false" type="text/javascript">
	(function (document, window) {
		var c = document.createElement("script");
		c.type = "text/javascript";
		c.async = !0;
		c.src = "//clevernt.com/scripts/950ea1a08a58973364012f75afc20a7c.min.js?20170309=" + Math.floor((new Date).getTime());
		var a = !1;
		try {
		    a = parent.document.getElementsByTagName("script")[0] || document.getElementsByTagName("script")[0];
		} catch (e) {
		    a = !1;
		}
		a || ( a = document.getElementsByTagName("head")[0] || document.getElementsByTagName("body")[0]);
		a.parentNode.insertBefore(c, a);
	})(document, window);
</script>
<!-- clever-advertising -->

<jsp:include page="/skins/tatame/default/google-analytics-script.jsp"/>