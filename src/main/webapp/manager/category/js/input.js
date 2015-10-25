function delCategory(id){	
	if(confirm("Deseja excluir esta categoria?")){
		window.location="category-delete?id="+id;
	}
}
function listarSkins(){
	window.open("win-skin-list", "Templates", "width=500,height=300");
}