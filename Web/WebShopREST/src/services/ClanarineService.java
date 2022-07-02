package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Clanarina;
import dao.ClanarinaDAO;

@Path("/clanarine")
public class ClanarineService {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	public ClanarineService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("clanarinaDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("clanarinaDAO", new ClanarinaDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Clanarina> getClanarine() {
		ClanarinaDAO dao = (ClanarinaDAO) ctx.getAttribute("clanarinaDAO");
		return dao.findAll();
	}
}