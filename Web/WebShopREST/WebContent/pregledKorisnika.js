function addKorisnikTr(korisnik) {
	let tr = $('<tr></tr>');
	let tdKorisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
	let tdIme = $('<td>' + korisnik.ime + '</td>');
	let tdPrezime = $('<td>' + korisnik.prezime + '</td>');
	let tdPol = $('<td>' + korisnik.pol + '</td>');
	let tdDatumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
	let tdUloga = $('<td>' + korisnik.uloga + '</td>');
	let tdBodovi;
	if (korisnik.uloga == 'KUPAC'){
		tdBodovi = $('<td>' + korisnik.brojSakupljenihBodova + '</td>');
	} else{
		tdBodovi = $('<td></td>');
	}
	tr.append(tdKorisnickoIme).append(tdIme).append(tdPrezime).append(tdPol).append(tdDatumRodjenja).append(tdUloga).append(tdBodovi);
	$('#tabela').append(tr);
}

function updateTable(korisnici) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Korisničko ime</th><th>Ime</th><th>Prezime</th><th>Pol</th><th>Datum rodjenja</th><th>Uloga</th><th>Bodovi</th></tr></thead>');
	$('#tabela').append(tr);
	for (let korisnik of korisnici) {
				addKorisnikTr(korisnik);
	}
}

function sortiraj(){
	
	  let kriterijumZaSortiranje = $('#sortComboBox').val();
	  let kriterijumZaFiltriranje;
	  if ($('#filter1ComboBox').val() == 'filtrirajPoUlozi'){
		 kriterijumZaFiltriranje = "bezUloge";
      } else{
		 kriterijumZaFiltriranje = $('#filter1ComboBox').val();
	  }  
   	   if (kriterijumZaSortiranje == "sortiraj"){
	        $.get({
			url: 'rest/kupci/filtrirajPoUlozi/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	   }
   	   
       if (kriterijumZaSortiranje == "imeRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoImenuRastuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	   } 
	   
	    if (kriterijumZaSortiranje == "imeOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoImenuOpadajuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    if (kriterijumZaSortiranje == "prezimeRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoPrezimenuRastuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    if (kriterijumZaSortiranje == "prezimeOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoPrezimenuOpadajuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    if (kriterijumZaSortiranje == "korisnickoImeRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoKorisnickomImenuRastuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	      if (kriterijumZaSortiranje == "korisnickoImeOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoKorisnickomImenuOpadajuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	  
	  
	   if (kriterijumZaSortiranje == "bodoviRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoBodovimaRastuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    
	    if (kriterijumZaSortiranje == "bodoviOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoBodovimaOpadajuce/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
}



$(document).ready(function() {
	$.get({
		url: 'rest/kupci',
		success: function(korisnici) {
			updateTable(korisnici);
		}
	});
	
	$('form#formaZaPretragu').submit(function(event) {
		event.preventDefault();
		$('#sortComboBox').val('sortiraj');
		$('#filter1ComboBox').val('filtrirajPoUlozi');
		let ime = $('#pretragaIme').val();
		let prezime = $('#pretragaPrezime').val();
		let korisnickoIme = $('#pretragaKorisnickoIme').val();
		$.ajax({
			url: 'rest/kupci/pretrazi',
			type: 'PUT',
			data: JSON.stringify({ime: ime, prezime: prezime, korisnickoIme: korisnickoIme}),
			contentType: 'application/json',
			success: function(korisnici) {
				updateTable(korisnici);
			}
		});
	});
	
	 $('#sortComboBox').change(function(){
         sortiraj();
    });
    
     $('#filter1ComboBox').change(function(){
         sortiraj();
    });
});