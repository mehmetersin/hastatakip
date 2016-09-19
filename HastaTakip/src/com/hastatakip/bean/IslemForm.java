package com.hastatakip.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "islemForm")
@SessionScoped
public class IslemForm {

	private Date islemTarihi;
	private String subeId;

	public Date getIslemTarihi() {
		return islemTarihi;
	}

	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}

	public String getSubeId() {
		return subeId;
	}

	public void setSubeId(String subeId) {
		this.subeId = subeId;
	}

}
