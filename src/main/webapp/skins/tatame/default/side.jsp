<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<div id="ad_300_300-widget-11" class="widget ad_300_300">
	<div class="widget-inner video-box clearfix">
		<div class="ads300">
			<div class="ads-content">
				<div id="tatame_300x100_ros" style="width:300px; height:100px;"> 
					<script type="text/javascript">googletag.cmd.push(function(){googletag.display("tatame_300x100_ros");});</script> 
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 
<div id="bd-cate-posts-2" class="widget bd-cate-posts bd-poll">
	<div class="widget-title box-title">
		<h2><b>Enquete</b></h2>
	</div>
	<div class="widget-inner video-box clearfix">
		<div class="poll"></div>		
	</div>
</div> 
-->
<div id="bd-cate-posts-2" class="widget bd-cate-posts">
	<div class="widget-title box-title">
		<h2>
			<b class="tab-recent">Recente</b>
			<s:if test="model != null && model instanceof com.publisher.entity.Page">
				<b class="tab-read-more">Mais lidas</b>
			</s:if>
		</h2>
	</div>
	<div class="widget-inner video-box clearfix recent">		
		<p:tile xml="home/recente"/>
	</div>
	<s:if test="model != null && model instanceof com.publisher.entity.Page">
		<div class="widget-inner video-box clearfix read-more"></div>
	</s:if>
</div>
<div id="text-8" class="widget widget_text">
	<div class="widget-title box-title">
		<h2><b>Edição do mês</b></h2>
	</div>
	<p:tile xml="home/revista"/>
</div>
<div id="ad_300_300-widget-10" class="widget ad_300_300">
	<div class="widget-title box-title">
		<h2><b></b></h2>
	</div>
	<div class="widget-inner video-box clearfix">
		<div class="ads300">
			<div class="ads-content">
				<div id="tatame_300x250_ros" style="width:300px; height:250px;"> 
					<script type="text/javascript">googletag.cmd.push(function(){googletag.display("tatame_300x250_ros");});</script> 
				</div>
			</div>
		</div>
	</div>
</div>
<s:if test="model != null && model instanceof com.publisher.entity.Page">
	<div id="bd-recent-posts-2" class="widget bd-recent-posts">
		<div class="widget-title box-title">
			<h2><b>Últimas notícias</b></h2>
		</div>
		<div class="widget-inner video-box clearfix"></div>
	</div>
	<div id="bd-fb-likebox-3" class="widget bd-fb-likebox">
		<div class="widget-title box-title">
			<h2><b>Curta a TATAME</b></h2>
		</div>
		<div class="widget-inner video-box clearfix">
			<div id="fb-root"></div>
			<script type="text/javascript">
				(function(d, s, id) {
				  var js, fjs = d.getElementsByTagName(s)[0];
				  if (d.getElementById(id)) {
					  return;
				  }
				  js = d.createElement(s); 
				  js.id = id;
				  js.src = "//connect.facebook.net/pt_BR/sdk.js#xfbml=1&version=v2.5&appId=312287075567167";
				  fjs.parentNode.insertBefore(js, fjs);
				}(document, "script", "facebook-jssdk"));
			</script>
			<div class="like_box_footer" style="background:#FFFFFF;">
				<div class="fb-page" data-href="https://www.facebook.com/tatame" data-width="284" data-height="234" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true" data-show-posts="true">
					<div class="fb-xfbml-parse-ignore">
						<blockquote cite="https://www.facebook.com/tatame">
							<a href="https://www.facebook.com/tatame">TATAME</a>
						</blockquote>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:if>
<div id="ad_300_300-widget-6" class="widget ad_300_300">
	<div class="widget-title box-title">
		<h2><b></b></h2>
	</div>
	<div class="widget-inner video-box clearfix">
		<div class="ads300">
			<div class="ads-content">
				<div id="tatame_300x600_ros" style="width:300px; height:600px;"> 
					<script type="text/javascript">googletag.cmd.push(function(){googletag.display("tatame_300x600_ros");});</script> 
				</div>
			</div>
		</div>
	</div>
</div>