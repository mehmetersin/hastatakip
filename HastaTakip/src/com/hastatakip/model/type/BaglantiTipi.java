package com.hastatakip.model.type;

public enum BaglantiTipi {
	FTP("FTP"), WINDOWSSHARE("WINDOWSSHARE");

	private String label;

	private BaglantiTipi(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
