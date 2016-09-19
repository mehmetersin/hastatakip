package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Sehir;

@Stateless
public class SehirDao {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Sehir> getAll() {
		return entityManager.createQuery("select b from Sehir b")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Sehir getSehir(int id) {
		return (Sehir) entityManager.createQuery(
				"select b from Sehir b where b.id=" + id).getSingleResult();
	}

	public void insert(Sehir sube) {
		entityManager.persist(sube);

	}

}
