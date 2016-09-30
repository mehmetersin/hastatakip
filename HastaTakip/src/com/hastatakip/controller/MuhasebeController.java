package com.hastatakip.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.hastatakip.dao.HastaDao;
import com.hastatakip.dao.MuhasebeBilgileriDao;
import com.hastatakip.dao.RandevuDao;
import com.hastatakip.model.entity.Hasta;
import com.hastatakip.model.entity.MuhasebeBilgileri;

@ManagedBean(name = "muhasebeController")
public class MuhasebeController implements Serializable{
	
	@EJB
	HastaDao hastaDao;

	@EJB
	RandevuDao randevuDao;
	
	@EJB
	MuhasebeBilgileriDao muhasebeBilgileriDao;
	
	private MuhasebeBilgileri mb;
	
	private List<MuhasebeBilgileri> allMuhasebeBilgileri;
	
	private List<MuhasebeBilgileri> filteredAllMuhasebeBilgileri;
	
	private Hasta hasta;

	private List<Hasta> allHasta;
	
	private Integer hastaDosyaNo;
	






	public List<MuhasebeBilgileri> getFilteredAllMuhasebeBilgileri() {
		return filteredAllMuhasebeBilgileri;
	}



	public void setFilteredAllMuhasebeBilgileri(List<MuhasebeBilgileri> filteredAllMuhasebeBilgileri) {
		this.filteredAllMuhasebeBilgileri = filteredAllMuhasebeBilgileri;
	}



	public List<Hasta> getAllHastaList() {

		return hastaDao.getAllHasta();

	}
	

	
	public String goToPage() {
		return "both/" + "muhasebeIslem" + "?faces-redirect=true";
	}
	
	
	
	public List<MuhasebeBilgileri> getAllMuhasebeBilgileri() {
		return allMuhasebeBilgileri;
	}



	public void setAllMuhasebeBilgileri(List<MuhasebeBilgileri> allMuhasebeBilgileri) {
		this.allMuhasebeBilgileri = allMuhasebeBilgileri;
	}



	public List<Hasta> getAllHasta() {
		return allHasta;
	}


	public MuhasebeBilgileri getMb() {
		return mb;
	}


	public void setMb(MuhasebeBilgileri mb) {
		this.mb = mb;
	}


	public Hasta getHasta() {
		return hasta;
	}


	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}


	public void setAllHasta(List<Hasta> allHasta) {
		this.allHasta = allHasta;
	}


	public Integer getHastaDosyaNo() {
		return hastaDosyaNo;
	}


	public void setHastaDosyaNo(Integer hastaDosyaNo) {
		this.hastaDosyaNo = hastaDosyaNo;
	}
	
	@PostConstruct
	public void init() {
		try {
		
			
			Date date = Calendar.getInstance().getTime();
//			SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
			
//			Date myDefaultDate = format.parse("1/1/1753");
			mb = new MuhasebeBilgileri();
			mb.setIslemTarihi(date);
			
			allMuhasebeBilgileri = muhasebeBilgileriDao.getAll();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void init(MuhasebeBilgileri mblgi) {
		try {

			if (mblgi == null) {
				mb = new MuhasebeBilgileri();
			} else {
				mb = mblgi;
			}
			allMuhasebeBilgileri = muhasebeBilgileriDao.getAll();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void addMuhasebeBilgileri() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {
			
			MuhasebeBilgileri mhsb = null;
			mb.setHasta(hastaDao.getHasta(getHastaDosyaNo()));
			if (mb.getId() != null) {
				mhsb = muhasebeBilgileriDao.getMuhasebeBilgileri(mb.getId());
			}

			if (mhsb != null) {
				muhasebeBilgileriDao.merge(mb);
				context.addMessage(null, new FacesMessage("Uyarý",
						"Muhasebe Bilgileri Baþarýyla Güncellendi."));
			} else {
				muhasebeBilgileriDao.insert(mb);
				context.addMessage(null, new FacesMessage("Uyarý",
						"Muhasebe Bilgileri Baþarýyla Kaydedildi."));
			}

			mb = new MuhasebeBilgileri();
			init(null);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"Ýþleminiz yapýlamadý."));
			return;
		}

	}
	
	
	public void onRowCancel(RowEditEvent event) {

		try {
			MuhasebeBilgileri mblgi = ((MuhasebeBilgileri) event.getObject());

			MuhasebeBilgileri mblgi2 = muhasebeBilgileriDao.getMuhasebeBilgileri(mblgi.getId());

			boolean result = muhasebeBilgileriDao.delete(mblgi2);
			String message = "";
			if (result) {
				message = "Baþarýlý";
			} else {
				message = "Baþarýsýz";
			}

			FacesMessage msg = new FacesMessage("Bilgiler Silindi", message);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);
		} catch (Exception e) {
			e.printStackTrace();
		

			FacesMessage msg = new FacesMessage("Silme Ýþleminde",
					"Hata Oluþtu");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);

		}

	}

	public void onRowEdit(RowEditEvent event) {

		MuhasebeBilgileri mblgi = ((MuhasebeBilgileri) event.getObject());

		
		
		muhasebeBilgileriDao.merge(mblgi);

		FacesMessage msg = new FacesMessage("Bilgiler Güncellendi",
				"Baþarýlý");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init(null);
	}
	
	public void handleDateSelect(SelectEvent event) {
	    RequestContext.getCurrentInstance().execute("PF('muhasebeTable').filter()");
	}
	

}
