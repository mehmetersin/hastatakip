package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.model.entity.Hasta;
import com.hastatakip.model.entity.MuhasebeBilgileri;

@Stateless
public class MuhasebeBilgileriDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<MuhasebeBilgileri> getAll() {
		return entityManager.createQuery("select b from MuhasebeBilgileri b")
				.getResultList();
	}
	
	
	public MuhasebeBilgileri getMuhasebeBilgileri(int id) {
		return (MuhasebeBilgileri) entityManager.createQuery(
				"select s from MuhasebeBilgileri s where s.id=" + id).getSingleResult();
	}
	
	public void insert(MuhasebeBilgileri mhsb) {
		entityManager.merge(mhsb);

	}
	
	public MuhasebeBilgileri merge(MuhasebeBilgileri mhsb) {
		return entityManager.merge(mhsb);
		
	}
	
	public boolean delete(MuhasebeBilgileri mhsb) {
		try {
			int i = entityManager.createQuery(
					"delete from MuhasebeBilgileri k where k.id=" + mhsb.getId())
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
