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

import beans.TerminTreninga;

public class TerminTreningaDAO {
	private String path = "";
	public static HashMap<String, TerminTreninga> terminiTreninga = new HashMap<String, TerminTreninga>();
	
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public TerminTreningaDAO() {
		
	}
	
	public TerminTreningaDAO(String contextPath) {
		path = contextPath;
		loadTerminiTreninga(contextPath);
	}
	
	public Collection<TerminTreninga> kreirajTerminTreninga(TerminTreninga terminTreninga) {
		terminiTreninga.put(terminTreninga.getId().toString(), terminTreninga);
		saveTermineTreninga();
		return terminiTreninga.values();
	}
	
	public List<TerminTreninga> getTreneroviTreninzi(String username){
		List<TerminTreninga> zeljeniTermini = new ArrayList<TerminTreninga>();
		for (TerminTreninga tt: terminiTreninga.values()) {
			if (tt.getTrener().equals(username)) {
				zeljeniTermini.add(tt);
			}
		}
		
		return zeljeniTermini;
	}
	
	public Collection<TerminTreninga> deleteTerminTreninga(String id){
		terminiTreninga.remove(id);
		saveTermineTreninga();
		return terminiTreninga.values();
	}
	
	public Collection<TerminTreninga> getTerminiZaSportskiObjekat(String naziv){
		
		List<TerminTreninga> zeljeniTermini = new ArrayList<TerminTreninga>();
		for (TerminTreninga tt: terminiTreninga.values()) {
			if (tt.getSportskiObjekat().equals(naziv)) {
				zeljeniTermini.add(tt);
			}
		}
		return zeljeniTermini;
	}
	
	private void loadTerminiTreninga(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/terminiTreninga.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, TerminTreninga.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			terminiTreninga = ((HashMap<String, TerminTreninga>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(terminiTreninga);
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
	
	private void saveTermineTreninga() {
		File f = new File(path + "/data/terminiTreninga.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(terminiTreninga);
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
