package com.hastatakip.model.type;

public enum RandevuSaati {
	FTP("FTP"), WINDOWSSHARE("WINDOWSSHARE");

	private String label;

	private RandevuSaati(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
