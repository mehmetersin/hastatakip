package com.hastatakip.job;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.hastatakip.bean.FileProcessBean;
import com.hastatakip.dao.KullaniciDao;

@Singleton
public class FileProcessorJob {

	private final Logger logger = Logger.getLogger(FileProcessorJob.class);

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	FileProcessBean fileProcessorBean;

	@EJB
	KullaniciDao userDao;

	// TODO buradaki ayar confiugurasyona tasinmasi lazım
	@Schedule(hour = "4" , minute = "1",second="1", persistent = false)
	//@Schedule(hour = "*", minute = "0,5,10,15,20,25,30,35,40,45,50,55", second = "1", persistent = false)
	public void run() throws Exception {

		logger.info("File Processor job starting.");

		String message = fileProcessorBean.processFiles(userDao
				.getSystemKullanici());

		logger.info("File Processor job finished." + message);
	}
}
