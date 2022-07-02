package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
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
import beans.PromoKod;
import beans.Clanarina.StatusClanarine;
import dao.IstorijaTreningaDAO;
import dao.PromoKodDAO;
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
			istorijaTreninga.setTrener(treningDao.pronadjiTreneraZaTrening(istorijaTreninga.getSportskiObjekat(), istorijaTreninga.getTrening()));
			
			int brojPreostalihTermina = Integer.parseInt(clanarinaKupac.getBrojPreostalihTermina()) - 1;
			clanarinaKupac.setBrojPreostalihTermina(String.valueOf(brojPreostalihTermina));
			clanarinaKupacDao.izmeniBrojPreostalihTermina(clanarinaKupac);
			
			dao.kreirajTreningUIstoriji(istorijaTreninga);
			return dao.findAll(istorijaTreninga.getKupac());
		}
	}
}