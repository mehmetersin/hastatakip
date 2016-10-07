package com.hastatakip.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MuayeneBilgiDegeri {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String degeri;
	
	@ManyToOne
	MuayeneBilgileri muayeneBilgileri;
	
	@ManyToOne
	MuayeneTipi muayeneTipi;
	
	@ManyToOne
	MuayeneAltTipi muayeneAltTipi;
	
	@ManyToOne
	Randevu randevu;
	
	@ManyToOne
	Hasta hasta;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDegeri() {
		return degeri;
	}
	public void setDegeri(String degeri) {
		this.degeri = degeri;
	}
	public MuayeneBilgileri getMuayeneBilgileri() {
		return muayeneBilgileri;
	}
	public void setMuayeneBilgileri(MuayeneBilgileri muayeneBilgileri) {
		this.muayeneBilgileri = muayeneBilgileri;
	}
	public MuayeneTipi getMuayeneTipi() {
		return muayeneTipi;
	}
	public void setMuayeneTipi(MuayeneTipi muayeneTipi) {
		this.muayeneTipi = muayeneTipi;
	}
	public MuayeneAltTipi getMuayeneAltTipi() {
		return muayeneAltTipi;
	}
	public void setMuayeneAltTipi(MuayeneAltTipi muayeneAltTipi) {
		this.muayeneAltTipi = muayeneAltTipi;
	}
	public Randevu getRandevu() {
		return randevu;
	}
	public void setRandevu(Randevu randevu) {
		this.randevu = randevu;
	}
	public Hasta getHasta() {
		return hasta;
	}
	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}
	
	
}
