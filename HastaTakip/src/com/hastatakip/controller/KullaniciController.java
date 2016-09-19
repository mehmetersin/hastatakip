package com.hastatakip.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.hastatakip.dao.KullaniciDao;
import com.hastatakip.model.entity.Kullanici;
import com.hastatakip.model.type.Role;

@ManagedBean(name = "userController")
public class KullaniciController implements Serializable {

	private Kullanici user;

	private List<Kullanici> allUsers;

	@EJB
	KullaniciDao userDao;

	public List<Kullanici> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<Kullanici> allUsers) {
		this.allUsers = allUsers;
	}

	@PostConstruct
	public void init() {
		try {

			user = new Kullanici();
			allUsers = userDao.getAllUser();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Role[] getRoleList() {
		return Role.values();

	}

	public Kullanici getUser() {
		return user;
	}

	public void setUser(Kullanici user) {
		this.user = user;
	}

	public String goToPage() {
		return "both/" + "kullaniciIslem" + "?faces-redirect=true";
	}

	public String goToList() {
		return "/mainPage?faces-redirect=true";
	}

	public void createUser() {

		FacesContext context = FacesContext.getCurrentInstance();
		try {

			Kullanici kul = userDao.get(user);

			if (kul != null) {
				context.addMessage(null, new FacesMessage("Hata",
						"Belirtilen email kullanımaktadır."));
				return;
			}

			userDao.insert(user);

			context.addMessage(null, new FacesMessage("Uyarı",
					"İşleminiz başarı ile tamamlandı."));

			user = new Kullanici();
			init();

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Hata",
					"İşleminiz Yapılamadı."));
			return;
		}

	}

	public void adminControl(ComponentSystemEvent event) {
		if (isNormalUser()) {

			FacesContext fc = FacesContext.getCurrentInstance();
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc
					.getApplication().getNavigationHandler();

			nav.performNavigation("/mainPage?faces-redirect=true");
		}
	}

	public boolean isNormalUser() {

		String loginEmail = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal().getName();
		Kullanici kul = new Kullanici();
		kul.setEmail(loginEmail);
		Kullanici login = userDao.get(kul);
		if (login.getRole() == Role.admin) {
			return false;
		} else {
			return true;
		}

	}
	
	

	public void onRowCancel(RowEditEvent event) {
		Kullanici kul = ((Kullanici) event.getObject());

		boolean result = userDao.delete(kul);
		String message = "";
		if (result) {
			message = "Başarılı";
		} else {
			message = "Başarırız";
		}

		FacesMessage msg = new FacesMessage("Kullanıcı Silindi", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		init();
	}

	public void onRowEdit(RowEditEvent event) {

		Kullanici kul = ((Kullanici) event.getObject());

		userDao.merge(kul);

		FacesMessage msg = new FacesMessage("Kullanıcı Bilgileri Güncellendi",
				"Başarılı");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		init();
	}

}