(function(window, undefined) {
	var name = $("#memberName");
	var document = $("#memberDocument");
	
	var windowWidth = window.document.body.offsetWidth || screen.width;
	
	document.keypress(function(e) {
		if (e && e.keyCode >= 48 && e.keyCode <= 57) {
			return true;
		}
		return false;
	});
	var email = $("#memberEmail");
	var password = $("#memberPassword");
	var password2 = $("#memberPassword2");	
	var validate = function() {
		var hasError = false;
		var message = "";
		if (name && (name.val() == null || name.val() == "")) {
			message += "O campo 'name' não pode ser nulo.\n\n";
			hasError = true;
		}
		if (document && (document.val() == null || document.val() == "")) {
			message += "O campo 'cpf' não pode ser nulo.\n\n";
			hasError = true;			
		}
		if (email && (email.val() == null || email.val() == "")) {
			message += "O campo 'e-mail' não pode ser nulo.\n\n";
			hasError = true;
		} else if (email.val().indexOf("@") < 0) {
			message += "E-mail inválido.\n\n";
			hasError = true;
		}
		if (password && (password.val() == null || password.val() == "")) {
			message += "O campo 'senha' não pode ser nulo.\n\n";
			hasError = true;
		}
		if (password2 && (password2.val() == null || password2.val() == "")) {
			message += "O campo 'redigite a senha' não pode ser nulo.\n\n";
			hasError = true;
		}		
		if (password && password.val() != null && password2 && password2.val() != null && password.val() != password2.val()) {
			message += "Senhas diferentes.";
			hasError = true;			
		}
		if (hasError) {
			alert(message);
			return false;
		}
		return true;
	};
	$("#btnConfirm").click(function() {
		if (validate()) {
			$.ajax({
				url : "/clube/memberSimpleRegister?name=" + name.val() + "&document=" + document.val() + "&email=" + email.val() + "&password=" + password.val() + "&password2=" + password2.val(),
				cache : false,
				success : function(data) {
					if (data) {
						if (data == "success") {
							alert("Para finalizar o seu cadastro, acesse o seu e-mail e confirme.");
						} else {
							alert(data);
						}
					}
				}
			});
		}
	});
	
	$(".header .member-free").click(function() {
		var left = "14%";
		
		if(windowWidth < 800) left = "13%";
		
		$(".grey-arrow").css("left", left);
		
		$(".content .member-free").css("display","table-cell");
		$(".content .member-premium").css("display","none");
	});
	
	$(".header .member-premium").click(function() {
		var left = "45%";
		
		if(windowWidth < 800 && windowWidth > 600) {
			left = "37%";
		}else if(windowWidth <= 600 && windowWidth > 414){
			left = "43%";
		}else if(windowWidth <= 414){
			left = "49%";
		}
		
		$(".grey-arrow").css("left", left);
		
		$(".content .member-free").css("display","none");
		$(".content .member-premium").css("display","table-cell");
	});
})(window);