package com.hastatakip.job;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.hastatakip.Util;
import com.hastatakip.bean.FileCollectorBean;
import com.hastatakip.bean.ProcessResult;

@Singleton
public class FileCollectorJob {

	private final Logger logger = Logger.getLogger(FileCollectorJob.class);

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	FileCollectorBean fileCollectorBean;

	// TODO buradaki ayarın configurasyona tasinması lazım
	@Schedule(hour = "2", minute = "1", second = "1", persistent = false)
	// @Schedule(hour = "*" , minute =
	// "0,5,10,15,20,25,30,35,40,45,50,55",second="1", persistent = false)
	public void run() {

		try {

			Date date = new Date();
			date.setDate(date.getDate() - 1);

			logger.info("File Collector job starting.");

			ProcessResult r = fileCollectorBean.collectFiles(date, null);

			Util util = new Util();
			util.sendNoticeMail(r,1);
			
			logger.info("File Collector job finished.");

		} catch (Exception e) {
			logger.error("Hata", e);
		}
	}
}
