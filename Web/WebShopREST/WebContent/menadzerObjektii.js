function displayImages(sportskiObjekti){
	
	for (let s of sportskiObjekti){
				
		let div1 = $('<div class="col-sm-4"></div>');
		let div2 = $('<div class="card text-center" style="width: 18rem;"></div>');
		let img = $('<a href="menadzerPrikazObjekta.html"><img class="card-img-top" src="pictures/' + s.logo + '" id="' +  s.naziv + '" onClick="saveId(this.id)" height="300"/></a>');
		let div3 = $('<div class="card-body"></div>');
		let title;
		if (s.prosecnaOcena != 0){
			title = $('<h5 class="card-title">' + '<span class="prosecnaOcena">' + s.prosecnaOcena + '</span>' + s.naziv +'</h5>');
		} else {
			title = $('<h5 class="card-title">' + s.naziv +'</h5>');
		}
		let adresa = $('<p style="font-size:14px; margin:0px;">' + s.lokacija.adresa.ulicaIBroj + '</br>' +  s.lokacija.adresa.mesto + ', ' + s.lokacija.adresa.postanskiBroj +'</p>');
		let tipObjekta = $('<p style="font-size:14px; margin:0px;">' + s.tipObjekta + '</p>');
		let status;
		if (s.statusObjekta == "Radi"){
				status = $('<p style="color:green; margin:0px;">Otvoreno</p>')
		} else{
				status = $('<p style="color:red; margin:0px;">Zatvoreno</p>')
		}
		let radnoVreme = $('<p style="font-size:14px; margin:0px;">' + s.radnoVreme.odVreme + "-" + s.radnoVreme.doVreme + "h, " + s.radnoVreme.dani[0] + "-" + s.radnoVreme.dani[s.radnoVreme.dani.length - 1] + '</p>');
		
		div3.append(title).append(tipObjekta).append(adresa).append(radnoVreme).append(status);
		div2.append(img).append(div3);
		div1.append(div2);
		$('#row').append(div1);
	}
}

function saveId(id)
{
	 	localStorage.setItem("selektovaniObjekat", JSON.stringify(id));
}

function updateImages(sportskiObjekti) {
	$('#row').html("");
	displayImages(sportskiObjekti);
}


function sortiraj(){
	
	 let kriterijumZaSortiranje = $('#sortComboBox').val();
   	 let kriterijumFiltriranja = document.querySelector('#otvorenoCheckBox').checked;
   	 
   	   if (kriterijumZaSortiranje == "sortiraj"){
	       $.get({
			url: 'rest/sportskiObjekti/pretrazeniSportskiObjekti/' + kriterijumFiltriranja,
			success: function(sportskiObjekti) {
				updateImages(sportskiObjekti);
			}
		   });
	   } 
   	   
       if (kriterijumZaSortiranje == "prosecnaOcenaOpadajuce"){
	       $.get({
			url: 'rest/sportskiObjekti/sortiraniPoProsecnojOceniOpadajuce/' + kriterijumFiltriranja,
			success: function(sportskiObjekti) {
				updateImages(sportskiObjekti);
			}
		   });
	   } 
	   if (kriterijumZaSortiranje == "prosecnaOcenaRastuce"){
			$.get({
				url: 'rest/sportskiObjekti/sortiraniPoProsecnojOceniRastuce/' + kriterijumFiltriranja,
				success: function(sportskiObjekti) {
					updateImages(sportskiObjekti);
				}
		   });
	   }
	    if (kriterijumZaSortiranje == "nazivRastuce"){
			$.get({
				url: 'rest/sportskiObjekti/sortiraniPoNazivuRastuce/' + kriterijumFiltriranja,
				success: function(sportskiObjekti) {
					updateImages(sportskiObjekti);
				}
		   });
	   }
	   
	   if (kriterijumZaSortiranje == "nazivOpadajuce"){
			$.get({
				url: 'rest/sportskiObjekti/sortiraniPoNazivuOpadajuce/' + kriterijumFiltriranja,
				success: function(sportskiObjekti) {
					updateImages(sportskiObjekti);
				}
		   });
	   }
	   
	    if (kriterijumZaSortiranje == "lokacijaRastuce"){
			$.get({
				url: 'rest/sportskiObjekti/sortiraniPoLokacijiRastuce/' + kriterijumFiltriranja,
				success: function(sportskiObjekti) {
					updateImages(sportskiObjekti);
				}
		   });
	   }
	    if (kriterijumZaSortiranje == "lokacijaOpadajuce"){
			$.get({
				url: 'rest/sportskiObjekti/sortiraniPoLokacijiOpadajuce/' + kriterijumFiltriranja,
				success: function(sportskiObjekti) {
					updateImages(sportskiObjekti);
				}
		   });
	   }
	
}

$(document).ready(function() {
	$.get({
		url: 'rest/sportskiObjekti',
		success: function(sportskiObjekti) {
			displayImages(sportskiObjekti);
		}
	});
	
	$('#popupButton').click(function() {
			$('#popupOverlay, #popup').css("visibility", "hidden");
	});
	
	
	
	$('form#formaZaPretragu').submit(function(event) {
		event.preventDefault();
		$('#sortComboBox').val('sortiraj');
		$('#otvorenoCheckBox').prop('checked', false);
		let naziv = $('#pretragaNaziv').val();
		let mesto = $('#pretragaGradIliDrzava').val();
		let prosecnaOcena = $('#pretragaProsecnaOcena').val();
		let tipObjekta = $('#pretragaTipObjekta').val();
		if (tipObjekta == ""){
			tipObjekta = null;
		}
		$.ajax({
			url: 'rest/sportskiObjekti/pretrazi',
			type: 'PUT',
			data: JSON.stringify({naziv: naziv, lokacija: { adresa: {mesto: mesto}}, prosecnaOcena: prosecnaOcena, tipObjekta: tipObjekta}),
			contentType: 'application/json',
			success: function(sportskiObjekti) {
				updateImages(sportskiObjekti);
			}
		});
	});
	
	 $('#sortComboBox').change(function(){
         sortiraj();
    });
    
    $("#otvorenoCheckBox").change(function() {
		sortiraj();
	});
	
	
});