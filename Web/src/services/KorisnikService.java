package services;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Korisnik;
import beans.Kupac;
import beans.Menadzer;
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
			return Response.status(400).entity("Korisničko ime i lozinka su obavezna polja").build();
		}
		
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if(dao.korisnikExists(kupac.getKorisnickoIme())) {
			return Response.status(400).entity("Korisničko ime je zauzeto").build();
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
				session.setAttribute("korisnik", zeljeniKorisnik);
				return zeljeniKorisnik;
			} else {
				throw new WebApplicationException(Response.status(400).entity("Pogrešna lozinka!").build());
			}
		} else {
			throw new WebApplicationException(Response.status(400).entity("Pogrešno korisničko ime!").build());
		}
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		
		HttpSession session = request.getSession();
		Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
		
		if(korisnik != null) {
			session.invalidate();
			return Response.status(200).build();
		}
		else {
			return Response.status(400).entity("Korisnik je vec izlogovan!").build();
		}
	}
	
	@GET
	@Path("/ulogovanKorisnik")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik getUlogovanKorisnik() {
		
		HttpSession session = request.getSession();
		Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
		
		if(korisnik != null) {
			return (Korisnik) session.getAttribute("korisnik");
		}
		else {
			throw new WebApplicationException(Response.status(400).entity("Nijedan korisnik nije ulogovan").build());
		}
	}
	
	@PUT
	@Path("/izmeniProfil")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izmeniProfil(Korisnik newKorisnik) {
		
		HttpSession session = request.getSession();
		Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
		newKorisnik.setUloga(korisnik.getUloga());
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		dao.izmeniKorisnika(korisnik.getKorisnickoIme(), newKorisnik);
		return Response.status(200).build();
		
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Korisnik> getKorisnici() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.findAll();
	}
	
	@GET
	@Path("/slobodniMenadzeri")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Menadzer> getSlobodniMenadzeri() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.findSlobodniMenadzeri();
	}
}
