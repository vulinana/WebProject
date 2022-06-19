package beans;

public class Adresa {
	
	private String ulicaIBroj;
	private String mesto;
	private int postanskiBroj;
	
	public Adresa() {}
	
	public Adresa(String ulicaIBroj, String mesto, int postanskiBroj) {
		super();
		this.ulicaIBroj = ulicaIBroj;
		this.mesto = mesto;
		this.postanskiBroj = postanskiBroj;
	}

	public String getUlicaIBroj() {
		return ulicaIBroj;
	}

	public void setUlicaIBroj(String ulicaIBroj) {
		this.ulicaIBroj = ulicaIBroj;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public int getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(int postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}
	
	
	
}
