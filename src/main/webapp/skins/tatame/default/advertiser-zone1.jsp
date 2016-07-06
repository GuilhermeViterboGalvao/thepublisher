<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
	(function() {
		if (windowWidth > 800) {
			var advertising1 = '<div id="tatame_970x250_ros"></div>';			
			var zones = [ advertising1 ];	
			var position = Math.floor(Math.random() * zones.length);
			document.write(zones[position]);
			zones.splice(position, 1);
		}
	})();
</script>