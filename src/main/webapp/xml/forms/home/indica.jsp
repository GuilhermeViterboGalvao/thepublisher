<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<style type="text/css">
	object { margin-top: 22px; }
	.entrie { background: #cccccc; }
	.entrie .field { margin: 10px; }
	.entrie div.controller { float: left; width: 80%; position: relative; }
	.entrie div.controller .button { float: left; margin-left: 15px; }
	.entrie div.controller .close { position: absolute; left: 677px; }
	.swfupload { position: absolute; z-index: 10; }
	img.photoselector { width: 130px; height: 80px; float: left; }
	div.entrie-head { float: left; z-index: 10; width: 100%; height: 82px; }
</style>
<script type="text/javascript" src="/manager/js/swfDialog.js"></script>
<script type="text/javascript">
	function _up(e) {
		var id = e.id.replace("up", "");		
		if(id != 1){
			var img = document.getElementById("photoId" + id + "_ACS");
			var input = document.getElementById("photoId" + id);
			var title = document.getElementById("title" + id);
			var price = document.getElementById("price" + id);
			var description = document.getElementById("description" + id);
			var link = document.getElementById("link" + id);
			var date = document.getElementById("date" + id);
			
			id--;
			
			var imgUp = document.getElementById("photoId" + id + "_ACS");
			var cloneImgUp = imgUp.cloneNode(true);
			var inputUp = document.getElementById("photoId" + id);
			var cloneInputUp = inputUp.cloneNode(true);
			var titleUp = document.getElementById("title" + id);
			var cloneTitleUp = titleUp.cloneNode(true);
			var priceUp = document.getElementById("price" + id);
			var clonePriceUp = priceUp.cloneNode(true);
			var descriptionUp = document.getElementById("description" + id);
			var cloneDescriptionUp = descriptionUp.cloneNode(true);
			var linkUp = document.getElementById("link" + id);
			var cloneLinkUp = linkUp.cloneNode(true);
			var dateUp = document.getElementById("date" + id);
			var cloneDateUp = dateUp.cloneNode(true);
			
			imgUp.src = img.src;
			inputUp.value = input.value;
			titleUp.value = title.value;
			priceUp.value = price.value;
			descriptionUp.value = description.value;
			linkUp.value = link.value;
			dateUp.value = date.value;
			
			img.src = cloneImgUp.src;
			input.value = cloneInputUp.value;
			title.value = cloneTitleUp.value;
			price.value = clonePriceUp.value;
			description.value = cloneDescriptionUp.value;
			link.value = cloneLinkUp.value;
			date.value = cloneDateUp.value;
		}
	}
	function _down(e, max) {
		var id = e.id.replace("down", "");		
		if(id != max){
			var img = document.getElementById("photoId" + id + "_ACS");
			var input = document.getElementById("photoId" + id);
			var title = document.getElementById("title" + id);
			var price = document.getElementById("price" + id);
			var description = document.getElementById("description" + id);
			var link = document.getElementById("link" + id);
			var date = document.getElementById("date" + id);
			
			id++;
			
			var imgUp = document.getElementById("photoId" + id + "_ACS");
			var cloneImgUp = imgUp.cloneNode(true);
			var inputUp = document.getElementById("photoId" + id);
			var cloneInputUp = inputUp.cloneNode(true);
			var titleUp = document.getElementById("title" + id);
			var cloneTitleUp = titleUp.cloneNode(true);
			var priceUp = document.getElementById("price" + id);
			var clonePriceUp = priceUp.cloneNode(true);
			var descriptionUp = document.getElementById("description" + id);
			var cloneDescriptionUp = descriptionUp.cloneNode(true);
			var linkUp = document.getElementById("link" + id);
			var cloneLinkUp = linkUp.cloneNode(true);
			var dateUp = document.getElementById("date" + id);
			var cloneDateUp = dateUp.cloneNode(true);
			
			imgUp.src = img.src;
			inputUp.value = input.value;
			titleUp.value = title.value;
			priceUp.value = price.value;
			descriptionUp.value = description.value;
			linkUp.value = link.value;
			dateUp.value = date.value;
			
			img.src = cloneImgUp.src;
			input.value = cloneInputUp.value;
			title.value = cloneTitleUp.value;
			price.value = clonePriceUp.value;
			description.value = cloneDescriptionUp.value;
			link.value = cloneLinkUp.value;
			date.value = cloneDateUp.value;
		}
	}
	function _del(e) {
		e = e.parentNode.parentNode.parentNode.parentNode;
		e.parentNode.removeChild(e);
	}
</script>

<div class="ym-form linearize-form">
	<div class="ym-fbox-text">
		<s:iterator value="entries" status="st">
		
			<s:div id="entrie%{#st.count}" cssClass="entrie">
			
				<div class="entrie-head">
					<p:photoselector
						photoType="isTatame"
						id="photoId%{#st.count}"  
						name="photoId"
						delay="500" 
						pageSize="60"
						minLength="5" 
						showToolTip="true"
						display="top['photoId']"
						url="/manager/ac-photo"
						initialUrl="/manager/ac-photo"
						imagePattern="/img/[value]_130x80.jpg"
						loadingImage="/manager/img/loader.gif"
						evaluator="function(data) { 
							return '/img/' + data.value + '_130x80.jpg';
						}" 
					/>
					<div class="controller">
						<div class="button">
							<s:a id="swf%{#st.count}"  href="javascript:void(0);" cssClass="ym-button">upload</s:a>
							<script type="text/javascript">
								SWFDialog({
									sessionId    : '<s:property value="sessionId"/>',
									targetButton : 'swf<s:property value="#st.count"/>',
									photoType    : 'isTatame'
								});
							</script>							
						</div>
						<div class="button">
							<s:a id="up%{#st.count}"   href="javascript:void(0);" cssClass="ym-button" onclick="_up(this);">sobe</s:a>
						</div>
						<div class="button">
							<s:a id="down%{#st.count}" href="javascript:void(0);" cssClass="ym-button" onclick="_down(this, %{entries.size()});">desce</s:a>
						</div>
						<div class="close">
							<img id="del<s:property value="#st.count"/>" src="/manager/img/close.gif" onclick="_del(this);"/>
						</div>					
						<p:articleselectordialog category="Tatame" cssClass="button" url="article_select" text="matérias" onArticleClick="function(article){
							$('#photoId%{#st.count}_ACS').attr('src', article.photoId > 0 ? '/img/' + article.photoId + '_150x150.jpg' : '/manager/img/photo_80x60.jpg');
							$('#photoId%{#st.count}').val(article.photoId > 0 ? '/img/' + article.photoId + '_150x150.jpg' : '/manager/img/photo_80x60.jpg');
							$('#link%{#st.count}').val(article.url);
							$('#date%{#st.count}').val(article.publishedAt);
						}"/>					
					</div>				
				</div>
				
				<div class="field">
					<label for="title">Título:</label>
					<s:textfield id="title%{#st.count}" name="title" value="%{top['title']}"/>
				</div>
				
				<div class="field">
					<label for="price">Preço:</label>
					<s:textfield id="price%{#st.count}" name="price" value="%{top['price']}"/>
				</div>
				
				<div class="field">
					<label for="description">Descrição:</label>
					<s:textfield id="description%{#st.count}" name="description" value="%{top['description']}"/>
				</div>
				
				<div class="field">
					<label for="link">Link:</label>
					<s:textfield id="link%{#st.count}" name="link" value="%{top['link']}"/>
				</div>
				
				<div class="field">
					<label for="date">Data:</label>
					<s:textfield id="date%{#st.count}" name="date" value="%{top['date']}"/>
				</div>				

				<hr/>			
			</s:div>
		</s:iterator>
	</div>
</div>