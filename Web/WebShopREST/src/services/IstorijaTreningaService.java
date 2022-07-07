package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.ClanarinaKupac;
import beans.IstorijaTreninga;
import beans.Kupac;
import beans.Clanarina.StatusClanarine;
import dao.IstorijaTreningaDAO;
import dao.KorisnikDAO;
import dao.SportskiObjekatDAO;
import dao.TreningDAO;
import dao.ClanarinaKupacDAO;

@Path("/istorijaTreninga")
public class IstorijaTreningaService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public IstorijaTreningaService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("istorijaTreningaDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("istorijaTreningaDAO", new IstorijaTreningaDAO(contextPath));
		}
		
		if (ctx.getAttribute("clanarinaKupacDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("clanarinaKupacDAO", new ClanarinaKupacDAO(contextPath));
		}
		
		if (ctx.getAttribute("treningDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("treningDAO", new TreningDAO(contextPath));
		}
		
		if (ctx.getAttribute("sportskiObjekatDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportskiObjekatDAO", new SportskiObjekatDAO(contextPath));
		}
		
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
	}
	
	
	@GET
	@Path("/{korisnik}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<IstorijaTreninga> getIstorijaTreninga(@PathParam("korisnik") String korisnickoImeKupca) {
		IstorijaTreningaDAO dao = (IstorijaTreningaDAO) ctx.getAttribute("istorijaTreningaDAO");
		return dao.findAll(korisnickoImeKupca);
	}
	
	
	@GET
	@Path("/{korisnik}/{objekat}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<IstorijaTreninga> getIstorijaTreningaZaKupcaZaOdredjeniObjekat(@PathParam("korisnik") String korisnickoImeKupca, @PathParam("objekat") String objekat) {
		IstorijaTreningaDAO dao = (IstorijaTreningaDAO) ctx.getAttribute("istorijaTreningaDAO");
		return dao.findZaKupcaZaOdredjeniObjekat(korisnickoImeKupca, objekat);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<IstorijaTreninga> kreirajTreningUIstoriji(IstorijaTreninga istorijaTreninga) {
		
		ClanarinaKupacDAO clanarinaKupacDao = (ClanarinaKupacDAO) ctx.getAttribute("clanarinaKupacDAO");
		ClanarinaKupac clanarinaKupac = clanarinaKupacDao.getClanarinaZaKupca(istorijaTreninga.getKupac());
		
		if (clanarinaKupac == null) {
			
			throw new WebApplicationException(Response.status(400).entity("Kupac nema clanarinu!").build());
		
		} else if (clanarinaKupac.getStatus() == StatusClanarine.Neaktivna) {
			
			throw new WebApplicationException(Response.status(400).entity("Clanarina je istekla!").build());
			
		} else if (Integer.parseInt(clanarinaKupac.getBrojPreostalihTermina()) <= 0) {
			
			throw new WebApplicationException(Response.status(400).entity("Nemate vise termina!").build());
			
		}else {
		
			istorijaTreninga.setId(UUID.randomUUID());
			istorijaTreninga.setDatumIVremePrijave(new Date());
			IstorijaTreningaDAO dao = (IstorijaTreningaDAO) ctx.getAttribute("istorijaTreningaDAO");
			
			TreningDAO treningDao = (TreningDAO) ctx.getAttribute("treningDAO");
			istorijaTreninga.setTrening(treningDao.getByNazivObjektaINazivTreninga(istorijaTreninga.getSportskiObjekat().getNaziv(), istorijaTreninga.getTrening().getNaziv()));
			SportskiObjekatDAO sportskiObjekatDao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
			istorijaTreninga.setSportskiObjekat(sportskiObjekatDao.getByNaziv(istorijaTreninga.getSportskiObjekat().getNaziv()));
			istorijaTreninga.setTrener(treningDao.pronadjiTreneraZaTrening(istorijaTreninga.getSportskiObjekat().getNaziv(), istorijaTreninga.getTrening().getNaziv()));
			
			int brojPreostalihTermina = Integer.parseInt(clanarinaKupac.getBrojPreostalihTermina()) - 1;
			clanarinaKupac.setBrojPreostalihTermina(String.valueOf(brojPreostalihTermina));
			clanarinaKupacDao.izmeniBrojPreostalihTermina(clanarinaKupac);
			
			dao.kreirajTreningUIstoriji(istorijaTreninga);
			return dao.findAll(istorijaTreninga.getKupac());
		}
	}
	
	@GET
	@Path("/kupciPremaObjektu/{naziv}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Kupac> getKupcePremaObjektu(@PathParam("naziv") String nazivObjekta) {
		IstorijaTreningaDAO dao = (IstorijaTreningaDAO) ctx.getAttribute("istorijaTreningaDAO");
		List<String> korisnickaImena = dao.getKupcePremaObjektu(nazivObjekta);
		Set<String> set = new HashSet<>(korisnickaImena);
		korisnickaImena.clear();
		korisnickaImena.addAll(set);
		
		KorisnikDAO daoKorisnik = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return daoKorisnik.getKupceByKorisnickaImena(korisnickaImena);
	}
}