package beans;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IstorijaTreninga {
	
	private UUID id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date datumIVremePrijave;
	private SportskiObjekat sportskiObjekat;
	private Trening trening;
	private String kupac;
	private String trener;
	
	public IstorijaTreninga() {}
	
	public IstorijaTreninga(Date datumIVremePrijave, SportskiObjekat sportskiObjekat, Trening trening, String kupac, String trener) {
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

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
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

	public SportskiObjekat getSportskiObjekat() {
		return sportskiObjekat;
	}

	public void setSportskiObjekat(SportskiObjekat sportskiObjekat) {
		this.sportskiObjekat = sportskiObjekat;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
