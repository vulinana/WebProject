package beans;

import java.util.Date;


public class Clanarina {
	
	public enum TipClanarine{Godisnja, Mesecna}
	public enum StatusClanarine {Aktivna, Neaktivna}
	
	private String id;
	private TipClanarine tipClanarine;
	private double cena;
	private String brojTermina;
	
	public Clanarina() {}
	
	public Clanarina(String id, TipClanarine tipClanarine, Date datumPlacanja, Date vaziDo, double cena, Kupac kupac,
			StatusClanarine status, String brojTermina) {
		super();
		this.id = id;
		this.tipClanarine = tipClanarine;
		this.cena = cena;
		this.brojTermina = brojTermina;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipClanarine getTipClanarine() {
		return tipClanarine;
	}

	public void setTipClanarine(TipClanarine tipClanarine) {
		this.tipClanarine = tipClanarine;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public String getBrojTermina() {
		return brojTermina;
	}

	public void setBrojTermina(String brojTermina) {
		this.brojTermina = brojTermina;
	}
	
	
}
