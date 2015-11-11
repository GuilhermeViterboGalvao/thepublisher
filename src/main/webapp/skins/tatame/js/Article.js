$(function() {
	$("article.post div.photo img").each(function() {
		var img = $(this);
		var src = img.attr("src");
		if (src.indexOf("../") == 0) {
			src = src.replace("../", "/");	
		} else if (src.indexOf("img/") == 0) {
			src = src.replace("img/", "http://cdn-tatame.trrsf.com/img/");
		}
		img.attr("src", src);
	});
	$("article.post a").each(function() {
	  var a = $(this);
	  if (a.attr("href").indexOf("../") > -1) {
	      a.attr("href", a.attr("href").replace("../", "/"));
	  }
	});
	$("div.post-entry div.photo > img").css("cursor", "pointer").click(function() { 
		var img = $(this);
		var src = img.attr("src");
		var id = src.match(/\d+_/)[0].replace("_", "");
		window.open("http://cdn-tatame.trrsf.com/img/" + id + ".jpg", "_blank").focus();
	});
});