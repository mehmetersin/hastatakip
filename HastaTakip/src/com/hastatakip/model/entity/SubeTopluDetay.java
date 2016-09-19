package com.hastatakip.model.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hastatakip.model.type.HareketTipi;

@Entity
public class SubeTopluDetay {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String bolgeadi;

	private String sapKodu;
	private String subeAdi;

	private String kasiyerno;
	private String islemtarihi;
	private String kasano;
	private BigDecimal tutar;
	private String hareketTipi;

	public String getKasiyerno() {
		return kasiyerno;
	}

	public void setKasiyerno(String kasiyerno) {
		this.kasiyerno = kasiyerno;
	}

	public String getIslemtarihi() {
		return islemtarihi;
	}

	public void setIslemtarihi(String islemtarihi) {
		this.islemtarihi = islemtarihi;
	}

	public String getKasano() {
		return kasano;
	}

	public void setKasano(String kasano) {
		this.kasano = kasano;
	}


	public BigDecimal getTutar() {
		return tutar;
	}

	public void setTutar(BigDecimal tutar) {
		this.tutar = tutar;
	}

	public String getHareketTipi() {
		return hareketTipi;
	}

	public void setHareketTipi(String hareketTipi) {
		this.hareketTipi = hareketTipi;
	}

	public String getBolgeadi() {
		return bolgeadi;
	}

	public void setBolgeadi(String bolgeadi) {
		this.bolgeadi = bolgeadi;
	}

	public String getSapKodu() {
		return sapKodu;
	}

	public void setSapKodu(String sapKodu) {
		this.sapKodu = sapKodu;
	}

	public String getSubeAdi() {
		return subeAdi;
	}

	public void setSubeAdi(String subeAdi) {
		this.subeAdi = subeAdi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
