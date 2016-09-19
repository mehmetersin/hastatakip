package com.hastatakip.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.EventLog;
import com.hastatakip.model.entity.Sube;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EventLogDao {

	@PersistenceContext
	EntityManager entityManager;

	public void log(EventLog joblog) {
		 entityManager.persist(joblog);
	}

	public List<EventLog> raporGetir(Date startDate, Date endDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		startDate.setHours(0);
		startDate.setMinutes(1);

		endDate.setHours(0);
		endDate.setMinutes(1);
		endDate.setDate(endDate.getDate()+1);
		
		return entityManager
				.createNativeQuery(
						"select * from eventlog where to_char(baslangictarih,'YYYY-MM-DD')>=:sd and to_char(baslangictarih,'YYYY-MM-DD')<=:bd ",
						EventLog.class)
				.setParameter("sd", formatter.format(startDate))
				.setParameter("bd", formatter.format(endDate)).getResultList();
	}
	
	public int deleteEventLog(Sube sube){
		
		 return entityManager.createNativeQuery("delete from eventlog where sube_id=:sid").setParameter("sid", sube.getId()).executeUpdate();
	}

}
