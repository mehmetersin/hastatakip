package com.hastatakip.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FirmaBilgileri implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String firmaAdi;
	private String firmaTel;
	private String firmaAdres;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirmaAdi() {
		return firmaAdi;
	}
	public void setFirmaAdi(String firmaAdi) {
		this.firmaAdi = firmaAdi;
	}
	public String getFirmaTel() {
		return firmaTel;
	}
	public void setFirmaTel(String firmaTel) {
		this.firmaTel = firmaTel;
	}
	public String getFirmaAdres() {
		return firmaAdres;
	}
	public void setFirmaAdres(String firmaAdres) {
		this.firmaAdres = firmaAdres;
	}
	
	
}
