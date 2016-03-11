(function(window, undefined) {
	
	var ImageLazyLoad = {
			
			windowWidth: document.body.offsetWidth || window.innerWidth || screen.width,
			
			imgs: [],
			
			init: function() {
				$("img").each(function(index) {
					var img = $(this);
					var desktopSrc = img.data("desktopsrc");
					var mobileSrc = img.data("mobilesrc");
					if (desktopSrc && mobileSrc) {
						ImageLazyLoad.imgs.push(img);
					}
				});
				this.lazyLoad(0);
			},
			
			lazyLoad: function(position) {
				if (this.imgs && this.imgs.length > 0) {
					var currentImg = this.imgs[position];
					if (currentImg) {
						var img = new Image();
						img.id = position;
						img.onload = function() {
							var position = this.id;
							ImageLazyLoad.imgs[position].attr("src", this.src);
							ImageLazyLoad.lazyLoad(Number(position) + 1);
						};
						img.onerror = function() {
							if (console && console.log) {
								console.log(this);
							}
							var position = this.id;
							ImageLazyLoad.lazyLoad(Number(position) + 1);
						};
						if (this.windowWidth >= 800) {
							img.src = currentImg.data("desktopsrc");	
						} else {
							img.src = currentImg.data("mobilesrc");
						}
					}
				}
			}
	};
	
	window.ImageLazyLoad = ImageLazyLoad;
	
})(window);