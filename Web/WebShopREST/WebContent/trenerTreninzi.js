function addTreningTr(trening) {
	let tr = $('<tr></tr>');
	let tdNazivTreninga = $('<td>' + trening.naziv + '</td>');
	let tdTip = $('<td>' + trening.tip + '</td>');
	let tdSportskiObjekat = $('<td>' + trening.sportskiObjekatKomPripada + '</td>');
	let tdTrajanje = $('<td>' + trening.trajanje + '</td>');
	let tdDoplata = $('<td>' + trening.doplata + '</td>');
	
	tr.append(tdNazivTreninga).append(tdTip).append(tdSportskiObjekat).append(tdTrajanje).append(tdDoplata);
	if (trening.tip == 'Personalni'){
		let buttonOtkazi = $('<td><button>Otkazi</button></td>');
		tr.append(buttonOtkazi);
	}
	$('#tabela').append(tr);
}

function updateTable(treninzi) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Naziv</th><th>Tip</th><th>Sportski objekat</th><th>Trajanje</th><th>Doplata</th><th></th></tr><thead>');
	$('#tabela').append(tr);
	for (let trening of treninzi) {
				addTreningTr(trening);
	}
}



$(document).ready(function() {
	
	var korisnik = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	
	$.get({
		url: 'rest/treninzi/treneroviTreninzi/' + korisnik.korisnickoIme,
		success: function(treninzi) {
			updateTable(treninzi);
		}
	});
});