package com.hastatakip.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.hastatakip.model.type.BaglantiTipi;

@Entity
public class Sube {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String subeAdi;
	@Enumerated(EnumType.STRING)
	private BaglantiTipi baglantiTipi;
	private String url;
	private String userName;
	private String pass;
	@OneToMany(mappedBy = "sube")
	private List<Hareket> harekets = new ArrayList<Hareket>();
	private String ftpDirectoryPath;
	private int ftpPort = 21;

	private String sapKodu;

	@ManyToOne(cascade = CascadeType.ALL)
	private Sehir bolge;

	public Sehir getBolge() {
		return bolge;
	}

	public void setBolge(Sehir bolge) {
		this.bolge = bolge;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpDirectoryPath() {
		return ftpDirectoryPath;
	}

	public void setFtpDirectoryPath(String ftpDirectoryPath) {
		this.ftpDirectoryPath = ftpDirectoryPath;
	}

	public Integer getId() {
		return id;
	}

	public String getSubeAdi() {
		return subeAdi;
	}

	public BaglantiTipi getBaglantiTipi() {
		return baglantiTipi;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPass() {
		return pass;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSubeAdi(String subeAdi) {
		this.subeAdi = subeAdi;
	}

	public void setBaglantiTipi(BaglantiTipi baglantiTipi) {
		this.baglantiTipi = baglantiTipi;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public List<Hareket> getHarekets() {
		return harekets;
	}

	public void setHarekets(List<Hareket> harekets) {
		this.harekets = harekets;
	}

	public String getSapKodu() {
		return sapKodu;
	}

	public void setSapKodu(String sapKodu) {
		this.sapKodu = sapKodu;
	}

	public String toString() {
		return "[Sube:Id:" + getId() + "-Name:" + getSubeAdi() + "]";
	}
}
