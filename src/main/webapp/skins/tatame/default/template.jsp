<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>	
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<link href="https://fonts.googleapis.com/css?family=Metrophobic" rel="stylesheet" type="text/css">	
		<style type="text/css">
			@CHARSET "UTF-8";
			/*---------------------------------------------
			CSS Reset
			----------------------------------------------*/
			html, body, div, span, applet, object, iframe,
			h1, h2, h3, h4, h5, h6, p, blockquote, pre,
			a, abbr, acronym, address, big, cite, code,
			del, dfn, em, img, ins, kbd, q, s, samp,
			small, strike, strong, sub, sup, tt, var,
			b, u, i, center,
			dl, dt, dd, ol, ul, li,
			fieldset, form, label, legend,
			table, caption, tbody, tfoot, thead, tr, th, td,
			article, aside, canvas, details, embed, 
			figure, figcaption, footer, header, hgroup, 
			menu, nav, output, ruby, section, summary,
			time, mark, audio, video { margin: 0; padding: 0; border: 0; font-size: 100%; font: inherit; vertical-align: baseline; }
			article, aside, details, figcaption, figure, 
			footer, header, hgroup, menu, nav, section { display: block; }
			body { line-height: 1; }
			ol, ul { list-style: none; }
			blockquote, q { quotes: none; }
			blockquote:before, blockquote:after, q:before, q:after { content: ""; content: none; }
			table { border-collapse: collapse; border-spacing: 0; }
			/*---------------------------------------------
			CSS Page
			----------------------------------------------*/		
			html, body { float: left; width: 100%; height: 100%; position: relative; background-color: #fff; font-family: Metrophobic, sans-serif; }
			
			.header { float: left; width: 100%; }
			.header .terra-bar { float: left; width: 100%; }
			.header .terra-bar iframe { float: left; height: 25px; width: 100%; }
			.header .ads { float: left; width: 100%; }
			.header .ads #tatame_728x90_ros { width: 728px; height: 90px; margin: 15px auto; }
			.header .menu { float: left; width: 98%; background-color: #2b2b2b; padding: 10px 1%; }
			.header .menu .logo { float: left; max-height: 58px; height: auto; max-width: 194px; width: 15%; }
			.header .menu .logo img { float: left; width: 100%; height: auto; }
			.header .menu .categories { float: left; width: 10%; height: 58px; overflow: hidden; position: relative; margin-left: 1.1%; }
			.header .menu .categories li { float: left; width: 100%; height: 100%; line-height: 58px; }
			.header .menu .categories li a { float: left; color: #fff; text-decoration: none; width: 100%; text-align: center; font-size: 20px; }
			.header .menu .diary { float: left; font-size: 20px; color: #fff; text-decoration: none; line-height: 58px; height: 58px; width: 10%; text-align: center; margin-left: 1.1%; }
			.header .menu .columns { float: left; font-size: 20px; color: #fff; text-decoration: none; line-height: 58px; height: 58px; width: 10%; text-align: center; margin-left: 1.1%; }
			.header .menu .videos { float: left; font-size: 20px; color: #fff; text-decoration: none; line-height: 58px; height: 58px; width: 10%; text-align: center; margin-left: 1.1%; }
			.header .menu .tatameshop { float: left; font-size: 20px; color: #fff; text-decoration: none; line-height: 58px; height: 58px; width: 12%; text-align: center; margin-left: 1.1%; }
			.header .menu .subscribe { float: left; font-size: 20px; color: #fff; text-decoration: none; line-height: 58px; height: 58px; width: 10%; text-align: center; margin-left: 1.1%; }
			.header .menu .search { float: left; width: 15%; height: 58px; margin-left: 1.1%; }
			.header .menu .search form { float: left; width: 100%; height: 100%; }
			.header .menu .search form input { float: left; border: none; padding: 0 0 0 4%; margin: 0; width: 96%; height: 100%; font-size: 20px; color: #fff; background-color: #2b2b2b; font-family: Metrophobic, sans-serif; background-image: url("/skins/tatame/img/search.png"); background-repeat: no-repeat; background-position: 95% 50%; background-size: 15px 15px; }
			
			.content { float: left; width: 100%; }
			
			.footer { float: left; width: 100%; }
		</style>
	</head>
	<body>
		<div class="header">
			<div class="terra-bar">
				<iframe scrolling="no" src="http://s1.trrsf.com/navbar/superslim/index.html?id=1&amp;format=superslim&amp;itemMenu=esp"></iframe>
			</div>
			<div class="ads">
				<div id="tatame_728x90_ros"></div>
			</div>
			<div class="menu">
				<a href="/" class="logo">
					<img alt="Tatame" src="/skins/tatame/img/tatame_white.png">
				</a>
				<ul class="categories">
					<li>
						<a href="/tatame/noticias">NOTÍCIAS</a>
					</li>
					<li>
						<a href="/search?query=mma">MMA</a>
					</li>
					<li>
						<a href="/tatame/jiujitsu">JIU-JITSU</a>
					</li>						
				</ul>
				<a href="#" class="diary">AGENDA</a>
				<a href="#" class="columns">COLUNAS</a>
				<a href="/tatame/videos" class="videos">VÍDEOS</a>
				<a href="http://www.tatameshop.com.br/" target="_blank" class="tatameshop">TATAMESHOP</a>
				<a href="http://www.tatameshop.com.br/tatame-f65/" target="_blank" class="subscribe">ASSINE</a>
				<div class="search">
					<form action="/search" method="get">
						<input type="text" name="query" placeholder="Buscar...">
					</form>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="page">
				<div class="highlights">
					<div class="highlight">
						<div class="card-100">
							<img alt="" src=""/>
							<div class="header"></div>
							<div class="title"></div>
						</div>
					</div>
					<div class="highlight">
						<div class="card-50">
							<img alt="" src=""/>
							<div class="header"></div>
							<div class="title"></div>
						</div>
						<div class="card-50">
							<img alt="" src=""/>
							<div class="header"></div>
							<div class="title"></div>
						</div>						
					</div>
					<div class="highlight">
						<div class="card-50">
							<img alt="" src=""/>
							<div class="header"></div>
							<div class="title"></div>
						</div>
						<div class="card-50">
							<div class="card-50">
								<img alt="" src=""/>
								<div class="header"></div>
								<div class="title"></div>
							</div>
							<div class="card-50">
								<img alt="" src=""/>
								<div class="header"></div>
								<div class="title"></div>
							</div>							
						</div>						
					</div>											
				</div>
				<div class="ads-highlights">
				</div>
			</div>			
		</div>
		<div class="footer">
		</div>
		<script type="text/javascript" src="/skins/tatame/js/jquery-1.7.2-min.js"></script>
		<script type="text/javascript">
			var googletag = googletag || {};
			googletag.cmd = googletag.cmd || [];
			(function() {
				var gads = document.createElement("script");
				gads.async = true;
				gads.type = "text/javascript";
				var useSSL = "https:" == document.location.protocol;
				gads.src = (useSSL ? "https:" : "http:") + "//www.googletagservices.com/tag/js/gpt.js";
				var node = document.getElementsByTagName("script")[0];
				node.parentNode.insertBefore(gads, node);
			})();
		</script>
		<script type="text/javascript">
			googletag.cmd.push(function() {
				googletag.defineSlot("/13554095/Tatame_728x90_Ros", [728, 90], "tatame_728x90_ros").addService(googletag.pubads());
				googletag.pubads().enableSingleRequest();
				googletag.enableServices();
			});
		</script>
		<script type="text/javascript">googletag.cmd.push(function(){googletag.display("tatame_728x90_ros");});</script>		
	</body>
</html>