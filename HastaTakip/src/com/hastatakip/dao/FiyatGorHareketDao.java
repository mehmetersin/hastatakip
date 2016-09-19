package com.hastatakip.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.EventLog;
import com.hastatakip.model.entity.FiyatGorHareket;
import com.hastatakip.model.entity.Sube;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FiyatGorHareketDao {

	@PersistenceContext
	EntityManager entityManager;

	public void log(EventLog joblog) {
		entityManager.persist(joblog);
	}

	public List<FiyatGorHareket> getData(Date startDate, Date endDate,int subeId) {


		Timestamp sDate = new Timestamp(startDate.getTime());
		Timestamp eDate = new Timestamp(endDate.getTime());
		
		return entityManager
				.createNativeQuery(
						"select * from fiyatgorhareket where sube_id=:sbid and tarih BETWEEN :sd and :bd ",
						FiyatGorHareket.class)
				.setParameter("sd", sDate)
				.setParameter("sbid", subeId)
				.setParameter("bd", eDate).getResultList();
	}

	public List<FiyatGorHareket> getData(Date startDate, Date endDate) {

		Timestamp sDate = new Timestamp(startDate.getTime());
		Timestamp eDate = new Timestamp(endDate.getTime());
		
		return entityManager
				.createNativeQuery(
						"select * from fiyatgorhareket where tarih BETWEEN :sd and :bd ",
						FiyatGorHareket.class)
				.setParameter("sd", sDate)
				.setParameter("bd", eDate).getResultList();
	}

	public int deleteEventLog(Sube sube) {

		return entityManager
				.createNativeQuery("delete from eventlog where sube_id=:sid")
				.setParameter("sid", sube.getId()).executeUpdate();
	}

}
