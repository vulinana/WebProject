package services;


import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.SportskiObjekat;
import beans.TerminTreninga;
import beans.Trening.TipTreninga;
import dao.SportskiObjekatDAO;
import dao.TerminTreningaDAO;

@Path("/terminiTreninga")
public class TerminTreningaService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public TerminTreningaService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("terminTreningaDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("terminTreningaDAO", new TerminTreningaDAO(contextPath));
		}
		
		if (ctx.getAttribute("sportskiObjekatDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportskiObjekatDAO", new SportskiObjekatDAO(contextPath));
		}
	}

	
	@POST
	@Path("/{tipTreninga}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void kreirajTerminTreninga(TerminTreninga terminTreninga, @PathParam("tipTreninga") String tipTreninga){
		terminTreninga.setId(UUID.randomUUID());
		SportskiObjekatDAO daoObjekat = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		SportskiObjekat sportskiObjekat = daoObjekat.getByNaziv(terminTreninga.getSportskiObjekat());
		terminTreninga.setTipObjekta(sportskiObjekat.getTipObjekta());
		terminTreninga.setTipTreniga(TipTreninga.valueOf(tipTreninga));
		terminTreninga.setOtkazan(false);
		TerminTreningaDAO dao = (TerminTreningaDAO) ctx.getAttribute("terminTreningaDAO");
		dao.kreirajTerminTreninga(terminTreninga);
		
	}
	
	@GET
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TerminTreninga> getTreneroviTreninzi(@PathParam("username") String username){
		TerminTreningaDAO dao = (TerminTreningaDAO) ctx.getAttribute("terminTreningaDAO");
		return dao.getTreneroviTreninzi(username);
	}
	
	@GET
	@Path("/terminiZaSportskiObjekat/{naziv}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public 	Collection<TerminTreninga> getTreninziZaSportskiObjekat(@PathParam("naziv") String naziv){
		TerminTreningaDAO dao = (TerminTreningaDAO) ctx.getAttribute("terminTreningaDAO");
		return dao.getTerminiZaSportskiObjekat(naziv);
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTerminTreninga(@PathParam("id") String id){
		TerminTreningaDAO dao = (TerminTreningaDAO) ctx.getAttribute("terminTreningaDAO");
		dao.deleteTerminTreninga(id);
	}
}