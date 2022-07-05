function moreThanTwoDaysLeftUntilTraning(date){
	const d1 = new Date(date);
	const d2 = new Date();
	d2.setDate(d2.getDate() + 2);
	return d2 < d1;
}

function addTreningTr(t) {
	let tr = $('<tr></tr>');
	let tdSportskiObjekat = $('<td>' + t.sportskiObjekat + '</td>');
	let tdTipObjekta = $('<td>' + t.tipObjekta + '</td>');
	let tdNazivTreninga= $('<td>' + t.nazivTreninga + '</td>');
	let tdTipTreninga = $('<td>' + t.tipTreniga + '</td>');
	let tdTrajanje= $('<td>' + t.trajanje + '</td>');
	let tdCenaTreninga = $('<td>' + t.cenaTreninga + '</td>');
	let tdDatumIVreme = $('<td>' + t.datumIVreme + '</td>');
	tr.append(tdSportskiObjekat).append(tdTipObjekta).append(tdNazivTreninga).append(tdTipTreninga).append(tdTrajanje).append(tdCenaTreninga).append(tdDatumIVreme);
	
	let buttonOtkazi;
	if (t.tipTreniga == 'Personalni' && moreThanTwoDaysLeftUntilTraning(t.datumIVreme)){
	    buttonOtkazi = $('<td><button class="button">Otkaži</button></td>');
		buttonOtkazi.click(function(){
			$.ajax({
				url: 'rest/terminiTreninga/' + t.id,
				type: 'DELETE',
				success: function(terminiTreninga) {
					updateTable(terminiTreninga);
			}
			});
		});
	} else {
		buttonOtkazi = $('<td></td>');
	}
	tr.append(buttonOtkazi);
	$('#tabela').append(tr);
}

function updateTable(treninzi) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Objekat</th><th>Tip objekta</th><th>Naziv treininga</th><th>Tip treninga</th><th>Trajanje</th><th>Cena</th><th>Datum i vreme</th><th></th></tr><thead>');
	$('#tabela').append(tr);
	for (let trening of treninzi) {
				addTreningTr(trening);
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
				const datum = new Date(t.datumIVreme);
				if (t.sportskiObjekat.toLowerCase().startsWith(objekat.toLowerCase()) && t.cenaTreninga >= minCena && t.cenaTreninga <= maxCena && datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (objekat != "" && minCena != "" && maxCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.sportskiObjekat.toLowerCase().startsWith(objekat.toLowerCase()) && t.cenaTreninga >= minCena && t.cenaTreninga <= maxCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (objekat != "" && minDatum != "" && maxDatum != ""){
				const minD = new Date(minDatum + " 00:00");
			const maxD = new Date(maxDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVreme);
				if (t.sportskiObjekat.toLowerCase().startsWith(objekat.toLowerCase()) && datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minCena != "" && maxCena != "" && minDatum != "" && maxDatum != ""){
			const minD = new Date(minDatum + " 00:00");
			const maxD = new Date(maxDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVreme);
				if (t.cenaTreninga >= minCena && t.cenaTreninga <= maxCena && datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		}
		 else if (objekat != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.sportskiObjekat.toLowerCase().startsWith(objekat.toLowerCase())){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minCena != "" && maxCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.cenaTreninga >= minCena && t.cenaTreninga <= maxCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.cenaTreninga >= minCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (maxCena != "") {
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.cenaTreninga <= maxCena){
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
				const datum = new Date(t.datumIVreme);
				if (datum >= minD && datum <= maxD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if(minDatum != ""){
			const minD = new Date(minDatum + " 00:00");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVreme);
				if (datum >= minD){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (maxDatum != ""){
			const maxD = new Date(minDatum + " 23:59");
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				const datum = new Date(t.datumIVreme);
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
	    prikazaniTreninzi.sort((a, b) => a.sportskiObjekat.localeCompare(b.sportskiObjekat))
		updateTable(prikazaniTreninzi);
	 } 
	 if (kriterijumZaSortiranje == "nazivObjektaOpadajuce"){
	     prikazaniTreninzi.sort((a, b) => b.sportskiObjekat.localeCompare(a.sportskiObjekat))
		 updateTable(prikazaniTreninzi);
	 }
	 
	 if (kriterijumZaSortiranje == "cenaTreningaRastuce"){
		 prikazaniTreninzi.sort((a, b) => a.cenaTreninga - b.cenaTreninga);
		 updateTable(prikazaniTreninzi);
	} 
	
	if (kriterijumZaSortiranje == "cenaTreningaOpadajuce"){
		prikazaniTreninzi.sort((a, b) => b.cenaTreninga - a.cenaTreninga);
		updateTable(prikazaniTreninzi);
	}
	
	if (kriterijumZaSortiranje == "datumRastuce"){
		prikazaniTreninzi.sort((a, b) => new Date(a.datumIVreme) - new Date(b.datumIVreme));
		updateTable(prikazaniTreninzi);
	}
	
	if (kriterijumZaSortiranje == "datumOpadajuce"){
		prikazaniTreninzi.sort((a, b) => new Date(b.datumIVreme) - new Date(a.datumIVreme));
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
			if (t.tipObjekta == tipObjekta){
				filtriraniTermini.push(t);
			}
		}
		
		let filtriraniTermini2 = new Array();
		for (let t of filtriraniTermini) {
			if (t.tipTreniga == tipTreninga){
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
			if (t.tipObjekta == tipObjekta){
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
			if (t.tipTreniga == tipTreninga){
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
		url: 'rest/terminiTreninga/' + korisnik.korisnickoIme,
		success: function(terminiTreninga) {
			updateTable(terminiTreninga);
			localStorage.setItem("terminiTreninga", JSON.stringify(terminiTreninga));
			localStorage.setItem("prikazaniTreninzi", JSON.stringify(terminiTreninga));
			localStorage.setItem("filtriraniTreninzi", JSON.stringify(terminiTreninga));
		}
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