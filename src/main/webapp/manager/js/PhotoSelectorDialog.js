(function(window, undefined) {
	
	var w = window;
	
	function PhotoSelectorDialog(currentPage, pageSize, pages, query, popup, button, addedImages, onPhotoClick, photoType, isPhotoElement){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.pages = pages;
		this.query = query;
		this.onPhotoClick = onPhotoClick;
		this.photoType = photoType;
		this.isPhotoElement = isPhotoElement;
		if (popup == undefined || popup == null){
			this.popup = "popup";
			window.document.body.appendChild($('<div>').attr('id', 'popup').css('display', 'none').attr('title', 'Selecione uma foto')[0]);
		} else {
			this.popup = popup;
		}
		this.addedImages = addedImages;		
		if (button != undefined && button != null && button != "") {
			var reference = this;
			$('#' + button).click(function(){ 
				reference.show(); 
			});
		}
	}
	
	PhotoSelectorDialog.prototype.refreshPhotoSelectorDialog = function() {
		$("#" + this.popup).empty();
	};
	
	PhotoSelectorDialog.prototype.clear = function() {
		this.currentPage = 1;
		this.pageSize = 50;
		this.pages = 0;
		this.query = "";
		$("#" + this.popup).empty();
	};
	
	PhotoSelectorDialog.prototype.changePage = function(page) {
		this.currentPage = page;
		this.refreshPhotoSelectorDialog();
		this.show();
	};
	
	PhotoSelectorDialog.prototype.nextPage = function() {
		if (this.currentPage < this.pages)
			this.currentPage++;
		this.refreshPhotoSelectorDialog();
		this.show();
	};
	
	PhotoSelectorDialog.prototype.previousPage = function() {
		if (this.currentPage > 1)
			this.currentPage--;
		this.refreshPhotoSelectorDialog();
		this.show();
	};
	
	PhotoSelectorDialog.prototype.onClickBtnSearch = function() {
		this.query = $("#query").val();
		this.currentPage=1;
		this.refreshPhotoSelectorDialog(); 
		this.show();
	};
	
	PhotoSelectorDialog.prototype.show = function() {
	
		var url = "photo_select?";
		if (this.query != null && this.query != "")
			url += "query=" + this.query;
		if (this.pageSize > 0)
			url += "&pageSize=" + this.pageSize;
		if (this.currentPage > 0)
			url += "&currentPage=" + this.currentPage++;
		if (this.photoType != null && this.photoType != "")
			url += "&" + this.photoType + "=true";
	
		var referecePhotoSelectorDialog = this;
		
		$.ajax({
			url : url,
			cache : false,
			success : function(data) {
				var json = data;
	
				referecePhotoSelectorDialog.currentPage = json.currentPage;
				referecePhotoSelectorDialog.pageSize = json.pageSize;
				referecePhotoSelectorDialog.pages = json.pages;
				referecePhotoSelectorDialog.query = json.query;
						
				$('#' + referecePhotoSelectorDialog.popup)
				.empty()
				.append(
					$("<div>")
					.attr('id','divSearch')
					.attr('name','divSearch')
					.addClass('ym-fbox-button')
					.css('position','relative')
					.css('margin','10px 10px 10px 0px')
					.css('width','360px')
					.css('height','70px')
					.append(
						$('<input>')
						.attr('id','query')
						.attr('name','query')
						.attr('type','text')
						.css('width','200px')								
					)
					.append(
						$('<input>')
						.attr('id','btnSearch')
						.attr('name','btnSearch')
						.attr('type','button')
						.attr('value','Procurar')
						.css('margin-left','30px')
						.click(function(){referecePhotoSelectorDialog.onClickBtnSearch();})
					)
				);
				
				for (var i = 0; i < json.photos.length; i++) {					
					var tagA = $('<a>').append(
						$('<div>').css({
							'width'  : '80',
							'height' : '60',
							'float'  : 'left'
						}).append(
							$('<img>').css('border','3px solid white').attr({
								'src' :'/img/' + json.photos[i].id + '_80x60.jpg',
								'alt' : json.photos[i].description 
										+ 
										(json.photos[i].photographer && json.photos[i].photographer.name ? ' Foto: ' + json.photos[i].photographer.name : ''),
								'width': '80',
								'height': '60'
							})
						)
					);
					if (referecePhotoSelectorDialog.onPhotoClick != undefined && referecePhotoSelectorDialog.onPhotoClick != null) {
						var popupReference = referecePhotoSelectorDialog.popup; 
						tagA.attr('href', 'javascript:void(0);').click(function(){
							referecePhotoSelectorDialog.onPhotoClick(this.photo);
							$("#" + popupReference).empty().dialog('close');
						});
						tagA[0].photo = json.photos[i];
					} else {
						var txt = referecePhotoSelectorDialog.escapeHTML(json.photos[i].description);
						if (json.photos[i].photographer != null && json.photos[i].photographer.name != null) {
							txt += referecePhotoSelectorDialog.escapeHTML(" Foto: " + json.photos[i].photographer.name); 
						}
						var photographerId = null;
						if (json.photos[i].photographer && json.photos[i].photographer.id) {
							photographerId = json.photos[i].photographer.id;
						}
						var isSpot = false;
						if (json.photos[i].isSpot) {
							isSpot = json.photos[i].isSpot; 
						}
						var verticalCenter = "0.5";
						if (json.photos[i].verticalCenter) {
							verticalCenter = "" + json.photos[i].verticalCenter;
							verticalCenter = verticalCenter.replace(",", ".");
						}
						var published = false;
						if (json.photos[i].published) {
							published = json.photos[i].published;
						}
						var date = new Date();
						if (json.photos[i].date) {
							date = new Date(json.photos[i].date);
						}
						var day = date.getDate();
						if (day < 10) {
							day = "0" + day;
						}
						var month = date.getMonth();
						if (month < 10) {
							month = "0" + month;
						}
						var year = date.getFullYear();
						date  = day + "/" + month + "/" + year;
						tagA.attr('href','javascript:window.photoSelectorDialog.addImage('
							+ json.photos[i].id + ',"' 
							+ txt.trim() + '",' 
							+ photographerId + ',' 
							+ isSpot + ',"'
							+ verticalCenter + '",' 
							+ published + ',"' 
							+ date + '");');
					}					
					$('#' + referecePhotoSelectorDialog.popup).append(tagA);
				}
				
				$('#' + referecePhotoSelectorDialog.popup)
				.append(
					$("<div>")
					.attr('id','paginator')
					.addClass('ym-grid')
					.css('padding-top','15px')
					.css('width','99%')
				);
				
				if (json.currentPage > 1) {
					$("#paginator")
					.append(
						$("<div>")
						.addClass('ym-g33 ym-gl')
						.append(
							$("<a>")						
							.attr('href','javascript:window.photoSelectorDialog.previousPage();')
							.append(document.createTextNode("Página Anterior"))
						)
					);
				}
				
				var textAlign='';
				var gridSize='';
	
				if(json.currentPage == 1){
					textAlign = 'left';
					gridSize  = 'ym-g50';
				}else{
					textAlign = 'center';
					gridSize  = 'ym-g33';
				}
				
				$("#paginator").
				append(
					$("<div>")
					.attr('id','cPage')
					.addClass('ym-gl ' + gridSize)
					.css('textAlign',textAlign)
				);
				
				if (json.currentPage == 1 && json.pages > 10) {
					
					for ( var i = 1; i <= 10; i++) {
						if (i == json.currentPage) {
							
							$("#cPage")							
							.append(
								$("<span>")
								.addClass('page')
								//.css('color','#000000')
								.html(i)
							);
							
						} else {
							
							$("#cPage")
							.append(
								$("<a>")
								.attr('href','javascript:window.photoSelectorDialog.changePage(' + i + ');')
								//.css('color','#4d87c7')
								.append(document.createTextNode(i))
							);
						};
					}
				} else if(json.pages < 10){
					
					for ( var i = 1; i <= json.pages; i++) {
						if (i == json.currentPage) {
							
							$("#cPage")							
							.append(
								$("<span>")
								.addClass('page')
								//.css('color','#000000')							
								.html(i)
							);
							
						} else {
							
							$("#cPage")
							.append(
								$("<a>")
								.attr('href','javascript:window.photoSelectorDialog.changePage(' + i + ');')							
								.append(document.createTextNode(i))
								
							);
						};
					};
					
				} else {
				
					var intervalos = json.pages / 10;
					if (json.pages % 10 != 0)
						intervalos++;
					var posicao = Math.floor(json.currentPage / 10 + (json.currentPage % 10 != 0 ? 1 : 0));
					var fim = posicao * 10;
					var inicio = fim - 9;
					for (inicio; inicio <= fim; inicio++) {
						if (inicio == json.currentPage) {
							$("#paginator")
							.append(
								$("#cPage")
								.append(
									$("<span>")
									.addClass("page")								
									//.css('color','#000000')
									.html(inicio)
								)
							);
	
						} else {
							$("#paginator")
							.append(
								$("#cPage")
								.append(
									$("<a>")
									.attr('href','javascript:window.photoSelectorDialog.changePage('+ inicio+ ');')
									//.css('color','#4d87c7')
									.append(document.createTextNode(inicio))
								)
							);
						};
					};
				}
	
				if (json.currentPage < json.pages) {
					
					$("#paginator")
					.append(
						$("<div>")
						.addClass('ym-gl '+gridSize)
						.css('textAlign','end')
						.append( 
							$("<a>")						
							.attr('href','javascript:window.photoSelectorDialog.nextPage();')
							//.css('color','#4d87c7')
							.append(document.createTextNode("Próxima página"))
						)
					);
				}
				
				$('#' + referecePhotoSelectorDialog.popup).dialog({
					height : 600,		
					width : 895
				});
			}
		});
	
	};
	
	PhotoSelectorDialog.prototype.escapeHTML = function(txt) {
		return txt === undefined ? "" : txt.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\"/g,"&quot;");
	};
	
	PhotoSelectorDialog.prototype.addImage = function(id, description, photographerId, isSpot, verticalCenter, published, date){
		var referencePhotoSelectorDialog = this;
		var sortable = $('#sortable');
		var draggables = sortable.children("#draggable");
		var existis = false;
		for (var i = 0; i < draggables.length; i++) {
			var value = $(draggables[i]).children("input").val();
			if (value == id) {
				existis = true;
				break;
			}
		}
		var divDraggable = null;
		if (!existis) {
			sortable.append(
					(divDraggable = $('<div>')
					.attr('id', 'draggable')
					.attr('name', 'foto' + this.addedImages)
					.addClass('d-photo')
					.append(
						$('<input>')
						.attr('type', 'hidden')
						.attr('id', 'fotoid' + this.addedImages)
						.attr('name', this.isPhotoElement != null && !this.isPhotoElement ? 'photos[' + this.addedImages + '].id' : 'photos[' + this.addedImages + '].photo.id')
						.attr('value', id)
					).append(
						$('<img>')
						.attr('src','/img/'+id+'_130x80.jpg')
						.attr('id','fotoimg'+id)
						.addClass('d-photo')
					).append(
						$('<img>')
						.attr('src','/manager/img/close.gif')
						.attr('id','close'+id)
						.addClass('d-photo-nav')
						.click(function(){ referencePhotoSelectorDialog.remove(this.parentNode); })
					).append(
						$('<textarea>')
						.attr('id','fotolegenda' + this.addedImages)
						.attr('name','photos['+this.addedImages+'].description')
						.html(description)
						.addClass('d-photo')						
					).append(
						$('<input>').attr({
							'type' : 'hidden',
							'name' : 'photos[' + this.addedImages + '].verticalCenter',
							'value': verticalCenter
						})				
					).append(
						$('<input>').attr({
							'type' : 'hidden',
							'name' : 'photos[' + this.addedImages + '].published',
							'value': published
						})			
					).append(
						$('<input>').attr({
							'type' : 'hidden',
							'name' : 'photos[' + this.addedImages + '].date',
							'value': date
						})
					)
				));
			if (isSpot) {
				divDraggable.append(
					$('<input>').attr({
						'type' : 'hidden',
						'name' : 'photos[' + this.addedImages + '].isSpot',
						'value': true
					})
				);
			}
			if (photographerId && photographerId > 0) {
				divDraggable.append(
					$('<input>').attr({
						'type' : 'hidden',
						'name' : 'photos[' + this.addedImages + '].photographer.id',
						'value': photographerId
					})						
				);
			}
			this.addedImages++;
			this.currentPage = 1;
			this.pageSize = 50;
			this.pages = 0;
			this.query = "";
			//$("#" + this.popup).empty().dialog('close'); Não fechar o popup após o usuário selecionar a imagem.	
		}
	};
	
	PhotoSelectorDialog.prototype.remove = function(element){
		$(element).remove();	
		
		if(this.addedImages > 0) {
			
			this.addedImages--;
			
	    	var childrens = $('#sortable').children();
	      	    	
	    	for(var i = 0; i < childrens.length; i++){
	    		var nodes = removeTextNodes(childrens[i]).childNodes;
	    		nodes[0].name = this.isPhotoElement != null && !this.isPhotoElement ? 'photos['+i+'].id' : 'photos['+i+'].photo.id';
	    		nodes[0].id = 'fotoid'+i;
	    		nodes[3].name = 'photos['+i+'].description';
	    		nodes[3].id = 'fotolegenda'+i;
	    		if (nodes[4]) {
		    		nodes[4].name = 'photos['+i+'].verticalCenter';	    			
	    		}
	    		if (nodes[5]) {
		    		nodes[5].name = 'photos['+i+'].published';	    			
	    		}
	    		if (nodes[6]) {
		    		nodes[6].name = 'photos['+i+'].date';	    			
	    		}
	    		if (nodes[7]) {
		    		nodes[7].name = 'photos['+i+'].photographer.id';	    			
	    		}	    		
	    		childrens[i].attributes["name"].value = 'foto'+i;            		
	    	}
		}
	};
	
	function removeTextNodes(element){
		for(var i = 0; i < element.childNodes.length; i++){
			if(element.childNodes[i].nodeType == 3){
				element.removeChild(element.childNodes[i]);
			}
		}
		return element;
	}
	
	w.PhotoSelectorDialog = init = function(params){
		w.photoSelectorDialog = new PhotoSelectorDialog(
			params.currentPage    == undefined || params.currentPage    == null ? 1          : params.currentPage, 
			params.pageSize       == undefined || params.pageSize       == null ? 50         : params.pageSize, 
			params.pages          == undefined || params.pages          == null ? 0          : params.pages, 
			params.query          == undefined || params.query          == null ? ""         : params.query, 
			params.popup          == undefined || params.popup          == null ? undefined : params.popup, 
			params.button         == undefined || params.button         == null ? undefined : params.button,
			params.addedImages    == undefined || params.addedImages    == null ? 0		   : params.addedImages,
			params.onPhotoClick   == undefined || params.onPhotoClick   == null ? null	   : params.onPhotoClick,
			params.photoType      == undefined || params.photoType      == null ? null	   : params.photoType,
			params.isPhotoElement == undefined || params.isPhotoElement == null ? null	   : params.isPhotoElement
		);
	};
	w.removeTextNodes = removeTextNodes;
})(window);