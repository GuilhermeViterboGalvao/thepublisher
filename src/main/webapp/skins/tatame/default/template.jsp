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
			.header #TerraAdvertising { float: left; margin: 0 auto; }
			.header .ads { float: left; width: 100%; }
			.header .ads > div { max-width: 970px; width: auto; margin: 10px auto; box-shadow: 3px 3px 3px #777; }
			/* .header .ads #tatame_728x90_ros { width: 728px; height: 90px; margin: 15px auto; } */
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
						
			.box-1024 { float: left; width: 1024px; }			
			.box-714 { float: left; width: 714px; }
			.box-507 { float: left; width: 507px; }
			.box-300 { float: left; width: 300px; }
			
			.margin-right-5 { margin-right: 5px; }
			.margin-right-10 { margin-right: 10px; }
			.margin-right-15 { margin-right: 15px; }
			.margin-left-5 { margin-left: 5px; }
			.margin-left-10 { margin-left: 10px; }
			.margin-left-15 { margin-left: 15px; }
			.margin-top-5 { margin-top: 5px; }
			.margin-top-10 { margin-top: 10px; }
			.margin-top-15 { margin-top: 15px; }
			.margin-bottom-5 { margin-bottom: 5px; }
			.margin-bottom-10 { margin-bottom: 10px; }
			.margin-bottom-15 { margin-bottom: 15px; }
			
			.highlights { float: left; height: 595px; width: 714px; overflow: hidden; }
			.highlights .highlight { float: left; height: 595px; width: 714px; }
			.highlights .highlight .card-50 { float: left; width: 352px; height: 595px; position: relative; }
			.highlights .highlight .card-50 > img { float: left; width: 353px; height: auto; }
			.highlights .highlight .card-50 .transparence { width: 352px; background-color: #000; opacity: 0.5; height: 134px; position: absolute; left: 0; top: 461px; }
			.highlights .highlight .card-50 .header { position: absolute; left: 20px; top: 465px; font-size: 33px; color: #fff; width: 200px; height: auto; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; overflow: hidden; text-overflow: ellipsis; line-height: 38px; max-height: 76px; }
			.highlights .highlight .card-50 .title { position: absolute; left: 20px; top: 540px; font-size: 20px; color: #fff; height: auto; width: 285px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; overflow: hidden; text-overflow: ellipsis; line-height: 27px; max-height: 54px; }			
			.highlights .highlight .card-50 .card-50 { height: 290px; width: 352px; }
			.highlights .highlight .card-50 .card-50 .transparence { top: 201px; height: 90px; }
			.highlights .highlight .card-50 .card-50 .header { top: 201px; font-size: 24px; line-height: 28px; }
			.highlights .highlight .card-50 .card-50 .title { top: 260px; font-size: 16px; line-height: 20px; }
			
			.ads-highlights { float: left; height: 595px; width: 300px; }
			.ads-300-100 { float: left; margin-bottom: 15px; height: 100px; width: 300px; }
			.ads-300-250 { float: left; height: 250px; width: 300px; }
			
			.ads-1024 { float: left; width: 1024px; }
			.ads-1024 #tatame_728x90_ros { width: 728px; height: 90px; margin: 15px auto; box-shadow: 3px 3px 3px #777; }
			.ads-1024 > div { width: 728px; height: 90px; margin: 15px auto; box-shadow: 3px 3px 3px #777; }
			
			.black-belt-highlights { float: left; background-color: #2b2b2b; height: 25px; width: 1024px; margin: 15px 0; }
			.black-belt-highlights .dan { float: left; background-color: #a2393c; height: 25px; width: 96px; margin-left: 20px; color: #fff; font-size: 20px; line-height: 23px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			
			.other-highlights { float: left; width: 1024px; }
			.other-highlights .highlight { float: left; height: 200px; width: 507px; }
			.other-highlights .highlight img { float: left; width: 250px; border-right: 1px solid #a2393c; }
			.other-highlights .highlight .info { float: left; width: 256px; height: 200px; background-color: #2b2b2b; }
			.other-highlights .highlight .info .header { float: left; width: 246px; height: 90px; font-size: 34px; line-height: 45px; padding: 10px 0 0 10px; font-weight: bold; color: #fff; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; overflow: hidden; text-overflow: ellipsis; }
			.other-highlights .highlight .info .title { float: left; width: 246px; height: 90px; padding: 10px 0 0 10px; font-size: 20px; line-height: 28px; color: #fff; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; overflow: hidden; text-overflow: ellipsis; }
				
			.black-belt-vidoes { float: left; background-color: #2b2b2b; height: 25px; width: 714px; margin: 0 0 5px 0; }
			.black-belt-vidoes .dan { float: left; background-color: #a2393c; height: 25px; width: 60px; margin-left: 20px; color: #fff; font-size: 20px; line-height: 23px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; } 				
			.videos { float: left; height: 810px; width: 714px; }
			.videos .video { float: left; height: 400px; width: 352px; overflow: hidden; }
			.videos .video iframe { float: left; height: 335px; width: 352px; }
			.videos .video .note { float: left; height: 54px; width: 342px; background-color: #2b2b2b; color: #fff; font-size: 20px; padding: 10px 0 0 10px; line-height: 24px; box-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; overflow: hidden; border-top: 1px solid #a2393c; }
			
			.black-belt-articles { float: left; width: 300px; height: 25px; line-height: 25px; background-color: #2b2b2b; margin: 0 0 5px 0; }
			.black-belt-articles .dan { float: left; margin-left: 20px; background-color: #a2393c; font-size: 20px; line-height: 25px; height: 25px; color: #fff; width: 75px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.articles { float: left; height: 810px; width: 300px; overflow-x: hidden; overflow-y: scroll; border-bottom: 1px solid #a2393c; }
			.articles .article { float: left; width: 300px; height: 250px; overflow: hidden; margin: 0 0 5px 0; background-color: #2b2b2b; }
			.articles .article img { float: left; width: 300px; border-bottom: 1px solid #a2393c; }
			.articles .article .info { float: left; width: 300px; height: 70px; }
			.articles .article .info .date { float: left; width: 295px; height: 23px; line-height: 23px; font-size: 16px; color: #fff; padding: 0 0 0 5px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.articles .article .info .header { float: left; width: 295px; height: 23px; line-height: 23px; font-size: 16px; color: #fff; padding: 0 0 0 5px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.articles .article .info .title { float: left; width: 295px; height: 23px; line-height: 23px; font-size: 16px; color: #fff; padding: 0 0 0 5px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			
			.black-belt-most-vieweds { float: left; width: 300px; height: 25px; line-height: 25px; background-color: #2b2b2b; margin: 0 0 5px 0; }
			.black-belt-most-vieweds .dan { float: left; margin-left: 20px; background-color: #a2393c; font-size: 20px; line-height: 25px; height: 25px; color: #fff; width: 90px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.most-vieweds { float: left; height: 810px; width: 300px; overflow-x: hidden; overflow-y: scroll; border-bottom: 1px solid #a2393c; }
			.most-vieweds .most-viewed { float: left; width: 300px; height: 250px; overflow: hidden; margin: 0 0 5px 0; background-color: #2b2b2b; }
			.most-vieweds .most-viewed img { float: left; width: 300px; border-bottom: 1px solid #a2393c; }
			.most-vieweds .most-viewed .info { float: left; width: 300px; height: 70px; }
			.most-vieweds .most-viewed .info .date { float: left; width: 295px; height: 23px; line-height: 23px; font-size: 16px; color: #fff; padding: 0 0 0 5px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.most-vieweds .most-viewed .info .header { float: left; width: 295px; height: 23px; line-height: 23px; font-size: 16px; color: #fff; padding: 0 0 0 5px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.most-vieweds .most-viewed .info .title { float: left; width: 295px; height: 23px; line-height: 23px; font-size: 16px; color: #fff; padding: 0 0 0 5px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }			
			
			.black-belt-jiu-jitsu { float: left; background-color: #2b2b2b; height: 25px; width: 714px; margin: 0 0 5px 0; }
			.black-belt-jiu-jitsu .dan { float: left; background-color: #a2393c; height: 25px; width: 72px; margin-left: 20px; color: #fff; font-size: 20px; line-height: 23px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; } 				
			.jiu-jitsu-articles { float: left; height: 810px; width: 714px; }
			.jiu-jitsu-articles .jiu-jitsu-article { float: left; height: 400px; width: 352px; overflow: hidden; }
			.jiu-jitsu-articles .jiu-jitsu-article img { float: left; height: 335px; width: 352px; }
			.jiu-jitsu-articles .jiu-jitsu-article .note { float: left; height: 54px; width: 342px; background-color: #2b2b2b; color: #fff; font-size: 20px; padding: 10px 0 0 10px; line-height: 24px; box-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; overflow: hidden; border-top: 1px solid #a2393c; }						
			
			.black-belt-big-video { float: left; background-color: #2b2b2b; height: 25px; width: 714px; margin: 0 0 5px 0; }
			.black-belt-big-video .dan { float: left; background-color: #a2393c; height: 25px; width: 112px; margin-left: 20px; color: #fff; font-size: 20px; line-height: 23px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; }
			.big-video { float: left; height: 570px; width: 714px; }
			.big-video iframe { float: left; height: 400px; width: 714px; }
			.big-video .note { float: left; height: 159px; width: 704px; padding: 10px 0 0 10px; font-size: 30px; background-color: #2b2b2b; color: #fff; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; border-top: 1px solid #a2393c; }
			
			.magazine { float: left; height: 600px; width: 300px; overflow: hidden; }
			.magazine img { float: left; height: 402px; width: 300px; }
			.magazine .note { float: left; height: 187px; width: 300px; padding: 10px 0 0 10px; font-size: 30px; background-color: #2b2b2b; color: #fff; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black; border-top: 1px solid #a2393c; line-height: 35px; }
			
			.facebook { float: left; height: 600px; width: 714px; }
			.facebook > div { float: left; height: 600px; width: 714px; }
			
			.footer { float: left; width: 100%; }
		</style>
	</head>
	<body>
		<div id="fb-root"></div>
		<script type="text/javascript">
			(function(d, s, id) {
			  var js, fjs = d.getElementsByTagName(s)[0];
			  if (d.getElementById(id)) {
				  return;
			  }
			  js = d.createElement(s); 
			  js.id = id;
			  js.src = "//connect.facebook.net/pt_BR/sdk.js#xfbml=1&version=v2.5&appId=312287075567167";
			  fjs.parentNode.insertBefore(js, fjs);
			}(document, "script", "facebook-jssdk"));
		</script>	
		<div class="header">
			<div class="terra-bar">
				<iframe scrolling="no" src="http://s1.trrsf.com/navbar/superslim/index.html?id=1&amp;format=superslim&amp;itemMenu=esp"></iframe>
			</div>
			<div class="ads">
				<div id="TerraAdvertising" data-keyvalues="" data-clicktag=""></div>
				<script type="text/javascript" src="http://p2.trrsf.com/tagmanfe/ShowArea.aspx?key=br.cobranded_tatame.home.master1&direct=1"></script>
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
				<div class="box-714 box-shadow">
					<div class="highlights box-shadow">
						<div class="highlight">
							<div class="card-50 margin-right-5">
								<img alt="76774.jpg" src="http://cdn-tatame.trrsf.com/img/76774_352x595.jpg"/>
								<div class="transparence"></div>
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>
							<div class="card-50 margin-left-5">
								<div class="card-50 margin-bottom-15">
									<img alt="76775.jpg" src="http://cdn-tatame.trrsf.com/img/76775_352x290.jpg"/>
									<div class="transparence"></div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria muito longo aqui!</div>
								</div>
								<div class="card-50">
									<img alt="76777.jpg" src="http://cdn-tatame.trrsf.com/img/76777_352x290.jpg"/>
									<div class="transparence"></div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria muito longo aqui!</div>
								</div>							
							</div>						
						</div>								
					</div>				
				</div>
				<div class="box-300 margin-left-10">
					<div class="ads-highlights">
						<div id="tatame_300x100_ros" class="ads-300-100 box-shadow"></div>
						<div id="tatame_300x100_especial1_home" class="ads-300-100 box-shadow"></div>
						<div id="tatame_300x100_especial2_home" class="ads-300-100 box-shadow"></div>
						<div id="tatame_300x250_ros" class="ads-300-250 box-shadow"></div>
					</div>				
				</div>				
				<div class="black-belt-highlights box-shadow">
					<div class="dan">Destaques</div>
				</div>
				<div class="box-1024">
					<div class="other-highlights">
						<div class="highlight box-shadow">
							<img alt="75987_250x200.jpg" src="http://cdn-tatame.trrsf.com/img/75987_250x200.jpg"/>
							<div class="info">
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>
						</div>
						<div class="highlight margin-left-10 box-shadow">
							<img alt="73354_250x200.jpg" src="http://cdn-tatame.trrsf.com/img/73354_250x200.jpg"/>
							<div class="info">
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>
						</div>
						<div class="highlight margin-top-10 box-shadow">
							<img alt="73356_250x200.jpg" src="http://cdn-tatame.trrsf.com/img/73356_250x200.jpg"/>
							<div class="info">
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>
						</div>
						<div class="highlight margin-top-10 margin-left-10 box-shadow">
							<img alt="73357_250x200.jpg" src="http://cdn-tatame.trrsf.com/img/73357_250x200.jpg"/>
							<div class="info">
								<div class="header">Chapéu da matéria</div>
								<div class="title">Título da matéria muito longo aqui!</div>
							</div>
						</div>					
					</div>					
				</div>
				<div class="ads-1024">
					<div id="tatame_728x90_ros"></div>
				</div>
				<div class="box-1024">
					<div class="box-714">
						<div class="black-belt-vidoes box-shadow">
							<div class="dan">Vídeos</div>
						</div>
						<div class="videos">
							<div class="video box-shadow">
								<iframe width="352" height="315" src="https://www.youtube.com/embed/stkjHfshHxg" frameborder="0"></iframe>
								<div class="note">Texto sobre o vídeo.</div>
							</div>
							<div class="video box-shadow margin-left-10">
								<iframe width="352" height="315" src="https://www.youtube.com/embed/D55fV53K9zY" frameborder="0"></iframe>
								<div class="note">Texto sobre o vídeo.</div>
							</div>
							<div class="video box-shadow margin-top-10">
								<iframe width="352" height="315" src="https://www.youtube.com/embed/ijFMwNXJOPk" frameborder="0"></iframe>
								<div class="note">Texto sobre o vídeo.</div>
							</div>		
							<div class="video box-shadow margin-left-10 margin-top-10">
								<iframe width="352" height="315" src="https://www.youtube.com/embed/AZGmqPfmurk" frameborder="0"></iframe>
								<div class="note">Texto sobre o vídeo.</div>
							</div>	
						</div>
					</div>
					<div class="box-300 margin-left-10">
						<div class="black-belt-articles box-shadow">
							<div class="dan">Notícias</div>
						</div>					
						<div class="articles">
							<div class="article box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76489_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="article box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76490_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="article box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76491_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="article box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76492_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="article box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76493_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>																												
						</div>
					</div>
				</div>		
				<div class="ads-1024">
					<div id="TerraAdvertising" data-keyvalues="" data-clicktag=""></div>
					<script type="text/javascript" src="http://p2.trrsf.com/tagmanfe/ShowArea.aspx?key=br.cobranded_tatame.home.master2&direct=1"></script>				
				</div>	
				<div class="box-1024">
					<div class="box-300">
						<div class="black-belt-most-vieweds box-shadow">
							<div class="dan">Mais lidas</div>
						</div>					
						<div class="most-vieweds">
							<div class="most-viewed box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76489_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="most-viewed box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76490_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="most-viewed box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76491_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="most-viewed box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76492_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>
							<div class="most-viewed box-shadow">
								<img alt="76489_270x180.jpg" src="http://cdn-tatame.trrsf.com/img/76493_300x180.jpg"/>
								<div class="info">
									<div class="date">20/12/2015</div>
									<div class="header">Chapéu da matéria</div>
									<div class="title">Título da matéria</div>
								</div>
							</div>																												
						</div>					
					</div>
					<div class="box-714 margin-left-10">
						<div class="black-belt-jiu-jitsu box-shadow">
							<div class="dan">Jiu Jitsu</div>
						</div>
						<div class="jiu-jitsu-articles">
							<div class="jiu-jitsu-article box-shadow">
								<img alt="77204_353x315.jpg" src="http://cdn-tatame.trrsf.com/img/77204_352x335.jpg"/>
								<div class="note">Texto sobre a matéria.</div>
							</div>
							<div class="jiu-jitsu-article box-shadow margin-left-10">
								<img alt="76519_352x315.jpg" src="http://cdn-tatame.trrsf.com/img/76519_352x335.jpg"/>
								<div class="note">Texto sobre a matéria.</div>
							</div>
							<div class="jiu-jitsu-article box-shadow margin-top-10">
								<img alt="71917_352x315.jpg" src="http://cdn-tatame.trrsf.com/img/71917_352x335.jpg"/>
								<div class="note">Texto sobre a matéria.</div>
							</div>		
							<div class="jiu-jitsu-article box-shadow margin-left-10 margin-top-10">
								<img alt="77190_352x315.jpg" src="http://cdn-tatame.trrsf.com/img/77190_352x335.jpg"/>
								<div class="note">Texto sobre a matéria.</div>
							</div>	
						</div>					
					</div>					
				</div>				
				<div class="box-1024 margin-top-10">
					<div class="box-714">
						<div class="black-belt-big-video box-shadow">
							<div class="dan">Vídeo do dia</div>
						</div>					
						<div class="big-video box-shadow">
							<iframe width="714" height="400" src="https://www.youtube.com/embed/vomhIvxqulg" frameborder="0"></iframe>
							<div class="note">Texto sobre o vídeo.</div>
						</div>
					</div>
					<div class="box-300 box-shadow margin-left-10">
						<div id="tatame_300x600_ros"></div>
					</div>
				</div>				
				<div class="box-1024 margin-top-10">
					<div class="magazine box-shadow">
						<img alt="77168.jpg" src="http://cdn-tatame.trrsf.com/img/77168_300x402.jpg"/>
						<div class="note">TATAME #236: Abu Dhabi Grand Slam RJ, Jiu-Jitsu global, Marcus Buchecha e mais</div>
					</div>
					<div class="facebook box-shadow margin-left-10">
						<div 
							class="fb-page" 
							data-href="https://www.facebook.com/Tatame" 
							data-small-header="false" 
							data-adapt-container-width="true" 
							data-hide-cover="false" 
							data-show-facepile="true" 
							data-show-posts="true">
							<div class="fb-xfbml-parse-ignore">
								<blockquote cite="https://www.facebook.com/Tatame">
									<a href="https://www.facebook.com/Tatame">TATAME</a>
								</blockquote>
							</div>
						</div>					
					</div>
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
				googletag.defineSlot("/13554095/Tatame_300x600_ros", [300, 600], "tatame_300x600_ros").addService(googletag.pubads());				
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
			googletag.cmd.push(function(){googletag.display("tatame_300x600_ros");});
		</script>					
	</body>
</html>