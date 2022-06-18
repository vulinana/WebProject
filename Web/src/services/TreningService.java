package services;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Komentar;
import beans.Trening;
import dao.KomentarDAO;
import dao.TreningDAO;

@Path("/treninzi")
public class TreningService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public TreningService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("treningDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("treningDAO", new TreningDAO(contextPath));
		}
	}
	
	@GET
	@Path("/{nazivObjekta}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trening> getSportskiObjekti(@PathParam("nazivObjekta") String nazivObjekta) {
		TreningDAO dao = (TreningDAO) ctx.getAttribute("treningDAO");
		return dao.getByNazivObjekta(nazivObjekta);
	}
	
	@POST
	@Path("/kreirajNoviTrening")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Trening> kreirajNoviTrening(Trening trening) {
		trening.setId();
		trening.setSlika("trening1.jpg");
		TreningDAO dao = (TreningDAO) ctx.getAttribute("treningDAO");
		return dao.kreirajNoviTrening(trening);
	}
}