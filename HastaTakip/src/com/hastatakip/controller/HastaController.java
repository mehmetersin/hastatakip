package com.hastatakip.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.hastatakip.dao.HastaDao;
import com.hastatakip.dao.RandevuDao;
import com.hastatakip.dao.SehirDao;
import com.hastatakip.job.FileProcessorJob;
import com.hastatakip.model.entity.Hasta;
import com.hastatakip.model.entity.Sehir;
import com.hastatakip.model.type.Cinsiyet;
import com.hastatakip.model.type.KanGrubu;
import com.hastatakip.model.type.MedeniDurum;

@ManagedBean(name = "hastaController")
public class HastaController implements Serializable {

	private Hasta hasta;

	private List<Hasta> allHasta;

	@EJB
	HastaDao hastaDao;

	@EJB
	RandevuDao randevuDao;

	@EJB
	SehirDao sehirDao;

	private int sehirId;

	private Hasta selectedHasta;

	private final Logger logger = Logger.getLogger(FileProcessorJob.class);

	public Hasta getSelectedHasta() {
		return selectedHasta;
	}

	public void setSelectedSube(Hasta selectedSub) {
		this.selectedHasta = selectedSub;
	}

	public int getSehirId() {
		return sehirId;
	}

	public void setSehirId(int sehiId) {
		this.sehirId = sehiId;
	}

	public Hasta getHasta() {
		return hasta;
	}
	
	

	public List<Hasta> getAllHasta() {
		return allHasta;
	}

	public void setAllHasta(List<Hasta> allHasta) {
		this.allHasta = allHasta;
	}

	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}

	public void setSelectedHasta(Hasta selectedHasta) {
		this.selectedHasta = selectedHasta;
	}

	@PostConstruct
	public void init() {
		try {

			hasta = new Hasta();
			allHasta = hastaDao.getAllHasta();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void init(Hasta has) {
		try {

			if (has == null) {
				hasta = new Hasta();
			} else {
				hasta = has;
			}
			allHasta = hastaDao.getAllHasta();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public KanGrubu[] getKanGrubusList() {
		return KanGrubu.values();

	}

	public Cinsiyet[] getCinsiyetList() {
		return Cinsiyet.values();
	}

	public MedeniDurum[] getMedeniDurumList() {
		return MedeniDurum.values();
	}

	public List<Sehir> getSehirList() {

		return sehirDao.getAll();

	}

	public String goToPage() {
		return "both/" + "hastaIslem" + "?faces-redirect=true";
	}

	public void createHasta() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {

			hasta.setSehir(sehirDao.getSehir(getSehirId()));
			Hasta has = null;
			if (hasta.getDosyaNo() != null) {
				has = hastaDao.getHasta(hasta.getDosyaNo());
			}

			if (has != null) {
				hastaDao.merge(hasta);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Hasta başarı ile güncellendi."));
			} else {
				hastaDao.insert(hasta);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Hasta başarı ile kayıt edildi."));
			}

			hasta = new Hasta();
			init(null);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"İşleminiz Yapılamadı."));
			return;
		}

	}

	// public void updateSube(Sube sube) {
	//
	// FacesContext context = FacesContext.getCurrentInstance();
	// try {
	//
	// setBolgeId(sube.getBolge().getId());
	//
	// setSube(sube);
	//
	// init(sube);
	//
	// } catch (Exception e) {
	// context.addMessage(null, new FacesMessage("Hata",
	// "İşleminiz Yapılamadı."));
	// return;
	// }
	//
	// }

	public void onRowCancel(RowEditEvent event) {

		try {
			Hasta sub = ((Hasta) event.getObject());

			Hasta sub2 = hastaDao.getHasta(sub.getDosyaNo());

			boolean deletedRandevu = randevuDao.deleteHastaRandevus(sub2);

			boolean result = hastaDao.delete(sub2);
			String message = "";
			if (result) {
				message = "Hasta başarılı bir şekilde tüm bilgileri ile silindi";
			} else {
				message = "Başarısız";
			}

			FacesMessage msg = new FacesMessage("Hasta Silindi", message);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);

			FacesMessage msg = new FacesMessage("Hasta Silme İşleminde",
					"Hata Oluştu");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);

		}

	}

	public void onRowEdit(RowEditEvent event) {

		Hasta sub = ((Hasta) event.getObject());

		sub.setSehir(sehirDao.getSehir(sehirId));
		
		hastaDao.merge(sub);

		FacesMessage msg = new FacesMessage("Hasta Bilgileri Güncellendi",
				"Başarılı");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init(null);
	}

}