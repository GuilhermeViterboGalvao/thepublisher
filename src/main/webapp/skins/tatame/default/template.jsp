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
			.page { height: auto; width: 80%; max-width: 1024px; margin: 15px 10%; float: left; }
			
			.box-shadow { box-shadow: 3px 3px 3px #777; }
			
			.margin-right-5 { margin-right: 5px; }
			.margin-left-5 { margin-left: 5px; }
			.margin-top-5 { margin-top: 5px; }
			.margin-bottom-5 { margin-bottom: 5px; }
			.margin-bottom-15 { margin-bottom: 15px; }
			
			.highlights { float: left; height: 595px; width: 100%; max-width: 710px; overflow: hidden; }
			.highlights .highlight { float: left; height: 100%; width: 100%; }
			.highlights .highlight .card-50 { float: left; width: 50%; height: 100%; max-height: 595px; max-width: 350px; position: relative; }
			.highlights .highlight .card-50 > img { float: left; width: 100%; height: auto; }
			.highlights .highlight .card-50 .transparence { width: 100%; background-color: #000; opacity: 0.5; height: 8.41em; position: absolute; left: 0; top: 77.4%; }
			.highlights .highlight .card-50 .header { float: left; position: absolute; left: 5%; top: 78%; font-size: 2em; color: #fff; width: 55%; height: auto; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.highlights .highlight .card-50 .title { float: left; position: absolute; left: 5%; top: 90%; font-size: 1.2em; color: #fff; height: auto; width: 75%; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			
			.highlights .highlight .card-50 .card-50 { height: 50%; width: 100%; }
			.highlights .highlight .card-50 .card-50 .transparence { top: 67.1%; height: 5.63em; }
			.highlights .highlight .card-50 .card-50 .header { top: 68%; font-size: 1.5em; }
			.highlights .highlight .card-50 .card-50 .title { top: 85%; font-size: 1em; }
			
			.ads-highlights { float: left; margin-left: 10px; height: 540px; width: 300px; }
			.ads-300-100 { float: left; margin-bottom: 15px; height: 100px; width: 300px; }
			.ads-300-250 { float: left; height: 250px; width: 300px; }
						
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
				<div class="highlights box-shadow">
					<div class="highlight">
						<div class="card-50 margin-right-5">
							<img alt="76774.jpg" src="http://cdn-tatame.trrsf.com/img/76774_350x595.jpg"/>
							<div class="transparence"></div>
							<div class="header">Chapéu da matéria</div>
							<div class="title">Título da matéria muito longo aqui!</div>
						</div>
						<div class="card-50 margin-left-5">
							<div class="card-50 margin-bottom-15">
								<img alt="76775.jpg" src="http://cdn-tatame.trrsf.com/img/76775_350x290.jpg"/>
								<div class="transparence"></div>
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>
							<div class="card-50">
								<img alt="76777.jpg" src="http://cdn-tatame.trrsf.com/img/76777_350x290.jpg"/>
								<div class="transparence"></div>
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>							
						</div>						
					</div>								
				</div>
				<div class="ads-highlights">
					<div id="tatame_300x100_ros" class="ads-300-100 box-shadow"></div>
					<div id="tatame_300x100_especial1_home" class="ads-300-100 box-shadow"></div>
					<div id="tatame_300x100_especial2_home" class="ads-300-100 box-shadow"></div>
					<div id="tatame_300x250_ros" class="ads-300-250 box-shadow"></div>
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
				googletag.defineSlot("/13554095/Tatame_300x100_ros", [300, 100], "tatame_300x100_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_especial1_home", [300, 100], "tatame_300x100_especial1_home").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_especial2_home", [300, 100], "tatame_300x100_especial2_home").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x250_ros", [300, 250], "tatame_300x250_ros").addService(googletag.pubads());
				googletag.pubads().enableSingleRequest();
				googletag.enableServices();
			});
		</script>
		<script type="text/javascript">
			googletag.cmd.push(function(){googletag.display("tatame_728x90_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_especial1_home");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_especial2_home");});
			googletag.cmd.push(function(){googletag.display("tatame_300x250_ros");});
		</script>				
	</body>
</html>