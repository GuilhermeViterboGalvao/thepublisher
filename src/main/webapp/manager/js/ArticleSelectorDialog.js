(function(window, undefined) {
	var w = window;
	
	function ArticleSelectorDialog(popup_, target_button, on_article_click, url_, width_, height_, category_){		
		var popup = null;
		if(popup_ == undefined || popup_ == null){
			popup = "popup" + Math.ceil(Math.random() * 1000);
			document.body.appendChild($('<div>').attr('id', popup).css('display', 'none').attr('title', 'Selecione uma matéria')[0]);
		} else {
			popup = popup_ + Math.ceil(Math.random() * 1000);
		}
		this.setPopup = function (p) {
			popup = p;
		};		
		this.getPopup = function() {
			return popup;
		};
		
		var targetButton = null;
		if(target_button != undefined && target_button != null) {
			targetButton = target_button;
			var contextReference = this;
			$('#' + targetButton).click(function(){contextReference.show();});
		}		
		this.setTargetButton = function (tb) {
			targetButton = tb;
		};
		this.getTargetButton = function(){
			return targetButton;
		};
		
		var onArticleClick = null;
		if(on_article_click == undefined || on_article_click == null || typeof(on_article_click) != "function" ){
			alert("Error on create object ArticleSelectorDialog.\n For more information see error console log.");
			console.log("You need to pass a function to call when a image of article is clicked.");
			return;
		} else {
			onArticleClick = on_article_click;
		}
		this.setOnArticleClick = function(onAC){
			onArticleClick = onAC;
		};
		this.getOnArticleClick = function(){
			return onArticleClick;
		};
		
		var url = null;
		if(url_ == undefined || url_ == null){
			alert("Error on create object ArticleSelectorDialog.\n For more information see error console log.");
			console.log("You need to pass an url target to receive a data in json.");
			return;
		} else {
			url = url_;
		}
		this.setUrl = function(u){
			url = u;
		};		
		this.getUrl = function(){
			return url;
		};
		
		var width = null;
		if(width_ == undefined || width_ == null){
			width = 895;
		} else {
			width = width_;
		}
		this.setWidth = function(w){
			width = w;
		};
		this.getWidth = function(){
			return width;
		};
		
		var height = null;
		if (height_ == undefined || height_ == null) {
			height = 600;
		} else {
			height = height_;
		}
		this.setHeight = function(h){
			height = h;
		};
		this.getHeight = function(){
			return height;
		};
		
		var category = category_;
		this.getCategory = function() {
			return category; 
		};
		
		var instanceId = ArticleSelectorDialog.instancesCount++;
		this.getInstanceId = function(){
			return instanceId; 
		};

		ArticleSelectorDialog.instances[instanceId] = this;
		
		this.currentPage = 1;		
		this.pageSize = 50;		
		this.pages = 0;		
		this.query = "";
		this.data = null;
	}
	

	ArticleSelectorDialog.instances = {};
	
	ArticleSelectorDialog.instancesCount = 0;
	
	ArticleSelectorDialog.init = function(params){
		new ArticleSelectorDialog(
			params.popup_ == undefined || params.popup_ == null ? undefined : params.popup_, 
			params.target_button == undefined || params.target_button == null ? undefined : params.target_button,
			params.on_article_click == undefined || params.on_article_click == null ? undefined : params.on_article_click,
			params.url_ == undefined || params.url_ == null ? undefined : params.url_,
			params.width_ == undefined || params.width_ == null ? undefined : params.width_,
			params.heght_ == undefined || params.height_ == null ? undefined : params.height_,
			params.category_ == undefined || params.category_ == null ? undefined : params.category_
		);
	};
	
	ArticleSelectorDialog.prototype.refreshPhotoSelectorDialog = function() {
		$("#" + this.getPopup()).empty();
	};
	
	ArticleSelectorDialog.prototype.clear = function() {
		this.currentPage = 1;
		this.pageSize = 50;
		this.pages = 0;
		this.query = "";
		$("#" + this.getPopup()).empty();
	};
	
	ArticleSelectorDialog.prototype.changePage = function(page) {
		this.currentPage = page;
		this.refreshPhotoSelectorDialog();
		this.show();
	};
	
	ArticleSelectorDialog.prototype.nextPage = function() {
		if (this.currentPage < this.pages)
			this.currentPage++;
		this.refreshPhotoSelectorDialog();
		this.show();
	};
	
	ArticleSelectorDialog.prototype.previousPage = function() {
		if (this.currentPage > 1)
			this.currentPage--;
		this.refreshPhotoSelectorDialog();
		this.show();
	};
	
	ArticleSelectorDialog.prototype.onClickBtnSearch = function() {
		this.query = $("#query" + this.getInstanceId()).val();
		this.currentPage=1;
		this.refreshPhotoSelectorDialog(); 
		this.show();
	};
	
	ArticleSelectorDialog.prototype.show = function() {

		var url = this.getUrl() + "?";
		if (this.query != null && this.query != "")	url += "query=" + this.query + "&";
		if (this.pageSize > 0) url += "pageSize=" + this.pageSize + "&";
		if (this.currentPage > 0) url += "currentPage=" + this.currentPage++ + "&";
		if (this.getCategory()) url += "category=" + this.getCategory(); 
		
		var contextReference = this;
		
		$.ajax({
			url : url,
			cache : false,
			success : function(data) {
				contextReference.currentPage = data.currentPage;
				contextReference.pageSize = data.pageSize;				
				contextReference.pages = data.pages;
				contextReference.query = data.query;
				contextReference.data = data;
				
				$('#' + contextReference.getPopup()).empty().append(
					$("<div>").attr({
						'id':'divSearch',
						'name':'divSearch'
					}).css({
						'margin':'10px 10px 10px 0px',
						'position':'relative',						
						'width':'360px',
						'height':'70px'
					}).addClass('ym-fbox-button').append(
						$('<input>').attr({
							'id':'query' + contextReference.getInstanceId(),
							'name':'query' + contextReference.getInstanceId(),
							'type':'text'
						}).css('width','200px')								
					).append(
						$('<input>').attr({
							'id':'btnSearch' + contextReference.getInstanceId(),
							'name':'btnSearch' + contextReference.getInstanceId(),
							'type':'button',
							'value':'Procurar'
						}).css('margin-left','30px').click(function(){contextReference.onClickBtnSearch();})
					)
				);
				
				var d = data;
				for (var i = 0; i < data.articles.length; i++) {
					var article = data.articles[i];
					$('#' + contextReference.getPopup()).append(
						$('<a>').attr('id', i).attr('href','javascript:void(0);').html(
							article.publishedAt + ' - ' + article.title + ' - Autor: ' + article.authorName 
						).append(
							'<br>'
						).append(
							'<hr>'
						).click(function(){
							var position = this.id;
							contextReference.getOnArticleClick()(d.articles[position], contextReference.getInstanceId());
							$('#' + contextReference.getPopup()).dialog('close');
						})
					);
				}
				
				$('#' + contextReference.getPopup()).append(
					$("<div>").attr('id','paginator').addClass('ym-grid').css({
						'margin':'10px 0',
						'width':'99%'
					})
				);
				
				if (data.currentPage > 1) {
					$("#paginator").append(
						$("<div>").addClass('ym-g33 ym-gl')	.append(
							$("<a>").attr('href','javascript:ArticleSelectorDialog.instances[' + contextReference.getInstanceId() + '].previousPage();').append(document.createTextNode("Página Anterior"))
						)
					);
				}
				
				var textAlign='';
				var gridSize='';
	
				if(data.currentPage == 1){
					textAlign = 'left';
					gridSize  = 'ym-g50';
				}else{
					textAlign = 'center';
					gridSize  = 'ym-g33';
				}
				
				$("#paginator").append(
					$("<div>").attr('id','cPage').addClass('ym-gl ' + gridSize).css('textAlign',textAlign)
				);
				
				if (data.currentPage == 1 && data.pages > 10) {					
					for (var i = 1; i <= 10; i++) {
						if (i == data.currentPage) {							
							$("#cPage").append(
								$("<span>").addClass('page').css('color', 'red').html(i)
							);							
						} else {							
							$("#cPage").append(
								$("<a>").attr('href','javascript:ArticleSelectorDialog.instances[' + contextReference.getInstanceId() + '].changePage(' + i + ');').append(document.createTextNode(i))
							);
						};
					}
				} else if(data.pages < 10){					
					for (var i = 1; i <= data.pages; i++) {
						if (i == data.currentPage) {							
							$("#cPage").append(
								$("<span>").addClass('page').css('color', 'red').html(i)
							);							
						} else {							
							$("#cPage").append(
								$("<a>").attr('href','javascript:ArticleSelectorDialog.instances[' + contextReference.getInstanceId() + '].changePage(' + i + ');').append(document.createTextNode(i))								
							);
						};
					};					
				} else {				
					var intervalos = data.pages / 10;
					if (data.pages % 10 != 0) intervalos++;
					var posicao = Math.floor(data.currentPage / 10 + (data.currentPage % 10 != 0 ? 1 : 0));
					var fim = posicao * 10;
					var inicio = fim - 9;
					for (inicio; inicio <= fim; inicio++) {
						if (inicio == data.currentPage) {
							$("#paginator").append(
								$("#cPage").append(
									$("<span>").addClass("page").css('color', 'red').html(inicio)
								)
							);	
						} else {
							$("#paginator").append(
								$("#cPage").append(
									$("<a>").attr('href','javascript:ArticleSelectorDialog.instances[' + contextReference.getInstanceId() + '].changePage('+ inicio+ ');').append(document.createTextNode(inicio))
								)
							);
						};
					};
				}
				
				if (data.currentPage < data.pages) {					
					$("#paginator").append(
						$("<div>").addClass('ym-gl ' + gridSize).css('textAlign', 'end').append( 
							$("<a>").attr('href', 'javascript:ArticleSelectorDialog.instances[' + contextReference.getInstanceId() + '].nextPage();').append(document.createTextNode("Próxima página"))
						)
					);
				}
				
				$('#' + contextReference.getPopup()).dialog({
					height : contextReference.getHeight(),		
					width : contextReference.getWidth()
				});
			}
		});
	};
	
	w.ArticleSelectorDialog = ArticleSelectorDialog.init;
	w.ArticleSelectorDialog.instances = ArticleSelectorDialog.instances;
	
})(window);