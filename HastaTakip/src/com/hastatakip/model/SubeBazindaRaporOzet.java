package com.hastatakip.model;

import java.math.BigDecimal;

public class SubeBazindaRaporOzet {
	private String subeadi;
	private String islemtarihi;
	private int fisiptaladet;

	private BigDecimal fisiptaltoplamtutar;
	private BigDecimal satiriptaltoplamtutar;
	private BigDecimal satirduzeltmetoplamtutar;

	private int kasaacmaadeti;
	private int satiriptaladeti;
	private int satirduzeltmeadeti;

	private String kasano;
	private String kasiyerno;

	private int count;

	private int countSum;

	private String bolgeName;
	private String sapKodu;

	public String getKasiyerno() {
		return kasiyerno;
	}

	public void setKasiyerno(String kasiyerno) {
		this.kasiyerno = kasiyerno;
	}

	public String getKasano() {
		return kasano;
	}

	public void setKasano(String kasano) {
		this.kasano = kasano;
	}

	public String getBolgeName() {
		return bolgeName;
	}

	public void setBolgeName(String bolgeName) {
		this.bolgeName = bolgeName;
	}

	public String getSapKodu() {
		return sapKodu;
	}

	public void setSapKodu(String sapKodu) {
		this.sapKodu = sapKodu;
	}

	public int getCountSum() {
		return countSum;
	}

	public void setCountSum(int countSum) {
		this.countSum = countSum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getSatirduzeltmetoplamtutar() {
		return satirduzeltmetoplamtutar;
	}

	public void setSatirduzeltmetoplamtutar(BigDecimal satirduzeltmetoplamtutar) {
		this.satirduzeltmetoplamtutar = satirduzeltmetoplamtutar;
	}

	public int getSatirduzeltmeadeti() {
		return satirduzeltmeadeti;
	}

	public void setSatirduzeltmeadeti(int satirduzeltmeadeti) {
		this.satirduzeltmeadeti = satirduzeltmeadeti;
	}

	public BigDecimal getSatiriptaltoplamtutar() {
		return satiriptaltoplamtutar;
	}

	public void setSatiriptaltoplamtutar(BigDecimal satiriptaltoplamtutar) {
		this.satiriptaltoplamtutar = satiriptaltoplamtutar;
	}

	public int getSatiriptaladeti() {
		return satiriptaladeti;
	}

	public void setSatiriptaladeti(int satiriptaladeti) {
		this.satiriptaladeti = satiriptaladeti;
	}

	public int getKasaacmaadeti() {
		return kasaacmaadeti;
	}

	public void setKasaacmaadeti(int kasaacmaadeti) {
		this.kasaacmaadeti = kasaacmaadeti;
	}

	public String getSubeadi() {
		return subeadi;
	}

	public void setSubeadi(String subeadi) {
		this.subeadi = subeadi;
	}

	public String getIslemtarihi() {
		return islemtarihi;
	}

	public void setIslemtarihi(String islemtarihi) {
		this.islemtarihi = islemtarihi;
	}

	public int getFisiptaladet() {
		return fisiptaladet;
	}

	public void setFisiptaladet(int fisiptaladet) {
		this.fisiptaladet = fisiptaladet;
	}

	public BigDecimal getFisiptaltoplamtutar() {
		return fisiptaltoplamtutar;
	}

	public void setFisiptaltoplamtutar(BigDecimal fisiptaltoplamtutar) {
		this.fisiptaltoplamtutar = fisiptaltoplamtutar;
	}

}