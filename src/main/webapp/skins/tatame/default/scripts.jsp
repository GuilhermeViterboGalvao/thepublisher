<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type='text/javascript' src='/skins/tatame/js/jquery-1.7.2-min.js'></script>
<script type='text/javascript' src='/skins/tatame/js/jquery-lazyload-1.9.2.js'></script>
<script type='text/javascript' src='/skins/tatame/js/nivo-lightbox.js'></script>
<script type='text/javascript'>
	/* <![CDATA[ */
	var rlArgs = {
		"script" : "nivo",
		"selector" : "lightbox",
		"custom_events" : "",
		"activeGalleries" : "1",
		"effect" : "fade",
		"keyboardNav" : "1",
		"errorMessage" : "The requested content cannot be loaded. Please try again later."
	};
	/* ]]> */
</script>
<script type='text/javascript' src='/skins/tatame/js/responsive-lightbox.js'></script>
<script type='text/javascript' src='/skins/tatame/js/jquery-fancybox-pack.js'></script>
<script type='text/javascript' src='/skins/tatame/js/jquery-tools-min.js'></script>
<script type='text/javascript' src='/skins/tatame/js/comment-reply-min.js'></script>
<script type='text/javascript' src='/skins/tatame/js/lastNews.js'></script>
<script type='text/javascript'>
	/* <![CDATA[ */
	var promo_slider_options = { "version" : "3.3.1" };
	/* ]]> */
</script>
<script type='text/javascript' src='/skins/tatame/js/promo-slider.js'></script>
<script type="text/javascript">var bd_url = 'http://www.tatame.com.br/wp-content/themes/manshet';</script>
<!--[if lt IE 9]>
<script src="/skins/tatame/js/html5.js"></script>
<![endif]-->
<s:if test="model instanceof com.publisher.entity.Article">
	<script type='text/javascript' src='/skins/tatame/js/Article.js'></script>
	<s:if test="model instanceof com.publisher.entity.PhotoGallery">
		<script type='text/javascript' src='/skins/tatame/js/PhotoGallery.js'></script>
	</s:if>
</s:if>
<s:elseif test="model instanceof com.publisher.entity.Page">
	<script type='text/javascript' src='/skins/tatame/js/hoverIntent-min.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-cicle-all-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-easing-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-eislidshow-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-fancybox-pack.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-fitvids-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-flexslider-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-lazyload-1.9.2.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-lightbox-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-placeholder-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-widget-min-1.10.3.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-ui-accordion-min-1.10.3.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/jquery-ui-min-1.10.3.js'></script>	
	<script type='text/javascript' src='/skins/tatame/js/manshet-main-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/manshet-validation-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/mordernizr-min-3.8.1.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/soliloquy-lite-1.5.2.js'></script>
	<script type='text/javascript' src='/skins/tatame/js/Home.js'></script>
</s:elseif>
<!-- FB Rmkt – MMA 2x2 -->
<script type="text/javascript">
	(function() {
 		var _fbq = window._fbq || (window._fbq = []);
 		if (!_fbq.loaded) {
   			var fbds = document.createElement('script');
   			fbds.async = true;
   			fbds.src = '//connect.facebook.net/en_US/fbds.js';
   			var s = document.getElementsByTagName('script')[0];
   			s.parentNode.insertBefore(fbds, s);
   			_fbq.loaded = true;
 		}
 		_fbq.push(['addPixelId', '1411343469184877']);
	})();
	window._fbq = window._fbq || [];
	window._fbq.push(['track', 'PixelInitialized', {}]);
</script>
<!-- end FB Rmkt -->
<!-- Facebook Pixel Code - Tatame Geral -->
<script type="text/javascript">
	!function(f,b,e,v,n,t,s){if(f.fbq)return;n=f.fbq=function(){n.callMethod?
	n.callMethod.apply(n,arguments):n.queue.push(arguments)};if(!f._fbq)f._fbq=n;
	n.push=n;n.loaded=!0;n.version='2.0';n.queue=[];t=b.createElement(e);t.async=!0;
	t.src=v;s=b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t,s)}(window,
	document,'script','//connect.facebook.net/en_US/fbevents.js');
	fbq('init', '641946129239872');
	fbq('track', 'PageView');
</script>
<noscript>
	<img height="1" width="1" style="display: none;" src="https://www.facebook.com/tr?id=641946129239872&ev=PageView&noscript=1"/>
</noscript>
<!-- End Facebook Pixel Code -->
<jsp:include page="/skins/tatame/default/google-analytics-script.jsp"/>