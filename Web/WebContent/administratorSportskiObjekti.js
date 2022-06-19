function ucitajMenadzere(menadzeri) {
	let tdMenadzerLabela = $('<td><label class="label">Menadzer:</labela></td>');
	
	let td = $('<td></td>');
	let izaberiMenadzera;
	if (menadzeri.length === 0){
		izaberiMenadzera = $('<button id="kreirajMenadzeraButton">Kreiraj</button>');
		izaberiMenadzera.click(function(){
			$('#popupOverlay2, #popup2').css("visibility", "visible");
		});
	} else{
		izaberiMenadzera = $('<select></select>');
	}
	
		let comboBoxOption;
		for (let m of menadzeri) {
				comboBoxOption = $('<option value="' + m.korisnickoIme + '">' + m.korisnickoIme + '</option>');
				izaberiMenadzera.append(comboBoxOption);
		}

	td.append(izaberiMenadzera);
	$('#menadzer').append(tdMenadzerLabela).append(td);
}

function displayImages(sportskiObjekti){
	
	for (let s of sportskiObjekti){
				
		let div1 = $('<div class="col-sm-4"></div>');
		let div2 = $('<div class="card text-center" style="width: 18rem;"></div>');
		let img = $('<a href="prikazObjekta.html"><img class="card-img-top" src="pictures/' + s.logo + '" id="' +  s.naziv + '" onClick="saveId(this.id)" height="300"/></a>');
		let div3 = $('<div class="card-body"></div>');
		let title;
		if (s.prosecnaOcena != 0){
			title = $('<h5 class="card-title">' + '<span class="prosecnaOcena">' + s.prosecnaOcena + '</span>' + s.naziv +'</h5>');
		} else {
			title = $('<h5 class="card-title">' + s.naziv +'</h5>');
		}
		let adresa = $('<p style="font-size:14px; margin:0px;">' + s.lokacija.adresa.ulicaIBroj + ', ' +  s.lokacija.adresa.mesto + ', ' + s.lokacija.adresa.postanskiBroj +'</p>');
		let tipObjekta = $('<p style="font-size:14px; margin:0px;">' + s.tipObjekta + '</p>');
		let status;
		if (s.statusObjekta == "Radi"){
				status = $('<p style="color:green; margin:0px;">Otvoreno</p>')
		} else{
				status = $('<p style="color:red; margin:0px;">Zatvoreno</p>')
		}
		let radnoVreme = $('<p style="font-size:14px; margin:0px;">' + s.radnoVreme.odVreme + "-" + s.radnoVreme.doVreme + "h, " + s.radnoVreme.dani[0] + "-" + s.radnoVreme.dani[s.radnoVreme.dani.length - 1] + '</p>');
		
		div3.append(title).append(tipObjekta).append(adresa).append(radnoVreme).append(status);
		div2.append(img).append(div3);
		div1.append(div2);
		$('#row').append(div1);
	}
}

$(document).ready(function() {
	$.get({
		url: 'rest/sportskiObjekti',
		success: function(sportskiObjekti) {
			displayImages(sportskiObjekti);
		}
	});
	
	$.get({
		url: 'rest/kupci/slobodniMenadzeri',
		success: function(menadzeri) {
			ucitajMenadzere(menadzeri);
		}
	});
	
	$('#dodajNoviObjekat').click(function() {
			$('#popupOverlay, #popup').css("visibility", "visible");
	});
	
		
	 $('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});
	
	 $('#popupButtonOtkazi2').click(function(){
		$('#popupOverlay2, #popup2').css("visibility", "hidden");
	});
	
	$('form#dodajMenadzeraForma').submit(function(event) {
		event.preventDefault();
		$('#popupOverlay2, #popup2').css("visibility", "hidden");
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		let ponovljenaLozinka = $('input[name="ponovljenaLozinka"]').val();
		
		if(lozinka != ponovljenaLozinka){
			$('#error').text("Lozinke se ne poklapaju!");
			return;
		}
		
		let menadzer = {ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: 'MENADZER', korisnickoIme: korisnickoIme, lozinka: lozinka, sportskiObjekat: ""};
		localStorage.setItem("menadzerZaSportskiObjekat", JSON.stringify(menadzer));
		$('#menadzer').html("");
		let tdMenadzerLabela = $('<td><label class="label">Menadzer:</labela></td>');
		let td = $('<td><label class="label">' + menadzer.korisnickoIme + '</label></td>');
		$('#menadzer').append(tdMenadzerLabela).append(td);
	});
	
	$('form#dodajObjekatForma').submit(function(event) {
		event.preventDefault();
		$('#popupOverlay, #popup').css("visibility", "hidden");
		let naziv = $('input[name="naziv"]').val();
		let tip = $('#tip').val();
		let logo = $('select[name="logo"]').val();
		let ulicaIBroj = $('input[name="ulicaIBroj"]').val();
		let mesto = $('input[name="mesto"]').val();
		let postanskiBroj = $('input[name="postanskiBroj"]').val();
		let geografskaSirina = $('input[name="geografskaSirina"]').val();
		let geografskaDuzina = $('input[name="geografskaSirina"]').val();
		
		var menadzer = JSON.parse(localStorage.getItem("menadzerZaSportskiObjekat"));
		menadzer.sportskiObjekat = naziv;
		
		$.ajax({
			url: 'rest/kupci/registracijaMenadzera',
			type: 'POST',
			data: JSON.stringify(menadzer),
			contentType: 'application/json',
			success : function() {
				alert('Registracija menadzera je uspešna!');
			}
		});
	});
	
});