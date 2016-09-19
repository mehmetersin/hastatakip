package com.hastatakip.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.hastatakip.dao.HastaDao;
import com.hastatakip.dao.RandevuDao;
import com.hastatakip.model.entity.Randevu;
import com.hastatakip.model.entity.Sehir;

@ManagedBean(name = "randevuController")
@ViewScoped
public class RandevuController {
	private ScheduleModel eventModel;

	private ScheduleModel lazyEventModel;

	@EJB
	RandevuDao randevuDao;

	@EJB
	HastaDao hastaDao;

	private Integer hastaDosyaNo;

	private List<Randevu> allRandevu;

	public List<Sehir> getAllHastaList() {

		return hastaDao.getAllHasta();

	}

	private ScheduleEvent event;

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public Integer getHastaDosyaNo() {
		return hastaDosyaNo;
	}

	public void setHastaDosyaNo(Integer hastaDosyaNo) {
		this.hastaDosyaNo = hastaDosyaNo;
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
		// eventModel.addEvent(new
		// DefaultScheduleEvent("Champions League Match",
		// previousDay8Pm(), previousDay11Pm()));
		// eventModel.addEvent(new DefaultScheduleEvent("Birthday Party",
		// today1Pm(), today6Pm()));
		// eventModel.addEvent(new DefaultScheduleEvent("Breakfast at Tiffanys",
		// nextDay9Am(), nextDay11Am()));
		// eventModel.addEvent(new DefaultScheduleEvent(
		// "Plant the new garden stuff", theDayAfter3Pm(),
		// fourDaysLater3pm()));

		allRandevu = randevuDao.getAllRandevus();
		for (Iterator iterator = allRandevu.iterator(); iterator.hasNext();) {
			Randevu ran = (Randevu) iterator.next();

			DefaultScheduleEvent ds = new DefaultScheduleEvent(ran.getHasta()
					.getAdSoyad(), ran.getBaslangicTarihi(),
					ran.getBitisTarihi(), ran);
			
			ds.setId(String.valueOf(ran.getId()));

			eventModel.addEvent(ds);

		}

		// lazyEventModel = new LazyScheduleModel() {
		//
		// @Override
		// public void loadEvents(Date start, Date end) {
		//
		//
		//
		//
		// Date random = getRandomDate(start);
		// addEvent(new DefaultScheduleEvent("Lazy Event 1", random,
		// random));
		//
		// random = getRandomDate(start);
		// addEvent(new DefaultScheduleEvent("Lazy Event 2", random,
		// random));
		// }
		// };
	}

	public ScheduleModel getEventModel() {

		return eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	// private Date fourDaysLater3pm() {
	// Calendar t = (Calendar) today().clone();
	// t.set(Calendar.AM_PM, Calendar.PM);
	// t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
	// t.set(Calendar.HOUR, 3);
	//
	// return t.getTime();
	// }

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null) {

			Randevu newRand = new Randevu();
			newRand.setBaslangicTarihi(event.getStartDate());
			newRand.setBitisTarihi(event.getEndDate());
			newRand.setHasta(hastaDao.getHasta(hastaDosyaNo));

			

			Randevu reRand = randevuDao.insert(newRand);
			
			eventModel.addEvent(event);
			

		} else {
			eventModel.updateEvent(event);
		}

		event = new DefaultScheduleEvent();
	}
	
	
	public void deleteEvent(ActionEvent actionEvent) {
		if (event.getData() != null) {
			randevuDao.delete((Randevu) event.getData());
			eventModel.deleteEvent(event);
		} 
		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (DefaultScheduleEvent) selectEvent.getObject();

	}

	public void onDateSelect(SelectEvent selectEvent) {
		
		Date baslangicTarihi = (Date) selectEvent.getObject();
		Date bitisTarihi = (Date) baslangicTarihi.clone();
		
		bitisTarihi.setMinutes(bitisTarihi.getMinutes()+30);
		
		event = new DefaultScheduleEvent("", baslangicTarihi,
				bitisTarihi,new Randevu());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		
		if (event.getScheduleEvent().getData() != null ){
			Randevu ran = (Randevu) event.getScheduleEvent().getData();
			
			ran.setBaslangicTarihi(event.getScheduleEvent().getStartDate());
			ran.setBitisTarihi(event.getScheduleEvent().getEndDate());
			randevuDao.merge(ran);
			FacesMessage message = new FacesMessage("Randevu Yeni Saati Güncellendi.");
			addMessage(message);

		}else{
			FacesMessage message = new FacesMessage("Randevu Yeni Saati Güncellendi.");
			addMessage(message);

		}
		
			}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String goToPage() {
		return "both/" + "randevuIslem" + "?faces-redirect=true";
	}

}