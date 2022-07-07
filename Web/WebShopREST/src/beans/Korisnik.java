package beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Korisnik {

	public enum Uloga {ADMINISTRATOR, MENADZER, TRENER, KUPAC}
	public enum Pol {MUSKO, ZENSKO}
	
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private Pol pol;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date datumRodjenja;
	private Uloga uloga;
	private boolean izbrisan;
	
	public Korisnik() {
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
			Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = Pol.valueOf(pol);
		this.datumRodjenja = datumRodjenja;
		this.uloga = uloga;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public boolean isIzbrisan() {
		return izbrisan;
	}

	public void setIzbrisan(boolean izbrisan) {
		this.izbrisan = izbrisan;
	}
	
}
