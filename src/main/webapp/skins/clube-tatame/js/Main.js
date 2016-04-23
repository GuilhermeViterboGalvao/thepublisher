(function(window, undefined) {	
	$("select.menu-mobile").change(function() {
		var value = $(this).val();
		window.location.href = value;
	});
})(window);