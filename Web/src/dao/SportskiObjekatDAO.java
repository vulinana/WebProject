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

import beans.SportskiObjekat;

public class SportskiObjekatDAO {

	private HashMap<String, SportskiObjekat> sportskiObjekti = new HashMap<String, SportskiObjekat>();
	
	public SportskiObjekatDAO() {
		
	}
	
	
	public SportskiObjekatDAO(String contextPath) {
		loadSportskiObjekti(contextPath);
	}
	
	public List<SportskiObjekat> findAll(){
		List<SportskiObjekat> sortiraniObjekti = new ArrayList<SportskiObjekat>();
		
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(SportskiObjekat.StatusObjekta.Radi == s.odrediStatus()) {
				sortiraniObjekti.add(s);
			}
		}
		
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(SportskiObjekat.StatusObjekta.NeRadi == s.odrediStatus()) {
				sortiraniObjekti.add(s);
			}
		}
		
		return sortiraniObjekti;
	}
	
	public void kreirajSportskiObjekat(SportskiObjekat sportskiObjekat) {
			
	}
	
	private void loadSportskiObjekti(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/sportskiObjekti.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, SportskiObjekat.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			sportskiObjekti = ((HashMap<String, SportskiObjekat>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(sportskiObjekti);
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
	
}
