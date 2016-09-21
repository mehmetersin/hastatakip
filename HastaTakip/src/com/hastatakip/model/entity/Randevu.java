package com.hastatakip.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Randevu {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne
	private Hasta hasta;
	
	@ManyToOne
	private MuayeneAltTipi mat;
	
	private Date baslangicTarihi;
	private Date bitisTarihi;

	private String randevuBilgileri;

	private BigDecimal ucreti;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Hasta getHasta() {
		return hasta;
	}

	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}

	public Date getBaslangicTarihi() {
		return baslangicTarihi;
	}

	public void setBaslangicTarihi(Date baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}

	public Date getBitisTarihi() {
		return bitisTarihi;
	}

	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}

	public String getRandevuBilgileri() {
		return randevuBilgileri;
	}

	public void setRandevuBilgileri(String randevuBilgileri) {
		this.randevuBilgileri = randevuBilgileri;
	}

	public BigDecimal getUcreti() {
		return ucreti;
	}

	public void setUcreti(BigDecimal ucreti) {
		this.ucreti = ucreti;
	}

	public MuayeneAltTipi getMat() {
		return mat;
	}

	public void setMat(MuayeneAltTipi mat) {
		this.mat = mat;
	}

	
	
	

}
