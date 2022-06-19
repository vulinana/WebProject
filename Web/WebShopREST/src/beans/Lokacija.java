package beans;

public class Lokacija {
	
	private double geografskaSirina;
	private double geografskaDuzina;
	private Adresa adresa;
	
	public Lokacija() {}
	
	public Lokacija(double geografskaSirina, double geografskaDuzina, Adresa adresa) {
		super();
		this.geografskaSirina = geografskaSirina;
		this.geografskaDuzina = geografskaDuzina;
		this.adresa = adresa;
	}

	public double getGeografskaSirina() {
		return geografskaSirina;
	}

	public void setGeografskaSirina(double geografskaSirina) {
		this.geografskaSirina = geografskaSirina;
	}

	public double getGeografskaDuzina() {
		return geografskaDuzina;
	}

	public void setGeografskaDuzina(double geografskaDuzina) {
		this.geografskaDuzina = geografskaDuzina;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
	
}
