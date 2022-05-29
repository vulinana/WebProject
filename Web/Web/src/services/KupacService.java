package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Kupac;
import dao.KupacDAO;

@Path("/kupci")
public class KupacService {
	
	@Context
	ServletContext ctx;
	
	public KupacService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("kupacDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("kupacDAO", new KupacDAO(contextPath));
		}
	}
	
	
	@POST
	@Path("/registracija")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrujKupca(Kupac kupac) {
		
		if (kupac.getKorisnickoIme() == "" || kupac.getLozinka() == "") {
			return Response.status(400).entity("Korisnicko ime i lozinka su obavezna polja").build();
		}
		
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupacDAO");
		if(dao.korisnikExists(kupac.getKorisnickoIme())) {
			return Response.status(400).entity("Korisnicko ime je zauzeto").build();
		}
		
		dao.registerKupac(kupac);
		return Response.status(200).build();
	}
	
}
