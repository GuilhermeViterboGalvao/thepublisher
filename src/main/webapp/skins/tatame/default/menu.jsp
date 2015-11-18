<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" 											 %>
<div class="top-bar">
	<div class="container">
		<div class="top-bar-left"></div>
		<div class="top-bar-right">
			<div class="search-block">
				<form method="get" id="searchform" action="/search">
					<button type="submit" class="search-btn"><i class="icon-search"></i></button>
					<input class="search-text" type="text" id="s" name="query" placeholder="Buscar..."  />
				</form>
			</div>
			<div class="social-icons icon-16">
				<a class="tooldown" title="Facebook"    href="https://www.facebook.com/tatame" 										  target="_blank"><i class="social_icon-facebook"></i></a>
				<a class="tooldown" title="Twitter"     href="http://www.twitter.com/tatamemagazine" 								  target="_blank"><i class="social_icon-twitter"></i></a>
				<a class="tooldown" title="Youtube"     href="http://www.youtube.com/tatametv"                                        target="_blank"><i class="social_icon-youtube"></i></a>
				<a class="tooldown" title="instagram"   href="http://instagram.com/tatamemagazine"                                    target="_blank" ><i class="social_icon-instagram"></i></a>
				<a class="tooldown" title="Google Play" href="https://play.google.com/store/apps/details?id=com.rlvp.tatame.android"  target="_blank" ><i class="social_icon-googleplay"></i></a>
				<a class="tooldown" title="Apple"       href="https://itunes.apple.com/us/app/revista-tatame/id721307804?ls=1&mt=8"   target="_blank" ><i class="social_icon-appstore"></i></a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
<div class="container">
	<div class="header-adv">
		<div id='tatame_728x90_ros' style='width:728px; height:90px; margin: 0 auto;'> 
			<script type='text/javascript'>googletag.cmd.push(function(){googletag.display('tatame_728x90_ros');});</script> 
		</div>
		<%--
		<div id='tatame_215x90_home' style='float: right; height:90px; width:215px;'>
			<script type='text/javascript'>googletag.cmd.push(function() { googletag.display('tatame_215x90_home'); });</script>
		</div> 
		--%>
	</div>
</div>
<div class="clear"></div>
<div class="container">
	<div class="logo header-logo" style="">
	   	<h1 title="TATAME">
	       	<a href="http://www.tatame.com.br/" title="TATAME" rel="home">
	       		<img src='http://cdn-tatame.trrsf.com/skins/tatame/img/tatame.png' alt='TATAME' border='0' />                
	       	</a>
	   	</h1>
	</div>
	<nav id="nav" class="bd-nav">
		<div class="nav">
			<ul id="menu-nav" class="menu">
	       		<li id="menu-item-209271" class="menu-item menu-item-type-custom menu-item-object-custom menu-item-home">
	       			<a href="http://www.tatame.com.br/">Home</a>
	       		</li>
				<li id="menu-item-92290" class="menu-item menu-item-type-taxonomy menu-item-object-category current-post-ancestor current-menu-parent current-post-parent">
					<a href="/tatame/noticias">Notícias</a>
				</li>
				<li id="menu-item-166564" class="menu-item menu-item-type-custom menu-item-object-custom">
					<a href="/search?query=mma">MMA</a>
				</li>
				<li id="menu-item-166563" class="menu-item menu-item-type-custom menu-item-object-custom">
					<a href="/search?query=ufc">UFC</a>
				</li>
				<li id="menu-item-166562" class="menu-item menu-item-type-custom menu-item-object-custom">
					<a href="/tatame/jiujitsu">Jiu-Jitsu</a>
				</li>
				<li id="menu-item-161008" class="menu-item menu-item-type-taxonomy menu-item-object-category">
					<a href="/tatame/edicoes">Edições</a>
				</li>
				<li id="menu-item-92291" class="menu-item menu-item-type-taxonomy menu-item-object-category">
					<a href="/tatame/videos">Vídeos</a>
				</li>
				<li id="menu-item-203879" class="menu-item menu-item-type-post_type menu-item-object-page">
					<a href="http://www.tatameshop.com.br/tatame-f65/">Assine TATAME</a>
				</li>
			</ul>
         	<div class="clear"></div>
         </div>
     </nav>
     <div class="clear"></div>
</div>