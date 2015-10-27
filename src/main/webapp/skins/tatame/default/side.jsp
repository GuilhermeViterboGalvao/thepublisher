<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/publisher-tags" %>
<div id="ad_300_300-widget-11" class="widget ad_300_300">
	<div class="widget-inner video-box clearfix">
		<div class="ads300">
			<div class="ads-content">
				<div id='tatame_300x100_ros' style='width:300px; height:100px;'> 
					<script type='text/javascript'>googletag.cmd.push(function(){googletag.display('tatame_300x100_ros');});</script> 
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
			<b class="tab-read-more">Mais lidas</b>
		</h2>
	</div>
	<div class="widget-inner video-box clearfix recent">		
		<p:tile xml="home/recente"/>
	</div>
	<div class="widget-inner video-box clearfix read-more"></div>	
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
				<div id='tatame_300x250_ros' style='width:300px; height:250px;'> 
					<script type='text/javascript'>googletag.cmd.push(function(){googletag.display('tatame_300x250_ros');});</script> 
				</div>
			</div>
		</div>
	</div>
</div>
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
		<script type="text/javascript">
			$(function(){
				var fb_box = $('.bd-fb-likebox iframe');
				fb_box.attr('src', "//www.facebook.com/plugins/likebox.php?href=http://www.facebook.com/tatame&amp;width=250&amp;colorscheme=light&amp;show_faces=true&amp;border_color=%23e7402f&amp;stream=false&amp;header=false&amp;height=250");
			});
		</script>
		<div class="like_box_footer" style='background:#FFFFFF;'>
			<iframe scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:250px; height:250px;" allowTransparency="true"></iframe>
		</div>
	</div>
</div>
<div id="ad_300_300-widget-6" class="widget ad_300_300">
	<div class="widget-title box-title">
		<h2><b></b></h2>
	</div>
	<div class="widget-inner video-box clearfix">
		<div class="ads300">
			<div class="ads-content">
				<div id='tatame_300x600_ros' style='width:300px; height:600px;'> 
					<script type='text/javascript'>googletag.cmd.push(function(){googletag.display('tatame_300x600_ros');});</script> 
				</div>
			</div>
		</div>
	</div>
</div>