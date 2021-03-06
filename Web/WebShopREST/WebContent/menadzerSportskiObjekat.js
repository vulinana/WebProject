function prikaziPodatke(sportskiObjekat){
	const map = new ol.Map({
		view: new ol.View({
			center: [0, 0],
			zoom: 2
		}),
		layers: [
			new ol.layer.Tile({
				source: new ol.source.OSM()
			})
		],
		target: 'js-map'
	});
	
	var layer = new ol.layer.Vector({
     source: new ol.source.Vector({
         features: [
             new ol.Feature({
                 geometry: new ol.geom.Point(ol.proj.fromLonLat([sportskiObjekat.lokacija.geografskaDuzina, sportskiObjekat.lokacija.geografskaSirina]))
             })
         ]
     })
 	});
 	map.addLayer(layer);
	
	map.getView().animate({zoom: 16, center: ol.proj.fromLonLat([sportskiObjekat.lokacija.geografskaDuzina, sportskiObjekat.lokacija.geografskaSirina])});
	let adresa =  sportskiObjekat.lokacija.adresa.ulicaIBroj + ', ' +  sportskiObjekat.lokacija.adresa.mesto + ', ' + sportskiObjekat.lokacija.adresa.postanskiBroj;
	$('#adresa').text(adresa);
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
		if (k.statusKomentara == 'PRIHVACEN'){
			let komentar = $('<hr><p style="background-color:#cefad0;"><span class="ocenaKomentar">' + k.ocena + '</span>&nbsp;' + k.kupac + ':</br>&nbsp;&nbsp;&nbsp;&nbsp;' + k.tekstKomentara +'</p>');
			$('#komentari').append(komentar);
		}
		if (k.statusKomentara == 'ODBIJEN'){
			let komentar = $('<hr><p style="background-color:#FFCCCB;"><span class="ocenaKomentar">' + k.ocena + '</span>&nbsp;' + k.kupac + ':</br>&nbsp;&nbsp;&nbsp;&nbsp;' + k.tekstKomentara +'</p>');
			$('#komentari').append(komentar);
		}
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
		let izmeniTreningButton = $('<button class="izmeniButtons">Izmeni informacije</button>');
		izmeniTreningButton.click(function(){
			$('input[name="id2"]').val(t.id);
			$('input[name="naziv2"]').val(t.naziv);
			$('#tip2').val(t.tip);
			$('#izmeniObjekatOpis2').val(t.opis);
			$('input[name="trajanje2"]').val(t.trajanje);
			$('input[name="doplata2"]').val(t.doplata);
	        if (t.tip == "Personalni" || t.tip == "Grupni"){
				$('#izmeniObjekatTrener2').css("visibility", "visible");
				$('#labelTrener2').css("visibility", "visible");
				$('#izmeniObjekatTrener2').val(t.trener);
			} else {
				$('#izmeniObjekatTrener2').css("visibility", "hidden");
				$('#labelTrener2').css("visibility", "hidden");
			}
			$('#popupOverlay2, #popup2').css("visibility", "visible");
		});
		let promeniSlikuButton = $('<button class="izmeniButtons">Promeni sliku</button>');
		promeniSlikuButton.click(function(){
			$('input[name="id3"]').val(t.id);
			$('input[name="naziv3"]').val(t.naziv);
			$('#popupOverlay3, #popup3').css("visibility", "visible");	
		});
		
		slikaTreninga.append(slika);
		tekstTreninga.append(trening).append(izmeniTreningButton).append(promeniSlikuButton);
		
		if (t.trener != ""){
			let zakaziTreningButton =  $('<button class="izmeniButtons">Zaka??i trening</button>');
			zakaziTreningButton.click(function(){
				$('input[name="tipTreninga4"]').val(t.tip);
				$('input[name="doplata4"]').val(t.doplata);
				$('input[name="trajanjeTreninga4"]').val(t.trajanje);
				
				$('input[name="naziv4"]').val(t.naziv);
				$('input[name="trener4"]').val(t.trener);
				$('#popupOverlay4, #popup4').css("visibility", "visible");	
			});
			tekstTreninga.append(zakaziTreningButton);
		}
		
		$('#sadrzajPrikazaTreninga').append(slikaTreninga);
		$('#sadrzajPrikazaTreninga').append(tekstTreninga);
	}
}

function updateTreninge(treninzi){
	$("#sadrzajPrikazaTreninga").html("");
	prikaziTreninge(treninzi);
}


function dodajTrenere(treneri) {
		
		for (let t of treneri) {
				comboBoxOption = $('<option value="' + t.korisnickoIme + '">' + t.korisnickoIme + '</option>');
				$('#dodajObjekatTrener').append(comboBoxOption);
		}
		
		for (let t of treneri) {
				comboBoxOption = $('<option value="' + t.korisnickoIme + '">' + t.korisnickoIme + '</option>');
				$('#izmeniObjekatTrener2').append(comboBoxOption);
		}
}

function isInPast(date){
	const currentDate = new Date();
	return currentDate > date;
}

$(document).ready(function() {
	
	 var menadzer = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	 naziv = menadzer.sportskiObjekat;
	 if (naziv == ""){
		$('#logoINaziv').css("visibility", "hidden");
		$('#komentari').css("visibility", "hidden");
		$('#treninzi').css("visibility", "hidden");	
		$('#info').text("Menad??er nije zadu??en ni za jedan sportski objekat!");
		return;
	 }
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
		let naziv = $('input[name="naziv"]').val();
		let tip = $('#tip').val();
		let opis = $('#dodajObjekatOpis').val();
		let trajanje = $('input[name="trajanje"]').val();
		let doplata = $('input[name="doplata"]').val();
		let trener;
		if (tip != "Personalni" && tip != "Grupni"){
			trener = "";
		} else {
			trener = $('#dodajObjekatTrener').val();
		}
		var s = JSON.parse(localStorage.getItem("sportskiObjekat"));
	 	let sportskiObjekatKomPripada = s.naziv;
	 	
	 	var file = $('input[name="file"').get(0).files[0];
		var formData = new FormData();
		formData.append('file', file);
		$.ajax({
		  url :  'rest/treninzi/uploadImage',
		  type : 'POST',
		  data : formData,
		  cache : false,
		  contentType : false,
		  processData : false,
		  success : function(slikaNaziv) {
			setTimeout(function(){
					 $.ajax({
						url: 'rest/treninzi/kreirajNoviTrening',
						type: 'POST',
						data: JSON.stringify({naziv: naziv, tip: tip, sportskiObjekatKomPripada: sportskiObjekatKomPripada, trajanje: trajanje, trener: trener, opis: opis, slika: slikaNaziv, doplata: doplata}),
						contentType: 'application/json',
						success : function() {
							$.get({
									url: 'rest/treninzi/' + sportskiObjekatKomPripada,
									success: function(treninzi) {
										updateTreninge(treninzi);
										$('#popupOverlay, #popup').css("visibility", "hidden");	
										$('#dodajObjekatTrener').css("visibility", "hidden");
										$('#labelTrener').css("visibility", "hidden");
										$('input[name="naziv"]').val("");
										$('#tip').val("");
										$('#dodajObjekatOpis').val("");
										$('input[name="trajanje"]').val("");
										$('input[name="doplata"]').val("");
										$('#error').text("");
									}		
							 });
						},
						error : function(message) {
							$('#error').text('Naziv vec postoji!');
							$("#error").show();
						}
					});
				} , 3000);
		  }
		});
	});
	
	
	$('form#izmeniTreningForma2').submit(function(event) {
		event.preventDefault();
		let id = $('input[name="id2"]').val();
		let naziv = $('input[name="naziv2"]').val();
		let tip = $('#tip2').val();
		let slika = $('input[name="slika2"]').val();
		let opis = $('#izmeniObjekatOpis2').val();
		let trajanje = $('input[name="trajanje2"]').val();
		let doplata = $('input[name="doplata2"]').val();
		let trener;
		if (tip != "Personalni" && tip != "Grupni"){
			trener = "";
		} else {
			trener = $('#izmeniObjekatTrener2').val();
		}
		var s = JSON.parse(localStorage.getItem("sportskiObjekat"));
	 	let sportskiObjekatKomPripada = s.naziv;
		
		$.ajax({
			url: 'rest/treninzi/izmeniTrening/' + id ,
			type: 'PUT',
			data: JSON.stringify({naziv: naziv, tip: tip, sportskiObjekatKomPripada: sportskiObjekatKomPripada, trajanje: trajanje, trener: trener, opis: opis, slika: slika, doplata: doplata}),
			contentType: 'application/json',
			success : function() {
				 $.get({
					url: 'rest/treninzi/' + sportskiObjekatKomPripada,
					success: function(treninzi) {
						updateTreninge(treninzi);
						$('#popupOverlay2, #popup2').css("visibility", "hidden");
						$('#izmeniObjekatTrener2').css("visibility", "hidden");
						$('#labelTrener2').css("visibility", "hidden");
					}
	 			});
				
			}
		});
	
	});
	
	
	
	 $('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
		$('#dodajObjekatTrener').css("visibility", "hidden");
		$('#labelTrener').css("visibility", "hidden");
	});
	
	 $('#popupButtonOtkazi2').click(function(){
		$('#popupOverlay2, #popup2').css("visibility", "hidden");
		$('#izmeniObjekatTrener2').css("visibility", "hidden");
		$('#labelTrener2').css("visibility", "hidden");
	});
	
	$('#popupButtonOtkazi3').click(function(){
		$('#popupOverlay3, #popup3').css("visibility", "hidden");
	});
	
	$('#popupButtonOtkazi4').click(function(){
		$('#popupOverlay4, #popup4').css("visibility", "hidden");
	});
	
	$('form#promeniSliku').submit(function(event) {
		event.preventDefault();
		let id = $('input[name="id3"]').val();
	 	var file = $('input[name="file3"').get(0).files[0];
		var formData = new FormData();
		formData.append('file', file);
		$.ajax({
		  url :  'rest/treninzi/uploadImage',
		  type : 'POST',
		  data : formData,
		  cache : false,
		  contentType : false,
		  processData : false,
		  success : function(slikaNaziv) {
			setTimeout(function(){
					 $.ajax({
						url: 'rest/treninzi/izmeniSlikuTreninga/' + id,
						type: 'PUT',
						data: JSON.stringify({slika: slikaNaziv}),
						contentType: 'application/json',
						success : function(treninzi) {
							updateTreninge(treninzi);	
							$('#popupOverlay3, #popup3').css("visibility", "hidden");	
						}
					});
				} , 3000);
		  }
		});
	});
	
	$('form#zakaziTrening').submit(function(event) {
		event.preventDefault();
		let tipTreninga = $('input[name="tipTreninga4"]').val();
		let doplata = $('input[name="doplata4"]').val();
		let trajanje = $('input[name="trajanjeTreninga4"]').val();
		let nazivTreninga = $('input[name="naziv4"]').val();
		let trener = $('input[name="trener4"]').val();
		let datum = $('input[name="datum4"]').val();
		let vreme = $('input[name="vreme4"]').val();
		let datumIVreme = datum + " " + vreme;
		
		if (isInPast(new Date(datumIVreme))){
			$('#error4').text("Ne mo??ete zakazati trening u pro??losti!");
			return;
		}
		
		$.ajax({
			url: 'rest/terminiTreninga/' + tipTreninga,
			type: 'POST',
			data: JSON.stringify({trener: trener, sportskiObjekat: naziv, nazivTreninga: nazivTreninga, trajanje: trajanje, cenaTreninga: doplata, datumIVreme: datumIVreme}),
			contentType: 'application/json',
			success: function(){
				$('#error4').text("");
				$('input[name="datum4"]').val("");
				$('input[name="vreme4"]').val("");
				$('#popupOverlay4, #popup4').css("visibility", "hidden");	
			}
		});
		
	});
	
	 let tip = $('#tip').val();
        if (tip == "Personalni" || tip == "Grupni"){
			$('#dodajObjekatTrener').css("visibility", "visible");
			$('#labelTrener').css("visibility", "visible");
		} else {
			$('#dodajObjekatTrener').css("visibility", "hidden");
			$('#labelTrener').css("visibility", "hidden");
	}
	$('#tip').change(function(){
        tip = $('#tip').val();
        if (tip == "Personalni" || tip == "Grupni"){
			$('#dodajObjekatTrener').css("visibility", "visible");
			$('#labelTrener').css("visibility", "visible");
		} else {
			$('#dodajObjekatTrener').css("visibility", "hidden");
			$('#labelTrener').css("visibility", "hidden");
		}
    });
	
	$('#tip2').change(function(){
        tip = $('#tip2').val();
        if (tip == "Personalni" || tip == "Grupni"){
			$('#izmeniObjekatTrener2').css("visibility", "visible");
			$('#labelTrener2').css("visibility", "visible");
		} else {
			$('#izmeniObjekatTrener2').css("visibility", "hidden");
			$('#labelTrener2').css("visibility", "hidden");
		}
    });
	
});