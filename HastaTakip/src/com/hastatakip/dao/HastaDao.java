package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Hasta;

@Stateless
public class HastaDao {

	@PersistenceContext
	EntityManager entityManager;


	@SuppressWarnings("unchecked")
	public Hasta getHasta(int id) {
		return (Hasta) entityManager.createQuery(
				"select s from Hasta s where s.dosyaNo=" + id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List getAllHasta() {
		return entityManager.createQuery("select s from Hasta s ORDER BY s.dosyaNo ")
				.getResultList();
	}

	public void insert(Hasta h) {
		entityManager.merge(h);

	}

	public boolean delete(Hasta sub) {
		try {
			int i = entityManager.createQuery(
					"delete from Hasta k where k.dosyaNo=" + sub.getDosyaNo())
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

	public Hasta merge(Hasta sub) {
		return entityManager.merge(sub);
		
	}
}
