package com.hastatakip.model.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class MuhasebeBilgileri {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String islemGirisi;
	
	private BigDecimal ucreti;
	
	@Temporal(TemporalType.DATE)
	private Date islemTarihi;
	
	@ManyToOne
	private Hasta hasta;
	
	@Transient
	private String dateFilter;
	
	

	public String getDateFilter() {
		return new SimpleDateFormat("dd/MM/yyyy").format(islemTarihi);
	}

	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIslemGirisi() {
		return islemGirisi;
	}

	public void setIslemGirisi(String islemGirisi) {
		this.islemGirisi = islemGirisi;
	}

	public BigDecimal getUcreti() {
		return ucreti;
	}

	public void setUcreti(BigDecimal ucreti) {
		this.ucreti = ucreti;
	}



	public Date getIslemTarihi() {
		return islemTarihi;
	}

	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}

	public Hasta getHasta() {
		return hasta;
	}

	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}
	
	
	
	
	
	

}
