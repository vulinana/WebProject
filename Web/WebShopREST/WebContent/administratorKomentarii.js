function addKomentarTr(komentar) {
	let tr = $('<tr></tr>');
	let tdKupac = $('<td>' + komentar.kupac + '</td>');
	let tdObjekat = $('<td>' + komentar.sportskiObjekat + '</td>');
	let tdTekstKomentara = $('<td>' + komentar.tekstKomentara + '</td>');
	let tdOcena = $('<td>' + komentar.ocena + '</td>');
	let tdOdobri = $('<td></td>');
	let tdOdbij = $('<td></td>');
	let buttonOdobri = $('<button class="button">Odobri</button>');
	buttonOdobri.click(function(){
		
		$.ajax({
			url: 'rest/komentari/recenzirajKomentar/' + komentar.id + '/PRIHVACEN',
			type: 'PUT',
			success: function(komentari){
				updateTable(komentari);
			}
		});
	});
	let buttonOdbij = $('<button class="button">Odbij</button>');
	buttonOdbij.click(function(){
		
		$.ajax({
			url: 'rest/komentari/recenzirajKomentar/' + komentar.id + '/ODBIJEN',
			type: 'PUT',
			success: function(komentari){
				updateTable(komentari);
			}
		});
	});
	tdOdobri.append(buttonOdobri);
	tdOdbij.append(buttonOdbij);
	let tdIzbrisi = $('<td></td>');
	let buttonIzbrisi = $('<button class="button">Izbriši</button>');
	buttonIzbrisi.click(function(){
		$.ajax({
			url: 'rest/komentari/' + komentar.id,
			type: 'DELETE',
			success: function(komentari){
				updateTable(komentari);
			}
		});
	});
	tdIzbrisi.append(buttonIzbrisi);
	tr.append(tdKupac).append(tdObjekat).append(tdTekstKomentara).append(tdOcena).append(tdOdobri).append(tdOdbij).append(tdIzbrisi);
	$('#tabela').append(tr);
}

function updateTable(komentari) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Kupac</th><th>Sportski objekat</th><th>Tekst komentara</th><th>Ocena</th><th></th><th></th><th></th></tr></thead>');
	$('#tabela').append(tr);
	for (let komentar of komentari) {
		if (komentar.statusKomentara == 'NERECENZIRAN'){
			addKomentarTr(komentar);
		}
	}
}

$(document).ready(function() {
	$.get({
		url: 'rest/komentari',
		success: function(komentari) {
			updateTable(komentari);
		}
	});
});