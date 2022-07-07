package services;

import java.util.Collection;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.PromoKod;
import dao.PromoKodDAO;

@Path("/promoKodovi")
public class PromoKodService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public PromoKodService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("promoKodDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("promoKodDAO", new PromoKodDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PromoKod> getPromoKodovi() {
		PromoKodDAO dao = (PromoKodDAO) ctx.getAttribute("promoKodDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PromoKod> kreirajPromoKod(PromoKod promoKod) {
		PromoKodDAO dao = (PromoKodDAO) ctx.getAttribute("promoKodDAO");
		if (dao.promoKodExists(promoKod.getOznaka()) != null) {
			throw new WebApplicationException(Response.status(400).entity("Oznaka promo koda mora biti jedinstvena!").build());
		}
		promoKod.setId(UUID.randomUUID());
		promoKod.setIzbrisan(false);
		return dao.kreirajPromoKod(promoKod);
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PromoKod> deletePromoKod(@PathParam("id") String id) {
		PromoKodDAO dao = (PromoKodDAO) ctx.getAttribute("promoKodDAO");
		return dao.deletePromoKod(id);
	}
}