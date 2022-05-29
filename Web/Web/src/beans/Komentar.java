package beans;


public class Komentar {
	
	private Kupac kupac;
	private SportskiObjekat sportskiObjekat;
	private String tekstKomentara;
	private int ocena;
	
	public Komentar(Kupac kupac, SportskiObjekat sportskiObjekat, String tekstKomentara, int ocena) {
		super();
		this.kupac = kupac;
		this.sportskiObjekat = sportskiObjekat;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public SportskiObjekat getSportskiObjekat() {
		return sportskiObjekat;
	}

	public void setSportskiObjekat(SportskiObjekat sportskiObjekat) {
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
	
	
}
