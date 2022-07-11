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

import beans.Administrator;
import beans.Korisnik;
import beans.Korisnik.Uloga;
import beans.TipKupca.NazivTipaKupca;
import beans.Kupac;
import beans.Menadzer;
import beans.Trener;


public class KorisnikDAO {
	
	private HashMap<String, Kupac> kupci = new HashMap<String, Kupac>();
	private HashMap<String, Kupac> izbrisaniKupci = new HashMap<String, Kupac>();
	private HashMap<String, Administrator> administratori = new HashMap<String, Administrator>();
	private HashMap<String, Administrator> izbrisaniAdministratori = new HashMap<String, Administrator>();
	private HashMap<String, Menadzer> menadzeri = new HashMap<String, Menadzer>();
	private HashMap<String, Menadzer> izbrisaniMenadzeri = new HashMap<String, Menadzer>();
	private HashMap<String, Trener> treneri = new HashMap<String, Trener>();
	private HashMap<String, Trener> izbrisaniTreneri = new HashMap<String, Trener>();
	private String path = "";
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public KorisnikDAO() {
		
	}

	public KorisnikDAO(String contextPath) {
		path = contextPath;
		loadKupci(contextPath);
		loadAdministratori(contextPath);
		loadMenadzeri(contextPath);
		loadTreneri(contextPath);
	}

	public void registerKupac(Kupac kupac) {
		kupci.put(kupac.getKorisnickoIme(), kupac);
		saveKupci();
	}
	
	public void registerTrener(Trener trener) {
		treneri.put(trener.getKorisnickoIme(), trener);
		saveTreneri();
	}
	
	public void registerMenadzer(Menadzer menadzer) {
		menadzeri.put(menadzer.getKorisnickoIme(), menadzer);
		saveMenadzeri();
	}
	
	public Korisnik searchKupac(String korisnickoIme) {
		for(Kupac k: kupci.values()) {
			if(k.getKorisnickoIme().equals(korisnickoIme)) {
				return k;
			}
		}
		for(Administrator a: administratori.values()) {
			if(a.getKorisnickoIme().equals(korisnickoIme)) {
				return a;
			}
		}
		for(Menadzer m: menadzeri.values()) {
			if(m.getKorisnickoIme().equals(korisnickoIme)) {
				return m;
			}
		}
		for(Trener t: treneri.values()) {
			if(t.getKorisnickoIme().equals(korisnickoIme)) {
				return t;
			}
		}
		return null;
	}
	
	public boolean korisnikExists(String username) {
		for (Kupac k : kupci.values()) {
			if (k.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Administrator a : administratori.values()) {
			if (a.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Menadzer m : menadzeri.values()) {
			if (m.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Trener t : treneri.values()) {
			if (t.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Kupac k : izbrisaniKupci.values()) {
			if (k.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Administrator a : izbrisaniAdministratori.values()) {
			if (a.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Menadzer m : izbrisaniMenadzeri.values()) {
			if (m.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		for (Trener t : izbrisaniTreneri.values()) {
			if (t.getKorisnickoIme().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public void izmeniKorisnika(String korisnickoIme, Korisnik korisnik) {
		if(korisnik.getUloga() == Uloga.KUPAC) {
			kupci.get(korisnickoIme).setIme(korisnik.getIme());
			kupci.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			kupci.get(korisnickoIme).setPol(korisnik.getPol());
			kupci.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
			kupci.get(korisnickoIme).setKorisnickoIme(korisnik.getKorisnickoIme());
			Kupac obj = kupci.remove(korisnickoIme);
			kupci.put(korisnik.getKorisnickoIme() , obj);
			saveKupci();
		} else if(korisnik.getUloga() == Uloga.ADMINISTRATOR) {
			administratori.get(korisnickoIme).setIme(korisnik.getIme());
			administratori.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			administratori.get(korisnickoIme).setPol(korisnik.getPol());
		    administratori.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
		    administratori.get(korisnickoIme).setKorisnickoIme(korisnik.getKorisnickoIme());
		    Administrator obj = administratori.remove(korisnickoIme);
			administratori.put(korisnik.getKorisnickoIme() , obj);
			saveAdministratori();
		} else if(korisnik.getUloga() == Uloga.TRENER) {
			treneri.get(korisnickoIme).setIme(korisnik.getIme());
			treneri.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			treneri.get(korisnickoIme).setPol(korisnik.getPol());
		    treneri.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
		    treneri.get(korisnickoIme).setKorisnickoIme(korisnik.getKorisnickoIme());
		    Trener obj = treneri.remove(korisnickoIme);
			treneri.put(korisnik.getKorisnickoIme() , obj);
			saveTreneri();
		} else {
			menadzeri.get(korisnickoIme).setIme(korisnik.getIme());
			menadzeri.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			menadzeri.get(korisnickoIme).setPol(korisnik.getPol());
		    menadzeri.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
		    menadzeri.get(korisnickoIme).setKorisnickoIme(korisnik.getKorisnickoIme());
		    Menadzer obj = menadzeri.remove(korisnickoIme);
			menadzeri.put(korisnik.getKorisnickoIme() , obj);
			saveMenadzeri();
		}
	}
	
	public List<Kupac> findAll(){
		
		List<Kupac> korisnici = new ArrayList<Kupac>();
		korisnici.addAll(kupci.values());
		for (Administrator a: administratori.values()) {
			korisnici.add(new Kupac(a.getKorisnickoIme(), a.getLozinka(), a.getIme(), a.getPrezime(), a.getPol(), a.getDatumRodjenja(), a.getUloga()));
		}
		for (Menadzer m: menadzeri.values()) {
			korisnici.add(new Kupac(m.getKorisnickoIme(), m.getLozinka(), m.getIme(), m.getPrezime(), m.getPol(), m.getDatumRodjenja(), m.getUloga()));
		}
		for (Trener t: treneri.values()) {
			korisnici.add(new Kupac(t.getKorisnickoIme(), t.getLozinka(), t.getIme(), t.getPrezime(), t.getPol(), t.getDatumRodjenja(), t.getUloga()));
		}
		return korisnici;
	}
	
	public List<Menadzer> findSlobodniMenadzeri(){
			
			List<Menadzer> slobodniMenadzeri = new ArrayList<Menadzer>();
			
			for (Menadzer m: menadzeri.values()) {
				if (m.getSportskiObjekat() == null || m.getSportskiObjekat() == "") {
					slobodniMenadzeri.add(m);
				}
			}		
			return slobodniMenadzeri;
	}
	
	
	public Collection<Trener> findTreneri(){
		return treneri.values();
	}
	
	public List<Kupac> pretraziPoImenuPrezimenuKorisnickomImenu(String ime, String prezime, String korisnickoIme){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getIme().toLowerCase().startsWith(ime.toLowerCase()) && k.getPrezime().toLowerCase().startsWith(prezime.toLowerCase()) && k.getKorisnickoIme().toLowerCase().startsWith(korisnickoIme.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	
	public List<Kupac> pretraziPoImenuPrezimenu(String ime, String prezime){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getIme().toLowerCase().startsWith(ime.toLowerCase()) && k.getPrezime().toLowerCase().startsWith(prezime.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	
	public List<Kupac> pretraziPoImenuKorisnickomImenu(String ime, String korisnickoIme){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getIme().toLowerCase().startsWith(ime.toLowerCase()) && k.getKorisnickoIme().toLowerCase().startsWith(korisnickoIme.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	
	public List<Kupac> pretraziPoPrezimenuKorisnickomImenu(String prezime, String korisnickoIme){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getPrezime().toLowerCase().startsWith(prezime.toLowerCase()) && k.getKorisnickoIme().toLowerCase().startsWith(korisnickoIme.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	
	public List<Kupac> pretraziPoImenu(String ime){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getIme().toLowerCase().startsWith(ime.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	
	public List<Kupac> pretraziPoPrezimenu(String prezime){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getPrezime().toLowerCase().startsWith(prezime.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	
	public List<Kupac> pretraziPoKorisnickomImenu(String korisnickoIme){
		
		Collection<Kupac> korisnici = findAll();
		List<Kupac> trazeniKorisnici = new ArrayList<Kupac>();
		for(Kupac k: korisnici) {
			if (k.getKorisnickoIme().toLowerCase().startsWith(korisnickoIme.toLowerCase())) {
				trazeniKorisnici.add(k);
			}
		}
		
		return trazeniKorisnici;
	}
	

	public List<Kupac> sortirajPoImenuRastuce(List<Kupac> prikazaniKorisnici){
		
		List<Kupac> sortiraniKorisnici = new ArrayList<Kupac>();
		int tempKorisnik = 0;
		String min = null;
		while (!prikazaniKorisnici.isEmpty()) {
			boolean prviProlaz = true;
			for (int i = 0; i < prikazaniKorisnici.size(); i++) {
				if (prviProlaz) {
					min = prikazaniKorisnici.get(i).getIme().toLowerCase();
					prviProlaz = false;
				}
				if (prikazaniKorisnici.get(i).getIme().toLowerCase().compareTo(min.toLowerCase()) <= 0) {
					min = prikazaniKorisnici.get(i).getIme().toLowerCase();
					tempKorisnik = i;
				}
			}
			sortiraniKorisnici.add(prikazaniKorisnici.get(tempKorisnik));
			prikazaniKorisnici.remove(tempKorisnik);
		}
		return sortiraniKorisnici;
	}
	
	public List<Kupac> sortirajPoPrezimenuRastuce(List<Kupac> prikazaniKorisnici){
		
		List<Kupac> sortiraniKorisnici = new ArrayList<Kupac>();
		int tempKorisnik = 0;
		String min = null;
		while (!prikazaniKorisnici.isEmpty()) {
			boolean prviProlaz = true;
			for (int i = 0; i < prikazaniKorisnici.size(); i++) {
				if (prviProlaz) {
					min = prikazaniKorisnici.get(i).getPrezime().toLowerCase();
					prviProlaz = false;
				}
				if (prikazaniKorisnici.get(i).getPrezime().toLowerCase().compareTo(min.toLowerCase()) <= 0) {
					min = prikazaniKorisnici.get(i).getPrezime().toLowerCase();
					tempKorisnik = i;
				}
			}
			sortiraniKorisnici.add(prikazaniKorisnici.get(tempKorisnik));
			prikazaniKorisnici.remove(tempKorisnik);
		}
		return sortiraniKorisnici;
	}
	
	public List<Kupac> sortirajPoKorisnickomImenuRastuce(List<Kupac> prikazaniKorisnici){
		
		List<Kupac> sortiraniKorisnici = new ArrayList<Kupac>();
		int tempKorisnik = 0;
		String min = null;
		while (!prikazaniKorisnici.isEmpty()) {
			boolean prviProlaz = true;
			for (int i = 0; i < prikazaniKorisnici.size(); i++) {
				if (prviProlaz) {
					min = prikazaniKorisnici.get(i).getKorisnickoIme().toLowerCase();
					prviProlaz = false;
				}
				if (prikazaniKorisnici.get(i).getKorisnickoIme().toLowerCase().compareTo(min.toLowerCase()) <= 0) {
					min = prikazaniKorisnici.get(i).getKorisnickoIme().toLowerCase();
					tempKorisnik = i;
				}
			}
			sortiraniKorisnici.add(prikazaniKorisnici.get(tempKorisnik));
			prikazaniKorisnici.remove(tempKorisnik);
		}
		return sortiraniKorisnici;
	}
	
	public List<Kupac> sortirajPoBodovimaRastuce(List<Kupac> prikazaniKorisnici){
		
		List<Kupac> sortiraniKorisnici = new ArrayList<Kupac>();
		int tempKorisnik = 0;
		double min = 0;
		while (!prikazaniKorisnici.isEmpty()) {
			boolean prviProlaz = true;
			for (int i = 0; i < prikazaniKorisnici.size(); i++) {
				if (prviProlaz) {
					min = prikazaniKorisnici.get(i).getBrojSakupljenihBodova();
					prviProlaz = false;
				}
				if (prikazaniKorisnici.get(i).getBrojSakupljenihBodova() <= min) {
					min = prikazaniKorisnici.get(i).getBrojSakupljenihBodova();
					tempKorisnik = i;
				}
			}
			sortiraniKorisnici.add(prikazaniKorisnici.get(tempKorisnik));
			prikazaniKorisnici.remove(tempKorisnik);
		}
		return sortiraniKorisnici;
	}
	
	public List<Kupac> filtrirajPoUlozi(List<Kupac> prikazaniKorisnici, Korisnik.Uloga uloga){
		List<Kupac> filtriraniKorisnici = new ArrayList<Kupac>();
		for (Kupac k: prikazaniKorisnici) {
			if (k.getUloga() == uloga) {
				filtriraniKorisnici.add(k);
			}
		}
		return filtriraniKorisnici;
	}
	
	public List<Kupac> filtrirajPoTipu(List<Kupac> prikazaniKorisnici, NazivTipaKupca tipKupca){
		List<Kupac> filtriraniKorisnici = new ArrayList<Kupac>();
		for (Kupac k: prikazaniKorisnici) {
			if (k.getTipKupca() == tipKupca) {
				filtriraniKorisnici.add(k);
			}
		}
		return filtriraniKorisnici;
	}
	
	public Kupac findKupca(String korisnickoImeKupca) {
		
		for (Kupac k: kupci.values()) {
			if (k.getKorisnickoIme().equals(korisnickoImeKupca)) {
				return k;
			}
		}
		
		return null;
	}
	
	public void izmeniKupca(String korisnickoIme, Kupac kupac) {
		
		kupci.get(korisnickoIme).setBrojSakupljenihBodova(kupac.getBrojSakupljenihBodova());
		kupci.get(korisnickoIme).setTipKupca(kupac.getTipKupca());
		saveKupci();
	}
	
	public List<Trener> getTrenereByKorisnickaImena(List<String> korisnickaImena){
		List<Trener> zeljeniTreneri = new ArrayList<Trener>();
		for (Trener t: treneri.values()) {
			for (String ime: korisnickaImena) {
				if (t.getKorisnickoIme().equals(ime)) {
					zeljeniTreneri.add(t);
				}
			}
		}
		return zeljeniTreneri;
	}
	
	public List<Kupac> getKupceByKorisnickaImena(List<String> korisnickaImena){
		List<Kupac> zeljeniKupci = new ArrayList<Kupac>();
		for (Kupac t: kupci.values()) {
			for (String ime: korisnickaImena) {
				if (t.getKorisnickoIme().equals(ime)) {
					zeljeniKupci.add(t);
				}
			}
		}
		return zeljeniKupci;
	}
	
	public void razresiMenadzeraDuznostiNadObrisanimObjektom(String naziv) {
		for (Menadzer m: menadzeri.values()) {
			if (m.getSportskiObjekat().equals(naziv)) {
				m.setSportskiObjekat("");
			}
		}
		saveMenadzeri();
	}
	
	public Collection<Menadzer> zaduziMenadzera(String menadzer, String naziv) {
		for(Menadzer m: menadzeri.values()) {
			if (m.getKorisnickoIme().equals(menadzer)) {
				m.setSportskiObjekat(naziv);
			}
		}
		saveMenadzeri();
		return findSlobodniMenadzeri();
	}
	
	public void deleteKorisnik(String korisnickoIme, Uloga uloga){
		
		if (uloga == Uloga.ADMINISTRATOR) {
			Administrator a = administratori.get(korisnickoIme);
			a.setIzbrisan(true);
			administratori.remove(korisnickoIme);
			izbrisaniAdministratori.put(a.getKorisnickoIme(), a);
			saveAdministratori();
		}
		
		if (uloga == Uloga.MENADZER) {
			Menadzer m = menadzeri.get(korisnickoIme);
			m.setIzbrisan(true);
			menadzeri.remove(korisnickoIme);
			izbrisaniMenadzeri.put(m.getKorisnickoIme(), m);
			saveMenadzeri();
		}
		
		if (uloga == Uloga.KUPAC) {
			Kupac k = kupci.get(korisnickoIme);
			k.setIzbrisan(true);
			kupci.remove(korisnickoIme);
			izbrisaniKupci.put(k.getKorisnickoIme(), k);
			saveKupci();
		}
		
		if (uloga == Uloga.TRENER) {
			Trener t = treneri.get(korisnickoIme);
			t.setIzbrisan(true);
			treneri.remove(korisnickoIme);
			izbrisaniTreneri.put(t.getKorisnickoIme(), t);
			saveTreneri();
		}
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
		izbaciObrisaneKupce();
	}
	
	private void saveKupci() {
		ubaciObrisaneKupce();
		File f = new File(path + "/data/kupci.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
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
		izbaciObrisaneKupce();
	}
	
	private void loadAdministratori(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/administratori.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Administrator.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			administratori = ((HashMap<String, Administrator>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(administratori);
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
		izbaciObrisaneAdministratore();
	}
	
	private void saveAdministratori() {
		ubaciObrisaneAdministratore();
		File f = new File(path + "/data/administratori.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(administratori);
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
		izbaciObrisaneAdministratore();
	}
	
	private void loadMenadzeri(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/menadzeri.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Menadzer.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			menadzeri = ((HashMap<String, Menadzer>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(menadzeri);
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
		izbaciObrisaneMenadzere();
	}
	
	private void saveMenadzeri() {
		ubaciObrisaneMenadzere();
		File f = new File(path + "/data/menadzeri.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(menadzeri);
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
		izbaciObrisaneMenadzere();
	}
	
	private void loadTreneri(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/treneri.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Trener.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			treneri = ((HashMap<String, Trener>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(treneri);
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
		izbaciObrisaneTrenere();
	}
	
	private void saveTreneri() {
		ubaciObrisaneTrenere();
		File f = new File(path + "/data/treneri.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(treneri);
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
		izbaciObrisaneTrenere();
	}
	
	private void izbaciObrisaneAdministratore() {
		
		for (Administrator a: administratori.values()) {
			
			if (a.isIzbrisan()) {
				izbrisaniAdministratori.put(a.getKorisnickoIme(), a);
			}
		}
		
		for (Administrator a: izbrisaniAdministratori.values()) {
			administratori.remove(a.getKorisnickoIme());
		}
		
	}
	
	
	private void ubaciObrisaneAdministratore() {
		administratori.putAll(izbrisaniAdministratori);
	}
	
	private void izbaciObrisaneTrenere() {
		
		for (Trener t: treneri.values()) {
			
			if (t.isIzbrisan()) {
				izbrisaniTreneri.put(t.getKorisnickoIme(), t);
			}
		}
		
		for (Trener t: izbrisaniTreneri.values()) {
			treneri.remove(t.getKorisnickoIme());
		}
		
	}
	
	
	private void ubaciObrisaneTrenere() {
		treneri.putAll(izbrisaniTreneri);
	}
	
	private void izbaciObrisaneMenadzere() {
		
		for (Menadzer m: menadzeri.values()) {
			
			if (m.isIzbrisan()) {
				izbrisaniMenadzeri.put(m.getKorisnickoIme(), m);
			}
		}
		
		for (Menadzer m: izbrisaniMenadzeri.values()) {
			menadzeri.remove(m.getKorisnickoIme());
		}
		
	}
	
	
	private void ubaciObrisaneMenadzere() {
		menadzeri.putAll(izbrisaniMenadzeri);
	}
	
	private void izbaciObrisaneKupce() {
		
		for (Kupac k: kupci.values()) {
			
			if (k.isIzbrisan()) {
				izbrisaniKupci.put(k.getKorisnickoIme(), k);
			}
		}
		
		for (Kupac k: izbrisaniKupci.values()) {
			kupci.remove(k.getKorisnickoIme());
		}
		
	}
	
	
	private void ubaciObrisaneKupce() {
		kupci.putAll(izbrisaniKupci);
	}
	
}
