var hc;

var vc;

var inicialize = function(hc, vc){
	
	this.hc = hc;
	
	this.vc = vc;
	
	if (window.addEventListener) {
	    window.addEventListener('load', initPosition, false);
	} else if (window.attachEvent) {
	    window.attachEvent('onload', initPosition);
	}
};

function selectCollaborator(id,name) {
    document.forms[0].photographerId.value = id;    
    document.getElementById("photographerName").innerHTML="Photographer: " + name;
}

function initPosition() {
    var picture = document.getElementById("picture");
    var marker = document.getElementById("marker");
    marker.style.left = (hc*picture.width - 10) + "px";
    marker.style.top = (vc*picture.height - 10) + "px";
    marker.style.display = "block";
    if (window.addEventListener) {
        picture.addEventListener('click', updatePosition, false);
    } else if (window.attachEvent) {
        picture.attachEvent('onclick', updatePosition);
    }
}

function updatePosition(e2) {
    var e = e2;   
    if ((!window.addEventListener) && (window.event)) {    	
        e = window.event;
        target = e.srcElement;
    }
    var marker = document.getElementById("marker");
    var picture = document.getElementById("picture");
    marker.style.left = (e.layerX - 10)+"px";
    marker.style.top = (e.layerY - 10)+"px";
    document.forms[0].elements["horizontalCenter"].value=e.layerX/picture.width;
    document.forms[0].elements["verticalCenter"].value=e.layerY/picture.height;
}

function changePhotographer() {
	document.getElementById("searchPhotographerBox").value = "";
	document.getElementById("searchPhotographer").style.display = "block";
	document.getElementById("photographer").style.display = "none"; 
}