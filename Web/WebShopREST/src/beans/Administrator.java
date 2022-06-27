package beans;

import java.util.Date;

public class Administrator extends Korisnik {

	public Administrator() {
		super();
	}
	
	public Administrator(String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, Uloga.ADMINISTRATOR);
	}
	
}
