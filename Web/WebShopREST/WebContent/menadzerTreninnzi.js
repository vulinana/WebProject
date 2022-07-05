function addTreningTr(t) {
	let tr = $('<tr></tr>');
	let tdTrener = $('<td>' + t.trener + '</td>');
	let tdNazivTreninga= $('<td>' + t.nazivTreninga + '</td>');
	let tdTipTreninga = $('<td>' + t.tipTreniga + '</td>');
	let tdTrajanje= $('<td>' + t.trajanje + '</td>');
	let tdCenaTreninga = $('<td>' + t.cenaTreninga + '</td>');
	let tdDatumIVreme = $('<td>' + t.datumIVreme + '</td>');
	tr.append(tdTrener).append(tdNazivTreninga).append(tdTipTreninga).append(tdTrajanje).append(tdCenaTreninga).append(tdDatumIVreme);
	$('#tabela').append(tr);
}

function updateTable(treninzi) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Trener</th><th>Naziv treininga</th><th>Tip treninga</th><th>Trajanje</th><th>Cena</th><th>Datum i vreme</th></tr><thead>');
	$('#tabela').append(tr);
	for (let trening of treninzi) {
				addTreningTr(trening);
	}
}

function search(minCena, maxCena, minDatum, maxDatum){
		$('#sortComboBox').val("sortiraj");
		$('#filterPoTipuTreninga').val("");
		var terminiTreninga = JSON.parse(localStorage.getItem("terminiTreninga"));
		
		if (minCena != "" && maxCena != "" && minDatum != "" && maxDatum != ""){
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
		} else if (minCena != "" && maxCena != ""){
			let zeljeniTerminiTreninga = new Array();
			for (let t of terminiTreninga) {
				if (t.cenaTreninga >= minCena && t.cenaTreninga <= maxCena){
					zeljeniTerminiTreninga.push(t);
				}
			}
			updateTable(zeljeniTerminiTreninga);
		} else if (minDatum != "" && maxDatum != ""){
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
	let tipTreninga = $('#filterPoTipuTreninga').val();
	let filtriraniTermini = new Array();
	
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
	
	 var menadzer = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	 naziv = menadzer.sportskiObjekat;
	 $.get({
			url: 'rest/terminiTreninga/terminiZaSportskiObjekat/' + naziv,
			success: function(terminiTreninga) {
				updateTable(terminiTreninga);
				localStorage.setItem("terminiTreninga", JSON.stringify(terminiTreninga));
				localStorage.setItem("prikazaniTreninzi", JSON.stringify(terminiTreninga));
				localStorage.setItem("filtriraniTreninzi", JSON.stringify(terminiTreninga));
			}
	});
	
	$('form#formaZaPretragu').submit(function(event) {
		event.preventDefault();
		let minCena = $('#pretragaMinCena').val();
		let maxCena = $('#pretragaMaxCena').val();
		let minDatum = $('#pretragaMinDatum').val();
		let maxDatum = $('#pretragaMaxDatum').val();
		search(minCena, maxCena, minDatum, maxDatum);
	});
	
	 $('#sortComboBox').change(function(){
         sortiraj();
    });
    
     $('#filterPoTipuTreninga').change(function(){
         filtriraj();
         sortiraj();
    });
});