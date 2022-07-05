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

import beans.IstorijaTreninga;

public class IstorijaTreningaDAO {

	private String path = "";
	public static HashMap<String, IstorijaTreninga> istorijaTreningaZaKupce = new HashMap<String, IstorijaTreninga>();
	
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public IstorijaTreningaDAO() {
		
	}
	
	public IstorijaTreningaDAO(String contextPath) {
		path = contextPath;
		loadIstorijaTreninga(contextPath);
	}
	
	public Collection<IstorijaTreninga> findAll(String korisnickoImeKupca){
		
		List<IstorijaTreninga> istorijaTreningaZaZeljenogKupca = new ArrayList<IstorijaTreninga>();
		for (IstorijaTreninga i: istorijaTreningaZaKupce.values()) {
			if (i.getKupac().equals(korisnickoImeKupca)) {
				istorijaTreningaZaZeljenogKupca.add(i);
			}
		}
		return istorijaTreningaZaZeljenogKupca;
	}
	
	public Collection<IstorijaTreninga> findZaKupcaZaOdredjeniObjekat(String korisnickoImeKupca, String objekat){
		
		List<IstorijaTreninga> istorijaTreningaZaZeljenogKupca = new ArrayList<IstorijaTreninga>();
		for (IstorijaTreninga i: istorijaTreningaZaKupce.values()) {
			if (i.getKupac().equals(korisnickoImeKupca) && i.getSportskiObjekat().equals(objekat)) {
				istorijaTreningaZaZeljenogKupca.add(i);
			}
		}
		return istorijaTreningaZaZeljenogKupca;
	}
	
	public Collection<IstorijaTreninga> kreirajTreningUIstoriji(IstorijaTreninga istorijaTreninga){
		
		istorijaTreningaZaKupce.put(istorijaTreninga.getId().toString(), istorijaTreninga);
		saveIstorijaTreninga();
		return istorijaTreningaZaKupce.values();
		
	}
	
	public List<String> getKupcePremaObjektu(String nazivObjekta){
		List<String> korisnickaImena = new ArrayList<String>();
		for (IstorijaTreninga i: istorijaTreningaZaKupce.values()) {
			if (i.getSportskiObjekat().getNaziv().equals(nazivObjekta)) {
				korisnickaImena.add(i.getKupac());
			}
		}
		return korisnickaImena;
	}
	
	private void loadIstorijaTreninga(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/istorijaTreninga.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, IstorijaTreninga.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			istorijaTreningaZaKupce = ((HashMap<String, IstorijaTreninga>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(istorijaTreningaZaKupce);
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
	
	private void saveIstorijaTreninga() {
		File f = new File(path + "/data/istorijaTreninga.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(istorijaTreningaZaKupce);
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