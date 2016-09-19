package com.hastatakip.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.hastatakip.Util;
import com.hastatakip.bean.FileCollectorBean;
import com.hastatakip.bean.FileProcessBean;
import com.hastatakip.bean.ProcessResult;
import com.hastatakip.dao.KullaniciDao;
import com.hastatakip.dao.SubeDao;
import com.hastatakip.model.entity.EventLog;
import com.hastatakip.model.entity.Sube;

@ManagedBean(name = "islemController")
public class IslemController {

	private int subeId;
	
	private final Logger logger = Logger.getLogger(IslemController.class);

	private int backDay = 0;

	private Date islemTarihi;

	@EJB
	@Inject
	FileCollectorBean fileCollectorBean;

	@EJB
	FileProcessBean fileProcessorBean;

	@EJB
	KullaniciDao userDao;

	private String message;

	public String getMessage() {
		return message;
	}

	public int getBackDay() {
		return backDay;
	}

	public void setBackDay(int backDay) {
		this.backDay = backDay;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getIslemTarihi() {
		return islemTarihi;
	}

	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}

	public int getSubeId() {
		return subeId;
	}

	public void setSubeId(int subeId) {
		this.subeId = subeId;
	}

	@EJB
	SubeDao subeDao;

	@PostConstruct
	public void init() {
		try {

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String goToPage() {
		return "both/" + "dosyaIslem" + "?faces-redirect=true";
	}

	public String goToList() {
		return "/mainPage?faces-redirect=true";
	}

	public Map<String, Object> getSubeList() {
		LinkedHashMap<String, Object> list = new LinkedHashMap<String, Object>();

		List<Sube> suberler = subeDao.getAllSubes();
		for (Iterator iterator = suberler.iterator(); iterator.hasNext();) {
			Sube sube = (Sube) iterator.next();
			list.put(sube.getSubeAdi(), sube.getId()); // label, value
		}

		return list;

	}

	public void collectAllSube() {

		logger.debug("Start to collect files for collectAllSube.Islem tarihi:"+islemTarihi);
		
		String infoMessage = "";
		if (backDay == 0) {

			ProcessResult r = fileCollectorBean.collectFiles(islemTarihi,
					userDao.loginUser());
			infoMessage = r.toString();
			
			Util util = new Util();
			util.sendNoticeMail(r,2);
			
		} else {

			ProcessResult r = fileCollectorBean.collectFiles(islemTarihi,
					userDao.loginUser());
			infoMessage = r.toString();
			
			Util util = new Util();
			util.sendNoticeMail(r,2);

			for (int i = 1; i < backDay + 1; i++) {

				Date tempDate = new Date();
				tempDate.setYear(islemTarihi.getYear());
				tempDate.setMonth(islemTarihi.getMonth());
				tempDate.setDate(islemTarihi.getDate() - i);

				r = fileCollectorBean.collectFiles(tempDate,
						userDao.loginUser());
				String mes = r.toString();
				infoMessage = mes + infoMessage;
				util.sendNoticeMail(r,2);
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Uyarı",
				"Belirtilen tarih için dosyalar şubelerden toplandı.\n"
						+ infoMessage));
		return;

	}

	public void processAllFiles() {

		String detail = fileProcessorBean.processFiles(userDao.loginUser());

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Uyarı",
				"Dosya işleme işlemi tamamlandı.\n" + detail));
		return;

	}

	public void processFile(EventLog event) {

		FacesContext context = FacesContext.getCurrentInstance();
		Sube sube = null;
		try {

			boolean isSuccess = false;
			if (event == null) {
				sube = subeDao.getSube(getSubeId());
				isSuccess = fileCollectorBean.collectSubeFile(sube,
						islemTarihi, userDao.loginUser());
			} else {
				sube = subeDao.getSube(event.getSube().getId());
				isSuccess = fileCollectorBean.collectSubeFile(sube,
						event.getDosyaTarihi(), userDao.loginUser());
			}

			if (!isSuccess) {
				context.addMessage(null, new FacesMessage("Hata",
						"Şubeden dosya transfer işleminde hata oluştu."));
				return;
			}

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"Uygun Şube Bulunamadı"));
			return;
		}

		try {
			fileProcessorBean.processFile(sube, islemTarihi,
					userDao.loginUser());
			context.addMessage(null, new FacesMessage("Uyarı",
					"İşleminiz başarı ile tamamlandı."));
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"Şubeden alınan dosyanın işlenmesinde hata oluştu."));
		}

	}

}