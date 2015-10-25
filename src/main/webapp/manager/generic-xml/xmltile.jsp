<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
function up(e) {
	e = e.parentNode.parentNode;
	if (e==e.parentNode.firstChild) return;
	var parent = e.parentNode;
	var previous = e.previousSibling;
	var clone1=e.cloneNode(true);
	var clone2=previous.cloneNode(true);
	parent.replaceChild(clone2, e);
	parent.replaceChild(clone1, previous);
}
function down(e) {
	e = e.parentNode.parentNode;
	if (e==e.parentNode.lastChild) return;
	var parent = e.parentNode;
	var next = e.nextSibling;
	var clone1=e.cloneNode(true);
	var clone2=next.cloneNode(true);
	parent.replaceChild(clone2, e);
	parent.replaceChild(clone1, next);
}
function del(e) {
	e = e.parentNode.parentNode;
	e.parentNode.removeChild(e);
}
var canSubmit = false;
function checkIfPageIsLoaded() {
	return canSubmit;
}
$(document).ready(function() {
	canSubmit = true;
});
</script>
<s:form action="gxml-update" method="post" id="entriesForm" onsubmit="return checkIfPageIsLoaded();">	
	<nav class="ym-hlist">
		<ul>
			<li class="active">
				<strong><s:property value="name"/></strong>
			</li>											
		</ul>
		<ul>
			<s:submit action="%{updateAction}" value="Alterar" align="left" />
		</ul>	
	</nav>		
	<s:hidden name="path"></s:hidden>
	<s:hidden name="version"></s:hidden>
	<s:if test="message!=null">
		<script type="text/javascript">window.alert('<s:property value="message"/>');</script>
	</s:if>
	<s:include value="/xml/forms/%{form}.jsp"/>
</s:form>