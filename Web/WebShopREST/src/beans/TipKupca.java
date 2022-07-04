package beans;

public class TipKupca {
	
	public enum NazivTipaKupca{BRONZANI, SREBRNI, ZLATNI}
	
	private NazivTipaKupca imeTipa;
	private double popust;
	private double brojBodova;
	
	public TipKupca() {}
	
	public TipKupca(NazivTipaKupca imeTipa, double popust, double brojBodova) {
		super();
		this.imeTipa = imeTipa;
		this.popust = popust;
		this.brojBodova = brojBodova;
	}
	public NazivTipaKupca getImeTipa() {
		return imeTipa;
	}
	public void setImeTipa(NazivTipaKupca imeTipa) {
		this.imeTipa = imeTipa;
	}
	public double getPopust() {
		return popust;
	}
	public void setPopust(double popust) {
		this.popust = popust;
	}
	public double getBrojBodova() {
		return brojBodova;
	}
	public void setBrojBodova(double brojBodova) {
		this.brojBodova = brojBodova;
	}
	
	
	
}
