package beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PromoKod {
	
	private String oznaka;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date vaziOd;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date vaziDo;
	private int brojIskoriscavanja;
	private double procenatUmanjenjaClanarine;
	
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
	
	
}
