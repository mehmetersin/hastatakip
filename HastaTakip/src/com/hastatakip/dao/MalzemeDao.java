package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.FirmaBilgileri;
import com.hastatakip.model.entity.MalzemeGirisBilgileri;

@Stateless
public class MalzemeDao {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public MalzemeGirisBilgileri getMalzemeGirisBilgi(int id) {
		return (MalzemeGirisBilgileri) entityManager.createQuery(
				"select m from MalzemeGirisBilgileri m where m.id=" + id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List getAllMalzemeBilgisi() {
		return entityManager.createQuery("select m from MalzemeGirisBilgileri m ORDER BY m.id ")
				.getResultList();
	}
	
	public void insert(MalzemeGirisBilgileri mgb) {
		entityManager.merge(mgb);

	}
	
	public MalzemeGirisBilgileri merge(MalzemeGirisBilgileri mgb) {
		return entityManager.merge(mgb);
		
	}
	
	public boolean delete(MalzemeGirisBilgileri mgb) {
		try {
			int i = entityManager.createQuery(
					"delete from MalzemeGirisBilgileri mgb where mgb.id=" + mgb.getId())
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
}
