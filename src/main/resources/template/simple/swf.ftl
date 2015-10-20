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
<#if parameters.idDivBtn?contains("submitPhotos_")>
	<div 
		style="display: inline; 
		border: solid 1px #7FAAFF; 
		background-color: #C5D9FF; 
		padding: 2px;">
		<span 
			id="${parameters.idDivBtn?default("submitPhotos")?html}" 
			<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl"/>
		></span>
	</div>
</#if>

<#if parameters.idDivFileProgress?contains("divFileProgressContainer_")>
	<div 
		id="${parameters.idDivFileProgress?default("divFileProgressContainer")?html}" 
		style="height: 50px;"
	></div>
</#if>

<#if parameters.idDivThumbnail?contains("thumbnails_")>
	<div 
		id="${parameters.idDivThumbnail?default("thumbnails")?html}" 
		style="float: left;"
	></div>
</#if>	

<script type="text/javascript">
	if(window.swfInstances == null || window.swfInstances == undefined){						
		document.write("<link href='/frameworks/swfupload/swfupload.css' type='text/css' rel='stylesheet'" + "/>");
		document.write("<script type='text/javascript' src='/frameworks/swfupload/swfupload.js'></" + "script>");
		document.write("<script type='text/javascript' src='${parameters.alternativePathSWFHandler?default("/frameworks/swfupload/swfhandlers.js")}'></" + "script>");
	}
</script>

<script type="text/javascript">
	new SWFHandlers(
			"${parameters.flashUrl}",
			<#if parameters.alternativePath??>"${parameters.alternativePath}",<#else>undefined,</#if>
			"${parameters.uploadUrl}",
			"${parameters.deleteUrl}",
			"${parameters.sessionid}",
			<#if parameters.idDivBtn??>"${parameters.idDivBtn}",<#else>"submitPhotos",</#if>
			<#if parameters.idDivThumbnail??>"${parameters.idDivThumbnail}",<#else>"thumbnails",</#if>
			<#if parameters.idDivFileProgress??>"${parameters.idDivFileProgress}"<#else>"divFileProgressContainer"</#if>
	).inicialize();
</script>