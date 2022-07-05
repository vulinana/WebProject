package beans;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import beans.SportskiObjekat.TipObjekta;
import beans.Trening.TipTreninga;

public class TerminTreninga {
	
	private UUID id;
	private String trener;
	private String sportskiObjekat;
	private TipObjekta tipObjekta;
	private String nazivTreninga;
	private TipTreninga tipTreninga;
	private double trajanje;
	private double cenaTreninga;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date datumIVreme;
	
	public TerminTreninga() {}
	
	public TerminTreninga(String trener, String sportskiObjekat, String nazivTreninga,
			TipTreninga tipTreniga, double trajanje, double cenaTreninga) {
		super();
		this.trener = trener;
		this.sportskiObjekat = sportskiObjekat;
		this.nazivTreninga = nazivTreninga;
		this.tipTreninga = tipTreniga;
		this.cenaTreninga = cenaTreninga;
	}
	
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTrener() {
		return trener;
	}

	public void setTrener(String trener) {
		this.trener = trener;
	}

	public String getSportskiObjekat() {
		return sportskiObjekat;
	}

	public void setSportskiObjekat(String sportskiObjekat) {
		this.sportskiObjekat = sportskiObjekat;
	}

	public TipObjekta getTipObjekta() {
		return tipObjekta;
	}

	public void setTipObjekta(TipObjekta tipObjekta) {
		this.tipObjekta = tipObjekta;
	}

	public String getNazivTreninga() {
		return nazivTreninga;
	}

	public void setNazivTreninga(String nazivTreninga) {
		this.nazivTreninga = nazivTreninga;
	}

	public TipTreninga getTipTreniga() {
		return tipTreninga;
	}

	public void setTipTreniga(TipTreninga tipTreninga) {
		this.tipTreninga = tipTreninga;
	}

	public double getCenaTreninga() {
		return cenaTreninga;
	}

	public void setCenaTreninga(double cenaTreninga) {
		this.cenaTreninga = cenaTreninga;
	}

	public Date getDatumIVreme() {
		return datumIVreme;
	}

	public void setDatumIVreme(Date datumIVreme) {
		this.datumIVreme = datumIVreme;
	}

	public double getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(double trajanje) {
		this.trajanje = trajanje;
	}
	
	
	
	
	
}
