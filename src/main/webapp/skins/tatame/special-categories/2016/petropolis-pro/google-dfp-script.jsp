<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">	
	var googletag = googletag || {};
	googletag.cmd = googletag.cmd || [];
	(function() {
		var gads = document.createElement("script");
		gads.async = true;
		gads.type = "text/javascript";
		var useSSL = "https:" == document.location.protocol;
		gads.src = (useSSL ? "https:" : "http:") + "//www.googletagservices.com/tag/js/gpt.js";
		var node = document.getElementsByTagName("script")[0];
		node.parentNode.insertBefore(gads, node);			
	})();
</script>
<script type="text/javascript">
	if (windowWidth > 800) {
		googletag.cmd.push(function() {
			googletag.defineSlot("/13554095/Tatame_728x90_Ros", 	[728, 90],   "tatame_728x90_ros").addService(googletag.pubads());
			googletag.defineSlot("/13554095/Tatame_300x100_ros", 	[300, 100],  "tatame_300x100_ros").addService(googletag.pubads());
			googletag.defineSlot("/13554095/Tatame_300x250_ros", 	[300, 250],  "tatame_300x250_ros").addService(googletag.pubads());
			googletag.defineSlot("/13554095/Tatame_300x600_ros", 	[300, 600],  "tatame_300x600_ros").addService(googletag.pubads());
			googletag.defineSlot("/13554095/Tatame_Codigo_Ros", 	[1, 1], 	 "tatame_codigo_ros").addService(googletag.pubads());
			<s:if test="model != null && model instanceof com.publisher.entity.Article">
				googletag.pubads().setTargeting("pageType", "article");
			</s:if>
			<s:elseif test="model != null && model instanceof com.publisher.entity.Category">
				googletag.pubads().setTargeting("pageType", "category");
			</s:elseif>
			<s:else>
				googletag.pubads().setTargeting("pageType", "search");
			</s:else>		
			
			googletag.pubads().setTargeting("event", "petropolis");
			googletag.pubads().enableSingleRequest();
			googletag.pubads().collapseEmptyDivs();
			googletag.enableServices();
		});
	}else{
		googletag.cmd.push(function() {
			googletag.defineSlot("/13554095/Tatame_Mobile_728x227_Ros", [728, 227],  "tatame_mobile_728x227_ros").addService(googletag.pubads());
							
			<s:if test="model != null && model instanceof com.publisher.entity.Article">
				googletag.pubads().setTargeting("pageType", "article");
			</s:if>
			<s:elseif test="model != null && model instanceof com.publisher.entity.Category">
				googletag.pubads().setTargeting("pageType", "category");
			</s:elseif>
			<s:else>
				googletag.pubads().setTargeting("pageType", "search");
			</s:else>	
			
			googletag.pubads().setTargeting("event", "petropolis");
			googletag.pubads().enableSingleRequest();
			googletag.pubads().collapseEmptyDivs();
			googletag.enableServices();
		});
	}
</script>
<script type="text/javascript">
	if (windowWidth > 800) {
		googletag.cmd.push(function(){googletag.display("tatame_728x90_ros");});
		googletag.cmd.push(function(){googletag.display("tatame_300x100_ros");});
		googletag.cmd.push(function(){googletag.display("tatame_300x250_ros");});
		googletag.cmd.push(function(){googletag.display("tatame_300x600_ros");});
		googletag.cmd.push(function(){googletag.display("tatame_codigo_ros");});
	}else{
		googletag.cmd.push(function(){googletag.display("tatame_mobile_728x227_ros");});
	}
</script>