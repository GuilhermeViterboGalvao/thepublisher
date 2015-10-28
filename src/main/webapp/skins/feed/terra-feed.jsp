<%@ page contentType="text/xml" pageEncoding="ISO-8859-1"%><%@ taglib prefix="s" uri="/struts-tags" %><?xml version="1.0" encoding="ISO-8859-1"?>
<resultset total="<s:property value="articles.size()"/>">
	<s:iterator value="articles" status="i">
		<result pos="<s:property value="#i.index"/>">
			<id><s:property value="id"/></id>
			<state>published</state>
			<instance>BR</instance>
			<title><s:property value="header" escapeXml="true"/> - <s:property value="title" escapeXml="true"/></title>
			<categorystr><s:property value="category.name" escapeXml="true"/></categorystr>
			<subtitle><s:property value="header" escapeXml="true"/></subtitle>
			<keywords><s:property value="tags" escapeXml="true"/></keywords>
			<url>http://www.tatame.com.br/<s:property value="permanentLink.url" escapeXml="true"/></url>
			<body><s:property value="content" escapeXml="true"/></body>
			<pubdate><s:property value="publishedAt.getTime() / 1000"/></pubdate>
			<thumburl>http://cdn-tatame.trrsf.com/img/<s:property value="photo.id"/>.jpg</thumburl>
		</result>
	</s:iterator>
</resultset>