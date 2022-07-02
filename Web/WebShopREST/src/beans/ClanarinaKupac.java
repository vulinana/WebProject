package beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import beans.Clanarina.StatusClanarine;

public class ClanarinaKupac {
	
	private String kupac;
	private String clanarinaId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date datumPlacanja;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date vaziDo;
	private double placenaCena;
	private Clanarina.StatusClanarine status;
	private String brojPreostalihTermina;
	
	ClanarinaKupac(){}
	
	
	public ClanarinaKupac(String kupac, String clanarinaId, Date datumPlacanja, Date vaziDo, double placenaCena,
			StatusClanarine status, String brojPreostalihTermina) {
		super();
		this.kupac = kupac;
		this.clanarinaId = clanarinaId;
		this.datumPlacanja = datumPlacanja;
		this.vaziDo = vaziDo;
		this.placenaCena = placenaCena;
		this.status = status;
		this.brojPreostalihTermina = brojPreostalihTermina;
	}

	public String getKupac() {
		return kupac;
	}
	public void setKupac(String kupac) {
		this.kupac = kupac;
	}
	public String getClanarinaId() {
		return clanarinaId;
	}
	public void setClanarinaId(String clanarinaId) {
		this.clanarinaId = clanarinaId;
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
	public double getPlacenaCena() {
		return placenaCena;
	}
	public void setPlacenaCena(double placenaCena) {
		this.placenaCena = placenaCena;
	}
	public Clanarina.StatusClanarine getStatus() {
		return status;
	}
	public void setStatus(Clanarina.StatusClanarine status) {
		this.status = status;
	}
	public String getBrojPreostalihTermina() {
		return brojPreostalihTermina;
	}
	public void setBrojPreostalihTermina(String brojPreostalihTermina) {
		this.brojPreostalihTermina = brojPreostalihTermina;
	}
	
	
	
}
