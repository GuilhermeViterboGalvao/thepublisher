(function(window, undefined){
	
	var w = window;	
	
	function SWFDialog(sessionId, targetButton) {
		if(w.swfInstances == null || w.swfInstances == undefined){
			var head = 	document.getElementsByTagName("head")[0];
			var link = document.createElement("link");
			link.href = "/frameworks/swfupload/swfupload.css";
			link.type = "text/css";
			link.rel = "stylesheet";
			head.appendChild(link);			
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "/frameworks/swfupload/swfupload.js";
			head.appendChild(script);			
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "/manager/js/swfDialogHandlers.js";
			head.appendChild(script);
		}

		var contextReference = this;
		
		var session = null;
		if (sessionId != undefined && sessionId != null && sessionId != "") {
			session = sessionId;
		} else {
			console.log("You need to pass the value of session for SWFDialog.");
			alert("Error on create object SWFDialog.\nFor more information see error console log.");
			return;
		}
		this.getSession = function () {
			return session;
		};
		
		var target = null;
		if (targetButton != null && targetButton != undefined && targetButton != "") {
			target = targetButton;
			$('#' + target).click(function(){
				contextReference.show();
			});
		} else {
			console.log("You need to pass the value of button id for SWFDialog show the popup.");
			alert("Error on create object SWFDialog.\nFor more information see error console log.");
			return;			
		}
		this.getTarget = function () {
			return target;
		};

		$(w.document.body).append(
			$('<div>').attr({
				'id'    : 'swfDialog' + SWFDialog.instancesCount,
				'title' : 'Enviar fotos'
			})
		);		
		var popup = 'swfDialog' + SWFDialog.instancesCount;
		this.getPopup = function () {
			return popup;
		};
		
		var swfHandler = null;
		this.getSWFHandler = function () {
			return swfHandler;
		};
		this.setSWFHandler = function (handler) {
			swfHandler = handler;
		};
		
		var instanceId = SWFDialog.instancesCount++;
		this.getInstanceId = function () {
			return instanceId; 
		};

		SWFDialog.instances[instanceId] = this;
	}
	
	SWFDialog.instances = {};
	
	SWFDialog.instancesCount = 0;
	
	SWFDialog.init = function(params){
		new SWFDialog(
			params.sessionId    != null && params.sessionId    != undefined ? params.sessionId    : "",
			params.targetButton != null && params.targetButton != undefined ? params.targetButton : null,
			params.photoType    != null && params.photoType    != undefined ? params.photoType    : null
		);
	};
	
	SWFDialog.prototype.validateForm = function (contextReference) {
		if ($('#swf_dialog_pictureId' + contextReference.getInstanceId()) == null) {
			alert('Você precisa selecionar no mínimo uma imagem.');
			return false;
		}
		var val = $('#swf_dialog_date' + contextReference.getInstanceId()).val(),
			date = new Date();
		if (val) {
			val = val.split("/");
			date = new Date(
				parseInt(val[2]), 
				parseInt(val[1]), 
				parseInt(val[0]),
				0, 
				0, 
				0, 
				0
			);
		}
		if (date == 'Invalid Date') {
			alert('O formato da data é inválido ou nulo.\nExemplo de formato dd/mm/aaaa.');
			return;			
		} else {
			date = $('#swf_dialog_date' + contextReference.getInstanceId()).val();
		}
		var credits = $('input[name=credits' + contextReference.getInstanceId() + ']').val();
		if (credits == undefined || credits == null || credits == "") {
			alert('Você deve informar os créditos da foto.');
			return;			
		}
		var tags = $('#swf_dialog_tags' + contextReference.getInstanceId()).val();
		if (tags == undefined || tags == null || tags == "") {
			alert('Digite as palavras chave.');
			return;	
		}
		
		var data = 'description=' + $('#swf_dialog_description' + contextReference.getInstanceId()).val() + '&';
		
		$('input[name=swf_dialog_pictureId]').each(function(){
			data += 'pictureId=' + $(this).val() + '&';
		});
		
		$('input[name=swf_dialog_pictureDescription]').each(function(){
			data += 'pictureDescription=' + $(this).val() + '&';
		});
		
		$('input[name=swf_dialog_pictureTag]').each(function(){
			data += 'pictureTag=' + $(this).val() + '&';
		});
		
		data += 'tags=' + tags + '&';
		data += 'credits=' + credits + '&';
		data += 'date=' + date + '&';
		data += 'published=' + $('#swf_dialog_published' + contextReference.getInstanceId())[0].checked + '&';
		data += 'useFilename=' + $('#swf_dialog_useFilename' + contextReference.getInstanceId())[0].checked;		
		
		$.post('photomulti-save', data).done(function() {
			$('#'+contextReference.getPopup()).empty().dialog('close');
		});
	};	

	SWFDialog.prototype.show = function() {
		
		var date = new Date();	

		var contextReference = this;
		
		$('#'+this.getPopup()).empty().append(			
			$('<label>').attr({
				'for' : 'swf_dialog_description' + this.getInstanceId()
			}).html('Descrição:')
		).append(
			$('<br>')
		).append(
			$('<textarea>').attr({
				'id'   : 'swf_dialog_description' + this.getInstanceId(),
				'name' : 'swf_dialog_description' + this.getInstanceId()
			}).css('width', '780px')
		).append(
			$('<br>')
		).append(
			$('<br>')				
		).append(
			$('<label>').attr({
				'for' : 'swf_dialog_tags' + this.getInstanceId()
			}).html('Palavras chave:')
		).append(
			$('<br>')				
		).append(
			$('<textarea>').attr({
				'id'   : 'swf_dialog_tags' + this.getInstanceId(),
				'name' : 'swf_dialog_tags' + this.getInstanceId()
			}).css('width', '780px')
		).append(
			$('<br>')
		).append(
			$('<br>')				
		).append(
			$('<label>').attr({
				'for' : 'credits' + this.getInstanceId()
			}).html('Créditos da foto:')
		).append(
			$('<input>').attr({
				'type' : 'text',
				'id'   : 'credits' + this.getInstanceId(),
				'name' : 'credits' + this.getInstanceId()
			})
		).append(
			$('<br>')
		).append(
			$('<br>')					
		).append(
			$('<label>').attr({
				'for' : 'swf_dialog_date' + this.getInstanceId()
			}).html('Data')
		).append(
			$('<br>')
		).append(
			$('<br>')
		).append(
			$('<input>').attr({
				'id'   : 'swf_dialog_date' + this.getInstanceId(),
				'name' : 'swf_dialog_date' + this.getInstanceId(),
				'type' : 'text',
				'value': date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear()
			})
		).append(
			$('<br>')
		).append(
			$('<br>')					
		).append(			
			$('<input>').attr({
				'id'   : 'swf_dialog_published' + this.getInstanceId(),
				'name' : 'swf_dialog_published' + this.getInstanceId(),
				'type' : 'checkbox'
			})
		).append(
			$('<label>').attr({
				'for' : 'swf_dialog_published' + this.getInstanceId()
			}).html('Publicado')
		).append(
			$('<br>')
		).append(
			$('<br>')					
		).append(
			$('<input>').attr({
				'id'   : 'swf_dialog_useFilename' + this.getInstanceId(),
				'name' : 'swf_dialog_useFilename' + this.getInstanceId(),
				'type' : 'checkbox'
			})
		).append(
			$('<label>').attr({
				'for' : 'swf_dialog_useFilename' + this.getInstanceId()
			}).html('Acrescentar aos arquivos a Descrição e as Palavras chave')
		).append(
			$('<br>')
		).append(
			$('<br>')					
		).append(
			$('<div>').addClass('ym-fbox-button').css({
				'margin-top'    : '5px',
				'margin-bottom' : '20px'
			}).append(
				$('<input>').attr({
					'type'  : 'button',
					'value' : 'Enviar'
				}).click(function(){
					contextReference.validateForm(contextReference);
				})
			)
		).append(
			$('<br>')				
		).append(
			$('<input>').attr({
				'id'    : 'swfButton' + this.getInstanceId(),
				'name'  : 'swfButton' + this.getInstanceId(),
				'type'  : 'button',
				'value' : 'Selecionar imagens'
			})				
		).append(
			$('<div>').attr({
				'id' : 'swfFileProgress' + this.getInstanceId()
			}).css({
				'width' : '800px',
				'float' : 'left'
			})				
		).append(
			$('<div>').attr({
				'id' : 'swfThumbnail' + this.getInstanceId()
			}).css({
				'width' : '800px',
				'float' : 'left'
			})
		).dialog({
			height : 820,		
			width  : 820
		});		
		
		var handler = new SWFHandlers(
			"/frameworks/swfupload/swfupload.swf",
			null,
			"photo_swf-upload",
			"photo_swf-delete",
			this.getSession(),
			"swfButton" + this.getInstanceId(),					
			"swfThumbnail" + this.getInstanceId(),
			"swfFileProgress" + this.getInstanceId(),
			"*.jpg;*.jpeg",
			"JPG e JPEG Images"
		);
		handler.inicialize();
		this.setSWFHandler(handler);
	};
	
	w.SWFDialog = SWFDialog.init;
	w.SWFDialog.instances = SWFDialog.instances;
	
})(window);