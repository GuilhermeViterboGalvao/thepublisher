$(function() {
	$("div.text div.photo img").each(function() {
		var img = $(this);
		var src = img.attr("src");
		if (src.indexOf("../") == 0) {
			src = src.replace("../", "http://cdn-tatame.trrsf.com/");	
		} else if (src.indexOf("img/") == 0) {
			src = src.replace("img/", "http://cdn-tatame.trrsf.com/img/");
		}
		img.attr("src", src);
	});
	$("div.text a").each(function() {
	  var a = $(this);
	  if (a.attr("href").indexOf("../") > -1) {
	      a.attr("href", a.attr("href").replace("../", "/"));
	  }
	});	
});