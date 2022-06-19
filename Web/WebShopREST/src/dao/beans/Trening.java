package beans;

import java.util.UUID;

enum TipTreninga {Grupni, Personalni, Teretana}

public class Trening {
	
	private UUID id;
	private String naziv;
	private TipTreninga tip;
	private String sportskiObjekatKomPripada;
	private double trajanje;
	private String trener;
	private String opis;
	private String slika;
	private double doplata;

	public Trening() {}
	
	public Trening(String naziv, TipTreninga tip, String sportskiObjekatKomPripada, double trajanje,
			String trener, String opis, String slika, double doplata) {
		super();
		this.id = UUID.randomUUID();
		this.naziv = naziv;
		this.tip = tip;
		this.sportskiObjekatKomPripada = sportskiObjekatKomPripada;
		this.trajanje = trajanje;
		this.trener = trener;
		this.opis = opis;
		this.slika = slika;
		this.doplata = doplata;
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

	public String getSportskiObjekatKomPripada() {
		return sportskiObjekatKomPripada;
	}

	public void setSportskiObjekatKomPripada(String sportskiObjekatKomPripada) {
		this.sportskiObjekatKomPripada = sportskiObjekatKomPripada;
	}

	public double getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(double trajanje) {
		this.trajanje = trajanje;
	}

	public String getTrener() {
		return trener;
	}

	public void setTrener(String trener) {
		this.trener = trener;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}
	
	public double getDoplata() {
		return doplata;
	}

	public void setDoplata(double doplata) {
		this.doplata = doplata;
	}

	public UUID getId() {
		return id;
	}

	public void setId() {
		this.id = UUID.randomUUID();
	}
	
	
}
