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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hastatakip.model.type.HareketTipi;

@Entity
public class Hareket {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@ManyToOne
	private Sube sube;
	
	
	@Enumerated(EnumType.STRING)
	private HareketTipi hareketTipi;
	private String kasiyerNo;
	private String kasaNo;
	private String islemNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date tarih;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate=new Date();
	private BigDecimal tutar;
	private String barkod;
	private Integer adet;
	
	
	

	public Integer getAdet() {
		return adet;
	}

	public void setAdet(Integer adet) {
		this.adet = adet;
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

	public BigDecimal getTutar() {
		return tutar;
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

	public void setTutar(BigDecimal tutar) {
		this.tutar = tutar;
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


	public HareketTipi getHareketTipi() {
		return hareketTipi;
	}


	public void setHareketTipi(HareketTipi i) {
		this.hareketTipi = i;
	}

	

}
