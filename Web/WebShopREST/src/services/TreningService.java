package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
	@Path("/uploadImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		String imageName = getImageName();
		saveToDisk(uploadedInputStream, imageName);
		return imageName;
	}
	
	private void saveToDisk(InputStream uploadedInputStream,String imageName) {
		
		String uploadedFileLocation = ctx.getRealPath("") + "/pictures/" + imageName;
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			out = new FileOutputStream(new File(uploadedFileLocation));
			while((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getImageName() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date now = new Date();
		String timeStamp = sdf.format(now);
		return "image" + timeStamp + ".jpg";
	}
	
	@POST
	@Path("/kreirajNoviTrening")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Trening> kreirajNoviTrening(Trening trening) throws Exception {
		TreningDAO dao = (TreningDAO) ctx.getAttribute("treningDAO");
		if (dao.treningExists(trening.getNaziv())) {
			throw new Exception("Zauzeto ime");
		}
		trening.setId();
		return dao.kreirajNoviTrening(trening);
	}
	
	@PUT
	@Path("/izmeniTrening/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Trening> izmeniTrening(@PathParam("id") UUID id, Trening trening) {
		trening.setSlika("trening1.jpg");
		TreningDAO dao = (TreningDAO) ctx.getAttribute("treningDAO");
		return dao.izmeniTrening(id, trening);
	}
}