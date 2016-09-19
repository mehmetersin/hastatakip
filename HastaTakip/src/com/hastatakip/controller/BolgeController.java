package com.hastatakip.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.hastatakip.dao.BolgeDao;
import com.hastatakip.model.entity.Sehir;

@ManagedBean(name = "bolgeController")
public class BolgeController implements Serializable {

	private Sehir bolge;

	private List<Sehir> allBolges;

	@EJB
	BolgeDao BolgeDao;

	
	
	
	public Sehir getBolge() {
		return bolge;
	}

	public void setBolge(Sehir Bolge) {
		this.bolge = Bolge;
	}

	public List<Sehir> getAllBolges() {
		return allBolges;
	}

	public void setAllBolges(List<Sehir> allBolges) {
		this.allBolges = allBolges;
	}


	@PostConstruct
	public void init() {
		try {
			bolge = new Sehir();
			allBolges = BolgeDao.getAllBolges();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	


	public String goToPage() {
		return "both/" + "bolgeIslem" + "?faces-redirect=true";
	}


	public void createBolge() {

		//TODO create işleminde türkçe karakter sorunu var
		
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			BolgeDao.insert(bolge);

			context.addMessage(null, new FacesMessage("Uyarı",
					"İşleminiz başarı ile tamamlandı."));

			bolge = new Sehir();
			init();

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"İşleminiz Yapılamadı."));
			return;
		}

	}

	

	public void onRowCancel(RowEditEvent event) {
		Sehir sub = ((Sehir) event.getObject());

		boolean result = BolgeDao.delete(sub);
		String message = "";
		if (result) {
			message = "Başarılı";
		} else {
			message = "Başarırız";
		}

		FacesMessage msg = new FacesMessage("Bolge Silindi", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		init();
	}

	public void onRowEdit(RowEditEvent event) {

		Sehir sub = ((Sehir) event.getObject());

		BolgeDao.merge(sub);

		FacesMessage msg = new FacesMessage("Bolge Bilgileri Güncellendi",
				"Başarılı");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init();
	}

	

}