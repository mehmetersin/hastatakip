package com.hastatakip.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.hastatakip.dao.HastaDao;
import com.hastatakip.dao.MuayeneBilgileriDao;
import com.hastatakip.dao.RandevuDao;
import com.hastatakip.model.entity.EklenecekBilgiler;
import com.hastatakip.model.entity.MuayeneAltTipi;
import com.hastatakip.model.entity.MuayeneBilgileri;
import com.hastatakip.model.entity.MuayeneTipi;
import com.hastatakip.model.entity.Randevu;
import com.hastatakip.model.entity.Sehir;

@ManagedBean(name = "muayeneController")
@ViewScoped
public class MuayeneController {
	private ScheduleModel eventModel;

	private ScheduleModel lazyEventModel;

	@EJB
	RandevuDao randevuDao;

	@EJB
	HastaDao hastaDao;

	@EJB
	MuayeneBilgileriDao mbDao;

	@EJB
	MuayeneBilgileriDao muayeneBilgileriDao;

	private Randevu randevu;

	private Integer mtId;

	private Integer matId;

	private List<Randevu> allRandevu;

	private String bilgiId;

	private List<EklenecekBilgiler> eklenecekBilgiler;

	private String randevuBilgiDegeri;

	private List<MuayeneAltTipi> muayeneAltTipiList;

	public List<MuayeneAltTipi> getMuayeneAltTipiList() {
		return muayeneAltTipiList;
	}

	public void setMuayeneAltTipiList(List<MuayeneAltTipi> muayeneAltTipiList) {
		this.muayeneAltTipiList = muayeneAltTipiList;
	}

	public Integer getMtId() {
		return mtId;
	}

	public void setMtId(Integer mtId) {
		this.mtId = mtId;
	}

	public Integer getMatId() {
		return matId;
	}

	public void setMatId(Integer matId) {
		this.matId = matId;
	}

	public String getRandevuBilgiDegeri() {
		return randevuBilgiDegeri;
	}

	public void setRandevuBilgiDegeri(String randevuBilgiDegeri) {
		this.randevuBilgiDegeri = randevuBilgiDegeri;
	}

	public List<EklenecekBilgiler> getEklenecekBilgiler() {
		return eklenecekBilgiler;
	}

	public void setEklenecekBilgiler(List<EklenecekBilgiler> eklenecekBilgiler) {
		this.eklenecekBilgiler = eklenecekBilgiler;
	}

	public String getBilgiId() {
		return bilgiId;
	}

	public void setBilgiId(String bilgiId) {
		this.bilgiId = bilgiId;
	}

	public List<MuayeneBilgileri> getAllMuayeneBilgileriList() {

		return muayeneBilgileriDao.getAll();

	}

	public List<MuayeneTipi> getAllMuayeneTipiList() {

		return muayeneBilgileriDao.getAllMuayeneTipiList();

	}

	public void onMuayeneTipiChange() {
		if (mtId != null && !mtId.equals("")) {
			muayeneAltTipiList = muayeneBilgileriDao
					.getAltMuayeneTipiList(mtId);
		} else {
			muayeneAltTipiList = new ArrayList<MuayeneAltTipi>();
		}
	}

	public List<Sehir> getAllHastaList() {

		return hastaDao.getAllHasta();

	}

	private ScheduleEvent event;

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;

	}

	public RandevuDao getRandevuDao() {
		return randevuDao;
	}

	public void setRandevuDao(RandevuDao randevuDao) {
		this.randevuDao = randevuDao;
	}

	public List<Randevu> getAllRandevu() {
		return allRandevu;
	}

	public void setAllRandevu(List<Randevu> allRandevu) {
		this.allRandevu = allRandevu;
	}

	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();
		bilgiId = "";

		allRandevu = randevuDao.getAllRandevus();
		for (Iterator iterator = allRandevu.iterator(); iterator.hasNext();) {
			Randevu ran = (Randevu) iterator.next();

			DefaultScheduleEvent ds = new DefaultScheduleEvent(ran.getHasta()
					.getAdSoyad(), ran.getBaslangicTarihi(),
					ran.getBitisTarihi(), ran);

			ds.setId(String.valueOf(ran.getId()));

			eventModel.addEvent(ds);

		}
	}

	public ScheduleModel getEventModel() {

		return eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void updateEvent(ActionEvent actionEvent) {

		String randBil = null;

		for (int i = 0; i < eklenecekBilgiler.size(); i++) {
			if (randBil == null) {
				randBil = eklenecekBilgiler.get(i).getMb().getId() + "="
						+ eklenecekBilgiler.get(i).getDeger();
			} else {
				randBil = randBil + "|"
						+ eklenecekBilgiler.get(i).getMb().getId() + "="
						+ eklenecekBilgiler.get(i).getDeger();
			}
		}
		randevu.setMat(muayeneBilgileriDao.getMab(matId));
		randevu.setRandevuBilgileri(randBil);
		randevuDao.merge(randevu);

	}

	public void addBilgi(ActionEvent actionEvent) {

		if (eklenecekBilgiler == null) {
			eklenecekBilgiler = new ArrayList<EklenecekBilgiler>();
		}
		EklenecekBilgiler n = new EklenecekBilgiler();
		n.setDeger(randevuBilgiDegeri);

		MuayeneBilgileri mb = mbDao.getMb(bilgiId);
		n.setMb(mb);

		eklenecekBilgiler.add(n);

		bilgiId = "";
		randevuBilgiDegeri = "";

	}

	public Randevu getRandevu() {
		return randevu;
	}

	public void setRandevu(Randevu randevu) {
		this.randevu = randevu;
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (DefaultScheduleEvent) selectEvent.getObject();
		randevu = (Randevu) event.getData();

	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String goToPage() {
		return "both/" + "muayeneIslem" + "?faces-redirect=true";
	}

}