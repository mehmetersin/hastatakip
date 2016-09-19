package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Sehir;

@Stateless
public class BolgeDao {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Sehir> getAll() {
		return entityManager.createQuery("select b from Bolge b")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Sehir getBolge(int id) {
		return (Sehir) entityManager.createQuery(
				"select b from Bolge b where b.id=" + id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List getAllBolges() {
		return entityManager.createQuery("select b from Bolge b ")
				.getResultList();
	}

	public void insert(Sehir sube) {
		entityManager.persist(sube);

	}

	public boolean delete(Sehir sub) {
		try {
			int i = entityManager.createQuery(
					"delete from Bolge k where k.id=" + sub.getId())
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

	public Sehir merge(Sehir sub) {
		return entityManager.merge(sub);
		
	}
}
