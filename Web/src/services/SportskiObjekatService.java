package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	private static List<SportskiObjekat> prikazaniSportskiObjekti;
	
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
		prikazaniSportskiObjekti = dao.findAll();
		return prikazaniSportskiObjekti;
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
			prikazaniSportskiObjekti = dao.pretraziPoSvimKriterijumima(sportskiObjekat);
			return prikazaniSportskiObjekti;
		} else if (naziv != null && naziv != "" && tipObjekta != null && mesto != null && mesto != "") {
			prikazaniSportskiObjekti = dao.pretraziPoNazivuTipuMestu(naziv, tipObjekta, mesto);
			return prikazaniSportskiObjekti;
		} else if (naziv != null && naziv != "" && tipObjekta != null && prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoNazivuTipuProsecnojOceni(naziv, tipObjekta, prosecnaOcena);
			return prikazaniSportskiObjekti;
		} else if (naziv != null && naziv != "" && mesto != null && mesto != "" && prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoNazivuMestuProsecnojOceni(naziv, mesto, prosecnaOcena);
			return prikazaniSportskiObjekti;
		} else if (tipObjekta != null && mesto != null && mesto != "" && prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoTipuMestuProsecnojOceni(tipObjekta, mesto, prosecnaOcena);
			return prikazaniSportskiObjekti;
		} else if (naziv != null && naziv != "" && tipObjekta != null) {
			prikazaniSportskiObjekti = dao.pretraziPoNazivuTipu(naziv, tipObjekta);
			return prikazaniSportskiObjekti;
		} else if (naziv != null && naziv != "" && mesto != null && mesto != "") {
			prikazaniSportskiObjekti = dao.pretraziPoNazivuMestu(naziv, mesto);
			return prikazaniSportskiObjekti;
		} else if (naziv != null && naziv != "" && prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoNazivuProsecnojOceni(naziv, prosecnaOcena);
			return prikazaniSportskiObjekti;
		} else if (tipObjekta != null && mesto != null && mesto != "") {
			prikazaniSportskiObjekti = dao.pretraziPoTipuMestu(tipObjekta, mesto);
			return prikazaniSportskiObjekti;
		} else if (tipObjekta != null && prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoTipuProsecnojOceni(tipObjekta, prosecnaOcena);
			return prikazaniSportskiObjekti;
		} else if (mesto != null && mesto != "" && prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoMestuProsecnojOceni(mesto, prosecnaOcena);
			return prikazaniSportskiObjekti;
		}
		else if (naziv != null && naziv != "") {
			prikazaniSportskiObjekti = dao.pretraziPoNazivu(naziv);
			return prikazaniSportskiObjekti;
		} else if (tipObjekta != null) {
			prikazaniSportskiObjekti = dao.pretraziPoTipu(tipObjekta);
			return prikazaniSportskiObjekti;
		}	
		else if(mesto != null && mesto != ""){
			prikazaniSportskiObjekti = dao.pretraziPoMestu(mesto);
			return prikazaniSportskiObjekti;
		} else if (prosecnaOcena != 0.0) {
			prikazaniSportskiObjekti = dao.pretraziPoProsecnojOceni(prosecnaOcena);
			return prikazaniSportskiObjekti;
		}
		else {
			return getSportskiObjekti();
		}
	}
	

	@GET
	@Path("/sortiraniPoProsecnojOceniOpadajuce/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSortiraniSportskiObjektiOcenaOpadajuce(@PathParam("bool") boolean otvoreno) {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		
		if (otvoreno) {
			return dao.getOtvoreniSportskiObjekti(dao.sortirajPoProsecnojOceniOpadajuce(new ArrayList<>(prikazaniSportskiObjekti)));
		}
		
		return dao.sortirajPoProsecnojOceniOpadajuce(new ArrayList<>(prikazaniSportskiObjekti));
	}
	
	@GET
	@Path("/sortiraniPoProsecnojOceniRastuce/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSortiraniSportskiObjektiOcenaRastuce(@PathParam("bool") boolean otvoreno) {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		if (otvoreno) {
			return dao.getOtvoreniSportskiObjekti(dao.sortirajPoProsecnojOceniRastuce(new ArrayList<>(prikazaniSportskiObjekti)));
		}
		return dao.sortirajPoProsecnojOceniRastuce(new ArrayList<SportskiObjekat>(prikazaniSportskiObjekti));
	}
	
	@GET
	@Path("/sortiraniPoNazivuRastuce/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSortiraniSportskiObjektiNazivRastuce(@PathParam("bool") boolean otvoreno) {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		if (otvoreno) {
			return dao.getOtvoreniSportskiObjekti(dao.sortirajPoNazivuRastuce(new ArrayList<SportskiObjekat>(prikazaniSportskiObjekti)));
		}
		return dao.sortirajPoNazivuRastuce(new ArrayList<SportskiObjekat>(prikazaniSportskiObjekti));
	}
	
	@GET
	@Path("/sortiraniPoNazivuOpadajuce/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSortiraniSportskiObjektiNazivOpadajuce(@PathParam("bool") boolean otvoreno) {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		List<SportskiObjekat> sortirani = dao.sortirajPoNazivuRastuce(new ArrayList<SportskiObjekat>(prikazaniSportskiObjekti));
		Collections.reverse(sortirani);
		if (otvoreno) {
			return dao.getOtvoreniSportskiObjekti(sortirani);
		}
		return sortirani;
	}
	
	@GET
	@Path("/sortiraniPoLokacijiRastuce/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSortiraniSportskiObjektiLokacijaRastuce(@PathParam("bool") boolean otvoreno) {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		if (otvoreno) {
			return dao.getOtvoreniSportskiObjekti(dao.sortirajPoLokacijiRastuce(new ArrayList<>(prikazaniSportskiObjekti)));
		}
		return dao.sortirajPoLokacijiRastuce(new ArrayList<>(prikazaniSportskiObjekti));
	}
	
	@GET
	@Path("/sortiraniPoLokacijiOpadajuce/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getSortiraniSportskiObjektiLokacijaOpadajuce(@PathParam("bool") boolean otvoreno) {
		SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
		List<SportskiObjekat> sortirani = dao.sortirajPoLokacijiRastuce(dao.sortirajPoLokacijiRastuce(new ArrayList<>(prikazaniSportskiObjekti)));
		Collections.reverse(sortirani);
		if (otvoreno) {
			return dao.getOtvoreniSportskiObjekti(sortirani);
		}
		return sortirani;
	}
	
	@GET
	@Path("/pretrazeniSportskiObjekti/{bool}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportskiObjekat> getPretrazeniSportskiObjekti(@PathParam("bool") boolean otvoreno) {
		if (otvoreno) {
			SportskiObjekatDAO dao = (SportskiObjekatDAO) ctx.getAttribute("sportskiObjekatDAO");
			return dao.getOtvoreniSportskiObjekti(prikazaniSportskiObjekti);
		}
		return prikazaniSportskiObjekti;
	}
}