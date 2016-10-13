<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<s:if test="published">
	<div class="content">
		<div class="info">
			<div class="poll">
			</div>
			<div class="live-chat">
			</div>			
		</div>
		<div class="live-stats">
			<s:property value="code" escapeHtml="false"/>
		</div>
	</div>
</s:if>
<s:else>
	<div class="live-stats-info">
		<p>Evento indisponível no momento.</p>
	</div>
</s:else>