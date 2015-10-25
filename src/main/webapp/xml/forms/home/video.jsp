<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<div class="ym-form linearize-form">
	<div class="ym-fbox-text">
		<s:iterator value="entries" status="st">		
			<s:div id="entrie%{#st.count}" cssClass="entrie">				
				<div class="field">
					<label for="code">Código:</label>
					<s:textarea id="code%{#st.count}" name="code" value="%{top['code']}"/>
				</div>
				
				<div class="field">
					<label for="text">Texto:</label>
					<s:textarea id="text%{#st.count}" name="text" value="%{top['text']}"/>
				</div>		

				<hr/>
			</s:div>
		</s:iterator>
	</div>
</div>