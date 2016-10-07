package com.hastatakip.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.hastatakip.model.type.Cinsiyet;

@Entity
public class MalzemeGirisBilgileri implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String malzemeAdi;
	private String malzemeBirimi;
	private String malzemeKarti;
	private Integer stok;
	private Integer girisMiktari;
	private Integer cikisMiktari;
	
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMalzemeAdi() {
		return malzemeAdi;
	}
	public void setMalzemeAdi(String malzemeAdi) {
		this.malzemeAdi = malzemeAdi;
	}
	public String getMalzemeBirimi() {
		return malzemeBirimi;
	}
	public void setMalzemeBirimi(String malzemeBirimi) {
		this.malzemeBirimi = malzemeBirimi;
	}
	public String getMalzemeKarti() {
		return malzemeKarti;
	}
	public void setMalzemeKarti(String malzemeKarti) {
		this.malzemeKarti = malzemeKarti;
	}

	public Integer getStok() {
		return stok;
	}
	public void setStok(Integer stok) {
		this.stok = stok;
	}
	public Integer getGirisMiktari() {
		return girisMiktari;
	}
	public void setGirisMiktari(Integer girisMiktari) {
		this.girisMiktari = girisMiktari;
	}
	public Integer getCikisMiktari() {
		return cikisMiktari;
	}
	public void setCikisMiktari(Integer cikisMiktari) {
		this.cikisMiktari = cikisMiktari;
	}



	
	
	

}
