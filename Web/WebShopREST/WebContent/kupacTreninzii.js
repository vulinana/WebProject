function addTreningTr(trening) {
	let tr = $('<tr></tr>');
	let tdNazivTreninga = $('<td>' + trening.trening + '</td>');
	let tdNazivSportskogObjekta = $('<td>' + trening.sportskiObjekat + '</td>');
	let tdDatum = $('<td>' + trening.datumIVremePrijave + '</td>');
	tr.append(tdNazivTreninga).append(tdNazivSportskogObjekta).append(tdDatum);
	$('#tabela').append(tr);
}

function updateTable(istorijaTreninga) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Naziv treninga</th><th>Naziv sportskog objekta</th><th>Datum i vreme treniranja</th></tr><thead>');
	$('#tabela').append(tr);
	for (let trening of istorijaTreninga) {
				addTreningTr(trening);
	}
}

function ucitajSportskeObjekte(sportskiObjekti) {
		let comboBoxOption;
		comboBoxOption = $('<option value="" hidden>Select</option>');
		$('#sportskiObjektiComboBox').append(comboBoxOption);
		for (let s of sportskiObjekti) {
				comboBoxOption = $('<option value="' + s.naziv + '">' + s.naziv  + '</option>');
				$('#sportskiObjektiComboBox').append(comboBoxOption);
		}
}

function ucitajTreninge(treninzi) {
	$('#trening').html("");
	let tdTreningLabela = $('<td><label class="label">Trening:</labela></td>');
	
	let td = $('<td></td>');
	let izaberiTrening =$('<select class="comboBox" id="treningComboBox"></select>');
	
		let comboBoxOption;
		for (let t of treninzi) {
				comboBoxOption = $('<option value="' + t.naziv + '">' + t.naziv  + '</option>');
				izaberiTrening.append(comboBoxOption);
		}

	td.append(izaberiTrening);
	$('#trening').append(tdTreningLabela).append(td);
}


$(document).ready(function() {
	
	var korisnik = JSON.parse(localStorage.getItem("ulogovaniKorisnik"));
	
	$.get({
		url: 'rest/istorijaTreninga/' + korisnik.korisnickoIme,
		success: function(istorijaTreninga) {
			updateTable(istorijaTreninga);
		}
	});
	
	$.get({
		url: 'rest/sportskiObjekti',
		success: function(sportskiObjekti) {
			ucitajSportskeObjekte(sportskiObjekti);
		}
	});
	
	 $('#treniraj').click(function(){
		$('#popupOverlay, #popup').css("visibility", "visible");
	});
	
	$('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});
	
	 $('#sportskiObjektiComboBox').change(function(){
		 let naziv = $('#sportskiObjektiComboBox').val();
         $.get({
			url: 'rest/treninzi/' + naziv,
			success: function(treninzi) {
				ucitajTreninge(treninzi);
			}
		});
    });
    
    $('form#prijaviSeUSportskiObjekat').submit(function(event) {
		event.preventDefault();
		$('#error').text("");
		let sportskiObjekat = $('#sportskiObjektiComboBox').val();
		let trening = $('#treningComboBox').val();
		
		if (sportskiObjekat == null || trening == null){
			$('#error').text("Niste izabrali sportski objekat i/ili trening");
			return;
		}
		
		$.ajax({
			url: 'rest/istorijaTreninga' ,
			type: 'POST',
			data: JSON.stringify({kupac: korisnik.korisnickoIme, sportskiObjekat: sportskiObjekat, trening: trening}),
			contentType: 'application/json',
			success : function(istorijaTreninga) {
				updateTable(istorijaTreninga);
				$('#popupOverlay, #popup').css("visibility", "hidden");
			},
			error : function(message) {
				$('#error').text(message.responseText);
			}
		});
	});
});