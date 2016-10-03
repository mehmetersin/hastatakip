package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.FirmaBilgileri;

@Stateless
public class FirmaDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public FirmaBilgileri getFirma(int id) {
		return (FirmaBilgileri) entityManager.createQuery(
				"select f from FirmaBilgileri f where f.id=" + id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List getAllFirma() {
		return entityManager.createQuery("select f from FirmaBilgileri f ORDER BY f.id ")
				.getResultList();
	}
	
	public void insert(FirmaBilgileri fb) {
		entityManager.merge(fb);

	}
	
	public FirmaBilgileri merge(FirmaBilgileri fb) {
		return entityManager.merge(fb);
		
	}
	
	public boolean delete(FirmaBilgileri fb) {
		try {
			int i = entityManager.createQuery(
					"delete from FirmaBilgileri fb where fb.id=" + fb.getId())
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
