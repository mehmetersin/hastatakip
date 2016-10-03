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
import com.hastatakip.dao.MalzemeDao;
import com.hastatakip.model.entity.FirmaBilgileri;
import com.hastatakip.model.entity.MalzemeGirisBilgileri;

@ManagedBean(name = "malzemeController")
public class MalzemeController implements Serializable {
	
	@EJB
	MalzemeDao malzemeDao;
	
	@EJB
	FirmaDao firmaDao;
	
	private MalzemeGirisBilgileri mgb;
	private List<MalzemeGirisBilgileri> allMalzemeGirisBilgileri;
	private List<MalzemeGirisBilgileri> filteredAllMalzemeGirisBilgileri;
	private List<FirmaBilgileri> allFirmaBilgileri;
	private FirmaBilgileri firmaBilgileri;
	private Integer firmaID;
	
	
	
	
	public FirmaDao getFirmaDao() {
		return firmaDao;
	}
	public void setFirmaDao(FirmaDao firmaDao) {
		this.firmaDao = firmaDao;
	}
	public List<FirmaBilgileri> getAllFirmaBilgileri() {
		return allFirmaBilgileri;
	}
	public void setAllFirmaBilgileri(List<FirmaBilgileri> allFirmaBilgileri) {
		this.allFirmaBilgileri = allFirmaBilgileri;
	}
	public FirmaBilgileri getFirmaBilgileri() {
		return firmaBilgileri;
	}
	public void setFirmaBilgileri(FirmaBilgileri firmaBilgileri) {
		this.firmaBilgileri = firmaBilgileri;
	}
	public Integer getFirmaID() {
		return firmaID;
	}
	public void setFirmaID(Integer firmaID) {
		this.firmaID = firmaID;
	}
	public MalzemeDao getMalzemeDao() {
		return malzemeDao;
	}
	public void setMalzemeDao(MalzemeDao malzemeDao) {
		this.malzemeDao = malzemeDao;
	}
	public MalzemeGirisBilgileri getMgb() {
		return mgb;
	}
	public void setMgb(MalzemeGirisBilgileri mgb) {
		this.mgb = mgb;
	}
	public List<MalzemeGirisBilgileri> getAllMalzemeGirisBilgileri() {
		return allMalzemeGirisBilgileri;
	}
	public void setAllMalzemeGirisBilgileri(List<MalzemeGirisBilgileri> allMalzemeGirisBilgileri) {
		this.allMalzemeGirisBilgileri = allMalzemeGirisBilgileri;
	}
	public List<MalzemeGirisBilgileri> getFilteredAllMalzemeGirisBilgileri() {
		return filteredAllMalzemeGirisBilgileri;
	}
	public void setFilteredAllMalzemeGirisBilgileri(List<MalzemeGirisBilgileri> filteredAllMalzemeGirisBilgileri) {
		this.filteredAllMalzemeGirisBilgileri = filteredAllMalzemeGirisBilgileri;
	}
	
	
	public String goToPage() {
		return "both/" + "malzemeGiris" + "?faces-redirect=true";
	}
	
	public List<FirmaBilgileri> getAllFirmaList() {
		
		return firmaDao.getAllFirma();
	}
	

	
	@PostConstruct
	public void init() {
		try {
		
			mgb = new MalzemeGirisBilgileri();
			allMalzemeGirisBilgileri = malzemeDao.getAllMalzemeBilgisi();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void init(MalzemeGirisBilgileri mgblgi) {
		try {

			if (mgblgi == null) {
				mgb = new MalzemeGirisBilgileri();
			} else {
				mgb = mgblgi;
			}
			allMalzemeGirisBilgileri = malzemeDao.getAllMalzemeBilgisi();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void addMalzemeBilgileri() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {
			mgb.setFirmaBilgileri(firmaDao.getFirma(getFirmaID()));
			MalzemeGirisBilgileri mlzmgirisb = null;
			
			if (mgb.getId() != null) {
				mlzmgirisb = malzemeDao.getMalzemeGirisBilgi(mgb.getId());
			}

			if (mlzmgirisb != null) {
				malzemeDao.merge(mgb);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Malzeme Bilgileri Başarıyla Güncellendi."));
			} else {
				malzemeDao.insert(mgb);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Malzeme Bilgileri Başarıyla Kaydedildi."));
			}

			mgb = new MalzemeGirisBilgileri();
			init(null);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"İşleminiz Yapılamadı."));
			return;
		}

	}
	
	public void onRowCancel(RowEditEvent event) {

		try {
			MalzemeGirisBilgileri mgblgi = ((MalzemeGirisBilgileri) event.getObject());

			MalzemeGirisBilgileri mgblgi2 = malzemeDao.getMalzemeGirisBilgi(mgblgi.getId());
			

			boolean result = malzemeDao.delete(mgblgi2);
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

		MalzemeGirisBilgileri mgblgi = ((MalzemeGirisBilgileri) event.getObject());

		
		
		malzemeDao.merge(mgblgi);

		FacesMessage msg = new FacesMessage("Bilgiler Güncellendi",
				"Başarılı");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init(null);
	}

}
