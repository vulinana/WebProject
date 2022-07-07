function isMoreThan30DaysAgo(date){
	const date30daysAgo = new Date();
	date30daysAgo.setDate(date30daysAgo.getDate() - 30);
	return date < date30daysAgo;
}

function addTreningTr(t) {
	let tr = $('<tr></tr>');
	let tdNazivObjekta = $('<td>' + t.sportskiObjekat.naziv + '</td>');
	let tdTipObjekta = $('<td>' + t.sportskiObjekat.tipObjekta + '</td>');
	let tdNazivTreninga = $('<td>' + t.trening.naziv + '</td>');
	let tdTipTreninga = $('<td>' + t.trening.tip + '</td>');
	let tdTrajanjeTreninga = $('<td>' + t.trening.trajanje + '</td>');
	let tdCenaTreninga = $('<td>' + t.trening.doplata + '</td>');
	let tdDatum = $('<td>' + t.datumIVremePrijave + '</td>');
	tr.append(tdNazivObjekta).append(tdTipObjekta).append(tdNazivTreninga).append(tdTipTreninga).append(tdTrajanjeTreninga).append(tdCenaTreninga).append(tdDatum);
	$('#tabela').append(tr);
}

function updateTable(istorijaTreninga) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Objekat</th><th>Tip objekta</th><th>Naziv treininga</th><th>Tip treninga</th><th>Trajanje</th><th>Cena</th><th>Datum i vreme</th></tr><thead>');
	$('#tabela').append(tr);
	for (let trening of istorijaTreninga) {
		if (!isMoreThan30DaysAgo(new Date(trening.datumIVremePrijave))){
				addTreningTr(trening);
		}
	}
}

function ucitajSportskeObjekte(sportskiObjekti) {
		let comboBoxOption;
		comboBoxOption = $('<option value="" hidden>Select</option>');
		$('#sportskiObjektiComboBox').append(comboBoxOption);
		for (let s of sportskiObjekti) {
				comboBoxOption = $('<option value="' + s.naziv + '">' + s.naziv  + '</option>');
				$('#sportskiObjektiComboBox').append(comboBoxOption);
		}
}

function ucitajTreninge(treninzi) {
	$('#trening').html("");
	let tdTreningLabela = $('<td><label class="label">Trening:</labela></td>');
	
	let td = $('<td></td>');
	let izaberiTrening =$('<select class="comboBox" id="treningComboBox"></select>');
	
		let comboBoxOption;
		for (let t of treninzi) {
				comboBoxOption = $('<option value="' + t.naziv + '">' + t.naziv  + '</option>');
				izaberiTrening.append(comboBoxOption);
		}

	td.append(izaberiTrening);
	$('#trening').append(tdTreningLabela).append(td);
}

function ostaviKomentar(ostaviKomentar){
	
	if (ostaviKomentar == true){
		$('#popupOverlay2, #popup2').css("visibility", "visible");	
	}
}

function search(objekat, minCena, maxCena, minDatum, maxDatum){
		$('#sortComboBox').val("sortiraj");
		$('#filterPoTipuObjekta').val("");
		$('#filterPoTipuTreninga').val("");
		var terminiTreninga = JSON.parse(localStorage.getItem("terminiTreninga"));
		
		if (objekat != "" && minCena != "" && maxCena != "" && minDatum != "" && maxDatum != ""){
			const minD = new Date(minDatum + " 00:00");
			const maxD = new Date(maxDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVremePrijave);
				if (t.sportskiObjekat.naziv.toLowerCase().startsWith(objekat.toLowerCase()) && t.trening.doplata >= minCena && t.trening.doplata <= maxCena && datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (objekat != "" && minCena != "" && maxCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.sportskiObjekat.naziv.toLowerCase().startsWith(objekat.toLowerCase()) && t.trening.doplata >= minCena && t.trening.doplata <= maxCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (objekat != "" && minDatum != "" && maxDatum != ""){
				const minD = new Date(minDatum + " 00:00");
			const maxD = new Date(maxDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVremePrijave);
				if (t.sportskiObjekat.naziv.toLowerCase().startsWith(objekat.toLowerCase()) && datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minCena != "" && maxCena != "" && minDatum != "" && maxDatum != ""){
			const minD = new Date(minDatum + " 00:00");
			const maxD = new Date(maxDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVremePrijave);
				if (t.trening.doplata >= minCena && t.trening.doplata <= maxCena && datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		}
		 else if (objekat != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.sportskiObjekat.naziv.toLowerCase().startsWith(objekat.toLowerCase())){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minCena != "" && maxCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.trening.doplata >= minCena && t.trening.doplata <= maxCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.trening.doplata >= minCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (maxCena != "") {
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.trening.doplata <= maxCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} 
		else if (minDatum != "" && minDatum != ""){
			const minD = new Date(minDatum + " 00:00");
			const maxD = new Date(maxDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVremePrijave);
				if (datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if(minDatum != ""){
			const minD = new Date(minDatum + " 00:00");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVremePrijave);
				if (datum >= minD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (maxDatum != ""){
			const maxD = new Date(minDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVremePrijave);
				if (datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else {
			updateTable(terminiTreninga);
		}
		
		localStorage.setItem("prikazaniTreninzi", JSON.stringify(treninzi));
}

function sortiraj(){
	 let kriterijumZaSortiranje = $('#sortComboBox').val();
	 
	  var prikazaniTreninzi = JSON.parse(localStorage.getItem("filtriraniTreninzi"));
     if (kriterijumZaSortiranje == "nazivObjektaRastuce"){
	    prikazaniTreninzi.sort((a, b) => a.sportskiObjekat.naziv.localeCompare(b.sportskiObjekat.naziv))
		updateTable(prikazaniTreninzi);
	 } 
	 if (kriterijumZaSortiranje == "nazivObjektaOpadajuce"){
	     prikazaniTreninzi.sort((a, b) => b.sportskiObjekat.naziv.localeCompare(a.sportskiObjekat.naziv))
		 updateTable(prikazaniTreninzi);
	 }
	 
	 if (kriterijumZaSortiranje == "cenaTreningaRastuce"){
		 prikazaniTreninzi.sort((a, b) => a.trening.doplata - b.trening.doplata);
		 updateTable(prikazaniTreninzi);
	} 
	
	if (kriterijumZaSortiranje == "cenaTreningaOpadajuce"){
		prikazaniTreninzi.sort((a, b) => b.trening.doplata - a.trening.doplata);
		updateTable(prikazaniTreninzi);
	}
	
	if (kriterijumZaSortiranje == "datumRastuce"){
		prikazaniTreninzi.sort((a, b) => new Date(a.datumIVremePrijave) - new Date(b.datumIVremePrijave));
		updateTable(prikazaniTreninzi);
	}
	
	if (kriterijumZaSortiranje == "datumOpadajuce"){
		prikazaniTreninzi.sort((a, b) => new Date(b.datumIVremePrijave) - new Date(a.datumIVremePrijave));
		updateTable(prikazaniTreninzi);
	}
	 
}

function filtriraj(){
	let tipObjekta = $('#filterPoTipuObjekta').val();
	let tipTreninga = $('#filterPoTipuTreninga').val();
	let filtriraniTermini = new Array();
	
	if (tipObjekta != "" && tipTreninga != ""){
		var prikazaniTreninzi = JSON.parse(localStorage.getItem("prikazaniTreninzi"));
		for (let t of prikazaniTreninzi) {
			if (t.sportskiObjekat.tipObjekta == tipObjekta){
				filtriraniTermini.push(t);
			}
		}
		
		let filtriraniTermini2 = new Array();
		for (let t of filtriraniTermini) {
			if (t.trening.tip == tipTreninga){
				filtriraniTermini2.push(t);
			}
		}
		updateTable(filtriraniTermini2);
		localStorage.setItem("filtriraniTreninzi", JSON.stringify(filtriraniTermini2));
		return;
	}
	
	if (tipObjekta != ""){
		var prikazaniTreninzi = JSON.parse(localStorage.getItem("prikazaniTreninzi"));
		for (let t of prikazaniTreninzi) {
			if (t.sportskiObjekat.tipObjekta == tipObjekta){
				filtriraniTermini.push(t);
			}
		}
		updateTable(filtriraniTermini);
		localStorage.setItem("filtriraniTreninzi", JSON.stringify(filtriraniTermini));
		return;
	}
	
	if (tipTreninga != ""){
		var prikazaniTreninzi = JSON.parse(localStorage.getItem("prikazaniTreninzi"));
		for (let t of prikazaniTreninzi) {
			if (t.trening.tip == tipTreninga){
				filtriraniTermini.push(t);
			}
		}
		updateTable(filtriraniTermini);
		localStorage.setItem("filtriraniTreninzi", JSON.stringify(filtriraniTermini));
		return;
	}
}

$(document).ready(function() {
	
	var korisnik = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	$.get({
		url: 'rest/istorijaTreninga/' + korisnik.korisnickoIme,
		success: function(istorijaTreninga) {
			updateTable(istorijaTreninga);
			localStorage.setItem("terminiTreninga", JSON.stringify(istorijaTreninga));
			localStorage.setItem("prikazaniTreninzi", JSON.stringify(istorijaTreninga));
			localStorage.setItem("filtriraniTreninzi", JSON.stringify(istorijaTreninga));
		}
	});
	
	$.get({
		url: 'rest/sportskiObjekti',
		success: function(sportskiObjekti) {
			ucitajSportskeObjekte(sportskiObjekti);
		}
	});
	
	 $('#treniraj').click(function(){
		$('#popupOverlay, #popup').css("visibility", "visible");
	});
	
	$('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});
	
	$('#popupButtonOtkazi2').click(function(){
		$('#popupOverlay2, #popup2').css("visibility", "hidden");
	});
	
	 $('#sportskiObjektiComboBox').change(function(){
		 let naziv = $('#sportskiObjektiComboBox').val();
         $.get({
			url: 'rest/treninzi/' + naziv,
			success: function(treninzi) {
				ucitajTreninge(treninzi);
			}
		});
    });
    				
    
    
    $('form#prijaviSeUSportskiObjekat').submit(function(event) {
		event.preventDefault();
		$('#error').text("");
		let sportskiObjekat = $('#sportskiObjektiComboBox').val();
		let trening = $('#treningComboBox').val();
		if (sportskiObjekat == null || trening == null){
			$('#error').text("Niste izabrali sportski objekat i/ili trening");
			return;
		}
		
		let komentarisi;
		$.ajax({
			url: 'rest/istorijaTreninga/' + korisnik.korisnickoIme + '/' + sportskiObjekat ,
			type: 'GET',
			success : function(istorijaTreninga) {
				if (istorijaTreninga.length === 0){
					komentarisi = true;
				} else {
					komentarisi = false;
				}
				
				$.ajax({
					url: 'rest/istorijaTreninga' ,
					type: 'POST',
					data: JSON.stringify({kupac: korisnik.korisnickoIme, sportskiObjekat: {naziv: sportskiObjekat}, trening: {naziv: trening}}),
					contentType: 'application/json',
					success : function(istorijaTreninga) {
						localStorage.setItem("terminiTreninga", JSON.stringify(istorijaTreninga));
						localStorage.setItem("prikazaniTreninzi", JSON.stringify(istorijaTreninga));
						localStorage.setItem("filtriraniTreninzi", JSON.stringify(istorijaTreninga));
						$('#sortComboBox').val("sortiraj");
						$('#filterPoTipuObjekta').val("");
						$('#filterPoTipuTreninga').val("");
						updateTable(istorijaTreninga);
						$('#popupOverlay, #popup').css("visibility", "hidden");
						$('#komentarSportskiObjekat').val(sportskiObjekat);
						ostaviKomentar(komentarisi);
					},
					error : function(message) {
						$('#error').text(message.responseText);
					}
				});
			}
		});
		
	});
	
	
	 $('form#komentarisiSportskiObjekatForma').submit(function(event) {
		event.preventDefault();
		let sportskiObjekat = $('#komentarSportskiObjekat').val();
		let tekstKomentara = $('#komentar').val();
		let ocena = $('#ocena').val();
		
		$.ajax({
			url: 'rest/komentari',
			type: 'POST',
			data: JSON.stringify({kupac: korisnik.korisnickoIme, sportskiObjekat: sportskiObjekat, tekstKomentara: tekstKomentara, ocena: ocena}),
			contentType: 'application/json'
		});
		
	    $('#popupOverlay2, #popup2').css("visibility", "hidden");
		
	});
	
	$('form#formaZaPretragu').submit(function(event) {
		event.preventDefault();
		let objekat = $('#pretragaObjekat').val();
		let minCena = $('#pretragaMinCena').val();
		let maxCena = $('#pretragaMaxCena').val();
		let minDatum = $('#pretragaMinDatum').val();
		let maxDatum = $('#pretragaMaxDatum').val();
		search(objekat, minCena, maxCena, minDatum, maxDatum);
	});
	
	 $('#sortComboBox').change(function(){
         sortiraj();
    });
    
     $('#filterPoTipuObjekta').change(function(){
         filtriraj();
         sortiraj();
    });
    
     $('#filterPoTipuTreninga').change(function(){
         filtriraj();
         sortiraj();
    });
	
});