package beans;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PromoKod {
	
	private UUID id;
	private String oznaka;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date vaziOd;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date vaziDo;
	private int brojIskoriscavanja;
	private double procenatUmanjenjaClanarine;
	private boolean izbrisan;
	
	public PromoKod() {}
	
	
	public PromoKod(String oznaka, Date vaziOd, Date vaziDo, int brojIskoriscavanja,
			double procenatUmanjenjaClanarine) {
		super();
		this.oznaka = oznaka;
		this.vaziOd = vaziOd;
		this.vaziDo = vaziDo;
		this.brojIskoriscavanja = brojIskoriscavanja;
		this.procenatUmanjenjaClanarine = procenatUmanjenjaClanarine;
	}

	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	public Date getVaziOd() {
		return vaziOd;
	}
	public void setVaziOd(Date vaziOd) {
		this.vaziOd = vaziOd;
	}
	public Date getVaziDo() {
		return vaziDo;
	}
	public void setVaziDo(Date vaziDo) {
		this.vaziDo = vaziDo;
	}
	public int getBrojIskoriscavanja() {
		return brojIskoriscavanja;
	}
	public void setBrojIskoriscavanja(int brojIskoriscavanja) {
		this.brojIskoriscavanja = brojIskoriscavanja;
	}
	public double getProcenatUmanjenjaClanarine() {
		return procenatUmanjenjaClanarine;
	}
	public void setProcenatUmanjenjaClanarine(double procenatUmanjenjaClanarine) {
		this.procenatUmanjenjaClanarine = procenatUmanjenjaClanarine;
	}

	public boolean isIzbrisan() {
		return izbrisan;
	}

	public void setIzbrisan(boolean izbrisan) {
		this.izbrisan = izbrisan;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}
	
}
