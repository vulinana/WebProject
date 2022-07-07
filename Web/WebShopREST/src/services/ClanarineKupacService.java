package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.ClanarinaKupac;
import beans.PromoKod;
import beans.Clanarina.StatusClanarine;
import beans.Clanarina.TipClanarine;
import dao.ClanarinaKupacDAO;
import dao.KorisnikDAO;
import dao.PromoKodDAO;

@Path("/clanarineKupac")
public class ClanarineKupacService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public ClanarineKupacService() {
	}
	
	@PostConstruct
	public void init() {
		
		if (ctx.getAttribute("clanarinaKupacDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("clanarinaKupacDAO", new ClanarinaKupacDAO(contextPath));
		}
		
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
		
		if (ctx.getAttribute("promoKodDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("promoKodDAO", new PromoKodDAO(contextPath));
		}
	}
	
	@POST
	@Path("/{promoKod}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void kreirajClanarinuZaKupca(@PathParam("promoKod") String promoKodOznaka, @PathParam("tip") String tipClanarine, ClanarinaKupac clanarinaKupac) throws ParseException { 
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		if (!promoKodOznaka.equals("bezPromoKoda")) {
			PromoKodDAO promoKodDao = (PromoKodDAO) ctx.getAttribute("promoKodDAO");
			PromoKod promoKod = promoKodDao.promoKodExists(promoKodOznaka);
			if (promoKod == null){
				throw new WebApplicationException(Response.status(400).entity("Promo kod ne postoji!").build());
			} else {
				Date currentDate = new Date();
				if (promoKod.getVaziOd().after(currentDate)) {
					throw new WebApplicationException(Response.status(400).entity("Promo kod važi od " + dateFormat.format(promoKod.getVaziOd()) + "!").build());
				} else if (promoKod.getVaziDo().before(currentDate)) {
					throw new WebApplicationException(Response.status(400).entity("Promo kod je važio do " + dateFormat.format(promoKod.getVaziDo()) + "!").build());
				} else if (promoKod.getBrojIskoriscavanja() <= 0) {
					throw new WebApplicationException(Response.status(400).entity("Promo kod je upotrebljen maksimalan broj puta!").build());
				} else {
					
					promoKod.setBrojIskoriscavanja(promoKod.getBrojIskoriscavanja() - 1);
					promoKodDao.izmeniPromoKod(promoKod);
					double novaCena = 0;
					novaCena = clanarinaKupac.getPlacenaCena() - clanarinaKupac.getPlacenaCena()*promoKod.getProcenatUmanjenjaClanarine()/100;
					clanarinaKupac.setPlacenaCena(novaCena);
				}
			}
		}
		Date currentDate = new Date();
		clanarinaKupac.setDatumPlacanja(currentDate);
		
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		if (TipClanarine.valueOf(tipClanarine) == TipClanarine.Mesecna) {
			c.add(Calendar.DATE, 30);  
		} else {
			c.add(Calendar.DATE, 365);  
		}
		clanarinaKupac.setVaziDo(c.getTime());
		
		clanarinaKupac.setStatus(StatusClanarine.Aktivna);
		
		ClanarinaKupacDAO dao = (ClanarinaKupacDAO) ctx.getAttribute("clanarinaKupacDAO");
		dao.kreirajClanarinu(clanarinaKupac);
	}
}