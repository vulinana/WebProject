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
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.PromoKod;
import beans.SportskiObjekat;
import beans.Trening;

public class TreningDAO {

	private String path = "";
	public static HashMap<String, Trening> treninzi = new HashMap<String, Trening>();
	public static HashMap<String, Trening> obrisaniTreninzi = new HashMap<String, Trening>();
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public TreningDAO() {
		
	}
	
	public TreningDAO(String contextPath) {
		path = contextPath;
		loadTreninzi(contextPath);
	}
	
	public List<Trening> getByNazivObjekta(String nazivObjekta){
		List<Trening> zeljeniTreninzi = new ArrayList<Trening>();
		
		for (Trening t: treninzi.values()) {
			if (t.getSportskiObjekatKomPripada().equals(nazivObjekta)) {
				zeljeniTreninzi.add(t);
			}
		}
		
		return zeljeniTreninzi;
	}
	
	public List<Trening> getByTrener(String trener){
		List<Trening> zeljeniTreninzi = new ArrayList<Trening>();
		
		for (Trening t: treninzi.values()) {
			if (t.getTrener().equals(trener)) {
				zeljeniTreninzi.add(t);
			}
		}
		
		return zeljeniTreninzi;
	}
	
	public Collection<Trening> kreirajNoviTrening(Trening trening){
		
		treninzi.put(trening.getId().toString(), trening);
		saveTreninzi();
		return treninzi.values();
	}
	
	public String pronadjiTreneraZaTrening(String nazivSportskogObjekta, String trening) {
		
		for (Trening t: treninzi.values()) {
			if (t.getSportskiObjekatKomPripada().equals(nazivSportskogObjekta) && t.getNaziv().equals(trening)) {
				return t.getTrener();
			}
		}
		
		return null;
	}
	
	public Collection<Trening> izmeniTrening(UUID id, Trening trening){
		
		String idString = id.toString();
		treninzi.get(idString).setNaziv(trening.getNaziv());
		treninzi.get(idString).setTip(trening.getTip());
		treninzi.get(idString).setOpis(trening.getOpis());
		treninzi.get(idString).setTrajanje(trening.getTrajanje());
		treninzi.get(idString).setDoplata(trening.getDoplata());
		treninzi.get(idString).setTrener(trening.getTrener());
		saveTreninzi();
		return treninzi.values();
	}
	
	public Collection<Trening> izmeniSlikuTreninga(UUID id, Trening trening){
		
		String idString = id.toString();
		treninzi.get(idString).setSlika(trening.getSlika());
		saveTreninzi();
		return treninzi.values();
	}
	
	public boolean treningExists(String naziv) {
		for (Trening t: treninzi.values()) {
			if (t.getNaziv().toLowerCase().equals(naziv.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public Trening getByNazivObjektaINazivTreninga(String nazivObjekta, String nazivTreninga) {
		
		for (Trening t: treninzi.values()) {
			if (t.getSportskiObjekatKomPripada().equals(nazivObjekta) && t.getNaziv().equals(nazivTreninga)) {
				return t;
			}
		}
		
		return null;
	}
	
	public List<String> getTrenere(String nazivObjekta){
		
		List<String> korisnickaImena = new ArrayList<String>();
		for (Trening t: treninzi.values()) {
			if (t.getSportskiObjekatKomPripada().equals(nazivObjekta)) {
				korisnickaImena.add(t.getTrener());
			}
		}
		
		return korisnickaImena;
	}
	
	public void deleteTrening(String id){
		
		Trening t = treninzi.get(id);
		t.setIzbrisan(true);
		treninzi.remove(id);
		obrisaniTreninzi.put(t.getId().toString(), t);
		saveTreninzi();
	}
	
	private void loadTreninzi(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/treninzi.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Trening.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			treninzi = ((HashMap<String, Trening>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(treninzi);
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
		izbaciObrisane();
	}
	
	private void saveTreninzi() {
		ubaciObrisane();
		File f = new File(path + "/data/treninzi.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(treninzi);
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
		izbaciObrisane();
	}
	
	private static void izbaciObrisane() {
		
		for (Trening t: treninzi.values()) {
			
			if (t.isIzbrisan()) {
				obrisaniTreninzi.put(t.getId().toString(), t);
			}
		}
		
		for (Trening s: obrisaniTreninzi.values()) {
			treninzi.remove(s.getId().toString());
		}
		
	}
	
	
	private void ubaciObrisane() {
		treninzi.putAll(obrisaniTreninzi);
	}
	
}
