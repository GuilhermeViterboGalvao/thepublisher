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
<a id="${parameters.id?default("selectArticle")?html}" style="${parameters.cssStyle?default("")?html}" class="${parameters.cssClass?default("")?html} ym-button" href="javascript:void(0);">${parameters.text?default("matérias")?html}</a>
<script type="text/javascript">
	if(window.ArticleSelectorDialog == null || window.ArticleSelectorDialog == undefined){
		document.write("<script type='text/javascript' src='/manager/js/ArticleSelectorDialog.js'></" + "script>");
	}
</script>
<script type="text/javascript">
	ArticleSelectorDialog({ 
		"popup_" : <#if parameters.popup??>"${parameters.popup}",<#else>undefined,</#if>
		"target_button" : "${parameters.id}", 
		"on_article_click" : ${parameters.onArticleClick}, 
		"url_" : "${parameters.url}", 
		"width_" : <#if parameters.width??>"${parameters.width}",<#else>undefined,</#if>
		"height_" : <#if parameters.height??>"${parameters.height}",<#else>undefined,</#if>
		"category_" : <#if parameters.category??>"${parameters.category}"<#else>undefined</#if>
	});
</script>