(function(window, undefined){
	
	var w = window;
	
	var t = "";
	
	{
		$(w.document.body).append(
			$('<div>').attr({
				'id'    : 'collaboratorDialog',
				'title' : 'Cadastro do Colaboradores'
			})
		);
	}
	
	function show(target){
		t = target;	
		$('#collaboratorDialog').empty().append(
			$('<div>').addClass('ym-fbox-text').append(
				$('<label>').attr('for', 'collaborator_name').html('Nome')
			).append(
				$('<input>').attr({
					'id'   : 'collaborator_name',
					'name' : 'collaborator_name',
					'type' : 'text'
				})
			).append(
				$('<label>').attr('for', 'collaborator_email').html('E-mail')
			).append(
				$('<input>').attr({
					'id'   : 'collaborator_email',
					'name' : 'collaborator_email',
					'type' : 'text'
				})
			).append(
				$('<div>').addClass('ym-fbox-button').append(
					$('<input>').attr({
						'type'  : 'submit',
						'value' : 'Enviar'
					}).click(validate)
				)
			)
		).dialog({
			height : 180,
			width  : 270
		});
	}
	
	function validate() {
		var email = $('#collaborator_email').val();
		var name  = $('#collaborator_name').val();
		if (name != null && name != undefined && name != "") {
			if (email != null && email != undefined && email != "") {
				var targetReference = t;
				$.ajax({
					url : 'collaborator-check?email=' + email,
					cache : false,
					success : function(data) {
						if (data == null || data == undefined || data == "") {
							$.ajax({
								url : 'collaborator-saveForJson?name=' + name + '&email=' + email,
								cache : false,
								success : function(data) {
									if (data != null
											&& data != undefined
												&& data > 0) {
										$('#' + targetReference).attr('value', data);
										$('#' + targetReference + '_ACS').children().get(0).innerHTML = name + ' <' + email + '>';
										alert('Cadastrado concluído com sucesso.');
									} else {
										alert('Houve um problema ao cadastrar o contato ' + name + ' <' + email + '>.!');
									}									
									$('#collaboratorDialog').dialog('close');									
								}
							});
						} else {
							alert(data);
						}
					}
				});	
			} else {
				alert("O campo e-mail é obrigatório.");
			}
		} else {
			alert("O campo nome é obrigatório.");
		}
	}
	
	w.showCollaboratorDialog = show;
	
})(window);