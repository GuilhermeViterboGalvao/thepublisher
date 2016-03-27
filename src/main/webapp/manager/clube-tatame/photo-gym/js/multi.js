var photographerSelected = false;

function validateForm(){
	var field = document.getElementById("description");
	if (field == null || field == "") {
		alert("Insira uma descrição.");
		return false;		
	}
	
	field = document.getElementById("tags");	
	if (field == null || field == "") {
		alert("Insira uma ou mais tags.");
		return false;
	}
	
	field = document.getElementById("date");
	if (field == null || field == "") {
		alert("Insira uma ou mais tags.");
		return false;
	}
	
	if (document.getElementById("photographerId").value <= 0) {
		alert("Selecione um fotógrafo.");
		return false;
	}
	return true;
}

function changePhotographer() {
	document.getElementById("searchPhotographerBox").value = "";
	document.getElementById("searchPhotographer").style.display = "block";
	document.getElementById("photographer").style.display = "none";
}
