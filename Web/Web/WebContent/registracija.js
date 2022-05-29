$(document).ready(function() {
		
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		console.log(datumRodjenja);
		$.ajax({
			url: 'rest/kupci/registracija',
			type: 'POST',
			data: JSON.stringify({korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja}),
			contentType: 'application/json',
			success : function() {
				alert('Registracija je uspešna!');
			},
			error : function(message) {
				alert(message.responseText);
			}
		});
	});
	
});