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
		$("input[type='radio']").each(function() {
			var radio = $(this);
			radio.click(function() {
				
			});
		});
	},
	
	addEventClickToButtons: function() {
		$(".btnVote").each(function() {
			var btnVote = $(this);
			btnVote.click(function() {
				
			});
		});
	}
};
LiveStats.init();