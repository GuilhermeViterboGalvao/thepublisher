$(function() {
	var links = $("div.photoholder a"),
		pages = links.length > 6 ? Math.ceil(links.length/6) : 1,
		currentPage = 1,
		start = 0,
		end = links.length > 6 ? 5 : links.length;
	var left = $("div.left > a").click(function() {
		if (currentPage > 1) {
			currentPage--;			
			start -= 6;
			end -= 6;
			links.each(function(index) {
				var link = $(this);
				if (index >= start && index <= end) {
					link.removeClass("hide").addClass("show");
				} else {
					link.removeClass("show").addClass("hide");
				}
			});
			right.css("display", "block");
			if (currentPage == 1) {
				left.css("display", "none");	
			}
		}
	}).css("display", "none");
	var right = $("div.right > a").click(function() {
		if (currentPage < pages) {
			currentPage++;
			start = end + 1;
			end += 6;		
			links.each(function(index) {
				var link = $(this);
				if (index >= start && index <= end) {
					link.removeClass("hide").addClass("show");
				} else {
					link.removeClass("show").addClass("hide");
				}
			});
			left.css("display", "block");
			if (currentPage == pages) {
				right.css("display", "none");	
			}
		}
	});
	if (pages <= 1) {
		right.css("display", "none");
	}
	links.each(function() {
		$(this).click(function() {
			var link = $(this);
			$("div.holder img").attr("src", "/img/" + link.attr("id") + ".jpg");
			$("div.bigphotonote").html(link.children().attr("alt"));
		});
	});
	$("img.bigphoto").click(function() {
		var bigPhotoSrc = $(this).attr("src");
		var bigPhotoId = Number(bigPhotoSrc.substring(bigPhotoSrc.lastIndexOf("/") + 1, bigPhotoSrc.lastIndexOf("_")));
		var imageIndex = 0;
		var links = $("div.photoholder a");
		links.each(function(index) {
			var photoHolderId = $(this).attr("id");
			photoHolderId = Number(photoHolderId.substring(0, photoHolderId.lastIndexOf("_")));
			if (bigPhotoId == photoHolderId) {
				imageIndex = index + 1;
			}
		});
		if (imageIndex < links.length) {
			var nextPhoto = $(links[imageIndex]);
			var nextPhotoId = nextPhoto.attr("id");
			$("img.bigphoto").attr("src", "/img/" + nextPhotoId + ".jpg");
			$("div.bigphotonote").html(nextPhoto.children().attr("alt"));
			if (nextPhoto.attr("class") == "hide") {
				right.click();
			}
		}
	});
});