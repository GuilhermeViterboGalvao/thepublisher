<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>
			<tiles:insertAttribute name="title"/>
		</title>
		<tiles:insertAttribute name="head"/>
	</head>
	<body>
		<script type="text/javascript" src="/frameworks/jquery/jquery-1.11.3.min.js"></script>
		<div class="ym-wrapper" id="wrapper">
			<div class="ym-wbox">
				<div id="header">
					<tiles:insertAttribute name="header"/>
				</div>
				<div class="ym-column">
					<div class="ym-col1" id="content">
						<div class="ym-cbox">
							<tiles:insertAttribute name="content"/>
						</div>
					</div>
					<div class="tabs-menu">menu</div>
					<div class="ym-col3" id="menu">
						<div class="ym-cbox">
							<tiles:insertAttribute name="menu"/>
						</div>
					</div>
				</div>
				<div id="footer">
					<tiles:insertAttribute name="footer"/>
				</div>
			</div>
		</div>
	</body>
</html>