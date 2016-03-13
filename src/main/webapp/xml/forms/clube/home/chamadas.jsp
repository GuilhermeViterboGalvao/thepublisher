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
<script type="text/javascript" src="/manager/js/SwfDialog.js"></script>
<script type="text/javascript">
	function _up(e) {
		var id = e.id.replace("up", "");		
		if(id != 1){
			var img = document.getElementById("photoId" + id + "_ACS");
			var input = document.getElementById("photoId" + id);
			var link = document.getElementById("link" + id);
			var title = document.getElementById("title" + id);
			var text = document.getElementById("text" + id);
			
			id--;
			
			var imgUp = document.getElementById("photoId" + id + "_ACS");
			var cloneImgUp = imgUp.cloneNode(true);
			var inputUp = document.getElementById("photoId" + id);
			var cloneInputUp = inputUp.cloneNode(true);
			var linkUp = document.getElementById("link" + id);
			var cloneLinkUp = linkUp.cloneNode(true);			
			var titleUp = document.getElementById("title" + id);
			var cloneTitleUp = titleUp.cloneNode(true);			
			var textUp = document.getElementById("text" + id);
			var cloneTextUp = textUp.cloneNode(true);
			
			imgUp.src = img.src;
			inputUp.value = input.value;
			linkUp.value = link.value;
			titleUp.value = title.value;
			textUp.value = text.value;
			
			img.src = cloneImgUp.src;
			input.value = cloneInputUp.value;
			link.value = cloneLinkUp.value;
			title.value = cloneTitleUp.value;
			text.value = cloneTextUp.value;
		}
	}
	function _down(e, max) {
		var id = e.id.replace("down", "");		
		if(id != max){
			var img = document.getElementById("photoId" + id + "_ACS");
			var input = document.getElementById("photoId" + id);
			var link = document.getElementById("link" + id);
			var title = document.getElementById("title" + id);
			var text = document.getElementById("text" + id);
			
			id++;
			
			var imgUp = document.getElementById("photoId" + id + "_ACS");
			var cloneImgUp = imgUp.cloneNode(true);
			var inputUp = document.getElementById("photoId" + id);
			var cloneInputUp = inputUp.cloneNode(true);
			var linkUp = document.getElementById("link" + id);
			var cloneLinkUp = linkUp.cloneNode(true);			
			var titleUp = document.getElementById("title" + id);
			var cloneTitleUp = titleUp.cloneNode(true);			
			var textUp = document.getElementById("text" + id);
			var cloneTextUp = textUp.cloneNode(true);
			
			imgUp.src = img.src;
			inputUp.value = input.value;
			linkUp.value = link.value;
			titleUp.value = title.value;
			textUp.value = text.value;
			
			img.src = cloneImgUp.src;
			input.value = cloneInputUp.value;
			link.value = cloneLinkUp.value;
			title.value = cloneTitleUp.value;
			text.value = cloneTextUp.value;
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
									targetButton : 'swf<s:property value="#st.count"/>'
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
					</div>				
				</div>

				<div class="field">
					<label for="title">Título:</label>
					<s:textfield id="title%{#st.count}" name="title" value="%{top['title']}"/>
				</div>

				<div class="field">
					<label for="text">Texto:</label>
					<s:textarea id="text%{#st.count}" name="text" value="%{top['text']}" rows="5"/>
				</div>
				
				<div class="field">
					<label for="link">Link:</label>
					<s:textfield id="link%{#st.count}" name="link" value="%{top['link']}"/>
				</div>
				
				<hr/>
			</s:div>
		</s:iterator>
	</div>
</div>