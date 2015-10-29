$(function() {
	$.ajax({
		url : "/articleFeed?pageSize=5",
		cache : false,
		success : function(data) {
			if (data) {
				var div = $("div#bd-recent-posts-2 div.widget-inner");
				for (var i = 0; i < data.length; i++) {
					var article = data[i];
					div.append(
						$("<div>").addClass("post-warpper").append(
							$("<div>").addClass("post type-post status-publish format-standard hentry").append(
								$("<div>").addClass("post-image").append(
									$("<a>").attr("href", "/" + article.link).css({
										"width" : "52px",
										"height" : "50px",
										"background" : "url('/img/" + article.photoId + "_52x50.jpg') no-repeat scroll",
										"display" : "inline-block"
									})
								)
							).append(
								$("<div>").addClass("post-caption").append(
									$("<h3>").addClass("post-title").append(
										$("<a>").attr("href", "/" + article.link).html(article.title)
									)									
								).append(
									$("<div>").addClass("post-meta").append(
										$("<span>").addClass("meta-date").html("<i class='icon-time'></i>" + article.publishedAt)
									)									
								)
							)
						)
					);
				}				
			}
		}
	});
});