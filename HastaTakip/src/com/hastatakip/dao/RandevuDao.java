package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Hasta;
import com.hastatakip.model.entity.Randevu;

@Stateless
public class RandevuDao {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Randevu getRandevu(int id) {
		return (Randevu) entityManager.createQuery(
				"select s from Randevu s where s.id=" + id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List getAllRandevus() {
		return entityManager.createQuery("select s from Randevu s ")
				.getResultList();
	}

	public Randevu insert(Randevu h) {
		return entityManager.merge(h);

	}
	
	public boolean delete(Randevu ran) {

		try {
			int i = entityManager.createQuery(
					"delete from Randevu k where k.id=" + ran.getId())
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


	public boolean deleteHastaRandevus(Hasta has) {
		try {
			int i = entityManager.createQuery(
					"delete from Randevu k where k.hasta.id="
							+ has.getDosyaNo()).executeUpdate();
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

	public Randevu merge(Randevu ran) {
		return entityManager.merge(ran);

	}
}
