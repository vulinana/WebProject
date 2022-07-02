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
		slikaTreninga.append(slika);
		tekstTreninga.append(trening);
		$('#sadrzajPrikazaTreninga').append(slikaTreninga);
		$('#sadrzajPrikazaTreninga').append(tekstTreninga);
	}
}

$(document).ready(function() {
	
	 var naziv = JSON.parse(localStorage.getItem("selektovaniObjekat"));
	 
	 $.get({
			url: 'rest/sportskiObjekti/' + naziv,
			success: function(sportskiObjekat) {
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
});