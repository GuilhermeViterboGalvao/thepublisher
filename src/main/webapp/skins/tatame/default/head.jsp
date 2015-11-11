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
	<meta content="http://cdn-tatame.trrsf.com/img/${photo.id}_210x140.jpg" name="og:image" title="${photo.description}"/>	
	<meta property="og:description" content="<s:property value='note'/>"/>
	<meta property="og:title" content="<s:property value='title'/>" />
	<meta property="og:image" content="http://cdn-tatame.trrsf.com/img/<s:property value='photo.id'/>_670x418.jpg"/>
	<meta property="og:url" content="http://www.tatame.com.br/<s:property value='permanentLink.uri'/>"/>		
	<link href="http://cdn-tatame.trrsf.com/img/${photo.id}_210x140.jpg" rel="image_src" title="${photo.description}"/>
</s:if>
<link rel="shortcut icon" href="http://cdn-tatame.trrsf.com/skins/tatame/img/favicon_16x16-ok.png" type="image/x-icon"/>
<link rel="apple-touch-icon-precomposed" href="http://cdn-tatame.trrsf.com/skins/tatame/img/favicon_57x57-ok.png"/>
<link rel='stylesheet' id='responsive-lightbox-nivo-front-css'  href='/skins/tatame/css/nivo-lightbox.css' type='text/css' media='all'/>
<link rel='stylesheet' id='responsive-lightbox-nivo-front-template-css'  href='/skins/tatame/css/responsive-lightbox-default.css' type='text/css' media='all'/>
<link rel='stylesheet' id='fancybox-css'  href='/skins/tatame/css/jquery-fancybox.css' type='text/css' media='all'/>
<link rel='stylesheet' id='fancybox-thumbs-css'  href='/skins/tatame/css/jquery-fancybox-thumbs.css' type='text/css' media='all' />
<link rel='stylesheet' id='manshet-css'  href='/skins/tatame/css/manshet-min-cdn.css' type='text/css' media='all' />
<link rel='stylesheet' id='responsive-css' href='/skins/tatame/css/manshet-responsive-min.css' type='text/css' media='all' />
<link rel='stylesheet' id='OpenSans-css' href='http://fonts.googleapis.com/css?family=Open+Sans%3A400%2C700%2C600%27+rel%3D%27stylesheet%27+type%3D%27text%2Fcss&#038;ver=3.8.1' type='text/css' media='all' />
<link rel='stylesheet' id='Oswald-css' href='http://fonts.googleapis.com/css?family=Oswald%3A400%2C700&#038;ver=3.8.1#038&#039;rel=&#039;stylesheet&#039;type=&#039;text/css' type='text/css' media='all' />
<link rel='stylesheet' id='promoslider_main-css' href='/skins/tatame/css/slide.css' type='text/css' media='all' />
<link rel='stylesheet' id='fontello-css' href='/skins/tatame/css/fontello.css' type='text/css' media='all' />
<link rel='stylesheet' id='main-css' href='/skins/tatame/css/main.css' type='text/css' media='all' />
<style type="text/css"> 
	.wp-polls 
	.pollbar { margin: 1px; font-size: 8px; line-height: 10px; height: 10px; background: #900; border: 1px solid #333; } 
</style>
<style type="text/css">
	.cabeceira { width: 1020px; height: 82px; background: #F90; margin: 0 auto; }
	.cabeceira iframe { margin: 0 auto; display: block; }
</style>

<s:if test="model instanceof com.publisher.entity.Article">
	<link rel='stylesheet' id='article-main-css'  href='/skins/tatame/css/ArticleMain.css' type='text/css' media='all' />
	<s:if test="model instanceof com.publisher.entity.PhotoGallery">
		<link rel='stylesheet' id='article-main-css'  href='/skins/tatame/css/PhotoGallery.css' type='text/css' media='all' />		
	</s:if>
</s:if>
<s:elseif test="model instanceof com.publisher.entity.Page">
	<link rel='stylesheet' id='home-css'  href='/skins/tatame/css/Home.css' type='text/css' media='all'/>
	<link rel='stylesheet' id='soliloquy-css'  href='/skins/tatame/css/soliloquy.css' type='text/css' media='all'/>
</s:elseif>