package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Sube;

@Stateless
public class SubeDao {

	@PersistenceContext
	EntityManager entityManager;


	@SuppressWarnings("unchecked")
	public Sube getSube(int id) {
		return (Sube) entityManager.createQuery(
				"select s from Sube s where s.id=" + id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List getAllSubes() {
		return entityManager.createQuery("select s from Sube s ORDER BY s.subeAdi ")
				.getResultList();
	}

	public void insert(Sube sube) {
		entityManager.merge(sube);

	}

	public boolean delete(Sube sub) {
		try {
			int i = entityManager.createQuery(
					"delete from Sube k where k.id=" + sub.getId())
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

	public Sube merge(Sube sub) {
		return entityManager.merge(sub);
		
	}
}
