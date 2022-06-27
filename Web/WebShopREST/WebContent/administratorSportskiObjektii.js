function ucitajMenadzere(menadzeri) {
	let tdMenadzerLabela = $('<td><label class="label">Menadzer:</labela></td>');
	
	let td = $('<td></td>');
	let izaberiMenadzera;
	if (menadzeri.length === 0){
		izaberiMenadzera = $('<button type="button" id="kreirajMenadzeraButton">Kreiraj</button>');
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

function displayImage(s){
				
		let div1 = $('<div class="col-sm-4"></div>');
		let div2 = $('<div class="card text-center" style="width: 18rem;"></div>');
		let img = $('<a href="administratorPrikazObjekta.html"><img class="card-img-top" src="pictures/' + s.logo + '" id="' +  s.naziv + '" onClick="saveId(this.id)" height="300"/></a>');
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

function saveId(id)
{
	 	localStorage.setItem("selektovaniObjekat", JSON.stringify(id));
}


$(document).ready(function() {
	$.get({
		url: 'rest/sportskiObjekti',
		success: function(sportskiObjekti) {
			for (let s of sportskiObjekti) {
				displayImage(s);
			}
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
		localStorage.setItem("menadzerZaSportskiObjekat", null);
	});
	
	 $('#popupButtonOtkazi2').click(function(){
		$('#popupOverlay2, #popup2').css("visibility", "hidden");
	});
	
	$('form#dodajMenadzeraForma').submit(function(event) {
		event.preventDefault();
		$('#error2').text("");
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		let ponovljenaLozinka = $('input[name="ponovljenaLozinka"]').val();
		
		if(lozinka != ponovljenaLozinka){
			$('#error2').text("Lozinke se ne poklapaju!");
			return;
		}
		
		$.ajax({
			url: 'rest/kupci/usernameExists',
			type: 'POST',
			data: JSON.stringify({ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: 'MENADZER', korisnickoIme: korisnickoIme, lozinka: lozinka, sportskiObjekat: ""}),
			contentType: 'application/json',
			success : function(message) {
				localStorage.setItem("menadzerZaSportskiObjekat", JSON.stringify({ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: 'MENADZER', korisnickoIme: korisnickoIme, lozinka: lozinka, sportskiObjekat: ""}));
				$('#menadzer').html("");
				let tdMenadzerLabela = $('<td><label class="label">Menadzer:</labela></td>');
				let td = $('<td><button type="button" id="kreirajMenadzeraButton">' + korisnickoIme + '</button></td>');
				td.click(function(){
					$('#popupOverlay2, #popup2').css("visibility", "visible");
				});
				$('#menadzer').append(tdMenadzerLabela).append(td);
				$('#popupOverlay2, #popup2').css("visibility", "hidden");
			},
			error : function(message) {
				$('#error2').text("Zauzeto korisničko ime");
			}
		});
	});
	
	
	$('form#dodajObjekatForma').submit(function(event) {
		event.preventDefault();
		$('#error').text("");
		let naziv = $('input[name="naziv"]').val();
		let tip = $('#tip').val();
		let ulicaIBroj = $('input[name="ulicaIBroj"]').val();
		let mesto = $('input[name="mesto"]').val();
		let postanskiBroj = $('input[name="postanskiBroj"]').val();
		let geografskaSirina = $('input[name="geografskaSirina"]').val();
		let geografskaDuzina = $('input[name="geografskaSirina"]').val();
		
		var menadzer = JSON.parse(localStorage.getItem("menadzerZaSportskiObjekat"));
		if (menadzer == null){
			$('#error').text("Menadžer nije odabran");
			return;
		}
		
		var file = $('input[name="file"').get(0).files[0];
		var formData = new FormData();
		formData.append('file', file);
		$.ajax({
		  url :  'rest/sportskiObjekti/uploadImage',
		  type : 'POST',
		  data : formData,
		  cache : false,
		  contentType : false,
		  processData : false,
		  success : function(slikaNaziv) {
			console.log(slikaNaziv);
			setTimeout(function(){
					 $.ajax({
						url: 'rest/sportskiObjekti/kreirajSportskiObjekat',
						type: 'POST',
						data: JSON.stringify({naziv: naziv, tipObjekta: tip, logo: slikaNaziv, lokacija : {geografskaSirina: geografskaSirina, geografskaDuzina: geografskaDuzina, adresa: {ulicaIBroj: ulicaIBroj, mesto: mesto, postanskiBroj: postanskiBroj}}}),
						contentType: 'application/json',
						success : function(s) {
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
							
							displayImage(s);	
							$('#popupOverlay, #popup').css("visibility", "hidden");		
						},
						error : function(message) {
							$('#error').text("Naziv za sportski objekat mora biti jedinstven");
						}
					});
				} , 3000);
		  }
		});
		localStorage.setItem("menadzerZaSportskiObjekat", null);
	});
});
