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

import com.hastatakip.dao.BolgeDao;
import com.hastatakip.dao.EventLogDao;
import com.hastatakip.dao.HareketDao;
import com.hastatakip.dao.SubeDao;
import com.hastatakip.job.FileProcessorJob;
import com.hastatakip.model.entity.Sehir;
import com.hastatakip.model.entity.Sube;
import com.hastatakip.model.type.BaglantiTipi;

@ManagedBean(name = "subeController")
public class SubeController implements Serializable {

	private Sube sube;

	private List<Sube> allSubes;

	@EJB
	SubeDao subeDao;

	@EJB
	HareketDao hareketDao;

	@EJB
	EventLogDao eventLogDao;

	@EJB
	BolgeDao bolgeDao;

	private int bolgeId;

	private Sube selectedSube;

	private final Logger logger = Logger.getLogger(FileProcessorJob.class);

	public Sube getSelectedSube() {
		return selectedSube;
	}

	public void setSelectedSube(Sube selectedSube) {
		this.selectedSube = selectedSube;
	}

	public int getBolgeId() {
		return bolgeId;
	}

	public void setBolgeId(int bolgeId) {
		this.bolgeId = bolgeId;
	}

	public Sube getSube() {
		return sube;
	}

	public void setSube(Sube sube) {
		this.sube = sube;
	}

	public List<Sube> getAllSubes() {
		return allSubes;
	}

	public void setAllSubes(List<Sube> allSubes) {
		this.allSubes = allSubes;
	}

	@PostConstruct
	public void init() {
		try {

			sube = new Sube();
			allSubes = subeDao.getAllSubes();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void init(Sube sub) {
		try {

			if (sub == null) {
				sube = new Sube();
			} else {
				sube = sub;
			}
			allSubes = subeDao.getAllSubes();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public BaglantiTipi[] getBaglantiList() {
		return BaglantiTipi.values();

	}

	public List<Sehir> getBolgeList() {

		return bolgeDao.getAll();

		// List<Bolge> items = new ArrayList<Bolge>(list.size());
		// for(Bolge value : list){
		// items.add(value);
		// }
		// return items;

	}

	public String goToPage() {
		return "both/" + "subeIslem" + "?faces-redirect=true";
	}

	public void createSube() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {

			sube.setBolge(bolgeDao.getBolge(getBolgeId()));

			Sube sub = null;
			if (sube.getId() != null) {
				sub = subeDao.getSube(sube.getId());
			}

			if (sub != null) {
				subeDao.merge(sube);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Şube başarı ile güncellendi."));
			} else {
				subeDao.insert(sube);
				context.addMessage(null, new FacesMessage("Uyarı",
						"Şube başarı ile kayıt edildi."));
			}

			sube = new Sube();
			init(null);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"İşleminiz Yapılamadı."));
			return;
		}

	}

	public void updateSube(Sube sube) {

		FacesContext context = FacesContext.getCurrentInstance();
		try {

			setBolgeId(sube.getBolge().getId());

			setSube(sube);

			init(sube);

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"İşleminiz Yapılamadı."));
			return;
		}

	}

	public void onRowCancel(RowEditEvent event) {

		try {
			Sube sub = ((Sube) event.getObject());

			Sube sub2 = subeDao.getSube(sub.getId());
			int eventCount = eventLogDao.deleteEventLog(sub2);
			int subeHarCount = hareketDao.deleteSubeHarekets(sub);

			boolean result = subeDao.delete(sub);
			String message = "";
			if (result) {
				message = "Başarılı-Silinen Event Sayisi:" + eventCount
						+ "-Silinen Hareket Sayisi:" + subeHarCount;
			} else {
				message = "Başarısız";
			}

			FacesMessage msg = new FacesMessage("Sube Silindi", message);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);

			FacesMessage msg = new FacesMessage("Sube Silme İşleminde",
					"Hata Oluştu");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			init(null);

		}

	}

	public void onRowEdit(RowEditEvent event) {

		Sube sub = ((Sube) event.getObject());

		sub.setBolge(bolgeDao.getBolge(bolgeId));

		subeDao.merge(sub);

		FacesMessage msg = new FacesMessage("Sube Bilgileri Güncellendi",
				"Başarılı");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init(null);
	}

}