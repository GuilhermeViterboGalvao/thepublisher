<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"                                             %>
<iframe frameborder="no" scrolling="no" Allowtransparency="true"></iframe>
<script>
$(function() {
	$("img.lazy").lazyload({
   		effect : "fadeIn"
	});
	var block = $('.cabeceira');
	var iframe = block.find('iframe');
	var is_desktop = true;
	var check_is_loaded = function(url) {
		if (jQuery.type(iframe.attr('src')) == "undefined" && iframe.attr('src') == url) {
			return
		}
	};
	$(window).resize(function() {
		var screen_size = $(window).width();
		if (screen_size < 770 && !is_desktop) {
			is_desktop = true;
			check_is_loaded('http://s1.trrsf.com/navbar/superslim/index.html?id=1&format=superslim&itemMenu=esp')
			block.css('height', 24).css('width', 'auto')
			iframe.attr('src', 'http://s1.trrsf.com/navbar/superslim/index.html?id=1&format=superslim&itemMenu=esp').removeAttr('width');
		} else if (screen_size > 770 && is_desktop) {
			is_desktop = false;
			check_is_loaded('http://s1.trrsf.com/navbar/superslim/index.html?id=1&format=superslim&itemMenu=esp');
			block.css('height', 24).css('width', 1020).css('background', '#F90');
			iframe.attr('src', 'http://s1.trrsf.com/navbar/superslim/index.html?id=1&format=superslim&itemMenu=esp').attr('width', 960).attr('height', 24);
		}
	}).resize();
});
</script>
<div id='tatame_1680x912_home' style='display: none;'> 
	<script type='text/javascript'>googletag.cmd.push(function(){googletag.display('tatame_1680x912_home');});</script> 
</div>