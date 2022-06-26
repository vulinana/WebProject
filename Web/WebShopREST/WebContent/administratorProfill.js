$(document).ready(function() {
		$.get({
			url: 'rest/kupci/ulogovanKorisnik',
			contentType: 'application/json',
			success : function(korisnik) {
				 $('input[name="korisnickoIme"]').val(korisnik.korisnickoIme);
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
		$('#popupOverlay, #popup').css("visibility", "visible");
		event.preventDefault();
		let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
		let datumRodjenja = $('input[name="datumRodjenja"]').val();
		localStorage.setItem("izmenjeniKorisnik", JSON.stringify({korisnickoIme: korisnickoIme, ime: ime, prezime: prezime, pol: pol, datumRodjenja: datumRodjenja}));
	});
	
	 $('form#potvrdiSifrom').submit(function(event){
		event.preventDefault();
		let sifra = $('input[name="sifra"]').val();
		var korisnik = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
		if (sifra == korisnik.lozinka) {
			$('#popupOverlay, #popup').css("visibility", "hidden");
			var k = JSON.parse(localStorage.getItem("izmenjeniKorisnik"));
			$.ajax({
				url: 'rest/kupci/izmeniProfil',
				type: 'PUT',
				data: JSON.stringify({korisnickoIme: k.korisnickoIme, ime: k.ime, prezime: k.prezime, pol: k.pol, datumRodjenja: k.datumRodjenja}),
				contentType: 'application/json',
				success : function() {
					$('#error').attr("hidden", true);
					$('#success').text("Profil je uspešno izmenjen");
					$('#success').fadeOut(3000);
				},
				error : function(message) {
					$('#error').text(message.responseText);
					$('#error').show();
				}
			});
			
			$('#error2').attr("hidden", true);
			$('input[name="sifra"]').val("");
			
		} else {
			$('#error2').text("Pogrešna lozinka");
			$('#error2').show();
		}
	});
	
	 $('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});

});