package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Clanarina;
import beans.Clanarina.StatusClanarine;
import beans.ClanarinaKupac;
import beans.Korisnik;
import beans.Korisnik.Uloga;
import beans.Kupac;
import beans.Menadzer;
import beans.SportskiObjekat;
import beans.TipKupca.NazivTipaKupca;
import beans.Trener;
import dao.ClanarinaDAO;
import dao.ClanarinaKupacDAO;
import dao.KorisnikDAO;
import dao.SportskiObjekatDAO;

@Path("/kupci")
public class KorisnikService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	private static List<Kupac> prikazaniKorisnici;
	
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
		
		if (ctx.getAttribute("clanarinaKupacDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("clanarinaKupacDAO", new ClanarinaKupacDAO(contextPath));
		}
		
		if (ctx.getAttribute("clanarinaDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("clanarinaDAO", new ClanarinaDAO(contextPath));
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
		HttpSession session = request.getSession();
		session.setAttribute("korisnik", kupac);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/usernameExists")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response usernameExists(Menadzer menadzer) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (dao.korisnikExists(menadzer.getKorisnickoIme())) {
			return Response.status(400).build();
		}
		return Response.status(200).build();
	}
	
	
	@POST
	@Path("/registracijaMenadzera")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrujMenadzera(Menadzer menadzer) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		dao.registerMenadzer(menadzer);
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
	public Response izmeniProfil(@PathParam("username") String username, Korisnik newKorisnik) {
		
		HttpSession session = request.getSession();
		Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
		newKorisnik.setUloga(korisnik.getUloga());
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		if (!korisnik.getKorisnickoIme().toLowerCase().equals(newKorisnik.getKorisnickoIme().toLowerCase()) && dao.korisnikExists(newKorisnik.getKorisnickoIme())) {
			return Response.status(400).entity("Korisničko ime je zauzeto").build();
		}
		else {
			dao.izmeniKorisnika(korisnik.getKorisnickoIme(), newKorisnik);
			return Response.status(200).build();
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> getKorisnici() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		prikazaniKorisnici = dao.findAll();
		return prikazaniKorisnici;
	}
	
	@GET
	@Path("/slobodniMenadzeri")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Menadzer> getSlobodniMenadzeri() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.findSlobodniMenadzeri();
	}
	
	@GET
	@Path("/treneri")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Trener> getTreneri() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.findTreneri();
	}
	
	@PUT
	@Path("/pretrazi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> pretrazi(Kupac korisnik) {
		String ime = korisnik.getIme().trim();
		String prezime = korisnik.getPrezime().trim();
		String korisnickoIme = korisnik.getKorisnickoIme().trim();
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		if (ime != null && ime != "" && prezime != null && prezime != "" && korisnickoIme != null && korisnickoIme != "") {
			prikazaniKorisnici = dao.pretraziPoImenuPrezimenuKorisnickomImenu(ime, prezime, korisnickoIme);
			return prikazaniKorisnici;
		} else if (ime != null && ime != "" && prezime != null && prezime != "") {
			prikazaniKorisnici = dao.pretraziPoImenuPrezimenu(ime, prezime);
			return prikazaniKorisnici;
		} else if (ime != null && ime != "" && korisnickoIme != null && korisnickoIme != "") {
			prikazaniKorisnici =  dao.pretraziPoImenuKorisnickomImenu(ime, korisnickoIme);
			return prikazaniKorisnici;
		} else if (prezime != null && prezime != "" && korisnickoIme != null && korisnickoIme != "") {
			prikazaniKorisnici = dao.pretraziPoPrezimenuKorisnickomImenu(prezime, korisnickoIme);
			return prikazaniKorisnici;
		}
		else if (ime != null && ime != "") {
			prikazaniKorisnici = dao.pretraziPoImenu(ime);
			return prikazaniKorisnici;
		} else if (prezime != null && prezime != "") {
			prikazaniKorisnici = dao.pretraziPoPrezimenu(prezime);
			return prikazaniKorisnici;
		} else if (korisnickoIme != null && korisnickoIme != "") {
			prikazaniKorisnici = dao.pretraziPoKorisnickomImenu(korisnickoIme);
			return prikazaniKorisnici;
		} 
		return getKorisnici();
	}
	
	@GET
	@Path("/sortiraniPoImenuRastuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kupac> getSortiraniKorisniciImeRastuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (uloga.equals("bezUloge")) {
			return dao.sortirajPoImenuRastuce(new ArrayList<Kupac>(prikazaniKorisnici));
		} else {
			List<Kupac> filtriraniKorisniciPoUlozi = filtrirajPoUlozi(Uloga.valueOf(uloga));
			if (!tip.equals("bezTipa")) {
				List<Kupac> filtriraniKorisniciPoUloziITipu = filtrirajPoTipuPriv(filtriraniKorisniciPoUlozi, tip);
				return dao.sortirajPoImenuRastuce(filtriraniKorisniciPoUloziITipu);
			}
			return dao.sortirajPoImenuRastuce(filtriraniKorisniciPoUlozi);
		}
	}
	
	@GET
	@Path("/sortiraniPoImenuOpadajuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> getSortiraniKorisniciImeOpadajuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		List<Kupac> sortirani = getSortiraniKorisniciImeRastuce(uloga, tip);
		Collections.reverse(sortirani);
		return sortirani;
	}
	
	@GET
	@Path("/sortiraniPoPrezimenuRastuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kupac> getSortiraniKorisniciPrezimeRastuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (uloga.equals("bezUloge")) {
			return dao.sortirajPoPrezimenuRastuce(new ArrayList<Kupac>(prikazaniKorisnici));
		} else {
			List<Kupac> filtriraniKorisniciPoUlozi = filtrirajPoUlozi(Uloga.valueOf(uloga));
			if (!tip.equals("bezTipa")) {
				List<Kupac> filtriraniKorisniciPoUloziITipu = filtrirajPoTipuPriv(filtriraniKorisniciPoUlozi, tip);
				return dao.sortirajPoPrezimenuRastuce(filtriraniKorisniciPoUloziITipu);
			}
			return dao.sortirajPoPrezimenuRastuce(filtriraniKorisniciPoUlozi);
		}
	}
	
	@GET
	@Path("/sortiraniPoPrezimenuOpadajuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> getSortiraniKorisniciPrezimeOpadajuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		List<Kupac> sortirani = getSortiraniKorisniciPrezimeRastuce(uloga, tip);
		Collections.reverse(sortirani);
		return sortirani;
	}
	
	@GET
	@Path("/sortiraniPoKorisnickomImenuRastuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kupac> getSortiraniKorisniciKorisnickoImeRastuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (uloga.equals("bezUloge")) {
			return dao.sortirajPoKorisnickomImenuRastuce(new ArrayList<Kupac>(prikazaniKorisnici));
		} else {
			List<Kupac> filtriraniKorisniciPoUlozi = filtrirajPoUlozi(Uloga.valueOf(uloga));
			if (!tip.equals("bezTipa")) {
				List<Kupac> filtriraniKorisniciPoUloziITipu = filtrirajPoTipuPriv(filtriraniKorisniciPoUlozi, tip);
				return dao.sortirajPoKorisnickomImenuRastuce(filtriraniKorisniciPoUloziITipu);
			}
			return dao.sortirajPoKorisnickomImenuRastuce(filtriraniKorisniciPoUlozi);
		}
	}
	
	@GET
	@Path("/sortiraniPoKorisnickomImenuOpadajuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> getSortiraniKorisniciKorisnickoImeOpadajuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		List<Kupac> sortirani = getSortiraniKorisniciKorisnickoImeRastuce(uloga, tip);
		Collections.reverse(sortirani);
		return sortirani;
	}
	
	@GET
	@Path("/sortiraniPoBodovimaRastuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kupac> getSortiraniKorisniciBodovimaRastuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (uloga.equals("bezUloge")) {
			return dao.sortirajPoBodovimaRastuce(new ArrayList<Kupac>(prikazaniKorisnici));
		} else {
			List<Kupac> filtriraniKorisniciPoUlozi = filtrirajPoUlozi(Uloga.valueOf(uloga));
			if (!tip.equals("bezTipa")) {
				List<Kupac> filtriraniKorisniciPoUloziITipu = filtrirajPoTipuPriv(filtriraniKorisniciPoUlozi, tip);
				return dao.sortirajPoBodovimaRastuce(filtriraniKorisniciPoUloziITipu);
			}
			return dao.sortirajPoBodovimaRastuce(filtriraniKorisniciPoUlozi);
		}
	}
	

	@GET
	@Path("/sortiraniPoBodovimaOpadajuce/{uloga}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> getSortiraniKorisniciBodovimaOpadajuce(@PathParam("uloga") String uloga, @PathParam("tip") String tip) {
		List<Kupac> sortirani = getSortiraniKorisniciBodovimaRastuce(uloga, tip);
		Collections.reverse(sortirani);
		return sortirani;
	}
	
	
	@GET
	@Path("/filtrirajPoUlozi/{kriterijum}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kupac> filtrirajPoUlozi(@PathParam("kriterijum") Korisnik.Uloga uloga) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.filtrirajPoUlozi(new ArrayList<Kupac>(prikazaniKorisnici), uloga);
	}
	
	@GET
	@Path("/filtrirajPoTipuKupca/{kriterijum}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kupac> filtrirajPoTipu(@PathParam("kriterijum") String tipKupca) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.filtrirajPoTipu(new ArrayList<Kupac>(prikazaniKorisnici), NazivTipaKupca.valueOf(tipKupca));
	}
	
	private List<Kupac> filtrirajPoTipuPriv(List<Kupac> korisnici, String tipKupca) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.filtrirajPoTipu(korisnici, NazivTipaKupca.valueOf(tipKupca));
	}
	
	@PUT
	@Path("/preracunajBodove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void preracunajBodove(Korisnik korisnik) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		String korisnickoImeKupca = korisnik.getKorisnickoIme();
		Kupac kupac = dao.findKupca(korisnickoImeKupca);
		
		if (kupac != null) {
			ClanarinaKupacDAO clanarinaKupacDao = (ClanarinaKupacDAO) ctx.getAttribute("clanarinaKupacDAO");
			ClanarinaKupac clanarinaKupac = clanarinaKupacDao.getClanarinaZaKupca(korisnickoImeKupca);
			ClanarinaDAO clanarinaDAO = (ClanarinaDAO) ctx.getAttribute("clanarinaDAO");
			Clanarina clanarina = clanarinaDAO.getClanarina(clanarinaKupac.getClanarinaId());
			if (clanarinaKupac != null) {
				if (clanarinaKupac.getStatus() == StatusClanarine.Aktivna && clanarinaKupac.getVaziDo().before(new Date())) { //proveravamo da li je clanarina istekla, ako jeste manipulisemo bodovima
					//potrebno je staviti clanarinu u status neaktivna
					clanarinaKupac.setStatus(StatusClanarine.Neaktivna);
					clanarinaKupacDao.izmeniStatusClanarine(clanarinaKupac);
					
					//proveravamo da li cemo kupcu umanjiti bodove ili povecati
					double trecinaTermina = Integer.getInteger(clanarina.getBrojTermina())/3;
					int brojIskoriscenihTermina = Integer.parseInt(clanarina.getBrojTermina()) - Integer.parseInt(clanarinaKupac.getBrojPreostalihTermina());
					if (brojIskoriscenihTermina > trecinaTermina) {
						double brojBodova = kupac.getBrojSakupljenihBodova();
						brojBodova += clanarina.getCena()/1000*(brojIskoriscenihTermina);
						kupac.setBrojSakupljenihBodova(brojBodova);
						kupac.odrediTipKupca();	
					} else {
						double brojBodova = kupac.getBrojSakupljenihBodova();
						brojBodova -= clanarina.getCena()/1000*133*4;
						kupac.setBrojSakupljenihBodova(brojBodova);
						kupac.odrediTipKupca();	
					}
					dao.izmeniKupca(korisnickoImeKupca, kupac);
				}
			}
		}
		
	}
}
