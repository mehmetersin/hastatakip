package com.hastatakip.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DepoBilgileri implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private Integer miktar;
	@Temporal(TemporalType.DATE)
	private Date islemTarihi;
	private String giriscikis;
	private Integer totalMiktar;
	
	
	@ManyToOne
	private FirmaBilgileri firmaBilgileri;
	
	@ManyToOne
	private MalzemeGirisBilgileri malzemeBilgileri;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMiktar() {
		return miktar;
	}
	public void setMiktar(Integer miktar) {
		this.miktar = miktar;
	}
	public Date getIslemTarihi() {
		return islemTarihi;
	}
	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}
	public String getGiriscikis() {
		return giriscikis;
	}
	public void setGiriscikis(String giriscikis) {
		this.giriscikis = giriscikis;
	}
	public FirmaBilgileri getFirmaBilgileri() {
		return firmaBilgileri;
	}
	public void setFirmaBilgileri(FirmaBilgileri firmaBilgileri) {
		this.firmaBilgileri = firmaBilgileri;
	}
	public MalzemeGirisBilgileri getMalzemeBilgileri() {
		return malzemeBilgileri;
	}
	public void setMalzemeBilgileri(MalzemeGirisBilgileri malzemeBilgileri) {
		this.malzemeBilgileri = malzemeBilgileri;
	}
	public Integer getTotalMiktar() {
		return totalMiktar;
	}
	public void setTotalMiktar(Integer totalMiktar) {
		this.totalMiktar = totalMiktar;
	}

	
	

}
