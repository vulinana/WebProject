package services;


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

import beans.Kupac;
import beans.TipKupca;
import dao.KorisnikDAO;
import dao.TipKupcaDAO;

@Path("/tipKupca")
public class TipKupcaService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public TipKupcaService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("tipKupcaDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("tipKupcaDAO", new TipKupcaDAO(contextPath));
		}
		
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
	}
	
	@GET
	@Path("/{korisnickoIme}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TipKupca getTipKupca(@PathParam("korisnickoIme") String korisnickoIme) {
		KorisnikDAO korisnikDao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Kupac kupac = korisnikDao.findKupca(korisnickoIme);
		
		TipKupcaDAO dao = (TipKupcaDAO) ctx.getAttribute("tipKupcaDAO");
		return dao.findByNaziv(kupac.getTipKupca());
	}
}