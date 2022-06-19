
function addMenadzeriTr(menadzeri) {
	let tdMenadzerLabela = $('<td><label>Menadzer</labela></td>');
	
	let td = $('<td></td>');
	let izaberiMenadzera;
	if (menadzeri.length === 0){
		izaberiMenadzera = $('<button>Kreiraj</button>');
	} else{
		izaberiMenadzera = $('<select></select>');
	}
	
		let comboBoxOption;
		for (let m of menadzeri) {
				comboBoxOption = $('<option value="' + m.korisnickoIme + '">' + m.korisnickoIme + '</option>');
				izaberiMenadzera.append(comboBoxOption);
		}

	td.append(izaberiMenadzera);
	$('#menadzer').append(tdMenadzerLabela).append(td);
}

$(document).ready(function() {
	
	$.get({
		url: 'rest/kupci/slobodniMenadzeri',
		success: function(menadzeri) {
			addMenadzeriTr(menadzeri);
		}
	});
	
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let myFile = $('#logo').prop('files');
		console.log(myFile);
		var documentData = new FormData();
    	documentData.append('file', myFile);
    	console.log(documentData);
    	$.ajax({
        	url: 'rest/sportskiObjekti/uploadImage',
        	type: 'POST',
        	data: documentData,
        	cache: false,
        	contentType: false,
        	processData: false,
        	success: function (response) {
            	alert("Document uploaded successfully.");
        	}
    });
		
	});
	
});