package com.hastatakip.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MuayeneAltTipi {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String adi;

	@ManyToOne
	private MuayeneTipi tipi;

	public MuayeneTipi getTipi() {
		return tipi;
	}

	public void setTipi(MuayeneTipi tipi) {
		this.tipi = tipi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

}
