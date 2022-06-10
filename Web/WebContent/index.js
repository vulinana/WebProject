function displayImages(sportskiObjekti){
	
	
	for (let s of sportskiObjekti){
				
		let div1 = $('<div class="col-sm-4"></div>');
		let div2 = $('<div class="card text-center" style="width: 18rem;"></div>');
		let img = $('<img class="card-img-top" src="pictures/' + s.logo +'" height="300"/>');
		let div3 = $('<div class="card-body"></div>');
		let title = $('<h5 class="card-title">'+ s.naziv +'</h5>');
		let button = $('<button class="btn btn-primary">Saznaj više</button>');
		$(button).click(function() {
			$('#popupOverlay, #popup').css("visibility", "visible");
			$('#naziv').text(s.naziv);
			$('#tipObjekta').text(s.tipObjekta);
			if (s.statusObjekta == "Radi"){
				$('#status').text("Otvoreno");
			} else{
				$('#status').text("Zatvoreno");
			}
			$('#ulicaIBroj').text(s.lokacija.adresa.ulicaIBroj + ",  ");
			$('#mesto').text(s.lokacija.adresa.mesto + ", ");
			$('#postanskiBroj').text(s.lokacija.adresa.postanskiBroj);
			$('#prosecnaOcena').text(s.prosecnaOcena);
			$('#radnoVreme').text(s.radnoVreme.odVreme + "-" + s.radnoVreme.doVreme + "h, " + s.radnoVreme.dani[0] + "-" + s.radnoVreme.dani[s.radnoVreme.dani.length - 1]);
		});
		
		let status;
		if (s.statusObjekta == "Radi"){
				status = $('<p style="color:green">Otvoreno</p>')
		} else{
				status = $('<p style="color:red">Zatvoreno</p>')
		}
		
		div3.append(title).append(status).append(button);
		div2.append(img).append(div3);
		div1.append(div2);
		$('#row').append(div1);
			
	}
}

function updateImages(sportskiObjekti) {
	$('#row').html("");
	displayImages(sportskiObjekti);
}


$(document).ready(function() {
	$.get({
		url: 'rest/sportskiObjekti',
		success: function(sportskiObjekti) {
			displayImages(sportskiObjekti);
		}
	});
	
	$('#popupButton').click(function() {
			$('#popupOverlay, #popup').css("visibility", "hidden");
	});
	
	
	
	$('form#formaZaPretragu').submit(function(event) {
		event.preventDefault();
		let naziv = $('#pretragaNaziv').val();
		let mesto = $('#pretragaGradIliDrzava').val();
		let prosecnaOcena = $('#pretragaProsecnaOcena').val();
		let tipObjekta = $('#pretragaTipObjekta').val();
		if (tipObjekta == ""){
			tipObjekta = null;
		}
		$.ajax({
			url: 'rest/sportskiObjekti/pretrazi',
			type: 'PUT',
			data: JSON.stringify({naziv: naziv, lokacija: { adresa: {mesto: mesto}}, prosecnaOcena: prosecnaOcena, tipObjekta: tipObjekta}),
			contentType: 'application/json',
			success: function(sportskiObjekti) {
				updateImages(sportskiObjekti);
			}
		});
	});
});