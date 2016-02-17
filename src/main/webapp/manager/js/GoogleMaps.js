$(function(){
	
	function initGoogleMap(lat, lon, mapZoom){
		var isComma = false;		
		
		if (lat.indexOf(',') > 0) {
			isComma = true;
		}		
		if (isComma) {
			lat = lat.replace(',', '.');
			lon = lon.replace(',', '.');
		}		
		var latlng = new google.maps.LatLng(Number(lat), Number(lon));
		var myOptions = {
	  		zoom: Number(mapZoom),
	  		center: latlng,
	  		mapTypeId: google.maps.MapTypeId.SATELLITE
		};	
		var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);	
		var wavesMarker = new google.maps.Marker({
			position: latlng,
			map: map,
			draggable: true
		});	
	}	
	google.maps.event.addListener(map, 'zoom_changed', function(){
		document.getElementById("zoomGoogleMaps").value = map.getZoom();
	});		
	google.maps.event.addListener(wavesMarker, 'dragend', function(){		
		document.getElementById("lat").value = wavesMarker.getPosition().lat(); 
		if (isComma) 
			document.getElementById("lat").value = document.getElementById("lat").value.replace('.', ',');
		
		document.getElementById("lon").value = wavesMarker.getPosition().lng();
		if (isComma) 
			document.getElementById("lon").value = document.getElementById("lon").value.replace('.', ',');
	});
	
	function validate() {
		$("#lat").val($("#lat").val().replace(".",","));
		$("#lon").val($("#lon").val().replace(".",","));
	}
});