package com.hastatakip.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hastatakip.model.type.Durum;
import com.hastatakip.model.type.EventType;

@Entity
public class EventLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@ManyToOne
	private Sube sube;
	@Temporal(TemporalType.TIMESTAMP)
	private Date baslangicTarih = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date bitisTarih;
	private String fileName;
	@ManyToOne
	private Kullanici kullanici;
	@Enumerated(EnumType.STRING)
	private Durum durum;
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	private String aciklama;

	private Date dosyaTarihi;

	public Date getDosyaTarihi() {
		return dosyaTarihi;
	}

	public void setDosyaTarihi(Date dosyaTarihi) {
		this.dosyaTarihi = dosyaTarihi;
	}

	public Integer getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Sube getSube() {
		return sube;
	}

	public Date getBaslangicTarih() {
		return baslangicTarih;
	}

	public Date getBitisTarih() {
		return bitisTarih;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public Durum getDurum() {
		return durum;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSube(Sube sub) {
		this.sube = sub;
	}

	public void setBaslangicTarih(Date baslangicTarih) {
		this.baslangicTarih = baslangicTarih;
	}

	public void setBitisTarih(Date bitisTarih) {
		this.bitisTarih = bitisTarih;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public void setDurum(Durum durum) {
		this.durum = durum;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	
	public boolean isRetry(){
		if (durum == Durum.BASARISIZ && eventType == EventType.DosyaTransfer){
			return false;
		}else{
			return true;
		}
	}

}
