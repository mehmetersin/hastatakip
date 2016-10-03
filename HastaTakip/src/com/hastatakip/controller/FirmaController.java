package com.hastatakip.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.hastatakip.dao.FirmaDao;
import com.hastatakip.model.entity.FirmaBilgileri;

@ManagedBean(name = "firmaController")
public class FirmaController implements Serializable {
	
	@EJB
	FirmaDao firmaDao;
	
	private FirmaBilgileri fb;
	private List<FirmaBilgileri> allFirmaBilgileri;
	private List<FirmaBilgileri> filteredAllFirmaBilgileri;
	
	
	
	
	
	public void setAllFirmaBilgileri(List<FirmaBilgileri> allFirmaBilgileri) {
		this.allFirmaBilgileri = allFirmaBilgileri;
	}
	public FirmaDao getFirmaDao() {
		return firmaDao;
	}
	public void setFirmaDao(FirmaDao firmaDao) {
		this.firmaDao = firmaDao;
	}
	public List<FirmaBilgileri> getFilteredAllFirmaBilgileri() {
		return filteredAllFirmaBilgileri;
	}
	public void setFilteredAllFirmaBilgileri(List<FirmaBilgileri> filteredAllFirmaBilgileri) {
		this.filteredAllFirmaBilgileri = filteredAllFirmaBilgileri;
	}
	public FirmaBilgileri getFb() {
		return fb;
	}
	public void setFb(FirmaBilgileri fb) {
		this.fb = fb;
	}
	
	public String goToPage() {
		return "both/" + "firmaIslem" + "?faces-redirect=true";
	}
	
	public List<FirmaBilgileri> getAllFirmaBilgileri() {

		return allFirmaBilgileri;

	}
	
	@PostConstruct
	public void init() {
		try {
		
			fb = new FirmaBilgileri();
			allFirmaBilgileri = firmaDao.getAllFirma();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void init(FirmaBilgileri fblgi) {
		try {

			if (fblgi == null) {
				fb = new FirmaBilgileri();
			} else {
				fb = fblgi;
			}
			allFirmaBilgileri = firmaDao.getAllFirma();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void addFirmaBilgileri() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {
			
			FirmaBilgileri frmb = null;
			
			if (fb.getId() != null) {
				frmb = firmaDao.getFirma(fb.getId());
			}

			if (frmb != null) {
				firmaDao.merge(fb);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Firma Bilgileri Başar�yla G�ncellendi."));
			} else {
				firmaDao.insert(fb);
				context.addMessage(null, new FacesMessage("Uyar�",
						"Firma Bilgileri Ba�ar�yla Kaydedildi."));
			}

			fb = new FirmaBilgileri();
			init(null);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"��leminiz yap�lamad�."));
			return;
		}

	}
	
	public void onRowCancel(RowEditEvent event) {

		try {
			FirmaBilgileri fblgi = ((FirmaBilgileri) event.getObject());

			FirmaBilgileri fblgi2 = firmaDao.getFirma(fblgi.getId());
			

			boolean result = firmaDao.delete(fblgi2);
			String message = "";
			if (result) {
				message = "Başarılı";
			} else {
				message = "Başarısız";
			}

			FacesMessage msg = new FacesMessage("Bilgiler Silindi", message);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);
		} catch (Exception e) {
			e.printStackTrace();
		

			FacesMessage msg = new FacesMessage("Silme İşleminde",
					"Hata Oluştu");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);

		}

	}

	public void onRowEdit(RowEditEvent event) {

		FirmaBilgileri fblgi = ((FirmaBilgileri) event.getObject());

		
		
		firmaDao.merge(fblgi);

		FacesMessage msg = new FacesMessage("Bilgiler Güncellendi",
				"Başarılı");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init(null);
	}
	

}
