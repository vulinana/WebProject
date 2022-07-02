package beans;

import java.util.ArrayList;
import java.util.Date;
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

	public Kupac(String korisnickoIme, String lozinka, String ime, String prezime, Pol pol, Date datumRodjenja, Uloga uloga) {
		super(korisnickoIme, lozinka, ime, prezime, pol.toString(), datumRodjenja, uloga);
		this.clanarinaId = null;
		this.poseceniObjekti = new ArrayList<SportskiObjekat>();
		this.brojSakupljenihBodova = 0;
		this.tipKupca = null;
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
	
	public void odrediTipKupca(){
		if(brojSakupljenihBodova < 3000) {
			tipKupca = NazivTipaKupca.BRONZANI;
		} else if (brojSakupljenihBodova >= 3000 && brojSakupljenihBodova < 4000) {
			tipKupca = NazivTipaKupca.SREBRNI;
		} else {
			tipKupca = NazivTipaKupca.ZLATNI;
		}
	}
	
	
}
