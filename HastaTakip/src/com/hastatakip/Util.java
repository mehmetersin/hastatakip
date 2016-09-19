package com.hastatakip;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.hastatakip.bean.ProcessResult;

public class Util {

	private static Properties prop = null;

	public final static String ftpFilePrefix = "F_";
	public final static String windowsFilePrefix = "W_";

	public final static String waitingFilePrefix = "-";

	public final static int systemKullanciId = 1;

	public static String jobScheduleHour = ""; //

	final Logger logger = Logger.getLogger(Util.class);

	static {
		jobScheduleHour = Util.getProp("jobschedule.hour");
	}

	public final static String getFtpDownloadFileFormat(Date islemTarihi) {

		String fileFormat = Util.getProp("fileFormat");
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		String date = format.format(islemTarihi);

		String fileName = fileFormat.replaceAll("tarih", date);
		return fileName;
	}

	public final static String getWindowsShareDownloadFileFormat(
			Date islemTarihi) {

		String fileFormat = Util.getProp("windowsFileFormat");
		SimpleDateFormat format = new SimpleDateFormat(fileFormat);
		String date = format.format(islemTarihi);

		return date;
	}

	public static String getProp(String key) {

		if (prop == null) {
			try {
				ClassLoader loader = Thread.currentThread()
						.getContextClassLoader();
				InputStream stream = loader
						.getResourceAsStream("application.properties");
				prop = new Properties();

				prop.load(stream);

			} catch (Exception e) {
				e.printStackTrace();
				prop = null;
				return "";
			}
		}
		return prop.getProperty(key);
	}


	public void sendNoticeMail(ProcessResult r,int type) {

		try {

			MailSender mailSender = new MailSender();

			logger.debug("start to send email");

			String messageText = "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\"> <tr> <td align=\"center\" valign=\"top\"> <table border=\"1\" cellpadding=\"20\" cellspacing=\"0\" width=\"600\" id=\"emailContainer\"> "
					+ "<tr> <td align=\"center\" valign=\"top\"> Dosya Toplama Ozet Raporu </td> </tr>"
					+ "<tr> <td align=\"center\" valign=\"top\"> Dosya Toplama Günü :"
					+ r.getIslemTarihiStr()
					+ " </td> </tr>"
					+ "<tr> <td align=\"center\" valign=\"top\"> Toplam Toplanan Dosya : "
					+ r.getTotalCount()
					+ " </td> </tr> "
					+ "<tr> <td align=\"center\" valign=\"top\"> Başarılı Toplanan Dosya : "
					+ r.getSuccessCount()
					+ " </td> </tr>"
					+ "<tr> <td align=\"center\" valign=\"top\"> Başarısız Toplanan Dosya : "
					+ r.getErrorCount()
					+ " </td> </tr>"
					+ "</table> </td> </tr> </table>";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			String date = sdf.format(r.getIslemTarihi());

			if (type ==1){
				mailSender.mailSend("Fraud Gecelik Otomatik Rapor -" + date, messageText);
			}else{
				mailSender.mailSend("Fraud Manuel Dosya Toplama Rapor -" + date, messageText);
			}
			
			
		} catch (Exception e) {
			logger.error("Error while sending email", e);
		}
	}
}