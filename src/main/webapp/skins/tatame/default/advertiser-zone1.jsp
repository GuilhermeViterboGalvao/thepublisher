<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
	(function() {
		if (windowWidth > 800) {
			var advertising1 = '<div id="TerraAdvertising" class="mobile-hidden" data-keyvalues="" data-clicktag=""></div> \x3Cscript type="text/javascript" src="http://p2.trrsf.com/tagmanfe/ShowArea.aspx?key=br.cobranded_tatame.home.master2&direct=1">\x3C/script>';			
			var advertising2 = '<div id="tatame_970x250_ros"></div>';			
			var zones = [ advertising1, advertising2 ];	
			var position = Math.floor(Math.random() * zones.length);
			document.write(zones[position]);
			zones.splice(position, 1);
		}
	})();
</script>