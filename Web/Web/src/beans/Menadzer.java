package beans;


public class Menadzer extends Korisnik{

	private SportskiObjekat sportskiObjekat;

	public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String datumRodjenja, SportskiObjekat sportskiObjekat) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, Uloga.MENADZER);
		this.sportskiObjekat = sportskiObjekat;
	}

	public SportskiObjekat getSportskiObjekat() {
		return sportskiObjekat;
	}

	public void setSportskiObjekat(SportskiObjekat sportskiObjekat) {
		this.sportskiObjekat = sportskiObjekat;
	}
	
	
}
