package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.PromoKod;

public class PromoKodDAO {
	private String path = "";
	public static HashMap<String, PromoKod> obrisaniKodovi = new HashMap<String, PromoKod>();
	public static HashMap<String, PromoKod> kodovi = new HashMap<String, PromoKod>();
	
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public PromoKodDAO() {
		
	}
	
	public PromoKodDAO(String contextPath) {
		path = contextPath;
		loadKodove(contextPath);
	}
	
	public Collection<PromoKod> findAll(){
		return kodovi.values();
	}
	
	public PromoKod promoKodExists(String oznaka) {
		for (PromoKod pk: kodovi.values()) {
			if (pk.getOznaka().toLowerCase().equals(oznaka.toLowerCase())) {
				return pk;
			}
		}
		
		return null;
	}
	
	public Collection<PromoKod> kreirajPromoKod(PromoKod promoKod) {
		kodovi.put(promoKod.getId().toString(), promoKod);
		savePromoKodove();
		return kodovi.values();
	}
	
	public Collection<PromoKod> deletePromoKod(String id){
		
		PromoKod pk = kodovi.get(id);
		pk.setIzbrisan(true);
		kodovi.remove(id);
		obrisaniKodovi.put(pk.getId().toString(), pk);
		savePromoKodove();
		return kodovi.values();
	}
	
	public void izmeniPromoKod(PromoKod promoKod) {
		kodovi.remove(promoKod.getId().toString());
		kodovi.put(promoKod.getId().toString(), promoKod);
		savePromoKodove();
	}
	
	private void loadKodove(String contextPath) {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(contextPath + "/data/promoKodovi.txt");
			in = new BufferedReader(new FileReader(file));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, PromoKod.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			kodovi = ((HashMap<String, PromoKod>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringUsers = objectMapper.writeValueAsString(kodovi);
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
	
	private void savePromoKodove() {
		
		ubaciObrisane();
		
		File f = new File(path + "/data/promoKodovi.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringUsers = objectMapper.writeValueAsString(kodovi);
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
	
	private void izbaciObrisane() {
		
		for (PromoKod pk: kodovi.values()) {
			
			if (pk.isIzbrisan()) {
				obrisaniKodovi.put(pk.getId().toString(), pk);
			}
		}
		
		for (PromoKod pk: obrisaniKodovi.values()) {
			kodovi.remove(pk.getId().toString());
		}
		
	}
	
	
	private void ubaciObrisane() {
		kodovi.putAll(obrisaniKodovi);
	}
	
}
