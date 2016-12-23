<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"              %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<s:if test="articles != null && articles.size() > 0">
	<div class="padding-top-15">
		<div class="box-1024">
			<span class="category-name">
				<s:property value="query"/>
			</span>	
			<div class="box-1024 padding-15">
				<div class="box-714">
					<div class="first-article box-shadow">
						<s:if test="articles.get(0).photo != null">
							<a href="/${articles.get(0).permanentLink.uri}">
								<img alt="<s:property value="articles.get(0).header"/>" src="/img/<s:property value="articles.get(0).photo.id"/>_714x452.jpg"/>
							</a>
						</s:if>
						<div class="info">
							<p class="title">
								<a href="/${articles.get(0).permanentLink.uri}">
									<s:property value="articles.get(0).title"/>
								</a>
							</p>
							<p class="author">
								<a href="/${articles.get(0).permanentLink.uri}">
				    				<span><s:property value="articles.get(0).createdBy.name"/> <s:date name="articles.get(0).publishedAt" format="dd/MM/yyyy"/></span> 
				    			</a>
				    		</p>
				    		<p class="note">
				    			<a href="/${articles.get(0).permanentLink.uri}">
				    				<s:property value="articles.get(0).note"/>
				    			</a>
				    		</p>
						</div>
					</div>
					<div class="articles margin-top-10">					
					    <s:iterator value="articles.subList(1, articles.size())">	    	
					    	<s:if test="photo && photo.id > 0">
						    	<div class="article box-shadow">
						    		<a href="/${permanentLink.uri}">
										<img alt="<s:property value="header"/>" src="/img/<s:property value="photo.id"/>_270x190.jpg"/>
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
					<div class="page-list">
						<s:bean name="com.publisher.utils.PageList" id="counter">
						    <s:param name="selectedPage" value="currentPage"/>
						    <s:param name="numberOfPages" value="pages"/>
						</s:bean>
						<s:iterator value="counter" >											    	
						     <s:if test="top == currentPage">
						         <span class="current">
						         	<s:property value="currentPage"/>
						         </span> 
						     </s:if>
						     <s:else>
						         <s:url id="url" value="/search">
						         	<s:param name="query" value="query"/>
						            <s:param name="currentPage" value="top"/>
						            <s:param name="pageSize" value="pageSize"/>
						         </s:url>
						         <s:a cssClass="inactive" href="%{url}"><s:property/></s:a>
						     </s:else>
						</s:iterator>													
					</div>	
				</div>
				<div class="box-300 margin-left-10">
					<div id="tatame_300x100_ros" class="ads-300-100 box-shadow margin-bottom-10"></div>
			    	<div id="tatame_300x250_ros" class="ads-300-250 box-shadow margin-bottom-10"></div>
			    	<div class="box-300 box-shadow margin-bottom-10"><p:tile xml="home/revista"/></div>
			    	<div id="tatame_300x600_ros" class="ads-300-600 box-shadow margin-bottom-10"></div>	
				</div>		
			</div>		
		</div>
	</div>
</s:if>
<s:else>
	<div class="padding-top-15">
		<div class="box-1024">
			<h1>Nenhum resultado encontrado.</h1>
		</div>
	</div>	
</s:else>