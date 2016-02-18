
var map = null;
var isComma = false;

function initGoogleMap(lat, lon, zoom){	
	if (lat.indexOf(',') > 0) {
		isComma = true;
	}		
	if (isComma) {
		lat = lat.replace(',', '.');
		lon = lon.replace(',', '.');
	}		
	var latlng = new google.maps.LatLng(Number(lat), Number(lon));
	var myOptions = {
  		zoom      : Number(zoom),
  		center    : latlng,
  		mapTypeId : google.maps.MapTypeId.SATELLITE
	};	
	map = new google.maps.Map($("#map_canvas"), myOptions);
	window.map = map;
	var wavesMarker = new google.maps.Marker({
		position  : latlng,
		map       : map,
		draggable : true
	});
	window.wavesMarker = wavesMarker;
	google.maps.event.addListener(map, 'zoom_changed', function(){
		$("#zoomGoogleMaps").val(map.getZoom());
	});		
	google.maps.event.addListener(wavesMarker, 'dragend', function(){		
		$("#lat").val(wavesMarker.getPosition().lat()); 
		if (isComma) {
			$("#lat").val($("#lat").val().replace('.', ','));
		}
		$("#lon").val(wavesMarker.getPosition().lng());
		if (isComma) {
			$("#lon").val($("#lon").val().replace('.', ','));
		}
	});
}

function validate() {
	$("#lat").val($("#lat").val().replace(".",","));
	$("#lon").val($("#lon").val().replace(".",","));
}
