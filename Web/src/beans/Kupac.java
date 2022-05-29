package beans;

import java.util.ArrayList;
import java.util.List;

import beans.TipKupca.NazivTipaKupca;


public class Kupac extends Korisnik {
	
	private String clanarinaId;
	private List<SportskiObjekat> poseceniObjekti;
	private double brojSakupljenihBodova;
	private NazivTipaKupca tipKupca;
	
	public Kupac() {
		super();
		this.setUloga(Uloga.KUPAC);
		this.setClanarina("1");
		this.setBrojSakupljenihBodova(0);
		this.setPoseceniObjekti(new ArrayList<SportskiObjekat>());
		this.setTipKupca(NazivTipaKupca.BRONZANI);
	}
	
	public Kupac(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String datumRodjenja) {
		
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, Uloga.KUPAC);
		this.clanarinaId = "1";
		this.poseceniObjekti = new ArrayList<SportskiObjekat>();
		this.brojSakupljenihBodova = 0;
		this.tipKupca = NazivTipaKupca.BRONZANI;
	}


	public String getClanarina() {
		return clanarinaId;
	}

	public void setClanarina(String clanarina) {
		this.clanarinaId = clanarina;
	}

	public List<SportskiObjekat> getPoseceniObjekti() {
		return poseceniObjekti;
	}

	public void setPoseceniObjekti(List<SportskiObjekat> poseceniObjekti) {
		this.poseceniObjekti = poseceniObjekti;
	}

	public double getBrojSakupljenihBodova() {
		return brojSakupljenihBodova;
	}

	public void setBrojSakupljenihBodova(double brojSakupljenihBodova) {
		this.brojSakupljenihBodova = brojSakupljenihBodova;
	}

	public NazivTipaKupca getTipKupca() {
		return tipKupca;
	}

	public void setTipKupca(NazivTipaKupca tipKupca) {
		this.tipKupca = tipKupca;
	}

	
	
}
