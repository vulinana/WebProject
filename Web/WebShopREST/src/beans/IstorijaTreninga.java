package beans;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IstorijaTreninga {
	
	private UUID id;
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm")
	private Date datumIVremePrijave;
	private String sportskiObjekat;
	private String trening;
	private String kupac;
	private String trener;
	
	public IstorijaTreninga() {}
	
	public IstorijaTreninga(Date datumIVremePrijave, String sportskiObjekat, String trening, String kupac, String trener) {
		super();
		this.datumIVremePrijave = datumIVremePrijave;
		this.sportskiObjekat = sportskiObjekat;
		this.trening = trening;
		this.kupac = kupac;
		this.trener = trener;
	}

	public Date getDatumIVremePrijave() {
		return datumIVremePrijave;
	}

	public void setDatumIVremePrijave(Date datumIVremePrijave) {
		this.datumIVremePrijave = datumIVremePrijave;
	}

	public String getTrening() {
		return trening;
	}

	public void setTrening(String trening) {
		this.trening = trening;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
