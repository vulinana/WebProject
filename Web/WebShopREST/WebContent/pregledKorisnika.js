function addKorisnikTr(korisnik) {
	let tr = $('<tr></tr>');
	let tdKorisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
	let tdIme = $('<td>' + korisnik.ime + '</td>');
	let tdPrezime = $('<td>' + korisnik.prezime + '</td>');
	let tdPol = $('<td>' + korisnik.pol + '</td>');
	let tdDatumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
	let tdUloga = $('<td>' + korisnik.uloga + '</td>');
	tr.append(tdKorisnickoIme).append(tdIme).append(tdPrezime).append(tdPol).append(tdDatumRodjenja).append(tdUloga);
	$('#tabela').append(tr);
}

function updateTable(korisnici) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Korisničko ime</th><th>Ime</th><th>Prezime</th><th>Pol</th><th>Datum rodjenja</th><th>Uloga</th></tr></thead>');
	$('#tabela').append(tr);
	for (let korisnik of korisnici) {
				addKorisnikTr(korisnik);
	}
}


$(document).ready(function() {
	$.get({
		url: 'rest/kupci',
		success: function(korisnici) {
			updateTable(korisnici);
		}
	});
	
	$('form#formaZaPretragu').submit(function(event) {
		event.preventDefault();
		let ime = $('#pretragaIme').val();
		let prezime = $('#pretragaPrezime').val();
		let korisnickoIme = $('#pretragaKorisnickoIme').val();
		$.ajax({
			url: 'rest/kupci/pretrazi',
			type: 'PUT',
			data: JSON.stringify({ime: ime, prezime: prezime, korisnickoIme: korisnickoIme}),
			contentType: 'application/json',
			success: function(korisnici) {
				updateTable(korisnici);
			}
		});
	});
});