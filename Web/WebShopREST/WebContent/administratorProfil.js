$(document).ready(function() {
		$.get({
			url: 'rest/kupci/ulogovanKorisnik',
			contentType: 'application/json',
			success : function(korisnik) {
				 $('input[name="ime"]').val(korisnik.ime);
				 $('input[name="prezime"]').val(korisnik.prezime);
		         $('select[name="pol"]').val(korisnik.pol);
				 $('input[name="datumRodjenja"]').val(korisnik.datumRodjenja);
			},
			error : function(message) {
				alert(message.responseText);
			}
		});
	
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		$.ajax({
			url: 'rest/kupci/izmeniProfil',
			type: 'PUT',
			data: JSON.stringify({ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja}),
			contentType: 'application/json',
			success : function() {
				alert('Profil je uspesno izmenjen!');
			},
			error : function(message) {
				alert(message.responseText);
			}
		});
	
	});

});