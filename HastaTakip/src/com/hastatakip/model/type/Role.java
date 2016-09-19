package com.hastatakip.model.type;

public enum Role {
	user("user"), admin("admin");

	private String label;

	private Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
