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
import beans.SportskiObjekat;
import beans.Trener;

/***
 * Klasa namenjena da u�ita proizvode iz fajla i pru�a operacije nad njima (poput pretrage).
 * Proizvodi se nalaze u fajlu WebContent/products.txt u obliku: <br>
 * id;naziv;jedinicna cena
 * @author Lazar
 *
 */
public class KorisnikDAO {
	
	private HashMap<String, Kupac> kupci = new HashMap<String, Kupac>();
	private HashMap<String, Administrator> administratori = new HashMap<String, Administrator>();
	private HashMap<String, Menadzer> menadzeri = new HashMap<String, Menadzer>();
	private HashMap<String, Trener> treneri = new HashMap<String, Trener>();
	private String path = "";
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public KorisnikDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
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
		return false;
	}
	
	public void izmeniKorisnika(String korisnickoIme, Korisnik korisnik) {
		if(korisnik.getUloga() == Uloga.KUPAC) {
			kupci.get(korisnickoIme).setIme(korisnik.getIme());
			kupci.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			kupci.get(korisnickoIme).setPol(korisnik.getPol());
			kupci.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
			saveKupci();
		} else if(korisnik.getUloga() == Uloga.ADMINISTRATOR) {
			administratori.get(korisnickoIme).setIme(korisnik.getIme());
			administratori.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			administratori.get(korisnickoIme).setPol(korisnik.getPol());
		    administratori.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
			saveAdministratori();
		} else if(korisnik.getUloga() == Uloga.TRENER) {
			treneri.get(korisnickoIme).setIme(korisnik.getIme());
			treneri.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			treneri.get(korisnickoIme).setPol(korisnik.getPol());
		    treneri.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
			saveTreneri();
		} else {
			menadzeri.get(korisnickoIme).setIme(korisnik.getIme());
			menadzeri.get(korisnickoIme).setPrezime(korisnik.getPrezime());
			menadzeri.get(korisnickoIme).setPol(korisnik.getPol());
		    menadzeri.get(korisnickoIme).setDatumRodjenja(korisnik.getDatumRodjenja());
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
		
	}
	
	private void saveAdministratori() {
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
		
	}
	
	private void saveMenadzeri() {
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
		
	}
	
	private void saveTreneri() {
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
	}
	
}
