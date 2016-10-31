var LiveStats = {
	init: function() {
		this.renderProgressBar();
		this.addEventClickToRadios();
		this.addEventClickToButtons();		
	},
	
	renderProgressBar: function() {
		var highestVote = 0;
		var maxWidth = $($(".progress-bar")[0]).width();
		console.log(maxWidth);
		$(".progress-bar").each(function() {
			var progressBar = $(this);
			var vote = Number(progressBar.data("votes"));
			if (vote > highestVote) {
				highestVote = vote;
			}
		});
		$(".progress-bar").each(function() {
			var progressBar = $(this);
			var votes = Number(progressBar.data("votes"));
			var width = Number((maxWidth * votes) / highestVote).toFixed(0);
			if (width < 5) {
				progressBar.animate({
					width: 5
				}, 1000);
			} else {
				progressBar.animate({
					width: width
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
				var result = null;
				$.ajax({
					url : "/poll-vote?pollId=" + pollId + "&alternativeId=" + alternativeId,
					async: false,
					cache : false,
					success : function(data) {
						result = eval(data);
					}
				});
				if (result && result.voted) {
					LiveStats.updateVotes(alternativeId);
					alert("Voto realizado com sucesso.");					
				} else {
					alert("Você já votou nessa enquete.");
				}				
			});
		});
	},
	
	updateVotes: function(alternativeId) {
		var divAlternative = $("#alternative-" + alternativeId);
		var votes = Number(divAlternative.find("#votes").data("votes"));
		if (votes < 2) {
		 	votes = 2;
		}
		votes += 1;
		var text =  divAlternative.find("#text").data("text");
		var pText = divAlternative.find("p.text");
		pText.html(text + " " + votes + " votos.");
	}
};
LiveStats.init();

$(window).load(function(){ 
	var width = window.document.body.offsetWidth || window.screen.width;
	if (width && width > 800) {
		var pollsH = $(".info .polls").height();
		var ads = $(".info .ads");
		
		var difference = 0;
		
		if (ads.css('display') != "none"){
			difference = ads.height() + 20;
		}
		
		$(".live-chat iframe").css("height", (pollsH - difference) + "px");
	}
});