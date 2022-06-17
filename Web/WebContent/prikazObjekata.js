function prikaziPodatke(sportskiObjekat){
	$('#logo').attr("src", "pictures/" + sportskiObjekat.logo);
	$('#naziv').text(sportskiObjekat.naziv);
	if(sportskiObjekat.prosecnaOcena != 0){
		$('#ocena').text("Ocena: " + sportskiObjekat.prosecnaOcena);
	} else{
		$('#ocena').text("Ocena:");
	}
	$('#tipObjekta').text(sportskiObjekat.tipObjekta);
	if (sportskiObjekat.statusObjekta == "Radi"){
		$('#status').text("OTVORENO,  " + sportskiObjekat.radnoVreme.odVreme + "-" + sportskiObjekat.radnoVreme.doVreme + "h, " + sportskiObjekat.radnoVreme.dani[0] + "-" + sportskiObjekat.radnoVreme.dani[sportskiObjekat.radnoVreme.dani.length - 1]);
	} else {
		$('#status').text("ZATVORENO,  " + + sportskiObjekat.radnoVreme.odVreme + "-" + sportskiObjekat.radnoVreme.doVreme + "h, " + sportskiObjekat.radnoVreme.dani[0] + "-" + sportskiObjekat.radnoVreme.dani[sportskiObjekat.radnoVreme.dani.length - 1]);	
	}
}

function prikaziKomentare(komentari){
	
	if (komentari.length == 0){
		let nemaKomentara = $('<p>Za ovu teretanu trenutno nema komentara</p>');
		$('#komentari').append(nemaKomentara);
		return;
	}
	
	for (let k of komentari){
		let komentar = $('<p><span class="ocenaKomentar">' + k.ocena + '</span>&nbsp;' + k.kupac + ':</br>&nbsp;&nbsp;&nbsp;&nbsp;' + k.tekstKomentara +'</p>');
		$('#komentari').append(komentar);
	}
	
}

$(document).ready(function() {
	
	 var naziv = JSON.parse(localStorage.getItem("selektovaniObjekat"));
	 
	 $.get({
			url: 'rest/sportskiObjekti/' + naziv,
			success: function(sportskiObjekat) {
				prikaziPodatke(sportskiObjekat);
			}
	 });
	
	 $.get({
			url: 'rest/komentari/' + naziv,
			success: function(komentari) {
				prikaziKomentare(komentari);
			}
	 });
	
});