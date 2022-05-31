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
		
		$('#pocetna').click(function() {
		 $.get({
			url: "rest/kupci/ulogovanKorisnik",
			success : function(korisnik) {
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
				alert(message.responseText);
			}
   		 });
	});
	
});