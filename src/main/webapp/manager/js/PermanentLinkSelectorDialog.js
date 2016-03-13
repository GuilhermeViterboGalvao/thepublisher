(function(window, undefined) {
	
	var w = window;
	
	function PermanentLinkSelectorDialog(popup_, element_target, on_confirm, on_show, suggest_permanentLink){
		var popup = null;
		if(popup_ == undefined || popup_ == null){
			popup = "popup" + Math.ceil(Math.random() * 1000);
			document.body.appendChild($('<div>').attr('id', popup).css('display', 'none').attr('title', 'Teste de PermanentLink')[0]);
		} else {
			popup = popup_ + Math.ceil(Math.random() * 1000);
		}
		this.getPopup = function() {
			return popup;
		};
		
		var elementTarget = null;
		if(element_target == undefined || element_target == null){
			alert("Error on create object PermanentLinkSelectorDialog.\n For more information see error console log.");
			console.log("You need to pass a element target to call a show function when user click.");
			return;
		} else {
			elementTarget = element_target;
		}
		this.getElementTarget = function(){
			return elementTarget;
		};
		
		var onConfirm = null;
		if(on_confirm == undefined || on_confirm == null || typeof(on_confirm) != "function" ){
			alert("Error on create object PermanentLinkSelectorDialog.\n For more information see error console log.");
			console.log("You need to pass a function to call when user click on button confirm.");
			return;
		} else {
			onConfirm = on_confirm;
		}
		this.getOnConfirm = function(){
			return onConfirm;
		};
		
		var onShow = null;
		if(on_show == undefined || on_show == null || typeof(on_show) != "function" ){
			alert("Error on create object PermanentLinkSelectorDialog.\n For more information see error console log.");
			console.log("You need to pass a function to recive a show method.");
			return;
		} else {
			onShow = on_show;
		}
		this.getOnShow = function(){
			return onShow;
		};
		
		var instanceId = PermanentLinkSelectorDialog.instancesCount++;
		this.getInstanceId = function(){
			return instanceId; 
		};
		
		if(suggest_permanentLink != undefined && suggest_permanentLink != null && typeof(suggest_permanentLink) == "function" ) {
			PermanentLinkSelectorDialog.prototype.suggestPermanentLink = suggest_permanentLink;
		}		

		PermanentLinkSelectorDialog.instances[instanceId] = this;
		
		var contextReference = this;
		$('#' + elementTarget).click(function(){
			//now 'this' is the element clicked and not the PermanentLinkSelectorDialog
			contextReference.getOnShow()(this ,contextReference);
		});
	}
	
	PermanentLinkSelectorDialog.instances = {};
	
	PermanentLinkSelectorDialog.instancesCount = 0;
	
	PermanentLinkSelectorDialog.init = function(params){
		new PermanentLinkSelectorDialog(
			params.popup_                == undefined || params.popup_                == null ? undefined : params.popup_,
			params.element_target        == undefined || params.element_target        == null ? undefined : params.element_target,
			params.on_confirm            == undefined || params.on_confirm            == null ? undefined : params.on_confirm,
			params.on_show               == undefined || params.on_show               == null ? undefined : params.on_show,
			params.suggest_permanentLink == undefined || params.suggest_permanentLink == null ? undefined : params.suggest_permanentLink
		);
	};
	
	PermanentLinkSelectorDialog.prototype.check = function(currentValue){
		$('#message').css('display', 'none');
		var contextReference = this;		
		$.ajax({
			url : '/manager/ac-permanentlink?term=' + $('#term').val(),
			cache : false,
			success : function(data) {
				if(data[0].value != "" && data[0].value != currentValue){
					$('#message').css('display', 'block').html('PermanentLink já cadastrado');
				} else {
					contextReference.getOnConfirm()($('#term').val());
					$('#' + contextReference.getPopup()).dialog('close');
				}
			}
		});
	};
	
	PermanentLinkSelectorDialog.prototype.show = function(currentValue){
		var contextReference = this;
		var value = null;
		if (currentValue != undefined && currentValue != null && currentValue != "") {
			value = currentValue;
		} else {
			value = contextReference.convertToPermanentLink(contextReference.suggestPermanentLink());
		}
		$('#' + this.getPopup()).empty().append(
			$('<div>').append(
				$('<label>').attr('for', 'term').html('PermanentLink')
			).append(
				$('<input>').attr({
					'id'    : 'term',
					'name'  : 'term',
					'type'  : 'text',
					'value' : value
				}).css('margin-left', '5px')
			).append(
				$('<input>').attr({
					'id': 'check',
					'name' : 'check',
					'type' : 'button',
					'value' : 'Verificar'
				}).click(function(){contextReference.check(currentValue);})
			).append(
				$('<label>').css('display', 'none').attr('id', 'message')
			)
		).dialog({
			'height' : '250',		
			'width' : '400'
		});		
	};
	
	PermanentLinkSelectorDialog.prototype.suggestPermanentLink = function() {
		var header = $('input[name=header]').get(0).value;
		var title = $('input[name=title]').get(0).value;
		var category = $('a[href^="javascript:StrutsAC.search(\'photoselector_"]').get(0).innerHTML;
		
		var term = category != null && category != "" ? category + " - " : "";
		term += (
					title != null && title != "" ? 
						title  
					: 
						(
							header != null && header != "" ? 
								header 
							: 
								""
						)
				);
		
		return this.convertToPermanentLink(term);
	};
	
	PermanentLinkSelectorDialog.prototype.convertToPermanentLink = function(text) {
		return text
		   .replace(new RegExp(' - ',             'gi'), '/')
		   .replace(new RegExp('[áàâÁÀÂãÃ]',      'gi'), 'a')
	       .replace(new RegExp('[óòôÓÒÔõÕ]',      'gi'), 'o')
	       .replace(new RegExp('[úùÚÙ]',          'gi'), 'u')
	       .replace(new RegExp('[íìÍÌ]',          'gi'), 'i')
	       .replace(new RegExp('[éèêÉÈÊ]',        'gi'), 'e')
	   	   .replace(new RegExp('[çÇ]',            'gi'), 'c')		
		   .replace(new RegExp('[^a-zA-Z0-9/ -]', 'gi'),  '')		   
		   .trim()
		   .replace(new RegExp(' ',               'gi'), '-')
		   .toLowerCase();
	};
		
	w.PermanentLinkSelectorDialog = PermanentLinkSelectorDialog.init;
	w.PermanentLinkSelectorDialog.instances = PermanentLinkSelectorDialog.instances;	
	w.suggestPermanentLink = PermanentLinkSelectorDialog.prototype.suggestPermanentLink;
	w.convertToPermanentLink = PermanentLinkSelectorDialog.prototype.convertToPermanentLink;
	
})(window);
