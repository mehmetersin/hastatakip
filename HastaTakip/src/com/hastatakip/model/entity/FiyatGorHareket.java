package com.hastatakip.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FiyatGorHareket {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@ManyToOne
	private Sube sube;

	private String kasiyerNo;
	private String kasaNo;
	private String islemNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date tarih;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
	private String barkod;

	public FiyatGorHareket(Hareket hareket) {
		setSube(hareket.getSube());
		setKasiyerNo(hareket.getKasiyerNo());
		setKasaNo(hareket.getKasaNo());
		setIslemNo(hareket.getIslemNo());
		setTarih(hareket.getTarih());
		setCreateDate(hareket.getCreateDate());
		setBarkod(hareket.getBarkod());
	}

	public FiyatGorHareket() {
	}

	public Integer getId() {
		return id;
	}

	public Sube getSube() {
		return sube;
	}

	public String getKasiyerNo() {
		return kasiyerNo;
	}

	public String getKasaNo() {
		return kasaNo;
	}

	public String getIslemNo() {
		return islemNo;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSube(Sube sube) {
		this.sube = sube;
	}

	public void setKasiyerNo(String kasiyerNo) {
		this.kasiyerNo = kasiyerNo;
	}

	public void setKasaNo(String kasaNo) {
		this.kasaNo = kasaNo;
	}

	public void setIslemNo(String islemNo) {
		this.islemNo = islemNo;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBarkod() {
		return barkod;
	}

	public void setBarkod(String barkod) {
		this.barkod = barkod;
	}

}
