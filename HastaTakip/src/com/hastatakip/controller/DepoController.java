package com.hastatakip.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.hastatakip.dao.DepoDao;
import com.hastatakip.dao.FirmaDao;
import com.hastatakip.dao.MalzemeDao;
import com.hastatakip.model.entity.DepoBilgileri;
import com.hastatakip.model.entity.FirmaBilgileri;
import com.hastatakip.model.entity.Hasta;
import com.hastatakip.model.entity.MalzemeGirisBilgileri;
import com.hastatakip.model.entity.MuhasebeBilgileri;
import com.sun.mail.handlers.image_gif;

@ManagedBean(name = "depoController")
public class DepoController implements Serializable{
	
	@EJB
	FirmaDao firmaDao;
	
	@EJB
	MalzemeDao malzemeDao;
	
	@EJB
	DepoDao depoDao;
	
	private DepoBilgileri db;
	private List<DepoBilgileri> allDepoBilgileri;
	private List<DepoBilgileri> filteredAllDepoBilgileri;
	private List<FirmaBilgileri> allFirmaBilgileri;
	private List<MalzemeGirisBilgileri> allMalzemeGirisBilgileri;
	private FirmaBilgileri firmaBilgileri;
	private MalzemeGirisBilgileri malzemeBilgileri;
	
	private Integer firmaID;
	private Integer malzemeID;
	private Integer totalMiktar=0;

	
	
	public Integer getTotalMiktar() {
		return totalMiktar;
	}
	public void setTotalMiktar(Integer totalMiktar) {
		this.totalMiktar = totalMiktar;
	}
	public FirmaBilgileri getFirmaBilgileri() {
		return firmaBilgileri;
	}
	public void setFirmaBilgileri(FirmaBilgileri firmaBilgileri) {
		this.firmaBilgileri = firmaBilgileri;
	}
	public MalzemeGirisBilgileri getMalzemeBilgileri() {
		return malzemeBilgileri;
	}
	public void setMalzemeBilgileri(MalzemeGirisBilgileri malzemeBilgileri) {
		this.malzemeBilgileri = malzemeBilgileri;
	}
	public Integer getFirmaID() {
		return firmaID;
	}
	public void setFirmaID(Integer firmaID) {
		this.firmaID = firmaID;
	}
	public Integer getMalzemeID() {
		return malzemeID;
	}
	public void setMalzemeID(Integer malzemeID) {
		this.malzemeID = malzemeID;
	}
	public FirmaDao getFirmaDao() {
		return firmaDao;
	}
	public void setFirmaDao(FirmaDao firmaDao) {
		this.firmaDao = firmaDao;
	}
	public MalzemeDao getMalzemeDao() {
		return malzemeDao;
	}
	public void setMalzemeDao(MalzemeDao malzemeDao) {
		this.malzemeDao = malzemeDao;
	}
	public DepoDao getDepoDao() {
		return depoDao;
	}
	public void setDepoDao(DepoDao depoDao) {
		this.depoDao = depoDao;
	}
	public DepoBilgileri getDb() {
		return db;
	}
	public void setDb(DepoBilgileri db) {
		this.db = db;
	}
	public List<DepoBilgileri> getAllDepoBilgileri() {
		return allDepoBilgileri;
	}
	public void setAllDepoBilgileri(List<DepoBilgileri> allDepoBilgileri) {
		this.allDepoBilgileri = allDepoBilgileri;
	}
	public List<DepoBilgileri> getFilteredAllDepoBilgileri() {
		return filteredAllDepoBilgileri;
	}
	public void setFilteredAllDepoBilgileri(List<DepoBilgileri> filteredAllDepoBilgileri) {
		this.filteredAllDepoBilgileri = filteredAllDepoBilgileri;
	}
	public List<FirmaBilgileri> getAllFirmaBilgileri() {
		return allFirmaBilgileri;
	}
	public void setAllFirmaBilgileri(List<FirmaBilgileri> allFirmaBilgileri) {
		this.allFirmaBilgileri = allFirmaBilgileri;
	}
	public List<MalzemeGirisBilgileri> getAllMalzemeGirisBilgileri() {
		return allMalzemeGirisBilgileri;
	}
	public void setAllMalzemeGirisBilgileri(List<MalzemeGirisBilgileri> allMalzemeGirisBilgileri) {
		this.allMalzemeGirisBilgileri = allMalzemeGirisBilgileri;
	}
	
	public List<FirmaBilgileri> getAllFirmaList() {

		return firmaDao.getAllFirma();

	}
	
	public List<MalzemeGirisBilgileri> getAllMalzemeList() {

		return malzemeDao.getAllMalzemeBilgisi();

	}
	
	public String goToPage() {
		return "both/" + "depoBilgileri" + "?faces-redirect=true";
	}
	
	@PostConstruct
	public void init() {
		try {
			Date date = Calendar.getInstance().getTime();
			db = new DepoBilgileri();
			db.setIslemTarihi(date);
			allDepoBilgileri = depoDao.getAllDepoBilgileri();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void init(DepoBilgileri dblgi) {
		try {

			if (dblgi == null) {
				db = new DepoBilgileri();
			} else {
				db = dblgi;
			}
			allDepoBilgileri = depoDao.getAllDepoBilgileri();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void addDepoBilgileri() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {
			
			
			DepoBilgileri depo = null;
			db.setFirmaBilgileri(firmaDao.getFirma(getFirmaID()));
			db.setMalzemeBilgileri(malzemeDao.getMalzemeGirisBilgi(getMalzemeID()));
			
			List<DepoBilgileri> db1 = depoDao.getFirmaMalzemeBilgisi(getFirmaID(), getMalzemeID());
			
			if(db1 != null){
				
				
				
				for(int i=0; i<db1.size(); i++){
					
					totalMiktar = totalMiktar + db1.get(i).getMiktar();
				}
				
			
				if(db.getGiriscikis().equals("Çıkış")){
					totalMiktar = totalMiktar - db.getMiktar();
					db.setTotalMiktar(totalMiktar);
					
				}
				else{
					totalMiktar = totalMiktar + db.getMiktar();
					db.setTotalMiktar(totalMiktar);
				
				}
				
			}
			
			if (db.getId() != null) {
				depo = depoDao.getDepoBilgisi(db.getId());
			}
			
			
			
			if (depo != null) {
				depoDao.merge(db);
				context.addMessage(null, new FacesMessage("Uyar�",
						"Depo Bilgileri Ba�ar�yla G�ncellendi."));
			} else {
				depoDao.insert(db);
				context.addMessage(null, new FacesMessage("Uyar�",
						"Depo Bilgileri Ba�ar�yla Kaydedildi."));
			}

			db = new DepoBilgileri();
			init(null);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"��leminiz yap�lamad�."));
			return;
		}

	}
	
	public void onRowCancel(RowEditEvent event) {

		try {
			DepoBilgileri dblgi = ((DepoBilgileri) event.getObject());

			DepoBilgileri dblgi2 = depoDao.getDepoBilgisi(dblgi.getId());
			totalMiktar = dblgi2.getTotalMiktar() - dblgi2.getMiktar();
			
			boolean result = depoDao.delete(dblgi2);
			
			String message = "";
			if (result) {
				message = "Ba�ar�l�";
			} else {
				message = "Ba�ar�s�z";
			}

			FacesMessage msg = new FacesMessage("Bilgiler Silindi", message);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);
		} catch (Exception e) {
			e.printStackTrace();
		

			FacesMessage msg = new FacesMessage("Silme ��leminde",
					"Hata Olu�tu");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);

		}

	}

	public void onRowEdit(RowEditEvent event) {

		DepoBilgileri dblgi = ((DepoBilgileri) event.getObject());

		
		
		depoDao.merge(dblgi);

		FacesMessage msg = new FacesMessage("Bilgiler G�ncellendi",
				"Ba�ar�l�");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init(null);
	}
	
	
}
