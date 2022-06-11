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
		
		String naziv = sportskiObjekat.getNaziv();
		SportskiObjekat.TipObjekta tipObjekta = sportskiObjekat.getTipObjekta();
		String mesto = sportskiObjekat.getLokacija().getAdresa().getMesto();
		double prosecnaOcena = sportskiObjekat.getProsecnaOcena();
		
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		
		if (naziv != null && naziv != "" && mesto != null && mesto != "" && tipObjekta != null && prosecnaOcena != 0.0) {
			return dao.pretraziPoSvimKriterijumima(sportskiObjekat);
		} else if (naziv != null && naziv != "" && tipObjekta != null && mesto != null && mesto != "") {
			return dao.pretraziPoNazivuTipuMestu(naziv, tipObjekta, mesto);
		} else if (naziv != null && naziv != "" && tipObjekta != null && prosecnaOcena != 0.0) {
			return dao.pretraziPoNazivuTipuProsecnojOceni(naziv, tipObjekta, prosecnaOcena);
		} else if (naziv != null && naziv != "" && mesto != null && mesto != "" && prosecnaOcena != 0.0) {
			return dao.pretraziPoNazivuMestuProsecnojOceni(naziv, mesto, prosecnaOcena);
		} else if (tipObjekta != null && mesto != null && mesto != "" && prosecnaOcena != 0.0) {
			return dao.pretraziPoTipuMestuProsecnojOceni(tipObjekta, mesto, prosecnaOcena);
		} else if (naziv != null && naziv != "" && tipObjekta != null) {
			return dao.pretraziPoNazivuTipu(naziv, tipObjekta);
		} else if (naziv != null && naziv != "" && mesto != null && mesto != "") {
			return dao.pretraziPoNazivuMestu(naziv, mesto);
		} else if (naziv != null && naziv != "" && prosecnaOcena != 0.0) {
			return dao.pretraziPoNazivuProsecnojOceni(naziv, prosecnaOcena);
		} else if (tipObjekta != null && mesto != null && mesto != "") {
			return dao.pretraziPoTipuMestu(tipObjekta, mesto);
		} else if (tipObjekta != null && prosecnaOcena != 0.0) {
			return dao.pretraziPoTipuProsecnojOceni(tipObjekta, prosecnaOcena);
		} else if (mesto != null && mesto != "" && prosecnaOcena != 0.0) {
			return dao.pretraziPoMestuProsecnojOceni(mesto, prosecnaOcena);
		}
		else if (naziv != null && naziv != "") {
			return dao.pretraziPoNazivu(naziv);
		} else if (tipObjekta != null) {
			return dao.pretraziPoTipu(tipObjekta);
		}	
		else if(mesto != null && mesto != ""){
			return dao.pretraziPoMestu(mesto);
		} else if (prosecnaOcena != 0.0) {
			return dao.pretraziPoProsecnojOceni(prosecnaOcena);
		}
		else {
			return getSportskiObjekti();
		}
	}
}