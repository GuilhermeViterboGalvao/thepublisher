$(function() {	
	//Perfect Scroll Bar
	$("div.perfect-scroll").perfectScrollbar();
	
	//Eventos de click da select do Menu Mobile
	$(".menu-mobile .categories select").change(function() {
		var width = window.document.body.offsetWidth || window.screen.width;
		if (width && width <= 800) {
			var val = $(".menu-mobile .categories select option:selected").val();
			if (val) {
				window.location.href = val;			
			}	
		}
	});	
});