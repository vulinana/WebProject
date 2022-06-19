$(document).ready(function() {
	
	$('#logout').click(function() {
		 $.ajax({
			url: "rest/kupci/logout",
			success : function() {
				alert("Uspešno ste se izlogovali!")
			},	
			error : function(message) {
				alert(message.responseText);
			}
   		 });
	});
	
});

	