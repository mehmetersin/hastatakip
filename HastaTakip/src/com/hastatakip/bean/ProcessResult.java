package com.hastatakip.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessResult {

	private int totalCount;
	private int successCount;
	private int errorCount;
	private Date islemTarihi;


	public Date getIslemTarihi() {
		return islemTarihi;
	}

	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public String getIslemTarihiStr() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String date = sdf.format(getIslemTarihi());

		return date;
	}

	@Override
	public String toString() {

		String message = "Ozet Dosya Toplama Sonucu: " + "IslemTarihi: "
				+ getIslemTarihiStr() + " Toplam:" + getTotalCount()
				+ " Basarili:" + getSuccessCount() + " Basarisiz:"
				+ getErrorCount();

		return message;

	}

}
