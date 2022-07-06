$(document).ready(function() {

	
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		let uloga = $('select[name="uloga"]').val();
		let ponovljenaLozinka = $('input[name="ponovljenaLozinka"]').val();
		
		if(lozinka != ponovljenaLozinka){
			$('#error').text("Lozinke se ne poklapaju!");
			return;
		}
		
		$.ajax({
			url: 'rest/kupci/registracijaTreneraMenadzera',
			type: 'POST',
			data: JSON.stringify({korisnickoIme: korisnickoIme, lozinka: lozinka, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja, uloga: uloga}),
			contentType: 'application/json',
			success : function() {
				window.location.href = "http://localhost:8080/WebShopREST/pregledKorisnika.html";
			},
			error : function(message) {
				$('#error').text(message.responseText);
			}
		});
	});
	
});