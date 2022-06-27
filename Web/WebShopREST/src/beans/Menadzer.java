package beans;

import java.util.Date;

public class Menadzer extends Korisnik{

	private String sportskiObjekat;

	public Menadzer() {}
	
	public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja, String sportskiObjekat) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, Uloga.MENADZER);
		this.sportskiObjekat = sportskiObjekat;
	}

	public String getSportskiObjekat() {
		return sportskiObjekat;
	}

	public void setSportskiObjekat(String sportskiObjekat) {
		this.sportskiObjekat = sportskiObjekat;
	}
	
	
}
