package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.SportskiObjekat;
import dao.SportskiObjekatDAO;

@Path("/sportskiObjekti")
public class SportskiObjekatService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public SportskiObjekatService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("sportskiObjekatDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportskiObjekatDAO", new SportskiObjekatDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSportskiObjekti() {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/uploadImage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadImage(String sportskiObjekat) {
		
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		//dao.registerKupac(kupac);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/kreirajSportskiObjekat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response kreirajSportskiObjekat(SportskiObjekat sportskiObjekat) {
		
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		//dao.registerKupac(kupac);
		return Response.status(200).build();
	}
	
	
	@PUT
	@Path("/pretrazi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> pretrazi (SportskiObjekat sportskiObjekat) {
		
		String mesto = sportskiObjekat.getLokacija().getAdresa().getMesto();
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		if (sportskiObjekat.getNaziv() != null && sportskiObjekat.getNaziv() != "" && mesto != null && mesto != "" && sportskiObjekat.getTipObjekta() != null && sportskiObjekat.getProsecnaOcena() != 0.0) {
			return dao.pretraziPoSvimKriterijumima(sportskiObjekat);
		}
		if (sportskiObjekat.getNaziv() != null && sportskiObjekat.getNaziv() != "") {
			return dao.pretraziPoNazivu(sportskiObjekat.getNaziv());
		} else if (sportskiObjekat.getTipObjekta() != null) {
			return dao.pretraziPoTipu(sportskiObjekat.getTipObjekta());
		}	
		else if(mesto != null && mesto != ""){
			return dao.pretraziPoMestu(sportskiObjekat.getLokacija().getAdresa().getMesto());
		} else if (sportskiObjekat.getProsecnaOcena() != 0.0) {
			return dao.pretraziPoProsecnojOceni(sportskiObjekat.getProsecnaOcena());
		}
		else {
			return getSportskiObjekti();
		}
	}
}