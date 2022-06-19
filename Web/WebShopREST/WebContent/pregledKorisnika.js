function addKorisnikTr(korisnik) {
	let tr = $('<tr></tr>');
	let tdKorisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
	let tdIme = $('<td>' + korisnik.ime + '</td>');
	let tdPrezime = $('<td>' + korisnik.prezime + '</td>');
	let tdPol = $('<td>' + korisnik.pol + '</td>');
	let tdDatumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
	let tdUloga = $('<td>' + korisnik.uloga + '</td>');
	tr.append(tdKorisnickoIme).append(tdIme).append(tdPrezime).append(tdPol).append(tdDatumRodjenja).append(tdUloga);
	$('#tabela tbody').append(tr);
}

$(document).ready(function() {
	$.get({
		url: 'rest/kupci',
		success: function(korisnici) {
			for (let korisnik of korisnici) {
				addKorisnikTr(korisnik);
			}
		}
	});
});