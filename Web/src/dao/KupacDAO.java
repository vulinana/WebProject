package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.Korisnik;
import beans.Kupac;

/***
 * Klasa namenjena da u�ita proizvode iz fajla i pru�a operacije nad njima (poput pretrage).
 * Proizvodi se nalaze u fajlu WebContent/products.txt u obliku: <br>
 * id;naziv;jedinicna cena
 * @author Lazar
 *
 */
public class KupacDAO {
	
	private HashMap<String, Kupac> kupci = new HashMap<String, Kupac>();
	private String kupciPath = "";
	
	public void setKupciPath(String path) {
		kupciPath = path;
	}
	
	public KupacDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public KupacDAO(String contextPath) {
		setKupciPath(contextPath);
		loadKupci(contextPath);
	}

	public void registerKupac(Kupac kupac) {
		kupci.put(kupac.getKorisnickoIme(), kupac);
		saveKupci();
	}
	
	public boolean korisnikExists(String username) {
		for (Kupac k : kupci.values()) {
			if (k.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void loadKupci(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/kupci.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Kupac.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			kupci = ((HashMap<String, Kupac>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(kupci);
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
	
	private void saveKupci() {
		File f = new File(kupciPath + "/data/kupci.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(kupci);
			System.out.println(stringUsers);
			System.out.print(kupciPath + "\\data\\kupci.txt");
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
