function prikaziPodatke(sportskiObjekat){
	$('#logo').attr("src", "pictures/" + sportskiObjekat.logo);
	$('#naziv').text(sportskiObjekat.naziv);
	$('#ocena').text("Ocena: " + sportskiObjekat.prosecnaOcena);
	$('#tipObjekta').text(sportskiObjekat.tipObjekta);
	if (sportskiObjekat.statusObjekta == "Radi"){
		$('#status').text("OTVORENO,  " + sportskiObjekat.radnoVreme.odVreme + "-" + sportskiObjekat.radnoVreme.doVreme + "h, " + sportskiObjekat.radnoVreme.dani[0] + "-" + sportskiObjekat.radnoVreme.dani[sportskiObjekat.radnoVreme.dani.length - 1]);
	} else {
		$('#status').text("ZATVORENO,  " + + sportskiObjekat.radnoVreme.odVreme + "-" + sportskiObjekat.radnoVreme.doVreme + "h, " + sportskiObjekat.radnoVreme.dani[0] + "-" + sportskiObjekat.radnoVreme.dani[sportskiObjekat.radnoVreme.dani.length - 1]);	
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
	
});