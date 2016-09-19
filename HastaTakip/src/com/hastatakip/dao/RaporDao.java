package com.hastatakip.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hastatakip.model.RaporDetayModel;
import com.hastatakip.model.SubeBazindaRaporOzet;
import com.hastatakip.model.entity.FiyatGorHareket;
import com.hastatakip.model.entity.SubeTopluDetay;

@Stateless
public class RaporDao {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<SubeBazindaRaporOzet> raporSubeOzetGetir(Date startDate,
			Date endDate) {
		List<SubeBazindaRaporOzet> list = new ArrayList<SubeBazindaRaporOzet>(); // SATIRDUZELTME
		Query q = entityManager
				.createNativeQuery("select a.subeadi, "
						+ "a.islemtarihi,"
						+ "sum(case when hareketTipi='SATIRDUZELTME' then tutar else 0 END) satirduzeltmetoplamtutar,"
						+ "count(case when hareketTipi='SATIRDUZELTME' then 1 END) satirduzeltmeadeti,"
						+ "count(case when hareketTipi='KASIYEROTURUMACMA' then 1 END) kasaacmaadeti,"
						+ "sum(case when hareketTipi='SATIRIPTAL' then tutar else 0 END) satiriptaltoplamtutar,"
						+ "count(case when hareketTipi='SATIRIPTAL' then 1 END) satiriptaladeti,"
						+ "count(case when hareketTipi='FISIPTAL' then 1 END) fisiptaladet,"
						+ "sum(case when hareketTipi='FISIPTAL' then tutar else 0 END) fisiptaltoplamtutar, "
						+ " a.bolgeadi,a.sapKodu "
						+ " from "
						+ "(select sube.subeadi subeAdi,sube.sapKodu sapKodu,bolge.adi bolgeadi, to_char(hareket.tarih, 'DD/MM/YYYY') islemtarihi, hareketTipi ,tutar from hareket, sube, bolge"
						+ " where sube.id = hareket.sube_id and sube.bolge_id=bolge.id and hareket.tarih BETWEEN ? and ? ) a "
						+ "group by a.subeAdi,a.bolgeadi, a.islemtarihi,a.sapKodu order by a.subeAdi, a.islemtarihi"
				// "select row_number() over() id, sube.subeadi subeAdi, hareket.tarih iptalTarihi, 0 kasaNo, 0 kasiyerNo, count(*) count, sum(tutar) total, to_char(sum(tutar)/count(*), '999999999999.99') ortalama, 0 countSum from hareket, sube where sube.id = hareket.sube_id group by sube.subeadi, hareket.tarih order by sube.subeadi, hareket.tarih"
				);
		Timestamp sDate = new Timestamp(startDate.getTime());
		Timestamp eDate = new Timestamp(endDate.getTime());
		eDate.setDate(eDate.getDate() + 1);
		q.setParameter(1, sDate);
		q.setParameter(2, eDate);

		List tmpList = q.getResultList();
		for (int i = 0; i < tmpList.size(); i++) {
			Object[] it = (Object[]) tmpList.get(i);

			SubeBazindaRaporOzet r = new SubeBazindaRaporOzet();
			r.setSubeadi((String) it[0]);
			r.setIslemtarihi((String) it[1]);
			r.setSatirduzeltmetoplamtutar((BigDecimal) it[2]);
			r.setSatirduzeltmeadeti(((BigInteger) it[3]).intValue());
			r.setKasaacmaadeti(((BigInteger) it[4]).intValue());
			r.setSatiriptaltoplamtutar((BigDecimal) it[5]);
			r.setSatiriptaladeti(((BigInteger) it[6]).intValue());
			r.setFisiptaladet(((BigInteger) it[7]).intValue());
			r.setFisiptaltoplamtutar((BigDecimal) it[8]);
			r.setBolgeName((String) it[9]);
			r.setSapKodu((String) it[10]);
			list.add(r);

		}

		return list;
	}

	// kasa ozet rapor getir
	public List<SubeBazindaRaporOzet> raporKasaOzetGetir(Date startDate,
			Date endDate, int subeId) {

		List<SubeBazindaRaporOzet> list = new ArrayList<SubeBazindaRaporOzet>(); // SATIRDUZELTME
		Query q = entityManager
				.createNativeQuery("select a.subeadi, "
						+ "a.islemtarihi,"
						+ "sum(case when hareketTipi='SATIRDUZELTME' then tutar else 0 END) satirduzeltmetoplamtutar,"
						+ "count(case when hareketTipi='SATIRDUZELTME' then 1 END) satirduzeltmeadeti,"
						+ "count(case when hareketTipi='KASIYEROTURUMACMA' then 1 END) kasaacmaadeti,"
						+ "sum(case when hareketTipi='SATIRIPTAL' then tutar else 0 END) satiriptaltoplamtutar,"
						+ "count(case when hareketTipi='SATIRIPTAL' then 1 END) satiriptaladeti,"
						+ "count(case when hareketTipi='FISIPTAL' then 1 END) fisiptaladet,"
						+ "sum(case when hareketTipi='FISIPTAL' then tutar else 0 END) fisiptaltoplamtutar, "
						+ " a.bolgeadi,a.kasano,a.sapKodu "
						+ " from "
						+ "(select sube.sapKodu,sube.subeadi subeAdi,bolge.adi bolgeadi, to_char(hareket.tarih, 'DD/MM/YYYY') islemtarihi, "
						+ " hareketTipi ,tutar,kasano from hareket, sube, bolge"
						+ " where sube.id = hareket.sube_id and sube.bolge_id=bolge.id and sube.id=? and hareket.tarih BETWEEN ? and ?  ) a "
						+ "group by a.kasano,a.islemtarihi,a.subeadi,a.bolgeadi,a.sapKodu order by a.subeAdi, a.islemtarihi"
				// "select row_number() over() id, sube.subeadi subeAdi, hareket.tarih iptalTarihi, 0 kasaNo, 0 kasiyerNo, count(*) count, sum(tutar) total, to_char(sum(tutar)/count(*), '999999999999.99') ortalama, 0 countSum from hareket, sube where sube.id = hareket.sube_id group by sube.subeadi, hareket.tarih order by sube.subeadi, hareket.tarih"
				);
		Timestamp sDate = new Timestamp(startDate.getTime());
		Timestamp eDate = new Timestamp(endDate.getTime());
		eDate.setDate(eDate.getDate() + 1);
		q.setParameter(1, subeId);
		q.setParameter(2, sDate);
		q.setParameter(3, eDate);

		List tmpList = q.getResultList();
		for (int i = 0; i < tmpList.size(); i++) {
			Object[] it = (Object[]) tmpList.get(i);

			SubeBazindaRaporOzet r = new SubeBazindaRaporOzet();
			r.setSubeadi((String) it[0]);
			r.setIslemtarihi((String) it[1]);
			r.setSatirduzeltmetoplamtutar((BigDecimal) it[2]);
			r.setSatirduzeltmeadeti(((BigInteger) it[3]).intValue());
			r.setKasaacmaadeti(((BigInteger) it[4]).intValue());
			r.setSatiriptaltoplamtutar((BigDecimal) it[5]);
			r.setSatiriptaladeti(((BigInteger) it[6]).intValue());
			r.setFisiptaladet(((BigInteger) it[7]).intValue());
			r.setFisiptaltoplamtutar((BigDecimal) it[8]);
			r.setBolgeName((String) it[9]);
			r.setKasano((String) it[10]);
			r.setSapKodu((String) it[11]);
			list.add(r);

		}

		return list;

	}

	@SuppressWarnings("unchecked")
	public List<RaporDetayModel> raporSubeDetayGetir(
			SubeBazindaRaporOzet raporOzetModel) {
		return entityManager
				.createNativeQuery(
						"select row_number() over() id, subeadi subeadi, to_char(tarih, 'DD/MM/YYYY HH24:MI:SS') iptaltarihi, hareketTipi, islemno, kasano, kasiyerno, barkod, tutar "
								+ " from hareket, sube where sube.id = hareket.sube_id and subeadi =:subeadi  and to_char(tarih,'DD/MM/YYYY') =:iptaltarihi "
								+ " order by subeadi, tarih, hareketTipi, islemno, kasano, kasiyerno, barkod, tutar ",
						RaporDetayModel.class)
				.setParameter("subeadi", raporOzetModel.getSubeadi())
				.setParameter("iptaltarihi", raporOzetModel.getIslemtarihi())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RaporDetayModel> raporKasaDetayGetir(
			SubeBazindaRaporOzet raporOzetModel) {
		return entityManager
				.createNativeQuery(
						"select row_number() over() id, subeadi subeadi, to_char(tarih, 'DD/MM/YYYY HH24:MI:SS') iptaltarihi, hareketTipi, islemno, kasano, kasiyerno, barkod, tutar "
								+ " from hareket, sube where sube.id = hareket.sube_id and subeadi =:subeadi  and to_char(tarih,'DD/MM/YYYY') =:iptaltarihi and hareket.kasano =:kasano "
								+ " order by subeadi, tarih,kasano, hareketTipi, islemno, kasiyerno, barkod, tutar ",
						RaporDetayModel.class)
				.setParameter("subeadi", raporOzetModel.getSubeadi())
				.setParameter("iptaltarihi", raporOzetModel.getIslemtarihi())
				.setParameter("kasano", raporOzetModel.getKasano()) // raporOzetModel.getKasano())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SubeBazindaRaporOzet> raporKasiyerOzetGetir(Date startDate,
			Date endDate, int subeId) {

		List<SubeBazindaRaporOzet> list = new ArrayList<SubeBazindaRaporOzet>(); // SATIRDUZELTME
		Query q = entityManager
				.createNativeQuery("select a.subeadi, "
						+ "a.islemtarihi,"
						+ "sum(case when hareketTipi='SATIRDUZELTME' then tutar else 0 END) satirduzeltmetoplamtutar,"
						+ "count(case when hareketTipi='SATIRDUZELTME' then 1 END) satirduzeltmeadeti,"
						+ "count(case when hareketTipi='KASIYEROTURUMACMA' then 1 END) kasaacmaadeti,"
						+ "sum(case when hareketTipi='SATIRIPTAL' then tutar else 0 END) satiriptaltoplamtutar,"
						+ "count(case when hareketTipi='SATIRIPTAL' then 1 END) satiriptaladeti,"
						+ "count(case when hareketTipi='FISIPTAL' then 1 END) fisiptaladet,"
						+ "sum(case when hareketTipi='FISIPTAL' then tutar else 0 END) fisiptaltoplamtutar, "
						+ " a.bolgeadi,a.kasiyerno "
						+ " from "
						+ "(select sube.subeadi subeAdi,bolge.adi bolgeadi, to_char(hareket.tarih, 'DD/MM/YYYY') islemtarihi, "
						+ " hareketTipi ,tutar,kasano,kasiyerno from hareket, sube, bolge"
						+ " where sube.id = hareket.sube_id and sube.bolge_id=bolge.id and sube.id=? and hareket.tarih BETWEEN ? and ?  ) a "
						+ "group by a.kasiyerno,a.islemtarihi,a.subeadi,a.bolgeadi order by a.kasiyerno,a.subeAdi, a.islemtarihi"
				// "select row_number() over() id, sube.subeadi subeAdi, hareket.tarih iptalTarihi, 0 kasaNo, 0 kasiyerNo, count(*) count, sum(tutar) total, to_char(sum(tutar)/count(*), '999999999999.99') ortalama, 0 countSum from hareket, sube where sube.id = hareket.sube_id group by sube.subeadi, hareket.tarih order by sube.subeadi, hareket.tarih"
				);
		Timestamp sDate = new Timestamp(startDate.getTime());
		Timestamp eDate = new Timestamp(endDate.getTime());
		eDate.setDate(eDate.getDate() + 1);
		q.setParameter(1, subeId);
		q.setParameter(2, sDate);
		q.setParameter(3, eDate);

		List tmpList = q.getResultList();
		for (int i = 0; i < tmpList.size(); i++) {
			Object[] it = (Object[]) tmpList.get(i);

			SubeBazindaRaporOzet r = new SubeBazindaRaporOzet();
			r.setSubeadi((String) it[0]);
			r.setIslemtarihi((String) it[1]);
			r.setSatirduzeltmetoplamtutar((BigDecimal) it[2]);
			r.setSatirduzeltmeadeti(((BigInteger) it[3]).intValue());
			r.setKasaacmaadeti(((BigInteger) it[4]).intValue());
			r.setSatiriptaltoplamtutar((BigDecimal) it[5]);
			r.setSatiriptaladeti(((BigInteger) it[6]).intValue());
			r.setFisiptaladet(((BigInteger) it[7]).intValue());
			r.setFisiptaltoplamtutar((BigDecimal) it[8]);
			r.setBolgeName((String) it[9]);
			r.setKasiyerno((String) it[10]);
			list.add(r);

		}

		return list;

	}

	@SuppressWarnings("unchecked")
	public List<RaporDetayModel> raporKasiyerDetayGetir(
			SubeBazindaRaporOzet raporOzetModel) {

		return entityManager
				.createNativeQuery(
						"select row_number() over() id, subeadi subeadi, to_char(tarih, 'DD/MM/YYYY HH24:MI:SS') iptaltarihi, hareketTipi, islemno, kasano, kasiyerno, barkod, tutar "
								+ " from hareket, sube where sube.id = hareket.sube_id and subeadi =:subeadi  and to_char(tarih,'DD/MM/YYYY') =:iptaltarihi and hareket.kasiyerno =:kasiyerno "
								+ " order by subeadi, tarih,kasano, hareketTipi, islemno, kasiyerno, barkod, tutar ",
						RaporDetayModel.class)
				.setParameter("subeadi", raporOzetModel.getSubeadi())
				.setParameter("iptaltarihi", raporOzetModel.getIslemtarihi())
				.setParameter("kasiyerno", raporOzetModel.getKasiyerno()) // raporOzetModel.getKasano())
				.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<RaporDetayModel> raporGenelDetayGetir() {
		return entityManager.createQuery("select fi from hareket fi")
				.getResultList();
	}

	public List<SubeTopluDetay> getTopluSubeDetay(Date startDate, Date endDate) {
		// TODO Auto-generated method stub

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		return entityManager
				.createNativeQuery(
						"select sum(tutar) tutar, min(sapKodu) sapKodu,kasiyerNo kasiyerNo,kasaNo kasano,to_char(hareket.tarih, 'DD/MM/YYYY') islemtarihi,hareketTipi hareketTipi,min(bolge.adi) bolgeadi, min(subeAdi) subeAdi,min(hareket.id) id from hareket, sube,bolge where "
						+ "bolge.id = sube.bolge_id and "
						+ "sube.id = hareket.sube_id "
						+ " and to_char(tarih,'YYYY-MM-DD')>=:sd and to_char(tarih,'YYYY-MM-DD')<=:bd group by islemtarihi,kasiyerNo,kasano,hareketTipi order by islemtarihi",
						SubeTopluDetay.class)
				.setParameter("sd", formatter.format(startDate))
				.setParameter("bd", formatter.format(endDate)).getResultList();

	}

}
