package beans;

import java.util.UUID;

public class Komentar {
	
	private UUID id;
	private String kupac;
	private String sportskiObjekat;
	private String tekstKomentara;
	private int ocena;
	
	public Komentar() {}
	
	public Komentar(String kupac, String sportskiObjekat, String tekstKomentara, int ocena) {
		super();
		this.id = UUID.randomUUID();
		this.kupac = kupac;
		this.sportskiObjekat = sportskiObjekat;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
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
	
	
	
}
