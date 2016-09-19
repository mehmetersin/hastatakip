package com.hastatakip.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Sube;

@Stateless
public class HareketDao {

	@PersistenceContext
	EntityManager entityManager;

	public int deleteSubeHarekets(Sube sub) {
		return entityManager
				.createQuery("delete from Hareket k where sube=:sb")
				.setParameter("sb", sub).executeUpdate();

	}

}
