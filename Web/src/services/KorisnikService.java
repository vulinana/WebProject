package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Korisnik;
import beans.Kupac;
import dao.KorisnikDAO;

@Path("/kupci")
public class KorisnikService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public KorisnikService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
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
		
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if(dao.korisnikExists(kupac.getKorisnickoIme())) {
			return Response.status(400).entity("Korisnicko ime je zauzeto").build();
		}
		
		dao.registerKupac(kupac);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/logovanje")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik login(Korisnik korisnik) {

		HttpSession session = request.getSession();
		if (session.getAttribute("korisnik") != null) {
			throw new WebApplicationException(Response.status(400).entity("Vec ste ulogovani!").build());
		}
		
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik zeljeniKorisnik = dao.searchKupac(korisnik.getKorisnickoIme());
		if (zeljeniKorisnik != null) {

			if (zeljeniKorisnik.getLozinka().equals(korisnik.getLozinka())) {
				session.setAttribute("korisnik", korisnik);
				return zeljeniKorisnik;
			} else {
				throw new WebApplicationException(Response.status(400).entity("Pogresna lozinka!").build());
			}
		} else {
			throw new WebApplicationException(Response.status(400).entity("Pogresno korisnicko ime!").build());
		}
	}

	
}
