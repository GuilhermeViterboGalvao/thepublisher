<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<style type="text/css">
	object { margin-top: 22px; }
	.entrie { background: #cccccc; }
	.entrie .field { margin: 10px; }
	.entrie div.controller { float: left; width: 80%; position: relative; }
	.entrie div.controller .button { float: left; margin-left: 15px; }
	.entrie div.controller .close { position: absolute; left: 809px; }
	div.entrie-head { float: left; z-index: 10; width: 100%; height: 82px; }
</style>
<script type="text/javascript" src="/manager/js/SwfDialog.js"></script>
<script type="text/javascript">
	function _up(e) {
		var id = e.id.replace("up", "");		
		if(id != 1){
			var text = document.getElementById("text" + id);
			var code = document.getElementById("code" + id);
			
			id--;

			var textUp = document.getElementById("text" + id);
			var cloneTextUp = textUp.cloneNode(true);						
			var codeUp = document.getElementById("code" + id);
			var cloneCodeUp = codeUp.cloneNode(true);
			
			textUp.value = text.value;
			codeUp.value = code.value;
			
			text.value = cloneTextUp.value;
			code.value = cloneCodeUp.value;
		}
	}
	function _down(e, max) {
		var id = e.id.replace("down", "");		
		if(id != max){
			var text = document.getElementById("text" + id);
			var code = document.getElementById("code" + id);
			
			id++;

			var textUp = document.getElementById("text" + id);
			var cloneTextUp = textUp.cloneNode(true);						
			var codeUp = document.getElementById("code" + id);
			var cloneCodeUp = codeUp.cloneNode(true);
			
			textUp.value = text.value;
			codeUp.value = code.value;
			
			text.value = cloneTextUp.value;
			code.value = cloneCodeUp.value;
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
					<div class="controller">
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
					<label for="text">Descrição para o membros pagantes:</label>
					<s:textfield id="text%{#st.count}" name="text" value="%{top['text']}"/>
				</div>
				<div class="field">
					<label for="text">Código de desconto:</label>
					<s:textfield id="text%{#st.count}" name="code" value="%{top['code']}"/>
				</div>
				<hr/>
			</s:div>
		</s:iterator>
	</div>
</div>