function displayImages(clanarine, kupac, tipKupca){
	
	$('#bodovi').text(kupac.brojSakupljenihBodova);
	$('#tipKupca').text(tipKupca.imeTipa);
	$('#popust').text(tipKupca.popust + "%");
	
	for (let c of clanarine){
				
		let div1 = $('<div class="col-sm-4"></div>');
		let div2 = $('<div class="card text-center" style="width: 18rem;"></div>');
		let div3 = $('<div class="card-body"></div>');
		let title =  $('<h4>' + c.id +'</h4>');
		let tipClanarine = $('<p style="font-size:18px; margin:0px;">' + c.tipClanarine + '</p>');
		let brojTermina = $('<p style="font-size:18px; margin:0px;">' + c.brojTermina + ' termina</p>');
		let cena;
		let cena2;
		let novaCena;
		if (tipKupca.imeTipa == 'SREBRNI' || tipKupca.imeTipa == 'ZLATNI'){
			cena = $('<p style="font-size:18px; margin-top:20px;"><s>' + c.cena + 'din</s></p>');
			cena2 =  c.cena - c.cena*tipKupca.popust/100;
			novaCena = $('<p style="font-size:18px;">' + cena2 + 'din</p>');
		} else {
			cena = $('<p style="font-size:18px; margin-top:20px;">' + c.cena + 'din</p>');
		} 
		
		
		let button = $('<button class="buttonKupi">Kupi</button>');
		button.click(function(){
			$('input[name="naziv"]').val(c.id);
			$('input[name="tip"]').val(c.tipClanarine);
			$('input[name="brojTermina"]').val(c.brojTermina);
			if (tipKupca.imeTipa == 'SREBRNI' || tipKupca.imeTipa == 'ZLATNI'){
				$('input[name="cena"]').val(cena2);
			} else {
				$('input[name="cena"]').val(c.cena);
			}
			$('#popupOverlay, #popup').css("visibility", "visible");	
		});
		
		if (tipKupca.imeTipa == 'SREBRNI' || tipKupca.imeTipa == 'ZLATNI'){
			div3.append(title).append(tipClanarine).append(brojTermina).append(cena).append(novaCena).append(button);
		} else {
			div3.append(title).append(tipClanarine).append(brojTermina).append(cena).append(button);
		}
		div2.append(div3);
		div1.append(div2);
		$('#row').append(div1);
	}
}

$(document).ready(function() {
	
	
	var korisnik = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	$.get({
		url: 'rest/clanarine',
		success: function(clanarine) {
			
			$.get({
				url: 'rest/kupci/kupac/' + korisnik.korisnickoIme,
				success: function(kupac) {
					
					$.get({
					url: 'rest/tipKupca/' + korisnik.korisnickoIme,
					success: function(tipKupca) {
						displayImages(clanarine, kupac, tipKupca);
					}
			});
				}
			});
		}
	});
	
	$('form#kupiClanarinuForma').submit(function(event) {
		event.preventDefault();
		$('#error').text("");
		let naziv = $('input[name="naziv"]').val();
		let tipClanarine = $('input[name="tip"]').val();
		let promoKod = $('input[name="promoKod"]').val();
		let brojPreostalihTermina = $('input[name="brojTermina"]').val();
		let cena = $('input[name="cena"]').val();
		
		if (promoKod == ""){
			promoKod = "bezPromoKoda";
		}
		
		$.ajax({
			url: 'rest/clanarineKupac/' + promoKod + '/' + tipClanarine ,
			type: 'POST',
			data: JSON.stringify({kupac: korisnik.korisnickoIme, clanarinaId: naziv, brojPreostalihTermina: brojPreostalihTermina, placenaCena: cena}),
			contentType: 'application/json',
			success : function() {
				$('#popupOverlay, #popup').css("visibility", "hidden");
				$('input[name="promoKod"]').val("");
			},
			error : function(message) {
				$('#error').text(message.responseText);
			}
		});
	});
	
	
	 $('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});
});