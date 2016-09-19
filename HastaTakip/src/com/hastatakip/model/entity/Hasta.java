package com.hastatakip.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hastatakip.model.type.Cinsiyet;
import com.hastatakip.model.type.HareketTipi;
import com.hastatakip.model.type.KanGrubu;
import com.hastatakip.model.type.MedeniDurum;

@Entity
public class Hasta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer dosyaNo;
	

	@Enumerated(EnumType.STRING)
	private Cinsiyet cinsiyet;

	@Enumerated(EnumType.STRING)
	private KanGrubu kanGrubu;

	@Enumerated(EnumType.STRING)
	private MedeniDurum medeniDurum;

	@ManyToOne
	private Sehir sehir;

	private String dogumYeri;
	private String adSoyad;
	private String tcNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dogumTarihi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();

	private String babaAdi;
	private String anneAdi;
	private String adres;

	private String telefonNo;

	public Integer getDosyaNo() {
		return dosyaNo;
	}

	public void setDosyaNo(Integer dosyaNo) {
		this.dosyaNo = dosyaNo;
	}


	public Cinsiyet getCinsiyet() {
		return cinsiyet;
	}

	public void setCinsiyet(Cinsiyet cinsiyet) {
		this.cinsiyet = cinsiyet;
	}

	public KanGrubu getKanGrubu() {
		return kanGrubu;
	}

	public void setKanGrubu(KanGrubu kanGrubu) {
		this.kanGrubu = kanGrubu;
	}

	public MedeniDurum getMedeniDurum() {
		return medeniDurum;
	}

	public void setMedeniDurum(MedeniDurum medeniDurum) {
		this.medeniDurum = medeniDurum;
	}

	public Sehir getSehir() {
		return sehir;
	}

	public void setSehir(Sehir sehir) {
		this.sehir = sehir;
	}

	public String getDogumYeri() {
		return dogumYeri;
	}

	public void setDogumYeri(String dogumYeri) {
		this.dogumYeri = dogumYeri;
	}

	public String getAdSoyad() {
		return adSoyad;
	}

	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}

	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public Date getDogumTarihi() {
		return dogumTarihi;
	}

	public void setDogumTarihi(Date dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBabaAdi() {
		return babaAdi;
	}

	public void setBabaAdi(String babaAdi) {
		this.babaAdi = babaAdi;
	}

	public String getAnneAdi() {
		return anneAdi;
	}

	public void setAnneAdi(String anneAdi) {
		this.anneAdi = anneAdi;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(String telefonNo) {
		this.telefonNo = telefonNo;
	}



}
