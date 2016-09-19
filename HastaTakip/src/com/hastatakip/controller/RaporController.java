package com.hastatakip.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.hastatakip.dao.EventLogDao;
import com.hastatakip.dao.FiyatGorHareketDao;
import com.hastatakip.dao.RaporDao;
import com.hastatakip.dao.SubeDao;
import com.hastatakip.model.RaporDetayModel;
import com.hastatakip.model.SubeBazindaRaporOzet;
import com.hastatakip.model.entity.EventLog;
import com.hastatakip.model.entity.FiyatGorHareket;
import com.hastatakip.model.entity.Sube;
import com.hastatakip.model.entity.SubeTopluDetay;

@ManagedBean
@ViewScoped
public class RaporController {

	private String raporAd;
	private List<SubeBazindaRaporOzet> subeOzetList = new ArrayList<SubeBazindaRaporOzet>();
	private List<RaporDetayModel> subeDetayList = new ArrayList<RaporDetayModel>();
	private List<SubeBazindaRaporOzet> kasaOzetList = new ArrayList<SubeBazindaRaporOzet>();
	private List<SubeBazindaRaporOzet> kasiyerOzetList = new ArrayList<SubeBazindaRaporOzet>();
	private List<FiyatGorHareket> fiyatGorList = new ArrayList<FiyatGorHareket>();
	private List<SubeTopluDetay> subeTopluDetayList = new ArrayList<SubeTopluDetay>();

	private List<Integer> sumIptalSayisi = new ArrayList<Integer>();
	private List<BigDecimal> sumToplamTutar = new ArrayList<BigDecimal>();
	private String tabloUstBilgi;

	private List<Sube> allSubes;

	@EJB
	SubeDao subeDao;

	public List<Sube> getAllSubes() {
		return allSubes;
	}

	public void setAllSubes(List<Sube> allSubes) {
		this.allSubes = allSubes;
	}

	private List<EventLog> eventList = new ArrayList<EventLog>();

	private Date startDate;
	private Date endDate;
	private int subeId;

	public int getSubeId() {
		return subeId;
	}

	public void setSubeId(int subeId) {
		this.subeId = subeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@EJB
	RaporDao raporDao;

	@EJB
	FiyatGorHareketDao fiyatGorDao;

	@EJB
	EventLogDao eventDao;

	@PostConstruct
	public void init() {
		try {
			allSubes = subeDao.getAllSubes();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String goToRapor(int i) {
		return "both/" + getRaporAd(i) + "?faces-redirect=true";
	}

	public String goToList() {
		return "/mainPage?faces-redirect=true";
	}

	public void eventRaporGetir() {

		try {
			eventList = eventDao.raporGetir(startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(eventList.size());
	}

	public void raporSubeOzetGetir() {
		subeOzetList = raporDao.raporSubeOzetGetir(startDate, endDate);
		String simdikiSube, oncekiSube = new String("");
		int i = 0;
		int k = 0;
		for (SubeBazindaRaporOzet raporOzetModel : subeOzetList) {
			simdikiSube = subeOzetList.get(i).getSubeadi();
			if (!simdikiSube.equals(oncekiSube) && oncekiSube.length() > 0) {
				k++;
			}
			if (sumIptalSayisi.size() > 0) {
				sumIptalSayisi.add(sumIptalSayisi.get(k).intValue()
						+ raporOzetModel.getCount());
			} else {
				sumIptalSayisi.add(raporOzetModel.getCount());
			}
			oncekiSube = subeOzetList.get(i).getSubeadi();
			i++;
			subeOzetList.get(k).setCountSum(sumIptalSayisi.get(k));
		}
	}

	public void raporSubeDetayGetir(SubeBazindaRaporOzet raporOzetModel) {
		tabloUstBilgi = raporOzetModel.getSubeadi() + " Şubesi "
				+ raporOzetModel.getIslemtarihi();
		subeDetayList = raporDao.raporSubeDetayGetir(raporOzetModel);
	}

	public void raporKasaOzetGetir() {
		kasaOzetList = raporDao.raporKasaOzetGetir(getStartDate(),
				getEndDate(), getSubeId());
		String simdikiSube, oncekiSube = new String("");
		int i = 0;
		int k = 0;
		for (SubeBazindaRaporOzet raporOzetModel : kasaOzetList) {
			simdikiSube = kasaOzetList.get(i).getSubeadi();
			if (!simdikiSube.equals(oncekiSube) && oncekiSube.length() > 0) {
				k++;
			}
			if (sumIptalSayisi.size() > 0) {
				sumIptalSayisi.add(sumIptalSayisi.get(k).intValue()
						+ raporOzetModel.getCount());
			} else {
				sumIptalSayisi.add(raporOzetModel.getCount());
			}
			oncekiSube = kasaOzetList.get(i).getSubeadi();
			i++;
			kasaOzetList.get(k).setCountSum(sumIptalSayisi.get(k));
		}
	}

	public void raporKasaDetayGetir(SubeBazindaRaporOzet raporOzetModel) {
		tabloUstBilgi = raporOzetModel.getSubeadi() + " Şubesi "
				+ raporOzetModel.getIslemtarihi();
		subeDetayList = raporDao.raporKasaDetayGetir(raporOzetModel);
	}

	public void raporKasiyerOzetGetir() {
		kasiyerOzetList = raporDao.raporKasiyerOzetGetir(getStartDate(),
				getEndDate(), getSubeId());
		String simdikiSube, oncekiSube = new String("");
		int i = 0;
		int k = 0;
		for (SubeBazindaRaporOzet raporOzetModel : kasiyerOzetList) {
			simdikiSube = kasiyerOzetList.get(i).getSubeadi();
			if (!simdikiSube.equals(oncekiSube) && oncekiSube.length() > 0) {
				k++;
			}
			if (sumIptalSayisi.size() > 0) {
				sumIptalSayisi.add(sumIptalSayisi.get(k).intValue()
						+ raporOzetModel.getCount());
			} else {
				sumIptalSayisi.add(raporOzetModel.getCount());
			}
			oncekiSube = kasiyerOzetList.get(i).getSubeadi();
			i++;
			kasiyerOzetList.get(k).setCountSum(sumIptalSayisi.get(k));
		}
	}

	public void raporFiyatGorForAll() {
		fiyatGorList = fiyatGorDao.getData(getStartDate(), getEndDate());
		fiyatGorList.size();
	}
	
	public void raporFiyatGorForSube() {
		fiyatGorList = fiyatGorDao.getData(getStartDate(), getEndDate(),getSubeId());
		fiyatGorList.size();
	}

	public void raporTopluSubeDetayGetir() {
		subeTopluDetayList = raporDao.getTopluSubeDetay(getStartDate(),
				getEndDate());
		subeTopluDetayList.size();
	}

	public void raporKasiyerDetayGetir(SubeBazindaRaporOzet raporOzetModel) {
		tabloUstBilgi = raporOzetModel.getSubeadi() + " Şubesi "
				+ raporOzetModel.getIslemtarihi();
		subeDetayList = raporDao.raporKasiyerDetayGetir(raporOzetModel);
	}

	public List<SubeBazindaRaporOzet> getSubeOzetList() {
		return subeOzetList;
	}

	public void setSubeOzetList(List<SubeBazindaRaporOzet> subeOzetList) {
		this.subeOzetList = subeOzetList;
	}

	public List<Integer> getSumIptalSayisi() {
		return sumIptalSayisi;
	}

	public void setSumIptalSayisi(List<Integer> sumIptalSayisi) {
		this.sumIptalSayisi = sumIptalSayisi;
	}

	public List<BigDecimal> getSumToplamTutar() {
		return sumToplamTutar;
	}

	public void setSumToplamTutar(List<BigDecimal> sumToplamTutar) {
		this.sumToplamTutar = sumToplamTutar;
	}

	public List<SubeTopluDetay> getSubeTopluDetayList() {
		return subeTopluDetayList;
	}

	public void setSubeTopluDetayList(List<SubeTopluDetay> subeTopluDetayList) {
		this.subeTopluDetayList = subeTopluDetayList;
	}

	public String getRaporAd(int i) {
		if (i == 1) {
			raporAd = "subeRapor";
		} else if (i == 2) {
			raporAd = "kasaRapor";
		} else if (i == 3) {
			raporAd = "kasiyerRapor";
		} else if (i == 4) {
			raporAd = "genelRapor";
		} else if (i == 5) {
			raporAd = "eventRapor";
		} else if (i == 6) {
			raporAd = "fiyatGorRapor";
		} else if (i == 7) {
			raporAd = "subeTopluDetayRapor";
		}
		return raporAd;
	}

	public void setRaporAd(String raporAd) {
		this.raporAd = raporAd;
	}

	public List<SubeBazindaRaporOzet> getKasaOzetList() {
		return kasaOzetList;
	}

	public void setKasaOzetList(List<SubeBazindaRaporOzet> kasaOzetList) {
		this.kasaOzetList = kasaOzetList;
	}

	public List<SubeBazindaRaporOzet> getKasiyerOzetList() {
		return kasiyerOzetList;
	}

	public List<FiyatGorHareket> getFiyatGorList() {
		return fiyatGorList;
	}

	public void setFiyatGorList(List<FiyatGorHareket> fiyatGorList) {
		this.fiyatGorList = fiyatGorList;
	}

	public void setKasiyerOzetList(List<SubeBazindaRaporOzet> kasiyerOzetList) {
		this.kasiyerOzetList = kasiyerOzetList;
	}

	public List<RaporDetayModel> getSubeDetayList() {
		return subeDetayList;
	}

	public void setSubeDetayList(List<RaporDetayModel> subeDetayList) {
		this.subeDetayList = subeDetayList;
	}

	public String getTabloUstBilgi() {
		return tabloUstBilgi;
	}

	public void setTabloUstBilgi(String tabloUstBilgi) {
		this.tabloUstBilgi = tabloUstBilgi;
	}

	public List<EventLog> getEventList() {
		return eventList;
	}

	public void setEventList(List<EventLog> eventList) {
		this.eventList = eventList;
	}

}