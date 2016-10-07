package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Hasta;
import com.hastatakip.model.entity.MuayeneAltTipi;
import com.hastatakip.model.entity.MuayeneBilgiDegeri;
import com.hastatakip.model.entity.MuayeneBilgileri;
import com.hastatakip.model.entity.MuayeneTipi;

@Stateless
public class MuayeneBilgileriDao {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<MuayeneBilgileri> getAll(int matID) {
		return entityManager.createQuery("select b from MuayeneBilgileri b where b.muayeneAltTipi.id=" +matID)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public MuayeneBilgileri getMb(String id) {
		return (MuayeneBilgileri) entityManager.createQuery(
				"select b from MuayeneBilgileri b where b.id=" + id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<MuayeneTipi> getAllMuayeneTipiList() {
		return entityManager.createQuery("select b from MuayeneTipi b ")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<MuayeneAltTipi> getAltMuayeneTipiList(Integer id ) {
		return entityManager.createQuery("select b from MuayeneAltTipi b where b.tipi.id="+id)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public MuayeneAltTipi getMab(Integer id) {
		return (MuayeneAltTipi) entityManager.createQuery(
				"select b from MuayeneAltTipi b where b.id=" + id)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public MuayeneTipi getMt(Integer id) {
		return (MuayeneTipi) entityManager.createQuery(
				"select b from MuayeneTipi b where b.id=" + id)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<MuayeneBilgiDegeri> getAllMuayeneBilgiDegeri(int randevuID) {
		return entityManager.createQuery("select b from MuayeneBilgiDegeri b where b.randevu.id=" + randevuID)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public MuayeneBilgiDegeri getMuayeneBilgiDegeri(int id) {
		return (MuayeneBilgiDegeri) entityManager.createQuery(
				"select s from MuayeneBilgiDegeri s where s.id=" + id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public MuayeneBilgiDegeri getHastaMuayeneBilgiDegeri(int dosyaNo) {
		return (MuayeneBilgiDegeri) entityManager.createQuery(
				"select s from MuayeneBilgiDegeri s where s.hasta.dosyaNo=" + dosyaNo).getSingleResult();
	}
	
	public void insert(MuayeneBilgiDegeri mbd) {
		entityManager.merge(mbd);

	}
	
	public MuayeneBilgiDegeri merge(MuayeneBilgiDegeri mbd) {
		return entityManager.merge(mbd);
		
	}

}
