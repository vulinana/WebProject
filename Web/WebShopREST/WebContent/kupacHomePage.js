$(document).ready(function() {

	
	var ulogovaniKorisnik = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	$.ajax({
		url: 'rest/kupci/preracunajBodove',
		type: 'PUT',
		data: JSON.stringify({korisnickoIme: ulogovaniKorisnik.korisnickoIme}),
		contentType: 'application/json',
		success : function() {
		}
	});
});