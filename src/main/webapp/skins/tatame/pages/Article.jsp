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
	    	<div class="box-714">
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

		    <div class="box-300 margin-left-10">
		    	<div id="tatame_300x100_ros" class="ads-300-100 box-shadow margin-bottom-10"></div>
		    	<div id="tatame_300x250_ros" class="ads-300-250 box-shadow margin-bottom-10"></div>
				<div class="black-belt-post-related box-shadow">
					<div class="dan">Notícias relacionadas</div>
				</div>
				<div class="related-posts box-shadow perfect-scroll margin-bottom-10">
					<s:iterator value="getLast(4)" status="st">
						<div class="related-item">
							<a href="/${permanentLink.uri}" title="${title}">
								<img alt="${photoId}" src="/img/${photo.id}_270x180.jpg" />
							</a>
							<div class="info">	
								<div class="title">
									<a href="/${permanentLink.uri}">${title}</a>
								</div>
								<div class="date"><s:date format="dd/MM/yyyy" name="lastModified" /></div>
							</div>
						</div>			
					</s:iterator>
				</div>
			</div>
		</div>
	</div>
</s:if>
<s:else>
	<script type="text/javascript">$(function(){window.location.href="/404.jsp";});</script>
</s:else>