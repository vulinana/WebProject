package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Komentar;
import dao.KomentarDAO;

@Path("/komentari")
public class KomentarService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public KomentarService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("komentarDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("komentarDAO", new KomentarDAO(contextPath));
		}
	}
	
	@GET
	@Path("/{nazivObjekta}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Komentar> getSportskiObjekti(@PathParam("nazivObjekta") String nazivObjekta) {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.getByNazivObjekta(nazivObjekta);
	}
}