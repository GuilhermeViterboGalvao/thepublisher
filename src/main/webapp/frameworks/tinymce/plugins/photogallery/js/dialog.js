var PhotoGalleryDialog = {
	init: function() {},
	insert : function(id, description, position, width, height, originalSize) {
		var hash = parseInt(Math.random() * 1000000);
		var s =	"<div class=\"photo size" + width + " " + position + "\">";
		    s+= 	"<img src=\"/img/" + id + "_" + width + "x" + height + ".jpg?" + hash + "\" alt=\"" + originalSize + "\"/>";
		    s+=     "<div>" + description + "</div>";
		    s+= "</div>";
		    s+= "<p></p>";
		tinyMCEPopup.editor.execCommand('mceInsertContent', false, s);
		tinyMCEPopup.close();
	}
};
tinyMCEPopup.onInit.add(PhotoGalleryDialog.init, PhotoGalleryDialog);