package beans;

import java.util.ArrayList;
import java.util.List;

public class Trener extends Korisnik {
	
	private List<IstorijaTreninga> istorijaTreninga;

	public Trener() {}
	
	public Trener(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String datumRodjenja,
			Uloga uloga) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, Uloga.TRENER);
		this.istorijaTreninga = new ArrayList<IstorijaTreninga>();
	}

	public List<IstorijaTreninga> getIstorijaTreninga() {
		return istorijaTreninga;
	}

	public void setIstorijaTreninga(List<IstorijaTreninga> istorijaTreninga) {
		this.istorijaTreninga = istorijaTreninga;
	}
	
	
	
}
