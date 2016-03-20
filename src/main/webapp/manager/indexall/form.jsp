<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
	.ym-fbox-text .ym-fbox-button,
	.ym-fbox-text .ym-fbox-button input { float: left; width: 100%; }
	.ym-fbox-text .ym-fbox-button input { margin: 2% 0 0 0; }
	.console { float: left; width: 100%; margin: 2% 0 0 0; }
</style>
<div class="ym-form">
	<div class="ym-fbox-text fields">
		<s:if test="isAdmin()">
			<div class="ym-fbox-button">
				<input type="button" id="btnAccount" value="Indexar ACCOUNT"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnArticle" value="Indexar ARTICLE"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnCategory" value="Indexar CATEGORY"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnPage" value="Indexar PAGE"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnPermanentLink" value="Indexar PERMANENTLINK"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnPhoto" value="Indexar PHOTO"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnSkin" value="Indexar SKIN"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnIndexAll" value="Indexar todas as entidades"/>
			</div>
			<div class="console">
				<label class="console">Console</label>
				<textarea id="console" name="console" rows="8" cols="4"></textarea>		
			</div>		
		</s:if>
		<s:else>
			<label>Acesso negado.</label>
		</s:else>
	</div>
</div>
<script type="text/javascript">
	var textAreaConsole = $("#console");
	var indexing = false;
	var print = function(message) {
		var html = textAreaConsole.html();
		html += message;
		html += "\n";
		textAreaConsole.html(html);
		if(textAreaConsole.length) {
			textAreaConsole.scrollTop(textAreaConsole[0].scrollHeight - textAreaConsole.height());	
		}
	};
	var currentEntity;
	var index = function(url, entityName, callBack) {
		if (!indexing) {
			currentEntity = entityName;
			indexing = true;
			print("Indexando entidade: " + entityName);
			print("Aguarde...");
			$.ajax({
				url: url,
				cache: false,
				async: true,
				success: function(data) {
					print(data);
					print("\n");
					indexing = false;
					if (callBack && "function" === typeof callBack) {
						callBack();
					}
				},
				error: function() {
					if (console && console.log) {
						console.log(e);
					}
					print("Erro ao indexar a entidade " + entityName + ".");
					print("Mensagem de erro: " + e);					
					indexing = false;
				}
			});
		} else {
			alert("Aguarde!\nIndexando entidade " + currentEntity + ".");
		}
	};
	$("#btnAccount").click(function() {
		index("/manager/indexAllFacade-indexAccount", "ACCOUNT");
	});
	$("#btnArticle").click(function() {
		index("/manager/indexAllFacade-indexArticle", "ARTICLE");		
	});
	$("#btnCategory").click(function() {
		index("/manager/indexAllFacade-indexCategory", "CATEGORY");		
	});
	$("#btnPage").click(function() {
		index("/manager/indexAllFacade-indexPage", "PAGE");		
	});	
	$("#btnPermanentLink").click(function() {
		index("/manager/indexAllFacade-indexPermanentLink", "PERMANENTLINK");
	});
	$("#btnPhoto").click(function() {
		index("/manager/indexAllFacade-indexPhoto", "PHOTO");		
	});
	$("#btnSkin").click(function() {
		index("/manager/indexAllFacade-indexSkin", "SKIN");		
	});
	$("#btnIndexAll").click(function() {
		if (!indexing) {
			index("/manager/indexAllFacade-indexAccount", "ACCOUNT", function() {
				index("/manager/indexAllFacade-indexArticle", "ARTICLE", function() {
					index("/manager/indexAllFacade-indexCategory", "CATEGORY", function() {
						index("/manager/indexAllFacade-indexPage", "PAGE", function() {
							index("/manager/indexAllFacade-indexPermanentLink", "PERMANENTLINK", function() {
								index("/manager/indexAllFacade-indexPhoto", "PHOTO", function() {
									index("/manager/indexAllFacade-indexSkin", "SKIN", function() {
										print("Todas as entidades indexadas com sucesso!!!");
									});									
								});		
							});				
						});			
					});		
				});
			});
		} else {
			alert("Aguarde!\nIndexando entidade " + currentEntity + ".");
		}
	});	
</script>