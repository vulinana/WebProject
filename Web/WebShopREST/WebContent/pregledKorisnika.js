function addKorisnikTr(korisnik) {
	let tr = $('<tr></tr>');
	let tdKorisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
	let tdIme = $('<td>' + korisnik.ime + '</td>');
	let tdPrezime = $('<td>' + korisnik.prezime + '</td>');
	let tdPol = $('<td>' + korisnik.pol + '</td>');
	let tdDatumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
	let tdUloga = $('<td>' + korisnik.uloga + '</td>');
	let tdBodovi;
	let tdTip;
	if (korisnik.uloga == 'KUPAC'){
		tdBodovi = $('<td>' + korisnik.brojSakupljenihBodova + '</td>');
		tdTip = $('<td>' + korisnik.tipKupca + '</td>');
	} else{
		tdBodovi = $('<td></td>');
		tdTip = $('<td></td>');
	}
	let tdIzbrisi = $('<td></td>');
	if (korisnik.uloga != 'ADMINISTRATOR') {
		let izbrisiButton = $('<button class="button">Izbriši</button>');
		izbrisiButton.click(function(){
			$.ajax({
				url: 'rest/kupci/' + korisnik.korisnickoIme + '/' + korisnik.uloga,
				type: 'DELETE',
				success: function() {
					$.get({
						url: 'rest/kupci',
						success: function(korisnici) {
							updateTable(korisnici);
						}
					});
				}
			});
		});
		tdIzbrisi.append(izbrisiButton);
	}
	tr.append(tdKorisnickoIme).append(tdIme).append(tdPrezime).append(tdPol).append(tdDatumRodjenja).append(tdUloga).append(tdBodovi).append(tdTip).append(tdIzbrisi);
	$('#tabela').append(tr);
}

function updateTable(korisnici) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Korisničko ime</th><th>Ime</th><th>Prezime</th><th>Pol</th><th>Datum rodjenja</th><th>Uloga</th><th>Bodovi</th><th>Tip</th><th></th></tr></thead>');
	$('#tabela').append(tr);
	for (let korisnik of korisnici) {
				addKorisnikTr(korisnik);
	}
}

function sortiraj(){
	
	  let kriterijumZaSortiranje = $('#sortComboBox').val();
	  let kriterijumZaFiltriranje;
	  let kriterijumZaFiltriranjeTip;
	  if ($('#filter1ComboBox').val() == 'filtrirajPoUlozi'){
		 kriterijumZaFiltriranje = "bezUloge";
      } else{
		 kriterijumZaFiltriranje = $('#filter1ComboBox').val();
	  }  
	  
	  if (kriterijumZaFiltriranje == "KUPAC"){
		$('#filter2ComboBox').attr("hidden", false);
	  } else {
		$('#filter2ComboBox').attr("hidden", true);
		$('#filter2ComboBox').val('filtrirajPoTipu');
	  }
	  
	  if ($('#filter2ComboBox').val() == 'filtrirajPoTipu'){
		 kriterijumZaFiltriranjeTip = "bezTipa";
      } else{
		 kriterijumZaFiltriranjeTip = $('#filter2ComboBox').val();
	  }
	  
   	   if (kriterijumZaSortiranje == "sortiraj"){
	        $.get({
			url: 'rest/kupci/filtrirajPoUlozi/' + kriterijumZaFiltriranje,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	   }
   	     if (kriterijumZaSortiranje == "sortiraj" && kriterijumZaFiltriranje == "KUPAC" && kriterijumZaFiltriranjeTip != "bezTipa"){
	        $.get({
			url: 'rest/kupci/filtrirajPoTipuKupca/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	   }
   	   
       if (kriterijumZaSortiranje == "imeRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoImenuRastuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	   } 
	   
	    if (kriterijumZaSortiranje == "imeOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoImenuOpadajuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    if (kriterijumZaSortiranje == "prezimeRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoPrezimenuRastuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    if (kriterijumZaSortiranje == "prezimeOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoPrezimenuOpadajuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    if (kriterijumZaSortiranje == "korisnickoImeRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoKorisnickomImenuRastuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	      if (kriterijumZaSortiranje == "korisnickoImeOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoKorisnickomImenuOpadajuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	  
	  
	   if (kriterijumZaSortiranje == "bodoviRastuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoBodovimaRastuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
			success: function(korisnici) {
				updateTable(korisnici);
			}
		   });
	    } 
	    
	    
	    if (kriterijumZaSortiranje == "bodoviOpadajuce"){
	       $.get({
			url: 'rest/kupci/sortiraniPoBodovimaOpadajuce/' + kriterijumZaFiltriranje + '/' + kriterijumZaFiltriranjeTip,
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
		$('#filter2ComboBox').attr("hidden", true);
		$('#filter2ComboBox').val('filtrirajPoTipu');
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
    
     $('#filter2ComboBox').change(function(){
         sortiraj();
    });
});