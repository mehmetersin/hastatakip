package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.MuayeneAltTipi;
import com.hastatakip.model.entity.MuayeneBilgileri;
import com.hastatakip.model.entity.MuayeneTipi;

@Stateless
public class MuayeneBilgileriDao {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<MuayeneBilgileri> getAll() {
		return entityManager.createQuery("select b from MuayeneBilgileri b")
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

}
