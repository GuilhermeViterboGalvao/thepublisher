<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<%
	boolean preview = false;
	try {
		preview = Boolean.parseBoolean(request.getParameter("preview"));	
	} catch (Exception e) {
		preview = false;
	}
	request.setAttribute("preview", preview);
%>
<s:if test="published || #request.preview">
	<article id="${id}">
		<span class="title">${title}</span>
		<span class="author">${createdBy.name} - <s:date name="publishedAt" format="dd/MM/yyyy hh:mm"/></span>
		<span class="note">${note}</span>
		<div class="content">
			<s:property value="articleContent" escapeHtml="false"/>
		</div>		
	</article>
</s:if>
<s:else>
	<script type="text/javascript">$(function(){window.location.href="/404.jsp";});</script>
</s:else>