<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="refresh" content="1200">
<s:if test="title != null || note != null || tags != null">
	<title>${title}</title>	
	<meta name="title"       content="${title}"/>
	<meta name="description" content="${note}" />
	<meta name="keywords"    content="${tags}" lang="pt-BR"/>
</s:if>
<s:else>
	<title>${titlePage}</title>
	<jsp:include page="/skins/tatame/default/meta-tags.jsp"/>
</s:else>
<s:if test="photo != null">
	<meta content="/img/${photo.id}_210x140.jpg" name="og:image" title="${photo.description}"/>	
	<meta property="og:description" content="<s:property value='note'/>"/>
	<meta property="og:title" content="<s:property value='title'/>" />
	<meta property="og:image" content="http://cdn-tatame.trrsf.com/img/<s:property value='photo.id'/>_670x418.jpg"/>
	<meta property="og:url" content="http://www.tatame.com.br/<s:property value='permanentLink.url'/>"/>		
	<link href="/img/${photo.id}_210x140.jpg" rel="image_src" title="${photo.description}"/>
</s:if>
<link rel="shortcut icon" href="http://cdn-tatame.trrsf.com/skins/tatame/img/favicon_16x16-ok.png" type="image/x-icon"/>
<link rel="apple-touch-icon-precomposed" href="http://cdn-tatame.trrsf.com/skins/tatame/img/favicon_57x57-ok.png"/>
<link rel='stylesheet' id='responsive-lightbox-nivo-front-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/nivo-lightbox.css' type='text/css' media='all'/>
<link rel='stylesheet' id='responsive-lightbox-nivo-front-template-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/responsive-lightbox-default.css' type='text/css' media='all'/>
<link rel='stylesheet' id='fancybox-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/jquery-fancybox.css' type='text/css' media='all'/>
<link rel='stylesheet' id='fancybox-thumbs-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/jquery-fancybox-thumbs.css' type='text/css' media='all' />
<link rel='stylesheet' id='manshet-css'  href='/skins/tatame/css/manshet-min-cdn.css' type='text/css' media='all' />
<link rel='stylesheet' id='responsive-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/manshet-responsive-min.css?3' type='text/css' media='all' />
<link rel='stylesheet' id='OpenSans-css'  href='http://fonts.googleapis.com/css?family=Open+Sans%3A400%2C700%2C600%27+rel%3D%27stylesheet%27+type%3D%27text%2Fcss&#038;ver=3.8.1' type='text/css' media='all' />
<link rel='stylesheet' id='Oswald-css'  href='http://fonts.googleapis.com/css?family=Oswald%3A400%2C700&#038;ver=3.8.1#038&#039;rel=&#039;stylesheet&#039;type=&#039;text/css' type='text/css' media='all' />
<link rel='stylesheet' id='promoslider_main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/slide.css' type='text/css' media='all' />
<link rel='stylesheet' id='fontello-css'  href='/skins/tatame/css/fontello.css' type='text/css' media='all' />
<link rel='stylesheet' id='main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/main.css' type='text/css' media='all' />
<style type="text/css"> 
	.wp-polls 
	.pollbar { margin: 1px; font-size: 8px; line-height: 10px; height: 10px; background: #900; border: 1px solid #333; } 
</style>
<style type="text/css">
	.cabeceira { width: 1020px; height: 82px; background: #F90; margin: 0 auto; }
	.cabeceira iframe { margin: 0 auto; display: block; }
</style>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-1.7.2-min.js'></script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-lazyload-1.9.2.js'></script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/nivo-lightbox.js'></script>
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
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/responsive-lightbox.js'></script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-fancybox-pack.js'></script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-tools-min.js'></script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/comment-reply-min.js'></script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/lastNews.js'></script>
<script type='text/javascript'>
	/* <![CDATA[ */
	var promo_slider_options = { "version" : "3.3.1" };
	/* ]]> */
</script>
<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/promo-slider.js'></script>
<script type="text/javascript">var bd_url = 'http://www.tatame.com.br/wp-content/themes/manshet';</script>
<!--[if lt IE 9]>
<script src="http://cdn-tatame.trrsf.com/skins/tatame/js/html5.js"></script>
<![endif]-->
<s:if test="model instanceof com.system.entity.Article">
	<link rel='stylesheet' id='article-main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/ArticleMain.css' type='text/css' media='all' />
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/Article.js'></script>
	<s:if test="model instanceof com.system.entity.DefaultArticle">
		<link rel='stylesheet' id='article-main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/PhotoGallery.css' type='text/css' media='all' />
		<link rel='stylesheet' id='article-main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/Video.css' type='text/css' media='all' />
		<style type="text/css">
			article.post div#video, article.post .video { height: auto; }
		</style>
		<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/PhotoGallery.js'></script>
	</s:if>
	<s:elseif test="model instanceof com.system.entity.PhotoGallery">
		<link rel='stylesheet' id='article-main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/PhotoGallery.css' type='text/css' media='all' />
		<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/PhotoGallery.js'></script>
	</s:elseif>
	<s:elseif test="model instanceof com.system.entity.Video">
		<link rel='stylesheet' id='article-main-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/Video.css' type='text/css' media='all' />
	</s:elseif>
</s:if>
<s:if test="model instanceof com.system.entity.Page">
	<link rel='stylesheet' id='home-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/Home.css' type='text/css' media='all' />
	<link rel='stylesheet' id='soliloquy-css'  href='http://cdn-tatame.trrsf.com/skins/tatame/css/soliloquy.css' type='text/css' media='all' />	
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/hoverIntent-min.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-cicle-all-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-easing-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-eislidshow-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-fancybox-pack.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-fitvids-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-flexslider-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-lazyload-1.9.2.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-lightbox-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-placeholder-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-widget-min-1.10.3.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-ui-accordion-min-1.10.3.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/jquery-ui-min-1.10.3.js'></script>	
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/manshet-main-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/manshet-validation-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/mordernizr-min-3.8.1.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/soliloquy-lite-1.5.2.js'></script>
	<script type='text/javascript' src='http://cdn-tatame.trrsf.com/skins/tatame/js/Home.js'></script>
</s:if>
<jsp:include page="/skins/tatame/default/terra-counter-head.jsp"/>
<s:if test="#dfpPathJSP != null">
	<s:include value="%{dfpPathJSP}"/>
</s:if>
<s:if test="#tgmKey != null">
	<script type="text/javascript">var tgmKey = '${tgmKey}';</script>
	<script type="text/javascript" src="http://stf.terra.com/tagman/js/tagman.js"></script>
</s:if>
<script type="text/javascript">tgm.ShowArea('default');</script>
<script type="text/javascript">terra_stats_metrics();</script>
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