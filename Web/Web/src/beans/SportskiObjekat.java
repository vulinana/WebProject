package beans;

enum TipObjekta{Teretana, Bazen, SportskiCentar, PlesniStudio}
enum StatusObjekta{Radi, NeRadi}

public class SportskiObjekat {

	private String naziv;
	private TipObjekta tipObjekta;
	//sadrzaj objekta
	private StatusObjekta statusObjekta;
	private Lokacija lokacija;
	//logo-slika
	private double prosecnaOcena;
	//raadno vreme
	
}
