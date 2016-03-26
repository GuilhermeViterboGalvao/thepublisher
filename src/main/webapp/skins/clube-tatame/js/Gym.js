(function(window, undefined){
			
	var map = null;

	var name = $("#name").html();
	var lat = Number($("#lat").val());
	var lon = Number($("#lon").val());
	var zoom = 15;			
	this.map = new google.maps.Map(document.getElementById("google-maps-canvas"), {
		center: new google.maps.LatLng(lat, lon),
		zoom: zoom,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	});
	google.maps.event.addListener(this.map, "zoom_changed", function(event) {
		window.Advertiser.map.panTo(new google.maps.LatLng(Number($("#lat").val()), Number($("#lon").val())));
	});			
	this.marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat, lon),
	    map: this.map,
	    title: name
	});
	
})(window);