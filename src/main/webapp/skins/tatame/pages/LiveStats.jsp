<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<s:if test="published">
	<div class="content">
		<div class="info">			
			<div class="polls box-shadow">
				<s:if test="poll != null && poll.photo != null">
					<img class="photo" alt="<s:property value="poll.photo.description"/>" src="http://cdn-tatame.trrsf.com/img/<s:property value="poll.photo.id"/>_420x200.jpg"/>
				</s:if>
			
				<div class="title">
					<p class="text">Enquete</p>
					<p class="line"></p>
				</div>
				
				<s:if test="poll != null">
					<div class="poll">						
						<input type="hidden" id="pollId" value="<s:property value="poll.id"/>"/>
						
						<p class="question"><s:property value="poll.question"/></p>
						<div class="alternatives">
							<s:iterator value="poll.alternatives" status="st">
								<div class="alternative" id="alternative-<s:property value="id"/>">
									<input type="hidden" data-id="<s:property value="votes"/>" id="alternativeId"/>
									<input type="hidden" data-votes="<s:property value="votes"/>" id="votes"/>
									<input type="hidden" data-text="<s:property value="text"/>" id="text"/>
									<input type="radio" class="alternative-radio" id="<s:property value="id"/>"/>
									<p class="text"><s:property value="text"/> <s:property value="votes"/> votos.</p>
									<p class="progress-bar" data-votes="<s:property value="votes"/>"></p>
								</div>
							</s:iterator>					
						</div>
						<button class="btnVote">Votar</button>					
					</div>
				</s:if>
			</div>
			<div class="live-chat box-shadow">
				<s:if test="twitter != null && !twitter.isEmpty()">
					<a class="twitter-timeline" href="https://twitter.com/<s:property value="twitter"/>" height="400" width="580">Tweets by @<s:property value="twitter"/></a>			
				</s:if>
				<s:else>
					<a class="twitter-timeline" href="https://twitter.com/tatamemagazine" height="400" width="580">Tweets by @tatamemagazine</a>			
				</s:else>
			</div>	
			
			<div id="tatame_live_stats" class="ads"></div>		
		</div>
		<div class="live-stats">
			<s:property value="code" escapeHtml="false"/>
		</div>
		
		<s:if test="articles != null">
			<div class="box-1024 padding-15">
				<div class="title-news">
					<p class="text">News</p>
					<p class="line"></p>
				</div>
				<div class="box-714">
					<div class="articles">					
					    <s:iterator value="articles">	    	
					    	<s:if test="photo && photo.id > 0">
						    	<div class="article box-shadow">
						    		<a href="/${permanentLink.uri}">
										<img alt="<s:property value="header"/>" src="http://cdn-tatame.trrsf.com/img/<s:property value="photo.id"/>_270x190.jpg"/>
						    		</a>
						    		<div class="info">
							    		<p class="title">
							    			<a href="/${permanentLink.uri}"><s:property value="title"/></a>
							    		</p>
							    		<p class="author">
							    			<a href="/${permanentLink.uri}">
							    				<span><s:property value="createdBy.name"/> <s:date name="publishedAt" format="dd/MM/yyyy"/></span>
							    			</a>
							    		</p>
							    		<p class="note">
							    			<a href="/${permanentLink.uri}"><s:property value="note"/></a>
							    		</p>
						    		</div>
						    		
						    	</div>
					    	</s:if>	    	
					    </s:iterator>	
					</div>
				</div>
				<div class="box-300 margin-left-10">
			    	<div id="tatame_300x600_ros" class="ads-300-600 box-shadow margin-bottom-10"></div>	
				</div>	
			</div>
			
			<div class="category-link">
				<a href="/${categoryLink.uri}" >Leia mais notícias</a>
			</div>
		</s:if>
		
		<s:if test="forumEnabled">
			<div class="fb-comments live-stats-fb-comments" data-href="http://www.tatame.com.br/${permanentLink.uri}" data-num-posts="4" data-width="100%"></div>
		</s:if>
	</div>	
</s:if>
<s:else>
	<div class="live-stats-info">
		<p>Evento indisponível no momento.</p>
	</div>
</s:else>
