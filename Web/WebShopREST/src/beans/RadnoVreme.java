package beans;

import java.util.List;

public class RadnoVreme {

	public enum Dan {Ponedeljak, Utorak, Sreda, Cetvrtak, Petak, Subota, Nedelja}

	private List<Dan> dani;
	private int odVreme;
	private int doVreme;
	
	public RadnoVreme() {}
	
	
	public RadnoVreme(List<Dan> dani, int odVreme, int doVreme) {
		super();
		this.dani = dani;
		this.odVreme = odVreme;
		this.doVreme = doVreme;
	}


	public List<Dan> getDani() {
		return dani;
	}


	public void setDani(List<Dan> dani) {
		this.dani = dani;
	}


	public int getOdVreme() {
		return odVreme;
	}


	public void setOdVreme(int odVreme) {
		this.odVreme = odVreme;
	}


	public int getDoVreme() {
		return doVreme;
	}


	public void setDoVreme(int doVreme) {
		this.doVreme = doVreme;
	}
	
	
}
