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
	<div class="padding-top-15">
		<div class="box-1024">	
    		<div class="title"><s:property value="title"/></div>
    		<div class="credits margin-top-10">	
				<div class="info">
			    	<p class="author"><a href="/search?query=${createdBy.name}"><s:property value="createdBy.name"/></a></p>
			    	<p class="date"><s:date name="publishedAt" format="dd/MM/yyyy hh:mm"/></p>
			    </div>
			    <div class="social-plugins">
			        <div class="addthis_sharing_toolbox"></div>
			        <script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5656ee9d0a89c810" async="async"></script>
			    </div>	
		    </div>
    	
    		<div class="text">
    			<s:property value="articleContent" escapeHtml="false"/>
    		</div>
    		<div class="tags">
				<b>Tags&nbsp;</b>
				<s:set var="tagsText" value="tagsText"/>
				<s:set var="tagsUrl" value="%{getTagsUrl('http://www.tatame.com.br/search?query=')}"/>
				<s:iterator value="#tagsText" status="st">
					<a href="<s:property value="#tagsUrl.get(#st.index)"/>"><s:property value="top"/>&nbsp;</a>
				</s:iterator>
			</div>
			<s:if test="forumEnabled">
				<div class="fb-comments margin-top-15" data-href="http://www.tatame.com.br/${permanentLink.uri}" data-num-posts="4" data-width="714"></div>
			</s:if>			
		</div>
	</div>
</s:if>
<s:else>
	<script type="text/javascript">$(function(){window.location.href="/404.jsp";});</script>
</s:else>