function prikaziPodatke(sportskiObjekat){
	$('#logo').attr("src", "pictures/" + sportskiObjekat.logo);
	$('#naziv').text(sportskiObjekat.naziv);
	if(sportskiObjekat.prosecnaOcena != 0){
		$('#ocena').text("Ocena: " + sportskiObjekat.prosecnaOcena);
	} else{
		$('#ocena').text("Ocena:");
	}
	$('#tipObjekta').text(sportskiObjekat.tipObjekta);
	if (sportskiObjekat.statusObjekta == "Radi"){
		$('#status').text("OTVORENO,  " + sportskiObjekat.radnoVreme.odVreme + "-" + sportskiObjekat.radnoVreme.doVreme + "h, " + sportskiObjekat.radnoVreme.dani[0] + "-" + sportskiObjekat.radnoVreme.dani[sportskiObjekat.radnoVreme.dani.length - 1]);
	} else {
		$('#status').text("ZATVORENO,  " + + sportskiObjekat.radnoVreme.odVreme + "-" + sportskiObjekat.radnoVreme.doVreme + "h, " + sportskiObjekat.radnoVreme.dani[0] + "-" + sportskiObjekat.radnoVreme.dani[sportskiObjekat.radnoVreme.dani.length - 1]);	
	}
}

function prikaziKomentare(komentari){
	
	if (komentari.length == 0){
		let nemaKomentara = $('<p>Trenutno nema komentara</p>');
		$('#komentari').append(nemaKomentara);
		return;
	}
	
	for (let k of komentari){
		let komentar = $('<hr><p><span class="ocenaKomentar">' + k.ocena + '</span>&nbsp;' + k.kupac + ':</br>&nbsp;&nbsp;&nbsp;&nbsp;' + k.tekstKomentara +'</p>');
		$('#komentari').append(komentar);
	}
	
}

function prikaziTreninge(treninzi){
	if (treninzi.length == 0){
		let nemaTreninga = $('<p>Trenutno nema treninga</p>');
		$('#treninzi').append(nemaTreninga);
		return;
	}
	
		
	for (let t of treninzi){
		let slikaTreninga =	$('<div class="slikaTreninga" id="slikaTreninga"></div>');
		let tekstTreninga =	$('<div class="tekstTreninga" id="tekstTreninga"></div>');
		let slika = $('<img src="pictures/' + t.slika + '"/>');
		let trening = $('<hr><h5>' + t.naziv + '</h5><p>Opis:&nbsp;&nbsp;' + t.opis + '</br>Trener:&nbsp;&nbsp;' + t.trener + '</br>Doplata:&nbsp;&nbsp;' + t.doplata + '</p>');
		slikaTreninga.append(slika);
		tekstTreninga.append(trening);
		$('#sadrzajPrikazaTreninga').append(slikaTreninga);
		$('#sadrzajPrikazaTreninga').append(tekstTreninga);
	}
}

function updateTreninge(treninzi){
	$("#sadrzajPrikazaTreninga").html("");
	prikaziTreninge(treninzi);
}


function dodajTrenere(treneri) {
	
		comboBoxOption = $('<option value="">Nema trenera</option>');
		$('#dodajObjekatTrener').append(comboBoxOption);
		
		for (let t of treneri) {
				comboBoxOption = $('<option value="' + t.korisnickoIme + '">' + t.korisnickoIme + '</option>');
				$('#dodajObjekatTrener').append(comboBoxOption);
		}
}

$(document).ready(function() {
	
	 var menadzer = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	 naziv = menadzer.sportskiObjekat;
	 $.get({
			url: 'rest/sportskiObjekti/' + naziv,
			success: function(sportskiObjekat) {
				localStorage.setItem("sportskiObjekat", JSON.stringify(sportskiObjekat));
				prikaziPodatke(sportskiObjekat);
			}
	 });
	
	 $.get({
			url: 'rest/komentari/' + naziv,
			success: function(komentari) {
				prikaziKomentare(komentari);
			}
	 });
	
	 $.get({
			url: 'rest/treninzi/' + naziv,
			success: function(treninzi) {
				prikaziTreninge(treninzi);
			}
	 });
	 
	  
	 $.get({
			url: 'rest/kupci/treneri',
			success: function(treneri) {
				dodajTrenere(treneri);
			}
	 });
	 
	 $('#buttonDodajTrening').click(function(){
		$('#popupOverlay, #popup').css("visibility", "visible");
	});
	
		
	$('form#dodajTreningForma').submit(function(event) {
		event.preventDefault();
		$('#popupOverlay, #popup').css("visibility", "hidden");
		let naziv = $('input[name="naziv"]').val();
		let tip = $('#tip').val();
		let slika = $('input[name="slika"]').val();
		let opis = $('input[name="opis"]').val();
		let trajanje = $('input[name="trajanje"]').val();
		let doplata = $('input[name="doplata"]').val();
		let trener = $('#dodajObjekatTrener').val();
		var s = JSON.parse(localStorage.getItem("sportskiObjekat"));
	 	let sportskiObjekatKomPripada = s.naziv;
		
		$.ajax({
			url: 'rest/treninzi/kreirajNoviTrening',
			type: 'POST',
			data: JSON.stringify({naziv: naziv, tip: tip, sportskiObjekatKomPripada: sportskiObjekatKomPripada, trajanje: trajanje, trener: trener, opis: opis, slika: slika, doplata: doplata}),
			contentType: 'application/json',
			success : function(treninzi) {
				updateTreninge(treninzi);
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