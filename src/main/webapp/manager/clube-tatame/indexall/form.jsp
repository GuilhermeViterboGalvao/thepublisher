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
				<input type="button" id="btnCompany" value="Indexar COMPANY"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnCompanyContract" value="Indexar COMPANYCONTRACT"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnEvent" value="Indexar EVENT"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnGym" value="Indexar GYM"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnGymContract" value="Indexar GYMCONTRACT"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnMember" value="Indexar MEMBER"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnMemberContract" value="Indexar MEMBERCONTRACT"/>
			</div>
			<div class="ym-fbox-button">
				<input type="button" id="btnProduct" value="Indexar PRODUCT"/>
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
	$("#btnCompany").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexCompany", "COMPANY");
	});
	$("#btnCompanyContract").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexCompanyContract", "COMPANYCONTRACT");		
	});
	$("#btnEvent").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexEvent", "EVENT");	
	});
	$("#btnGym").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexGym", "GYM");	
	});	
	$("#btnGymContract").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexGymContract", "GYMCONTRACT");
	});
	$("#btnMember").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexMember", "MEMBER");		
	});
	$("#btnMemberContract").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexMemberContract", "MEMBERCONTRACT");	
	});
	$("#btnProduct").click(function() {
		index("/manager/clube-tatame/indexAllFacade-indexProduct", "PRODUCT");	
	});	
	$("#btnIndexAll").click(function() {
		if (!indexing) {
			index("/manager/clube-tatame/indexAllFacade-indexCompany", "COMPANY", function() {
				index("/manager/clube-tatame/indexAllFacade-indexCompanyContract", "COMPANYCONTRACT", function() {
					index("/manager/clube-tatame/indexAllFacade-indexEvent", "EVENT", function() {
						index("/manager/clube-tatame/indexAllFacade-indexGym", "GYM", function() {
							index("/manager/clube-tatame/indexAllFacade-indexGymContract", "GYMCONTRACT", function() {
								index("/manager/clube-tatame/indexAllFacade-indexMember", "MEMBER", function() {
									index("/manager/clube-tatame/indexAllFacade-indexMemberContract", "MEMBERCONTRACT", function() {
										index("/manager/clube-tatame/indexAllFacade-indexProduct", "PRODUCT", function() {
											print("Todas as entidades indexadas com sucesso!!!");
										});										
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