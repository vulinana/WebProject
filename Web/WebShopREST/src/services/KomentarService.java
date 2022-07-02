package services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

import beans.Komentar;
import beans.Komentar.StatusKomentara;
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
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getAll() {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.findAll();
	}
	
	@GET
	@Path("/{nazivObjekta}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Komentar> getKomentariByObjekat(@PathParam("nazivObjekta") String nazivObjekta) {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.getByNazivObjekta(nazivObjekta);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sacuvajKomentar(Komentar komentar) {
		komentar.setId(UUID.randomUUID());
		komentar.setStatusKomentara(StatusKomentara.NERECENZIRAN);
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		dao.sacuvajKomentar(komentar);
	}
	
	@PUT
	@Path("/recenzirajKomentar/{id}/{status}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> recenzirajKomentar(@PathParam("id") String id, @PathParam("status") String status) {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");		
		return dao.recenzirajKomentar(id, StatusKomentara.valueOf(status));
	}
}