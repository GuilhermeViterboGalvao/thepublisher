var LiveStats = {
	init: function() {
		this.renderProgressBar();
		this.addEventClickToRadios();
		this.addEventClickToButtons();		
	},
	
	renderProgressBar: function() {
		$(".progress-bar").each(function() {
			var progressBar = $(this);
			var votes = progressBar.data("votes");
			var width = progressBar.width();
			if (votes == 0) {
				progressBar.animate({
					width: 2
				}, 1000);
			} else if (votes < width ) {
				progressBar.animate({
					width: votes
				}, 500);
			}
		});
	},
	
	addEventClickToRadios: function() {
		$(".alternative-radio").each(function() {
			var radio = $(this);
			radio.click(function() {
				var radio = $(this);
				var divAlternatives = radio.parent().parent();
				divAlternatives.find(".alternative-radio").each(function() {
					var currentRadio = $(this);
					if (currentRadio.attr("id") != radio.attr("id") && currentRadio.is(":checked")) {
						currentRadio.prop("checked", false);
					}
				});
			});
		});
	},
	
	addEventClickToButtons: function() {
		$(".btnVote").each(function() {
			var btnVote = $(this);
			btnVote.click(function() {
				var poll = $(this).parent();
				var pollId = poll.find("#pollId").val();
				var alternativeId = 0;
				poll.find(".alternative-radio").each(function() {
					var radio = $(this);
					if (radio.is(":checked")) {
						alternativeId = radio.attr("id");
					}
				});
				if (alternativeId == 0) {
					alert("Você precisa selecionar uma opção\nantes de clicar no botão \"Votar\".");
					return;
				}
				$.ajax({
					url : "/poll-vote?pollId=" + pollId + "&alternativeId=" + alternativeId,
					async: false,
					cache : false,
					success : function(data) {
						var result = eval(data);
						if (result && result.voted) {
							alert("Voto realizado com sucesso.");
						} else {
							alert("Você já votou nessa enquete.");
						}
					}
				});
			});
		});
	}
};
LiveStats.init();