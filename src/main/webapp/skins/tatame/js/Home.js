$(function() {	
	//Destaques com fotos
	var showNext = function() {
		var childrens = $(".highlights").children(".highlight");
		var previousHighlight = null;
		var nextHighlight = null;
		var nextPosition = 0;
		$(childrens).each(function(index) {
			var length = childrens.length;
			var children = $(this);
			if (children.attr("class").indexOf("current") > 0) {
				previousHighlight = children;
				if ((index + 1) == length) {
					nextPosition = 0;
				} else {
					nextPosition = (index + 1);
				}				
			}
		});
		nextHighlight = $(childrens[nextPosition]);
		previousHighlight.stop(true).animate({
			opacity: 0
		}, 500, "swing", function() {
			$(this).removeClass("current");
		});
		nextHighlight.stop(true).animate({
			opacity: 1
		}, 500, "swing", function() {
			$(this).addClass("current");
		});
	};
	var intervalId = setInterval(showNext, 12000);
	$("div.highlights").mouseover(function() {
		$("img.arrow-left").add("img.arrow-right").css("display", "block");
	}).mouseout(function() {
		$("img.arrow-left").add("img.arrow-right").css("display", "none");
	});
	$("img.arrow-left").mouseover(function() {
		$("img.arrow-left").css("display", "block");
	}).click(function() {
		if (intervalId > 0) {
			clearInterval(intervalId);
			intervalId = 0;
		}
		showNext();
	});
	$("img.arrow-right").mouseover(function() {
		$("img.arrow-right").css("display", "block");
	}).click(function() {
		if (intervalId > 0) {
			clearInterval(intervalId);
			intervalId = 0;
		}		
		showNext();
	});
	
	
	//Matérias mais lidas
	$.ajax({
		url     : "/mostViewed?categoryId=1&dayRange=4&currentPage=1&pageSize=30",
		cache   : false,
		success : function(data) {
			if (data && data.length > 0) {
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
				var divMostVieweds = $("div.most-vieweds");
				for (var i = 0; i < data.length; i++) {
					var article = data[i];
					divMostVieweds.append(
						$("<div>").addClass("most-viewed box-shadow").append(
							$("<a>").attr("href", article.link).append(
								$("<img>").attr({
									"alt": article.photoId,
									"src": "http://cdn-tatame.trrsf.com/img/" + article.photoId + "_300x180.jpg"
								})								
							)
						).append(
							$("<div>").addClass("info").append(
								$("<div>").addClass("date").html(article.publishedAt)
							).append(
								$("<div>").addClass("header").html(article.header)
							)			
						)
					);
				}				
			}
		}
	});
	
	
	//Agenda
	
	$("div.agenda-articles").mouseover(function() {
		$("img.arrow-left").add("img.arrow-right").css("display", "block");
	}).mouseout(function() {
		$("img.arrow-left").add("img.arrow-right").css("display", "none");
	});
	
	var elements = 3;
	
	var links = $("div.agenda-articles div"),
	currentPage = 1,
	start = 0,
	pages = links.length > elements ? Math.ceil(links.length/elements) : 1,
	end = links.length > elements ? elements-1 : links.length;
	
	var left = $("div.agenda-articles .arrow-left").click(function() {
		if (currentPage > 1) {
			currentPage--;			
			start -= elements;
			end -= elements;
			links.each(function(index) {
				var link = $(this);
				if (index >= start && index <= end) {
					link.removeClass("hide").addClass("show");
				} else {
					link.removeClass("show").addClass("hide");
				}
			});
		}
	});
	
	var right = $("div.agenda-articles .arrow-right").click(function() {
		if (currentPage < pages) {
			currentPage++;
			start = end + 1;
			end += elements;		
			links.each(function(index) {
				var link = $(this);
				if (index >= start && index <= end) {
					link.removeClass("hide").addClass("show");
				} else {
					link.removeClass("show").addClass("hide");
				}
			});
		}
	});
});