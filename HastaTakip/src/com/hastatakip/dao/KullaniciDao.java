package com.hastatakip.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hastatakip.Util;
import com.hastatakip.model.entity.Kullanici;

@Stateless
public class KullaniciDao {

	@PersistenceContext
	EntityManager entityManager;

	public Kullanici getSystemKullanici() {
		return (Kullanici) entityManager
				.createQuery(
						"select k from Kullanici k where k.id="
								+ Util.systemKullanciId).getSingleResult();
	}

	public void insert(Kullanici user) {
		entityManager.persist(user);

	}

	public Kullanici merge(Kullanici user) {
		return entityManager.merge(user);

	}

	public Kullanici loginUser() {

		String loginEmail = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		Kullanici kul = new Kullanici();
		kul.setEmail(loginEmail);
		return get(kul);

	}

	public boolean delete(Kullanici user) {

		try {
			int i = entityManager.createQuery(
					"delete from Kullanici k where k.id=" + user.getId())
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

	public Kullanici find(Kullanici user) {
		return entityManager.find(Kullanici.class, user);

	}

	public Kullanici get(Kullanici user) {

		try {
			return (Kullanici) entityManager.createQuery(
					"select k from Kullanici k where k.email='"
							+ user.getEmail() + "'").getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	public List<Kullanici> getAllUser() {

		try {
			return entityManager.createQuery("select k from Kullanici k ")
					.getResultList();
		} catch (Exception e) {
			return null;
		}

	}
}
