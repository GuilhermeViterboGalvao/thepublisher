<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">	
	var googletag = googletag || {};
	googletag.cmd = googletag.cmd || [];
	(function() {
		if (windowWidth > 800) {
			var gads = document.createElement("script");
			gads.async = true;
			gads.type = "text/javascript";
			var useSSL = "https:" == document.location.protocol;
			gads.src = (useSSL ? "https:" : "http:") + "//www.googletagservices.com/tag/js/gpt.js";
			var node = document.getElementsByTagName("script")[0];
			node.parentNode.insertBefore(gads, node);			
		}
	})();
</script>
<s:if test="model != null && model instanceof com.publisher.entity.Page && model.id == 1">
	<script type="text/javascript">
		if (windowWidth > 800) {
			googletag.cmd.push(function() {
				//googletag.defineSlot("/13554095/Tatame_970x250_Ros", [970, 250], "tatame_970x250_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_728x90_Ros", [728, 90], "tatame_728x90_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_ros", [300, 100], "tatame_300x100_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_Prime1_Home", [300, 100], "tatame_300x100_prime1_home").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_Prime2_Home", [300, 100], "tatame_300x100_prime2_home").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_Prime3_Home", [300, 100], "tatame_300x100_prime3_home").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x250_ros", [300, 250], "tatame_300x250_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x600_ros", [300, 600], "tatame_300x600_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_1680x912_Home", [1680, 912], "tatame_1680x912_home").addService(googletag.pubads());				
				//googletag.defineSlot("/13554095/Tatame_728x90_Cob_Cust_Home", [728, 90], "tatame_728x90_cob_cust").addService(googletag.pubads());				
				googletag.pubads().setTargeting("pageType", "home");
				googletag.pubads().enableSingleRequest();
				googletag.enableServices();
			});
		}
	</script>
	<script type="text/javascript">
		if (windowWidth > 800) {
			//googletag.cmd.push(function(){googletag.display("tatame_970x250_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_728x90_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_prime1_home");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_prime2_home");});
			googletag.cmd.push(function(){googletag.display("tatame_300x100_prime3_home");});
			googletag.cmd.push(function(){googletag.display("tatame_300x250_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_300x600_ros");});
			googletag.cmd.push(function(){googletag.display("tatame_1680x912_home");});			
			//googletag.cmd.push(function(){googletag.display("tatame_728x90_cob_cust");});
		}
	</script>
</s:if>
<s:else>
	<script type="text/javascript">
		if (windowWidth > 800) {
			googletag.cmd.push(function() {
				googletag.defineSlot("/13554095/Tatame_728x90_Ros", [728, 90], "tatame_728x90_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x100_ros", [300, 100], "tatame_300x100_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x250_ros", [300, 250], "tatame_300x250_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_300x600_ros", [300, 600], "tatame_300x600_ros").addService(googletag.pubads());
				googletag.defineSlot("/13554095/Tatame_1680x912_Ros", [1680, 912], "tatame_1680x912_ros").addService(googletag.pubads());
				<s:if test="model != null && model instanceof com.publisher.entity.Article">
					googletag.pubads().setTargeting("pageType", "article");
				</s:if>
				<s:elseif test="model != null && model instanceof com.publisher.entity.Category">
					googletag.pubads().setTargeting("pageType", "category");
				</s:elseif>
				<s:else>
					googletag.pubads().setTargeting("pageType", "search");
				</s:else>			
				googletag.pubads().enableSingleRequest();
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
			googletag.cmd.push(function(){googletag.display("tatame_1680x912_ros");});
		}
	</script>
</s:else>