function addKorisnikTr(korisnik) {
	let tr = $('<tr></tr>');
	let tdKorisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
	let tdIme = $('<td>' + korisnik.ime + '</td>');
	let tdPrezime = $('<td>' + korisnik.prezime + '</td>');
	let tdPol = $('<td>' + korisnik.pol + '</td>');
	let tdDatumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
	tr.append(tdKorisnickoIme).append(tdIme).append(tdPrezime).append(tdPol).append(tdDatumRodjenja);
	$('#tabela').append(tr);
}

function updateTable(korisnici) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Korisničko ime</th><th>Ime</th><th>Prezime</th><th>Pol</th><th>Datum rodjenja</th></tr></thead>');
	$('#tabela').append(tr);
	for (let korisnik of korisnici) {
				addKorisnikTr(korisnik);
	}
}

$(document).ready(function() {
	var menadzer = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	naziv = menadzer.sportskiObjekat;
	$.get({
		url: 'rest/treninzi/treneri/' + naziv,
		success: function(treneri) {
			updateTable(treneri);
		}
	});
});