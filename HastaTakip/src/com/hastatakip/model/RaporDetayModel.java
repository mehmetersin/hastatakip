package com.hastatakip.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RaporDetayModel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String subeadi;
	private String iptaltarihi;
	private String hareketTipi;
	private String islemno;
	private String kasano;
	private String kasiyerno;
	private String barkod;
	private BigDecimal tutar;

	public String getKasano() {
		return kasano;
	}

	public void setKasaNo(String kasano) {
		this.kasano = kasano;
	}

	public String getKasiyerno() {
		return kasiyerno;
	}

	public void setKasiyerNo(String kasiyerno) {
		this.kasiyerno = kasiyerno;
	}

	public String getSubeadi() {
		return subeadi;
	}

	public void setSubeadi(String subeadi) {
		this.subeadi = subeadi;
	}

	public String getIptaltarihi() {
		return iptaltarihi;
	}

	public void setIptaltarihi(String iptaltarihi) {
		this.iptaltarihi = iptaltarihi;
	}

	public String getHareketTipi() {
		return hareketTipi;
	}

	public void setHareketTipi(String hareketTipi) {
		this.hareketTipi = hareketTipi;
	}

	public String getIslemno() {
		return islemno;
	}

	public void setIslemno(String islemno) {
		this.islemno = islemno;
	}

	public String getBarkod() {
		return barkod;
	}

	public void setBarkod(String barkod) {
		this.barkod = barkod;
	}

	public BigDecimal getTutar() {
		return tutar;
	}

	public void setTutar(BigDecimal tutar) {
		this.tutar = tutar;
	}
}