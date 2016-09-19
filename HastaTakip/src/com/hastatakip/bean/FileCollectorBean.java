package com.hastatakip.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.hastatakip.Util;
import com.hastatakip.dao.EventLogDao;
import com.hastatakip.dao.KullaniciDao;
import com.hastatakip.dao.SubeDao;
import com.hastatakip.fm.FtpFileManager;
import com.hastatakip.fm.WindowsFileManager;
import com.hastatakip.model.entity.EventLog;
import com.hastatakip.model.entity.Kullanici;
import com.hastatakip.model.entity.Sube;
import com.hastatakip.model.type.BaglantiTipi;
import com.hastatakip.model.type.Durum;
import com.hastatakip.model.type.EventType;

@Stateless
public class FileCollectorBean {

	private final Logger logger = Logger.getLogger(FileCollectorBean.class);

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	SubeDao subeDao;

	@EJB
	EventLogDao jobLogDao;

	@EJB
	KullaniciDao userDao;

	public ProcessResult collectFiles(Date islemTarihi, Kullanici user) {
		
		int countTotal = 0;
		int countSucces = 0;
		int countFail = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy ");
		String date = sdf.format(islemTarihi);

		if (user == null) {
			user = userDao.getSystemKullanici();
		}
		
		List<Sube> subes = subeDao.getAllSubes();
		for (Sube sube : subes) {

			countTotal++;

			logger.debug(countTotal + ".Sube-Start to collect File,"
					+ sube.toString() + " File Process Date:" + date);

			boolean result = collectSubeFile(sube, islemTarihi, user);

			if (result) {
				countSucces++;
			} else {
				countFail++;
			}

			logger.debug("Finished to collect File" + sube.toString()
					+ " File Process Date:" + date);
		}

		ProcessResult p = new ProcessResult();
		
		
		
		p.setIslemTarihi(islemTarihi);
		p.setErrorCount(countFail);
		p.setSuccessCount(countSucces);
		p.setTotalCount(countTotal);

		logger.info(p.toString());
		
		return p;

	}

	public boolean collectSubeFile(Sube sube, Date islemTarihi, Kullanici user) {

		EventLog event = new EventLog();
		event.setBaslangicTarih(new Date());
		event.setKullanici(user);
		event.setSube(sube);
		event.setDosyaTarihi(islemTarihi);
		event.setEventType(EventType.DosyaTransfer);
		String fileName = "";

		boolean isSuccess = true;
		try {
			if (sube.getBaglantiTipi().equals(BaglantiTipi.WINDOWSSHARE)) {
				fileName = Util.getWindowsShareDownloadFileFormat(islemTarihi);
				WindowsFileManager fm = new WindowsFileManager();
				fm.copyFiles(sube, fileName);
			} else if (sube.getBaglantiTipi().equals(BaglantiTipi.FTP)) {
				fileName = Util.getFtpDownloadFileFormat(islemTarihi);
				FtpFileManager fm = new FtpFileManager();
				fm.copyFiles(sube, fileName);
			} else {
				logger.error("Invalid connection type-" + sube.toString());
			}
			event.setDurum(Durum.BASARILI);
			event.setFileName(fileName);

		} catch (Exception e) {
			event.setDurum(Durum.BASARISIZ);
			logger.error("Error while collecting files,Sube:" + sube.getId()
					+ e.getMessage() + e.getLocalizedMessage());
			isSuccess = false;
		} finally {
			event.setBitisTarih(new Date());
			jobLogDao.log(event);
		}
		return isSuccess;

	}

}
