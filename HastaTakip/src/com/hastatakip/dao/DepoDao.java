package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.DepoBilgileri;
import com.hastatakip.model.entity.MalzemeGirisBilgileri;
import com.hastatakip.model.entity.MuayeneAltTipi;
import com.hastatakip.model.entity.Randevu;

@Stateless
public class DepoDao {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public DepoBilgileri getDepoBilgisi(int id) {
		return (DepoBilgileri) entityManager.createQuery(
				"select d from DepoBilgileri d where d.id=" + id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List getAllDepoBilgileri() {
		return entityManager.createQuery("select d from DepoBilgileri d ORDER BY d.malzemeBilgileri.malzemeAdi ")
				.getResultList();
	}
	
	public void insert(DepoBilgileri db) {
		entityManager.merge(db);

	}
	
	public DepoBilgileri merge(DepoBilgileri db) {
		return entityManager.merge(db);
		
	}
	
	public boolean delete(DepoBilgileri db) {
		try {
			int i = entityManager.createQuery(
					"delete from DepoBilgileri db where db.id=" + db.getId())
					.executeUpdate();
			if (i == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DepoBilgileri> getMalzemeBilgisi(Integer malzemeID ) {
		return entityManager.createQuery("select b from DepoBilgileri b where b.malzemeBilgileri.id="+malzemeID)
				.getResultList();
	}
}
