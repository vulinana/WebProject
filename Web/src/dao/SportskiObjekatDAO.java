package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public List<SportskiObjekat> pretraziPoNazivu(String naziv){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase())) {
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoMestu(String mesto){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase())) {
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoProsecnojOceni(double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getProsecnaOcena() ==  prosecnaOcena) {
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoTipu(SportskiObjekat.TipObjekta tipObjekta){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getTipObjekta() ==  tipObjekta) {
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoSvimKriterijumima(SportskiObjekat so){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(so.getNaziv().toLowerCase()) && s.getTipObjekta() == so.getTipObjekta() 
				&& s.getLokacija().getAdresa().getMesto().toLowerCase().equals(so.getLokacija().getAdresa().getMesto().toLowerCase()) && s.getProsecnaOcena() == so.getProsecnaOcena()) {
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoNazivuTipuMestu(String naziv, SportskiObjekat.TipObjekta tipObjekta, String mesto){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase()) && s.getTipObjekta() == tipObjekta && s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase())){
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoNazivuTipuProsecnojOceni(String naziv, SportskiObjekat.TipObjekta tipObjekta, double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase()) && s.getTipObjekta() == tipObjekta && s.getProsecnaOcena() == prosecnaOcena){
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	
	public List<SportskiObjekat> pretraziPoNazivuMestuProsecnojOceni(String naziv, String mesto, double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase()) && s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase()) && s.getProsecnaOcena() == prosecnaOcena){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoTipuMestuProsecnojOceni(SportskiObjekat.TipObjekta tipObjekta, String mesto, double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getTipObjekta() == tipObjekta && s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase()) && s.getProsecnaOcena() == prosecnaOcena){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoNazivuTipu(String naziv, SportskiObjekat.TipObjekta tipObjekta){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase()) && s.getTipObjekta() == tipObjekta){
				filtriraniObjekti.add(s);
			}
		}
		
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoNazivuMestu(String naziv, String mesto){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase()) && s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase())){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoNazivuProsecnojOceni(String naziv, double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getNaziv().toLowerCase().equals(naziv.toLowerCase()) && s.getProsecnaOcena() == prosecnaOcena){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoTipuMestu(SportskiObjekat.TipObjekta tipObjekta, String mesto){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getTipObjekta() == tipObjekta && s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase())){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoTipuProsecnojOceni(SportskiObjekat.TipObjekta tipObjekta, double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getTipObjekta() == tipObjekta && s.getProsecnaOcena() == prosecnaOcena){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> pretraziPoMestuProsecnojOceni(String mesto, double prosecnaOcena){
		List<SportskiObjekat> filtriraniObjekti = new ArrayList<SportskiObjekat>();
		for (SportskiObjekat s: sportskiObjekti.values()) {
			if(s.getLokacija().getAdresa().getMesto().toLowerCase().equals(mesto.toLowerCase()) && s.getProsecnaOcena() == prosecnaOcena){
				filtriraniObjekti.add(s);
			}
		}
		return filtriraniObjekti;
	}
	
	public List<SportskiObjekat> sortirajPoProsecnojOceniOpadajuce(List<SportskiObjekat> prikazaniSportskiObjekti){
		
		List<SportskiObjekat> sortiraniObjekti = new ArrayList<SportskiObjekat>();
		while (!prikazaniSportskiObjekti.isEmpty()) {
			double max = -1;
			int tempSportskiObjekat = 0;
			for (int i = 0; i < prikazaniSportskiObjekti.size(); i++) {
				if (prikazaniSportskiObjekti.get(i).getProsecnaOcena() >= max) {
					max = prikazaniSportskiObjekti.get(i).getProsecnaOcena();
					tempSportskiObjekat = i;
				}
			}
			sortiraniObjekti.add(prikazaniSportskiObjekti.get(tempSportskiObjekat));
			prikazaniSportskiObjekti.remove(tempSportskiObjekat);
		}
		return sortiraniObjekti;
	}
	
	public List<SportskiObjekat> sortirajPoProsecnojOceniRastuce(List<SportskiObjekat> prikazaniSportskiObjekti){
		
		List<SportskiObjekat> sortiraniObjekti = new ArrayList<SportskiObjekat>();
		int tempSportskiObjekat = 0;
		double min = 0;
		while (!prikazaniSportskiObjekti.isEmpty()) {
			boolean prviProlaz = true;
			for (int i = 0; i < prikazaniSportskiObjekti.size(); i++) {
				if (prviProlaz) {
					min = prikazaniSportskiObjekti.get(i).getProsecnaOcena();
					prviProlaz = false;
				}
				if (prikazaniSportskiObjekti.get(i).getProsecnaOcena() <= min) {
					min = prikazaniSportskiObjekti.get(i).getProsecnaOcena();
					tempSportskiObjekat = i;
				}
			}
			sortiraniObjekti.add(prikazaniSportskiObjekti.get(tempSportskiObjekat));
			prikazaniSportskiObjekti.remove(tempSportskiObjekat);
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
