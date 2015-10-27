<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"			  %>
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
	<div class="inner">
		<ul class="breadcrumbs">
			<li>
				<a class="crumbs-home-icon" itemprop="breadcrumb" href="http://www.tatame.com.br"><i class="icon-home"></i></a>
				<i class="icon-angle-right"></i>
			</li> 
	        <a href="/${category.permanentLink.url}">${category.name}</a> 
			<i class="icon-angle-right"></i> 
			<li class="current">
				<s:property value="note"/>
			</li>
		</ul>                
		<article class="post type-post status-publish format-standard hentry category-noticias tag-cris-cyborg tag-rener-gracie tag-ufc article" id="post-${id}">
			<div class="clearfix"></div>
			<h2 class="post-title">
				<a href="/${permanentLink.url}" rel="bookmark" alt="${title}">
					<s:property value="title"/>
				</a>
			</h2>
			<div class='clearfix'></div> 
			<div class='post-meta-info'>
				<div class='post_meta_author'>
					<i class='icon-user'></i>
					<a href="/tatame/view/busca?query=${author.name}" title="${author.name}" rel="author">
						<s:property value="author.name"/>
					</a>
				</div>
				<div class='post_meta_date'>
					<s:if test="location != null && !location.isEmpty()"><s:property value="location"/></s:if>
					<s:else>Rio de Janeiro</s:else>				
				</div>
				<div class='post_meta_date'><i class='icon-time'></i><s:date name="publishedAt" format="dd/MM/yyyy hh:mm"/></div>
			</div>
    		<s:if test="photos != null && photos.size() > 0">
			    <div class="bigphotoholder">
			        <div class="holder">
			        	<img class="bigphoto" src="http://cdn-tatame.trrsf.com/img/<s:property value="photos[0].photo.id"/>_<s:property value="getSize(photos[0].photo,620,420)"/>.jpg" alt="<s:property value="photos[0].description"/>"/>
			        </div>
			        <div class="bigphotonote">
			        	<s:property value="photos[0].description"/>
			        </div>
			    </div>	    
			    <div class="bigphotoholder-thumbs">		    	
			        <div class="left">		        
			        	<a href="javascript:void(0);">
			        		<img src="http://cdn-tatame.trrsf.com/system/skins/tatame/img/arrow-left-gray.png" alt="esquerda"/>
			        	</a>
			        </div>
			        <div class="right">
			        	<a href="javascript:void(0);">
			        		<img src="http://cdn-tatame.trrsf.com/system/skins/tatame/img/arrow-right-gray.png" alt="direita"/>
			        	</a>
			        </div>
			        <div class="photoholder">
			            <s:iterator value="photos" status="st">
			                <a class="<s:if test="#st.count>6">hide</s:if><s:else>show</s:else>" id="<s:property value="photo.id"/>_<s:property value="getSize(photo,620,420)"/>" href="javascript:void(0);">
			                	<img src="http://cdn-tatame.trrsf.com/img/<s:property value="photo.id"/>_91x68.jpg" alt="<s:property value="description"/>"/>
			                </a>
			            </s:iterator>
			        </div>
			    </div>		    		
    		</s:if>
			<div class="post-entry bottom40">
				<s:property value="content2" escapeHtml="false"/>
			</div>
    		<s:if test="videos != null && videos.size() > 0">
				<s:iterator value="videos" status="st">
					<div class="video space">
						<h1>
							<s:property value="title"/>
						</h1>
						<div>
							<s:property value="player.getCode(code)" escapeHtml="false"/>
						</div>
						<s:property value="description"/>
					</div>				
				</s:iterator>		    		
    		</s:if>
			<div class="post-tags">
				<b>Tags </b>
				<s:property value="tags"/>
			</div>
			<script type="text/javascript">
				window.___gcfg = {lang: 'es-419'};
				(function(w, d, s) {
				    function go(){
				        var js, fjs = d.getElementsByTagName(s)[0], load = function(url, id) {
				            if (d.getElementById(id)) {return;}
				            js = d.createElement(s); js.src = url; js.id = id;
				            fjs.parentNode.insertBefore(js, fjs);
				        };
				        load('//connect.facebook.net/en/all.js#xfbml=1', 'fbjssdk');
				        load('https://apis.google.com/js/plusone.js', 'gplus1js');
				        load('//platform.twitter.com/widgets.js', 'tweetjs');
				    }
				    if (w.addEventListener) { w.addEventListener("load", go, false); }
				    else if (w.attachEvent) { w.attachEvent("onload",go); }
				}(window, document, 'script'));
			</script>
			<script type="text/javascript" src="//assets.pinterest.com/js/pinit.js"></script>
			<div class="post-share-box">
				<h4 class="post-share-title" style="display: none">Compartlhe:</h4>
				<ul class="social_sharing_box_small">
	              	<li class="facebook">
	          			<div id="fb-root"></div>
	          			<div class="fb-like" data-width="70" data-height="30" data-colorscheme="light" data-layout="button_count" data-action="like" data-show-faces="true" data-send="false"></div>
	      			</li>
	                  <li class="twitter">
	          			<a href="https://twitter.com/share" class="twitter-share-button" data-url="http://www.tatame.com.br/${permanentLink.url}" data-text="${note}" data-via="" data-lang="en">tweet</a>
	      			</li>
	      			<li class="google">
	          			<div class="g-plusone" data-size="medium" data-href="http://www.tatame.com.br/${permanentLink.url}"></div>
	      			</li>
				</ul>
	    	</div>
			<div class="single-post-related">
				<div class="box-title">
					<h2><b>Notícias relacionadas</b></h2>
				</div>
				<section id="related-posts">
					<div class="related-images">
						<s:iterator value="getLast(4)" status="st">
							<div class="related-item">
								<div class="post-image">
									<a href="/${permanentLink.url}" title="${title}">
										<img class="lazy" data-original="http://cdn-tatame.trrsf.com/img/${photo.id}.jpg" width="270" height="180" alt="${title}"/>
									</a>
								</div>
								<h4>
									<a href="/${permanentLink.url}" title="${title}" rel="bookmark" class="related-item-title">
										<s:property value="title"/>
									</a>
								</h4>
							</div>					
						</s:iterator>
					</div>
					<div class="clear"></div>
				</section>
			</div>
			<s:if test="forumEnabled">
				<div class="fb-comments" data-href="http://www.tatame.com.br/${permanentLink.url}" data-num-posts="4" data-width="620"></div>
			</s:if>
		</article>
		<div class="clear"></div>
	</div>
</s:if>
<s:else>
	<script type="text/javascript">$(function(){window.location.href="/404.jsp";});</script>
</s:else>