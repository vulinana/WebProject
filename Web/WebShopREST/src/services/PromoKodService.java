package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.PromoKod;
import beans.RadnoVreme;
import beans.SportskiObjekat;
import beans.RadnoVreme.Dan;
import dao.PromoKodDAO;
import dao.SportskiObjekatDAO;

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
	public Collection<PromoKod> kreirajPromoKod(PromoKod promoKod) throws Exception {
		PromoKodDAO dao = (PromoKodDAO) ctx.getAttribute("promoKodDAO");
		if (dao.promoKodExists(promoKod.getOznaka())) {
			throw new Exception("Oznaka promo koda mora biti jedinstvena!");
		}
		return dao.kreirajPromoKod(promoKod);
	}
}