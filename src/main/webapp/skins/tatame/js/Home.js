$(function() {
    var vids = $("#home-scroll-10 .post-item");
    for(var i = 0; i < vids.length; i += 3) {
        vids.slice(i, i+3).wrapAll('&lt;div class="post-items"&gt;&lt;/div&gt;');
    }
    $(function() {
        $('#home-scroll-10').cycle({
            fx              : 'scrollHorz',
            easing          : 'swing', //easeInOutBack
            timeout         : 5555,
            speed           : 600,
            slideExpr       : '.post-item',
            prev            : '#home-scroll10-prev',
            next            : '#home-scroll10-nxt',
            pause           : false
        });
    });
});
$(function(){
	$('#soliloquy-209696').soliloquy({
		animation:'fade',
		slideshowSpeed:7000,
		animationDuration:600,
		controlsContainer:'#soliloquy-container-209696',
		namespace:'soliloquy-',
		selector:'.soliloquy-slides > li',
		useCSS:false
	});
});
$(function() {
	$(".gallery dl.gallery-item .gallery-icon a").addClass("lightbox");
	$(".gallery dl.gallery-item .gallery-icon a").attr('rel', 'lightbox_241310');
	$(".gallery dl.gallery-item .gallery-icon a[href*='attachment']").removeClass("lightbox").attr('href');
});
$(function() {
	$('.tabs_nav li a').click(function(){
		var tab = $(this).attr('href');
		_gaq.push(['_trackEvent', 'Tabs', 'click', tab]);
	});	
	$(document).on('click', '.flex-direction-nav li a', function(){
		var nav = $(this).attr('class');	
		_gaq.push(['_trackEvent', 'Images', 'navigation', nav]);	
	});
});
$(function() {
	$.ajax({
		url     : "/mostviewed?categoryName=Tatame&currentPage=1&pageSize=5",
		cache   : false,
		success : function(data) {
			var dataInOrder = [];
			do {
				var mostViewed = data[0];
				var index = 0;
				for (var i = 0; i < data.length; i++) {
					if (mostViewed.views < data[i].views) {
						mostViewed = data[i];
						index = i;
					}
				}
				data.splice(index, 1);
				dataInOrder.push(mostViewed);
			} while (data.length > 0);
			data = dataInOrder;
			var divReadMore = $("div.read-more");
			for (var i = 0; i < data.length; i++) {
				var article = data[i];		
				divReadMore.append(
					$("<div>").addClass("post-warpper").append(
						$("<div>").addClass("post type-post status-publish format-standard hentry category-destaque tag-ronda-rousey post-item").append(
							$("<div>").addClass("post-image").append(
								$("<a>").css({
									"width"      : "52px",
									"height"     : "50px",
									"background" : "url('http://cdn-tatame.trrsf.com/img/" + article.photoId + "_52x50.jpg') no-repeat scroll",
									"display"    : "inline-block"
								}).attr("href", article.link)
							)
						).append(
							$("<div>").addClass("post-caption").append(
								$("<h3>").addClass("post-title").append(
									$("<a>").attr({
										"rel"  : "bookmark",
										"href" : article.link
									}).html(article.title)
								)
							)
						)							
					)
				).css("display", "none");
			}
			$("b.tab-recent").click(function() {
				$("b.tab-read-more").css({"opacity" : "0.6"});
				$("div.read-more").css({"display": "none"});
				$("b.tab-recent").css({"opacity" : "1"});
				$("div.recent").css({"display": "block"});
			}).css("cursor", "pointer");	
			$("b.tab-read-more").click(function() {
				$("b.tab-read-more").css({"opacity" : "1"});
				$("div.read-more").css({"display": "block"});
				$("b.tab-recent").css({"opacity" : "0.6"});
				$("div.recent").css({"display": "none"});			
			}).css({
				"cursor" : "pointer",
				"opacity" : "0.6"
			});
		}
	});
});
//$(function() {
//	$.ajax({
//		url : "/tatame/view/poll",
//		cache : false,
//		success : function(data) {			
//			if (data) {
//				var divPoll = $("div.poll");
//				var divAlternatives = $("<div>").addClass("alternatives");
//				for (var i = 0; i < data.alternatives.length; i++) {
//					var alternative = data.alternatives[i];
//					divAlternatives.append(
//						$("<div>").addClass("alternative").append(
//							$("<input>").attr({
//								"type"  : "radio",
//								"id"    : alternative.id,
//								"votes" : alternative.votes,
//								"value" : alternative.text						
//							}).click(function() {
//								var pollId = $("div.question").attr("id"); 
//								var alternativeId = $(this).attr("id");
//								if (getCookie(pollId)) {
//									$(this).attr("checked", false);
//									alert("Você já votou.");
//									return;
//								}							
//								$("div.alternative input[type=radio]").each(function() {
//									$(this).attr("checked", false);
//								});	
//								$(this).attr("checked", true);
//								$.ajax({
//									url : "/tatame/view/poll?pollId=" + pollId + "&alternativeId=" + alternativeId,
//									cache : false,
//									success : function(data) {
//										console.log(data);
//										alert("Voto realizado com sucesso.");
//									}
//								});
//								setCookie(pollId);
//							})					
//						).append(
//							$("<span>").html(alternative.text)
//						)
//					);
//				}
//				divPoll.append(
//					$("<div>").addClass("question").attr("id", data.id).html(data.question)
//				).append(
//					divAlternatives
//				);	
//			} else {
//				$("div.bd-poll").css("display", "none");
//			}
//		}
//	});
//});
//
//var setCookie = function(pollId){
//	var date = new Date();
//	date.setDate(date.getDate() + 365);
//	var value = ("POLL_" + pollId) + "; expires="+date.toUTCString();
//	document.cookie=("POLL_" + pollId) + "=" + value;
//};
//
//var getCookie = function(pollId){
//	var i, x, y, ARRcookies = document.cookie.split(";");
//	for (i = 0; i< ARRcookies.length; i++) {
//		x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
//	  	y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
//	  	x = x.replace(/^\s+|\s+$/g,"");
//	  	if (x == ("POLL_" + pollId)) {
//	    	return unescape(y);
//	    }
//	}
//};