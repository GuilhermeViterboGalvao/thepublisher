<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"                                             %>
<script type='text/javascript'>
	var googletag = googletag || {};
	googletag.cmd = googletag.cmd || [];
	(function() {
		var gads = document.createElement('script');
		gads.async = true;
		gads.type = 'text/javascript';
		var useSSL = 'https:' == document.location.protocol;
		gads.src = (useSSL ? 'https:' : 'http:') + '//www.googletagservices.com/tag/js/gpt.js';
		var node = document.getElementsByTagName('script')[0];
		node.parentNode.insertBefore(gads, node);
	})();
</script>
<script type='text/javascript'>
	googletag.cmd.push(function() {
		googletag.defineSlot('/13554095/Tatame_728x90_Ros', [728, 90], 'tatame_728x90_ros').addService(googletag.pubads());
		googletag.defineSlot('/13554095/Tatame_215x90_Home', [215, 90], 'tatame_215x90_home').addService(googletag.pubads());
		googletag.defineSlot('/13554095/Tatame_300x100_Ros', [300, 100], 'tatame_300x100_ros').addService(googletag.pubads());
		googletag.defineSlot('/13554095/Tatame_300x250_Ros', [300, 250], 'tatame_300x250_ros').addService(googletag.pubads());
        googletag.defineSlot('/13554095/Tatame_300x600_Ros', [300, 600], 'tatame_300x600_ros').addService(googletag.pubads());
        googletag.defineSlot('/13554095/Tatame_1680x912_Ros', [300, 600], 'tatame_1680x912_ros').addService(googletag.pubads());        
		<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
		googletag.defineSlot('/13554095/Tatame_620x90_Premium_Ros', [620, 90], 'tatame_620x90_premium_ros').addService(googletag.pubads());
		googletag.defineSlot('/13554095/Tatame_300x100_Especial1_Home', [300, 100], 'tatame_300x100_especial1_home').addService(googletag.pubads());
		googletag.defineSlot('/13554095/Tatame_300x100_Especial2_Home', [300, 100], 'tatame_300x100_especial2_home').addService(googletag.pubads());
		googletag.defineSlot('/13554095/Tatame_1680x912_Home', [1680, 912], 'tatame_1680x912_home').addService(googletag.pubads());
		</s:if>
		googletag.pubads().enableSingleRequest();
		googletag.enableServices();
	});
</script>