<#--
/*
 * $Id: text.ftl 1157009 2011-08-12 08:38:39Z mcucchiara $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<img 
	id="${parameters.id?default("")?html}_ACS" 
	onclick="StrutsPS.search('${parameters.id?default("")?html}')" 
	<#if parameters.display?? && parameters.display?contains("/0_")>
		src="/manager/img/photo_130x80.jpg"
	<#else>
		src="${parameters.display?default("/manager/img/photo_130x80.jpg")?html}" 
	</#if>
	class="photoselector"
/>

<input type="hidden"<#t/>
 name="${parameters.name?default("")?html}" id="${parameters.id?default("")?html}"<#rt/>
<#if parameters.nameValue??>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.cssClass??>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#t/>
</#if>
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" />
/>
<script src="/manager/js/AutoComplete.js" type="text/javascript"></script>
<script type="text/javascript">
PhotoSelector("${parameters.id?default("")?html}_ACSEngine",{
	url: "${parameters.url?default("")?html}",
	click: function(obj) {
		StrutsPS.update("${parameters.id?default("")?html}",obj,this<#if parameters.evaluator??>,${parameters.evaluator}</#if>);
	},
	<#if parameters.showToolTip??>showToolTip: "${parameters.showToolTip}",</#if>		
	<#if parameters.imagePattern??>imagePattern: "${parameters.imagePattern}",</#if>		
	<#if parameters.minLength??>minLength: "${parameters.minLength}",</#if>		
	<#if parameters.delay??>delay: "${parameters.delay}",</#if>		
	<#if parameters.loadingImage??>loadingImage: "${parameters.loadingImage}",</#if>		
	<#if parameters.initialUrl??>initialUrl: "${parameters.initialUrl}",</#if>		
	<#if parameters.pageSize??>pageSize: ${parameters.pageSize},</#if>
	<#if parameters.photoType??>photoType: "${parameters.photoType}",</#if>		
	target: "${parameters.id?default("")?html}_ACSInput",
	menu: "${parameters.id?default("")?html}_ACSBox"
});
</script>