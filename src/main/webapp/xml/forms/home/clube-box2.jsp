<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<style type="text/css">
	object { margin-top: 22px; }
	.entrie { background: #cccccc; }
	.entrie .field { margin: 10px; }
	.entrie div.controller { float: left; width: 80%; position: relative; }
	.entrie div.controller .button { float: left; margin-left: 15px; }
	.entrie div.controller .close { position: absolute; left: 800px; }
	.swfupload { position: absolute; z-index: 10; }
	img.photoselector { width: 130px; height: 80px; float: left; }
	div.entrie-head { float: left; z-index: 10; width: 100%; height: 27px; }
</style>

<div class="ym-form linearize-form">
	<div class="ym-fbox-text">
		<s:iterator value="entries" status="st">
		
			<s:div id="entrie%{#st.count}" cssClass="entrie">
			
				<div class="entrie-head">
					<div class="controller">
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
					<label for="text">Descrição:</label>
					<s:textarea id="text%{#st.count}" name="text" value="%{top['text']}"/>
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