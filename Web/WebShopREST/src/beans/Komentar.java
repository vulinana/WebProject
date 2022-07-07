package beans;

import java.util.UUID;

public class Komentar {
	
	public enum StatusKomentara {PRIHVACEN, ODBIJEN, NERECENZIRAN}
	
	private UUID id;
	private String kupac;
	private String sportskiObjekat;
	private String tekstKomentara;
	private int ocena;
	private StatusKomentara statusKomentara;
	private boolean izbrisan;
	
	public Komentar() {}
	
	public Komentar(String kupac, String sportskiObjekat, String tekstKomentara, int ocena, StatusKomentara statusKomentara) {
		super();
		this.id = UUID.randomUUID();
		this.kupac = kupac;
		this.sportskiObjekat = sportskiObjekat;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
		this.statusKomentara = statusKomentara;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public String getSportskiObjekat() {
		return sportskiObjekat;
	}

	public void setSportskiObjekat(String sportskiObjekat) {
		this.sportskiObjekat = sportskiObjekat;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public StatusKomentara getStatusKomentara() {
		return statusKomentara;
	}

	public void setStatusKomentara(StatusKomentara statusKomentara) {
		this.statusKomentara = statusKomentara;
	}

	public boolean isIzbrisan() {
		return izbrisan;
	}

	public void setIzbrisan(boolean izbrisan) {
		this.izbrisan = izbrisan;
	}
	
	
}
