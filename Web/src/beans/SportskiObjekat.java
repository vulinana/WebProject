package beans;

import beans.RadnoVreme.Dan;


public class SportskiObjekat {

	public enum StatusObjekta{Radi, NeRadi}
	public enum TipObjekta{Teretana, Bazen, SportskiCentar, PlesniStudio}
	
	private String naziv;
	private TipObjekta tipObjekta;
	//sadrzaj objekta
	private StatusObjekta statusObjekta;
	private Lokacija lokacija;
	private String logo;
	private double prosecnaOcena;
	private RadnoVreme radnoVreme;
	
	public SportskiObjekat() {}
	
	public SportskiObjekat(String naziv, TipObjekta tipObjekta, Lokacija lokacija,
			String logo, double prosecnaOcena, RadnoVreme radnoVreme) {
		super();
		this.naziv = naziv;
		this.tipObjekta = tipObjekta;
		this.lokacija = lokacija;
		this.logo = logo;
		this.prosecnaOcena = prosecnaOcena;
		this.radnoVreme = radnoVreme;
		this.statusObjekta = odrediStatus();
	}

	public StatusObjekta odrediStatus() {
		
		StatusObjekta status = statusObjekta.NeRadi;
		java.util.Date currentDate = new java.util.Date();   
	    
		for (Dan d: radnoVreme.getDani()) {
			
			if (d.ordinal() == currentDate.getDay() && radnoVreme.getOdVreme() <= currentDate.getHours() && radnoVreme.getDoVreme() > currentDate.getHours()) {
				status = StatusObjekta.Radi;
			}
		}
		
		this.statusObjekta = status;
		return status;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public TipObjekta getTipObjekta() {
		return tipObjekta;
	}
	public void setTipObjekta(TipObjekta tipObjekta) {
		this.tipObjekta = tipObjekta;
	}
	public StatusObjekta getStatusObjekta() {
		return statusObjekta;
	}
	public void setStatusObjekta(StatusObjekta statusObjekta) {
		this.statusObjekta = statusObjekta;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public double getProsecnaOcena() {
		return prosecnaOcena;
	}
	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public RadnoVreme getRadnoVreme() {
		return radnoVreme;
	}

	public void setRadnoVreme(RadnoVreme radnoVreme) {
		this.radnoVreme = radnoVreme;
	}
	
	
}
