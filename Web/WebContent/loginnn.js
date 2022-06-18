$(document).ready(function() {
		
		
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		$.ajax({
			url: 'rest/kupci/logovanje',
			type: 'POST',
			data: JSON.stringify({korisnickoIme: korisnickoIme, lozinka: lozinka}),
			contentType: 'application/json',
			success : function(korisnik) {
				localStorage.setItem("ulogovaniKorisnik", JSON.stringify(korisnik));
				alert('Logovanje je uspesno!');
				if(korisnik.uloga == 'KUPAC'){
					window.location.href = "http://localhost:8080/WebProject/kupacHomePage.html";
				}
				else if(korisnik.uloga == 'ADMINISTRATOR'){
					window.location.href = "http://localhost:8080/WebProject/administratorHomePage.html";
				}
				else if(korisnik.uloga == 'MENADZER'){
					window.location.href = "http://localhost:8080/WebProject/menadzerHomePage.html";
				} else{
					window.location.href = "http://localhost:8080/WebProject/trenerHomePage.html";
				}
			},
			error : function(message) {
				$('#error').text(message.responseText);
			}
		});
	});
	
});