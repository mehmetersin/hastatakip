package com.hastatakip.model.type;

public enum Cinsiyet {
 E("E"),
 K("K");

private String label;

private Cinsiyet(String label) {
	this.label = label;
}

public String getLabel() {
	return label;
}
}