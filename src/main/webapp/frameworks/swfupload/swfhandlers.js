/*Class SWFHandlers*/
function SWFHandlers (flashUrl, alternativePath, uploadUrl, deleteUrl, sessionid, idDivBtn, idDivThumbnail, idDivFileProgress, fileTypes, fileTypesDescription) {
	this.flashUrl = flashUrl;
	this.alternativePath = alternativePath;	
	this.uploadUrl = uploadUrl;	
	this.deleteUrl = deleteUrl;
	this.sessionid = sessionid;
	this.idDivBtn = idDivBtn;
	this.idDivThumbnail = idDivThumbnail;
	this.idDivFileProgress = idDivFileProgress;
	this.fileTypes = fileTypes;
	this.fileTypesDescription = fileTypesDescription;
};
SWFHandlers.prototype.inicialize = function() {
	new SWFUpload({
		flash_url: this.flashUrl,
		upload_url: this.uploadUrl + ";jsessionid=" + this.sessionid + (this.alternativePath != null && this.alternativePath != undefined ? "?path=" + this.alternativePath : ""),
		custom_settings : {
			upload_target : this.idDivFileProgress
		},
		
		obj_handler: this,
		
		file_size_limit: "2 MB",
		file_types: this.fileTypes ? this.fileTypes : "*.jpg",
		file_types_description: this.fileTypesDescription ? this.fileTypesDescription : "JPG Images",
		file_upload_limit: "0",
		file_post_name: "picture",
		file_queue_error_handler: SWFHandlers.fileQueueError,
		file_dialog_complete_handler: SWFHandlers.fileDialogComplete,
		
		upload_progress_handler: SWFHandlers.uploadProgress,
		upload_error_handler: SWFHandlers.uploadError,
		upload_success_handler: SWFHandlers.uploadSuccess,
		upload_complete_handler: SWFHandlers.uploadComplete,
		
		button_placeholder_id: this.idDivBtn,
		button_width: 230,
		button_height: 18,
		button_text: '<span class="button">Selecione as imagens <span class="buttonSmall">(2 MB no total)</span></span>',
		button_text_style: '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
		button_text_top_padding: 0,
		button_text_left_padding: 15,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		debug: false
	});	
};
SWFHandlers.addImage = function(src, id, file, objHandler) {
	var divContent = document.createElement("div");
	divContent.id = id;
	divContent.style.cssFloat = "left";
	divContent.style.marginLeft = "10px";
	document.getElementById(objHandler.idDivThumbnail).appendChild(divContent);
	
	var divImg = document.createElement("div");
	divImg.onload = function(){
		fadeIn(divContent, 0);
	};	
	divImg.style.backgroundImage = "url(" + src + ")";
	divImg.style.width = "150px";
	divImg.style.height = "130px";
	divContent.appendChild(divImg);
	
	var imgClose = document.createElement("img");
	imgClose.src = "/manager/img/close.gif";
	imgClose.style.cssFloat = "right";
	imgClose.onclick = function(){
		SWFHandlers.removeThumbnail(divContent, objHandler.idDivThumbnail, id, objHandler.alternativePath, objHandler.deleteUrl);	
	};
	divImg.appendChild(imgClose);
	
	var inputId = document.createElement("input");
	inputId.type = "hidden";
	inputId.name = "pictureId";
	inputId.value = id;
	divContent.appendChild(inputId);	
	
	var spanDesc = document.createElement("span");
	spanDesc.style.cssFloat = "left";
	spanDesc.style.clear = "both";
	spanDesc.innerHTML = "Descrição da foto:";	
	divContent.appendChild(spanDesc);
	
	var inputDesc = document.createElement("input");
	inputDesc.id = "pictureDescription" + id;
	inputDesc.name = "pictureDescription";
	inputDesc.type = "text";
	inputDesc.value = file.name.substring(0, file.name.indexOf("."));
	inputDesc.style.cssFloat = "left";
	inputDesc.style.clear = "both";
	inputDesc.style.width = "145px";
	inputDesc.style.height = "25px";	
	divContent.appendChild(inputDesc);
	
	var spanTags = document.createElement("span");
	spanTags.style.cssFloat = "left";
	spanTags.style.clear = "both";
	spanTags.innerHTML = "Tags:";
	divContent.appendChild(spanTags);
	
	var inputTags = document.createElement("input");
	inputTags.id = "pictureTag" + id;
	inputTags.name = "pictureTag";
	inputTags.type = "text";
	inputTags.value = file.name.substring(0, file.name.indexOf("."));
	inputTags.style.cssFloat = "left";
	inputTags.style.clear = "both";
	inputTags.style.width = "145px";
	inputTags.style.height = "25px";
	divContent.appendChild(inputTags);
};
SWFHandlers.removeThumbnail = function(thumbnail, divId, imgId, path, deleteUrl){
    var xhr;
    
    try {  
    	xhr = new ActiveXObject('Msxml2.XMLHTTP');  
    } catch (e) {
       try {  
          xhr = new ActiveXObject('Microsoft.XMLHTTP');
       } catch (e2) {
          try {  
       	     xhr = new XMLHttpRequest();     
          } catch (e3) {  
      		 xhr = false;   
          };
       };
    };
	
    xhr.onreadystatechange = function() {  
    	if (xhr.readyState == 4 && xhr.status == 200) {
    		var removed = eval("(" + xhr.responseText + ")");
    		
    		if (removed) {
    			document.getElementById(divId).removeChild(thumbnail);				
			} else {
				alert("Ocorreu um erro ao remover a image.");
			}
    	}
    };
	
    var url = deleteUrl + "?id=" + imgId + (path != null && path != undefined ? "&path=" + path : "");
    
    xhr.open("GET", url,  true);
    xhr.send(null); 
    
    thumbnail.style.diplay = "none";
};
SWFHandlers.fileQueueError = function (file, errorCode, message) {
	try {
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "Você excedeu o limite máximo de upload.";
		}
		if (errorName !== "") {
			alert(errorName);
			return;
		}
		switch (errorCode) {
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
				alert(message);
				break;
				
			case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
				alert(message);
				break;
				
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
				alert(message);
				break;
				
			case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
				alert(message);
				break;
				
			default:
				alert(message);
				break;
		}
	} catch (ex) {
		this.debug(ex);
	}
};
SWFHandlers.fileDialogComplete = function (numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
};
SWFHandlers.uploadProgress = function(file, bytesLoaded) {	
	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		var progress = new FileProgress(file, this.customSettings.upload_target);
		progress.setProgress(percent);
		if (percent === 100) {
			progress.setStatus("Criando imagem...");
			progress.toggleCancel(false, this);
		} else {			
			progress.setStatus("Enviando...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
};
SWFHandlers.uploadSuccess = function(file, serverData) {
	console.log(serverData);
	try {
		var progress = new FileProgress(file, this.customSettings.upload_target);		
		var id = eval("(" + serverData + ")");		
		if (id > 0) {
			var objHandler = this.getObjHandler();
			SWFHandlers.addImage("/upload/" + id + "_150x130.jpg?path=" + (objHandler.alternativePath != undefined ? objHandler.alternativePath : "") + "&_=" + parseInt(Math.random() * 1000) , id, file, objHandler);
			progress.setStatus("Imagem criada.");
			progress.toggleCancel(false);
		} else {
			progress.setStatus("Erro!");
			progress.toggleCancel(false);
			alert(serverData);
		}
	} catch (ex) {
		this.debug(ex);
	}
};
SWFHandlers.uploadComplete = function (file) {
	try {
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			var progress = new FileProgress(file, this.customSettings.upload_target);
			progress.setComplete();
			progress.setStatus("Todos os arquivos foram enviados com sucesso.");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
};
SWFHandlers.uploadError = function (file, errorCode, message) {
	var progress;
	try {
		switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
				try {
					progress = new FileProgress(file, this.customSettings.upload_target);
					progress.setCancelled();
					progress.setStatus("Cancelado.");
					progress.toggleCancel(false);
				}
				catch (ex1) {
					this.debug(ex1);
				}
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
				try {
					progress = new FileProgress(file, this.customSettings.upload_target);
					progress.setCancelled();
					progress.setStatus("Parado.");
					progress.toggleCancel(true);
				}
				catch (ex2) {
					this.debug(ex2);
				}
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				break;
			default:
				alert(message);
				break;
		}
	} catch (ex3) {
		this.debug(ex3);
	}
};
SWFHandlers.fadeIn = function (element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30; // 15 fps
	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}
		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}
	if (opacity < 100) {
		setTimeout(function () {
			fadeIn(element, opacity);
		}, rate);
	}
};
/*Class FileProgress*/
FileProgress = function(file, targetID) {
	this.fileProgressID = "divFileProgress";
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;
		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";
		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));
		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode(file.name));
		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";
		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";
		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);
		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);
	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = file.name;
	}
	this.height = this.fileProgressWrapper.offsetHeight;
};
FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	this.fileProgressElement.childNodes[3].style.width = "";
};
FileProgress.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";
};
FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";
};
FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};
FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};