package beans;

import java.time.LocalDateTime;

public class IstorijaTreninga {
	
	private LocalDateTime datumIVremePrijave;
	private Trening trening;
	private Kupac kupac;
	private Trener trener;
	
	public IstorijaTreninga(LocalDateTime datumIVremePrijave, Trening trening, Kupac kupac, Trener trener) {
		super();
		this.datumIVremePrijave = datumIVremePrijave;
		this.trening = trening;
		this.kupac = kupac;
		this.trener = trener;
	}

	public LocalDateTime getDatumIVremePrijave() {
		return datumIVremePrijave;
	}

	public void setDatumIVremePrijave(LocalDateTime datumIVremePrijave) {
		this.datumIVremePrijave = datumIVremePrijave;
	}

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Trener getTrener() {
		return trener;
	}

	public void setTrener(Trener trener) {
		this.trener = trener;
	}
	
	
}
