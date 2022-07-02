function displayImages(clanarine){
	
	for (let c of clanarine){
				
		let div1 = $('<div class="col-sm-4"></div>');
		let div2 = $('<div class="card text-center" style="width: 18rem;"></div>');
		let div3 = $('<div class="card-body"></div>');
		let title =  $('<h4>' + c.id +'</h4>');
		let tipClanarine = $('<p style="font-size:18px; margin:0px;">' + c.tipClanarine + '</p>');
		let brojTermina = $('<p style="font-size:18px; margin:0px;">' + c.brojTermina + '</p>');
		let cena = $('<p style="font-size:18px; margin-top:20px;">' + c.cena + 'din</p>');
		let button = $('<button class="buttonKupi">Kupi</button>');
		button.click(function(){
			$('input[name="naziv"]').val(c.id);
			$('input[name="tip"]').val(c.tipClanarine);
			$('input[name="brojTermina"]').val(c.brojTermina);
			$('input[name="cena"]').val(c.cena);
			$('#popupOverlay, #popup').css("visibility", "visible");	
		});
		div3.append(title).append(tipClanarine).append(brojTermina).append(cena).append(button);
		div2.append(div3);
		div1.append(div2);
		$('#row').append(div1);
	}
}

$(document).ready(function() {
	
	$.get({
		url: 'rest/clanarine',
		success: function(clanarine) {
			displayImages(clanarine);
		}
	});
	
	 $('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});
});