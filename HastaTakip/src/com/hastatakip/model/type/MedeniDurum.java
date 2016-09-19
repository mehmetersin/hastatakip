package com.hastatakip.model.type;

public enum MedeniDurum {
	 B("B"),
	 E("E"),;

	private String label;

	private MedeniDurum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	}