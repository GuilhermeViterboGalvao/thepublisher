<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<s:if test="published">
	<div class="content">
		<div class="info">			
			<div class="polls box-shadow">
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
								<div class="alternative" id="<s:property value="#st.count"/>">
									<input type="hidden" data-votes="<s:property value="votes"/>" id="radio-<s:property value="id"/>"/>
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
				<a class="twitter-timeline" href="https://twitter.com/tatamemagazine" height="500" width="490">Tweets by @tatamemagazine</a>			
			</div>			
		</div>
		<div class="live-stats">
			<s:property value="code" escapeHtml="false"/>
		</div>
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
