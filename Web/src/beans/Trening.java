package beans;

import java.awt.Image;

enum TipTreninga {Grupni, Personalni, Teretana}

public class Trening {
	
	private String naziv;
	private TipTreninga tip;
	private SportskiObjekat sportskiObjekatKomPripada;
	private double trajanje;
	private Trener trener;
	private String opis;
	private Image slika;
	
	public Trening(String naziv, TipTreninga tip, SportskiObjekat sportskiObjekatKomPripada, double trajanje,
			Trener trener, String opis, Image slika) {
		super();
		this.naziv = naziv;
		this.tip = tip;
		this.sportskiObjekatKomPripada = sportskiObjekatKomPripada;
		this.trajanje = trajanje;
		this.trener = trener;
		this.opis = opis;
		this.slika = slika;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipTreninga getTip() {
		return tip;
	}

	public void setTip(TipTreninga tip) {
		this.tip = tip;
	}

	public SportskiObjekat getSportskiObjekatKomPripada() {
		return sportskiObjekatKomPripada;
	}

	public void setSportskiObjekatKomPripada(SportskiObjekat sportskiObjekatKomPripada) {
		this.sportskiObjekatKomPripada = sportskiObjekatKomPripada;
	}

	public double getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(double trajanje) {
		this.trajanje = trajanje;
	}

	public Trener getTrener() {
		return trener;
	}

	public void setTrener(Trener trener) {
		this.trener = trener;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Image getSlika() {
		return slika;
	}

	public void setSlika(Image slika) {
		this.slika = slika;
	}
	
}
