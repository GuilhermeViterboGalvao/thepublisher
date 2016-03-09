(function(window, undefined) {
	
	var ImageLazyLoad = {
			
			windowWidth: document.body.offsetWidth || window.innerWidth || screen.width,
			
			loadImages: function() {
				$("img").each(function(index) {
					var img = $(this);
					var desktopSrc = img.data("desktopsrc");
					var mobileSrc = img.data("mobilesrc");
					if (ImageLazyLoad.windowWidth >= 800 && desktopSrc) {
						img.attr("src", desktopSrc);
					} else if (mobileSrc) {
						img.attr("src", mobileSrc);
					}
					console.log(img[0]);
				});
			}
	};
	
	window.ImageLazyLoad = ImageLazyLoad;
	
})(window);