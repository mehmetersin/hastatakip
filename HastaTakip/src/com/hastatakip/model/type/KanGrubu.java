package com.hastatakip.model.type;

public enum KanGrubu {
	O1("0-"),O2("0+"),A1("A-"),A2("A+"),B1("B-"),B2("B+")
	,AB1("AB-"),AB2("AB+");

	
	private String label;

	private KanGrubu(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	}

