package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.Komentar;
import beans.SportskiObjekat;
import beans.Komentar.StatusKomentara;

public class KomentarDAO {

	private String path = "";
	public static HashMap<String, Komentar> komentari = new HashMap<String, Komentar>();
	
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public KomentarDAO() {
		
	}
	
	public KomentarDAO(String contextPath) {
		path = contextPath;
		loadKomentari(contextPath);
	}
	
	public Collection<Komentar> findAll(){
		return komentari.values();
	}
	
	public List<Komentar> getByNazivObjekta(String nazivObjekta){
		List<Komentar> zeljeniKomentari = new ArrayList<Komentar>();
		
		for (Komentar k: komentari.values()) {
			if (k.getSportskiObjekat().equals(nazivObjekta)) {
				zeljeniKomentari.add(k);
			}
		}
		
		return zeljeniKomentari;
	}
	
	public void odrediProsecnuOcenuZaObjekte() {
		
		SportskiObjekatDAO.loadSportskiObjekti(path);	
		for (SportskiObjekat s: SportskiObjekatDAO.sportskiObjekti.values()) {
			double prosecnaOcena = 0;
			int brojOcena = 0;
			for (Komentar k: KomentarDAO.komentari.values()) {
				if (k.getSportskiObjekat().equals(s.getNaziv())) {
					prosecnaOcena += k.getOcena();
					brojOcena++;
				}
			}
			
			if (prosecnaOcena == 0) {
				s.setProsecnaOcena(0);
			}
			else {
				s.setProsecnaOcena(Math.round(prosecnaOcena/brojOcena*100.0)/100.0);
			}
		}
	}
	
	public void sacuvajKomentar(Komentar komentar) {
		komentari.put(komentar.getId().toString(), komentar);
		saveKomentar();
	}
	
	public Collection<Komentar> recenzirajKomentar(String id, StatusKomentara status){
		
		komentari.get(id).setStatusKomentara(status);
		saveKomentar();
		return komentari.values();
	}
	
	private void loadKomentari(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/komentari.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Komentar.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			komentari = ((HashMap<String, Komentar>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(komentari);
				fileWriter.write(stringUsers);
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileWriter != null) {
					try {
						fileWriter.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private void saveKomentar() {
		File f = new File(path + "/data/komentari.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(komentari);
			fileWriter.write(stringUsers);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
