function addKodTr(kod) {
	let tr = $('<tr></tr>');
	let tdOznaka = $('<td>' + kod.oznaka + '</td>');
	let tdVaziOd = $('<td>' + kod.vaziOd + '</td>');
	let tdVaziDo = $('<td>' + kod.vaziDo + '</td>');
	let tdBrojIskoriscavanja = $('<td>' + kod.brojIskoriscavanja + '</td>');
	let tdProcenatUmanjenjaClanarine = $('<td>' + kod.procenatUmanjenjaClanarine + '</td>');
	let td = $('<td></td>');
	let izbrisiButton = $('<button class="button">Izbriši</button>');
	izbrisiButton.click(function(){
		$.ajax({
			url: 'rest/promoKodovi/' + kod.id,
			type: 'DELETE',
			success : function(kodovi) {
				updateTable(kodovi);
			}
		});
	});
	td.append(izbrisiButton);
	tr.append(tdOznaka).append(tdVaziOd).append(tdVaziDo).append(tdBrojIskoriscavanja).append(tdProcenatUmanjenjaClanarine).append(td);
	$('#tabela').append(tr);
}

function updateTable(kodovi) {
	$('#tabela').html("");
	let tr = $('<thead><tr><th>Oznaka</th><th>Važi od</th><th>Važi do</th><th>Broj iskorišćavanja</th><th>Procenat umanjena članarine</th><th></th></tr><thead>');
	$('#tabela').append(tr);
	for (let kod of kodovi) {
				addKodTr(kod);
	}
}


function isAfter(date1, date2) {
  return date1 > date2;
}


$(document).ready(function() {

	$.get({
		url: 'rest/promoKodovi',
		success: function(kodovi) {
			updateTable(kodovi);
		}
	});
	
	$('#kreirajPromoKod').click(function() {
			$('#popupOverlay, #popup').css("visibility", "visible");
	});
	
	$('form#kreirajPromoKodForma').submit(function(event) {
		event.preventDefault();
		$('#error').text("");
		let oznaka = $('input[name="oznaka"]').val();
		let vaziOd = $('input[name="vaziOd"]').val();
		let vaziDo = $('input[name="vaziDo"]').val();
		let brojIskoriscavanja = $('input[name="brojIskoriscavanja"]').val();
		let procenat = $('input[name="procenat"]').val();
		
		const d1 = new Date(vaziOd);
		const d2 = new Date(vaziDo);
		
		if (isAfter(d1, d2)){
			$('#error').text("Datum početka važenja mora biti pre datuma isteka!");
			return;
		}

		
		$.ajax({
			url: 'rest/promoKodovi',
			type: 'POST',
			data: JSON.stringify({oznaka: oznaka, vaziOd: vaziOd, vaziDo: vaziDo, brojIskoriscavanja: brojIskoriscavanja, procenatUmanjenjaClanarine: procenat}),
			contentType: 'application/json',
			success : function(kodovi) {
				updateTable(kodovi);
				$('#popupOverlay, #popup').css("visibility", "hidden");
				$('input[name="oznaka"]').val("");
				$('input[name="vaziOd"]').val("");
				$('input[name="vaziDo"]').val("");
				$('input[name="brojIskoriscavanja"]').val("");
				$('input[name="procenat"]').val("");
		
			},
			error : function() {
				$('#error').text("Oznaka promo koda mora biti jedinstvena");
			}
		});
	});
	

	$('#popupButtonOtkazi').click(function(){
		$('#popupOverlay, #popup').css("visibility", "hidden");
	});
	
});
