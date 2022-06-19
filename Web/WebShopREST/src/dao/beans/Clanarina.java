package beans;

import java.util.Date;

enum TipClanarine{Godisnja, Mesecna}
enum StatusClanarine {Aktivna, Neaktivna}

public class Clanarina {
	
	private String id;
	private TipClanarine tipClanarine;
	private Date datumPlacanja;
	private Date vaziDo;
	private double cena;
	private Kupac kupac;
	private StatusClanarine status;
	private String brojTermina;
	
	public Clanarina(String id, TipClanarine tipClanarine, Date datumPlacanja, Date vaziDo, double cena, Kupac kupac,
			StatusClanarine status, String brojTermina) {
		super();
		this.id = id;
		this.tipClanarine = tipClanarine;
		this.datumPlacanja = datumPlacanja;
		this.vaziDo = vaziDo;
		this.cena = cena;
		this.kupac = kupac;
		this.status = status;
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

	public Date getDatumPlacanja() {
		return datumPlacanja;
	}

	public void setDatumPlacanja(Date datumPlacanja) {
		this.datumPlacanja = datumPlacanja;
	}

	public Date getVaziDo() {
		return vaziDo;
	}

	public void setVaziDo(Date vaziDo) {
		this.vaziDo = vaziDo;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public StatusClanarine getStatus() {
		return status;
	}

	public void setStatus(StatusClanarine status) {
		this.status = status;
	}

	public String getBrojTermina() {
		return brojTermina;
	}

	public void setBrojTermina(String brojTermina) {
		this.brojTermina = brojTermina;
	}
	
	
}
