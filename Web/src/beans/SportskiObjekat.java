package beans;

import java.awt.Image;

enum TipObjekta{Teretana, Bazen, SportskiCentar, PlesniStudio}
enum StatusObjekta{Radi, NeRadi}

public class SportskiObjekat {

	private String naziv;
	private TipObjekta tipObjekta;
	//sadrzaj objekta
	private StatusObjekta statusObjekta;
	private Lokacija lokacija;
	private Image logo;
	private double prosecnaOcena;
	//raadno vreme
	
}
