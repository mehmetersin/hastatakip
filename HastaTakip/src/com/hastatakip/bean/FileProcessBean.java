package com.hastatakip.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.hastatakip.Util;
import com.hastatakip.dao.EventLogDao;
import com.hastatakip.dao.KullaniciDao;
import com.hastatakip.dao.SubeDao;
import com.hastatakip.model.entity.EventLog;
import com.hastatakip.model.entity.FiyatGorHareket;
import com.hastatakip.model.entity.Hareket;
import com.hastatakip.model.entity.Kullanici;
import com.hastatakip.model.entity.Sube;
import com.hastatakip.model.type.BaglantiTipi;
import com.hastatakip.model.type.Durum;
import com.hastatakip.model.type.EventType;
import com.hastatakip.model.type.HareketTipi;

@Stateless
public class FileProcessBean {

	private static Logger logger = Logger.getLogger(FileProcessBean.class);

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	EventLogDao jobLogDao;

	@EJB
	SubeDao subeDao;

	@EJB
	KullaniciDao userDao;

	public void processFile(Sube sube, Date islemtarihi, Kullanici user)
			throws Exception {

		File file = null;

		try {
			String directoryName = Util.getProp("waitingFilesPath");

			String prefix = "";
			if (sube.getBaglantiTipi() == BaglantiTipi.FTP) {
				prefix = Util.ftpFilePrefix;
			} else if (sube.getBaglantiTipi() == BaglantiTipi.WINDOWSSHARE) {
				prefix = Util.windowsFilePrefix;
			}

			if (sube.getBaglantiTipi() == BaglantiTipi.WINDOWSSHARE) {
				String fileDate = Util
						.getWindowsShareDownloadFileFormat(islemtarihi);
				String fileName = "FisIptal" + fileDate + ".txt";
				String fileName2 = "SatirIptal" + fileDate + ".txt";
				try {

					String fullFileName = directoryName
							+ Util.getProp("directorySeperator") + prefix
							+ sube.getId() + Util.waitingFilePrefix + fileName;

					file = new File(fullFileName);

					processWindowsFile(fileName, file, user);
					copyFile(file, new File(Util.getProp("successFilePath")
							+ file.getName() + "_" + new Date().getTime()));

					fileName = "SatirIptal" + fileDate + ".txt";

					fullFileName = directoryName
							+ Util.getProp("directorySeperator") + prefix
							+ sube.getId() + Util.waitingFilePrefix + fileName;

					file = new File(fullFileName);

					processWindowsFile(fileName, file, user);
					copyFile(file, new File(Util.getProp("successFilePath")
							+ file.getName() + "_" + new Date().getTime()));

				} catch (Exception e) {
					copyFile(file, new File(Util.getProp("errorFilesPath")
							+ fileName + new Date().getTime()));
					copyFile(file, new File(Util.getProp("errorFilesPath")
							+ fileName2 + new Date().getTime()));
				}

			} else if (sube.getBaglantiTipi() == BaglantiTipi.FTP) {
				String fileName = Util.getFtpDownloadFileFormat(islemtarihi);
				try {
					String fullFileName = directoryName
							+ Util.getProp("directorySeperator") + prefix
							+ sube.getId() + Util.waitingFilePrefix + fileName;

					file = new File(fullFileName);

					processFtpFile(fileName, file, user);
					copyFile(file, new File(Util.getProp("successFilePath")
							+ file.getName() + "_" + new Date().getTime()));
				} catch (Exception e) {
					copyFile(file, new File(Util.getProp("errorFilesPath")
							+ fileName + new Date().getTime()));
				}

			}

		} catch (Exception e) {
			logger.error("Error while processing,", e);
			throw e;
		}

	}

	public String processFiles(Kullanici user) {

		// TODO kullanıci tablosunda sifreli şekilde tutulmıyor sifreler

		int countTotal = 0;
		int countSucces = 0;
		int countFail = 0;
		try {
			String directoryName = Util.getProp("waitingFilesPath");

			File folder = new File(directoryName);

			 countTotal = folder.listFiles().length;
			
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					logger.info("This is directory skip ,"
							+ fileEntry.getAbsolutePath());
				} else {
					if (fileEntry.isFile()) {
						String fileName = fileEntry.getName();

						if (fileName.startsWith("W")) {

							try {
								processWindowsFile(fileName, fileEntry, user);
								countSucces++;
								copyFile(
										fileEntry,
										new File(Util
												.getProp("successFilePath")
												+ fileEntry.getName()
												+ "_"
												+ new Date().getTime()));
							} catch (Exception e) {
								countFail++;
								copyFile(
										fileEntry,
										new File(Util.getProp("errorFilesPath")
												+ fileName
												+ new Date().getTime()));
							}

						} else if (fileName.startsWith("F")) {

							try {
								processFtpFile(fileName, fileEntry, user);
								countSucces++;
								copyFile(
										fileEntry,
										new File(Util
												.getProp("successFilePath")
												+ fileEntry.getName()
												+ "_"
												+ new Date().getTime()));
							} catch (Exception e) {
								countFail++;
								copyFile(
										fileEntry,
										new File(Util.getProp("errorFilesPath")
												+ fileName
												+ new Date().getTime()));
							}

						} else {
							logger.error("File is in valid format file,"
									+ fileName);
							fileEntry.delete();

						}

					}

				}
			}
		} catch (Exception e) {
			logger.error("Error while processing,", e);
		}
		
		 	String summary = "Summary Process File Result:Total" + countTotal
				+ "-Succes:" + countSucces + "-Fail:" + countFail;
		 	
		 	return summary;
				

	}

	@SuppressWarnings("unchecked")
	public void processFtpFile(String fileName, File file, Kullanici user)
			throws Exception { // F_50147802-EX141202.TXT

		logger.debug("Start to process ftp file:" + fileName);

		EventLog event = new EventLog();
		event.setBaslangicTarih(new Date());
		event.setEventType(EventType.DosyaIsleme);
		event.setDurum(Durum.BASARILI);
		event.setFileName(fileName);
		event.setKullanici(user);
		BufferedReader br = null;

		try {

			int subeId = Integer.parseInt(file.getName().substring(2,
					file.getName().indexOf(Util.waitingFilePrefix)));

			Sube sube = subeDao.getSube(subeId);

			event.setSube(sube);

			br = new BufferedReader(new FileReader(file.getPath()));

			int successCounter = 0;
			int failedCounter = 0;

			boolean isFirst = false;

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {

				try {
					int i = 0;
					String[] st = sCurrentLine.split("\\|");
					String tarihSaat = st[++i];
					String kasaNo = st[++i];
					String transactionNo = st[++i];
					String kasiyerNo = st[++i];
					String exType = st[++i];

					String barkod = "";
					String urunAdet = "0";
					String tutar = "0";

					Hareket hareket = new Hareket();

					switch (Integer.valueOf(exType).intValue()) {
					case 1:
						hareket.setHareketTipi(HareketTipi.FISIPTAL);
						tutar = st[++i];
						break;
					case 2:
						hareket.setHareketTipi(HareketTipi.SATIRIPTAL);
						barkod = st[++i];
						urunAdet = st[++i];
						tutar = st[++i];

						break;
					case 3:
						hareket.setHareketTipi(HareketTipi.SATIRDUZELTME);
						barkod = st[++i];
						urunAdet = st[++i];
						tutar = st[++i];

						break;
					case 4:
						hareket.setHareketTipi(HareketTipi.KASIYEROTURUMACMA);
						break;
					case 5:
						hareket.setHareketTipi(HareketTipi.KASIYEROTURUMKAPAMA);
						break;
					case 6:
						hareket.setHareketTipi(HareketTipi.KASIYERACKAPA);
						break;
					case 7:
						hareket.setHareketTipi(HareketTipi.FIYATGOR);
						barkod = st[++i];
						break;
					}
					hareket.setKasaNo(kasaNo);
					hareket.setBarkod(barkod);
					hareket.setIslemNo(transactionNo);
					hareket.setKasiyerNo(kasiyerNo);
					hareket.setAdet(Integer.valueOf(urunAdet));
					hareket.setTutar(new BigDecimal(tutar)
							.divide(new BigDecimal(100)));

					Calendar c1 = GregorianCalendar.getInstance();
					int year = Integer.valueOf(tarihSaat.substring(0, 4));
					int month = Integer.valueOf(tarihSaat.substring(4, 6));
					int date = Integer.valueOf(tarihSaat.substring(6, 8));
					int hourOfDay = Integer.valueOf(tarihSaat.substring(8, 10));
					int minute = Integer.valueOf(tarihSaat.substring(10, 12));
					int second = 0;

					c1.set(year, month, date, hourOfDay, minute, second);

					c1.set(year, month - 1, date, hourOfDay, minute, second);
					hareket.setTarih(c1.getTime());
					hareket.setSube(sube);

					if (!isFirst) {
						isFirst = true;

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");

						Query query = entityManager
								.createQuery(
										"delete from Hareket where to_char(tarih,'YYYY-MM-DD')=:t and sube=:sube   ")
								.setParameter("t",
										formatter.format(c1.getTime()))
								.setParameter("sube", sube);
						int recordCount = query.executeUpdate();

						logger.debug("Same file for hareket table," + sube.toString()
								+ " processing deleted record count:"
								+ recordCount);
						
						query = entityManager
								.createQuery(
										"delete from FiyatGorHareket where to_char(tarih,'YYYY-MM-DD')=:t and sube=:sube   ")
								.setParameter("t",
										formatter.format(c1.getTime()))
								.setParameter("sube", sube);
						recordCount = query.executeUpdate();
						
						logger.debug("Same file for fiyatgorhareket table," + sube.toString()
								+ " processing deleted record count:"
								+ recordCount);
						
						
					}

					if (hareket.getHareketTipi()==HareketTipi.FIYATGOR){
						FiyatGorHareket fiyathareket = new FiyatGorHareket(hareket);
						entityManager.persist(fiyathareket);
					}else{
						entityManager.persist(hareket);
					}
					
					successCounter = successCounter + 1;
				} catch (Exception e) {
					logger.error("Error while processing line skip this line,"
							+ sCurrentLine, e);
					failedCounter = failedCounter + 1;
				}

			}

			logger.info("File for " + fileName + ",SuccessCount:"
					+ successCounter + ",FailedCount:" + failedCounter);

			// file.renameTo(new File(Util.getProp("successFilePath") + fileName
			// + new Date().getTime()));
		} catch (Exception e) {

			// TODO hata durumunda kayıtların hepsi rollback edilmesi lazim
			// file.renameTo(new File(Util.getProp("errorFilesPath") + fileName
			// + new Date().getTime()));

			logger.error("Error while processing file", e);

			event.setDurum(Durum.BASARISIZ);

			throw e;
		} finally {
			event.setBitisTarih(new Date());
			jobLogDao.log(event);
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void copyFile(File sourceFile, File destFile)
			throws Exception {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();

			// previous code: destination.transferFrom(source, 0,
			// source.size());
			// to avoid infinite loops, should be:
			long count = 0;
			long size = source.size();
			while ((count += destination.transferFrom(source, count, size
					- count)) < size)
				;
		} catch (Exception e) {
			logger.error("Copy file error", e);
		}

		finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
			try {
				boolean res = sourceFile.delete();
				logger.debug("Delete file result," + res);
			} catch (Exception e2) {
				logger.error("Delete file error", e2);
			}

		}
	}

	public void processWindowsFile(String fileName, File file, Kullanici user)
			throws Exception {
		// F_50147802-EX141202.TXT
		// W_443243-FisIptal01-03-2015.txt

		logger.debug("Start to process windows file:" + fileName);

		EventLog event = new EventLog();
		event.setBaslangicTarih(new Date());
		event.setEventType(EventType.DosyaIsleme);
		event.setDurum(Durum.BASARILI);
		event.setFileName(fileName);
		event.setKullanici(user);
		BufferedReader br = null;

		try {

			int subeId = Integer.parseInt(file.getName().substring(2,
					file.getName().indexOf(Util.waitingFilePrefix)));

			Sube sube = subeDao.getSube(subeId);

			event.setSube(sube);

			br = new BufferedReader(new FileReader(file.getPath()));

			boolean isFileSatirIptalFile = false;
			if (file.getName().contains("Satir")) {
				isFileSatirIptalFile = true;
			}

			int successCounter = 0;
			int failedCounter = 0;

			boolean isFirst = false;

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {

				try {
					int i = -1;
					String[] st = sCurrentLine.split("\\;");
					HareketTipi hareketTipi = null;
					String kasiyerNo = "";
					String kasaNo = "";
					String tarih = ""; // 15/03/01
					String saat = ""; // 15:53:54
					String tutar = "";
					String barkod = "";
					String transactionNo = "";

					if (isFileSatirIptalFile) {
						hareketTipi = HareketTipi.SATIRIPTAL;
						kasiyerNo = st[++i];
						kasaNo = st[++i];
						transactionNo = st[++i];
						tarih = st[++i];
						saat = st[++i];
						barkod = st[++i];
						tutar = st[++i];
					} else {
						hareketTipi = HareketTipi.FISIPTAL;
						kasiyerNo = st[++i];
						kasaNo = st[++i];
						transactionNo = st[++i];
						tarih = st[++i];
						saat = st[++i];

						tutar = st[++i];

					}

					Hareket hareket = new Hareket();

					hareket.setHareketTipi(hareketTipi);
					hareket.setKasaNo(kasaNo);
					hareket.setBarkod(barkod);
					hareket.setIslemNo(transactionNo);
					hareket.setKasiyerNo(kasiyerNo);
					hareket.setTutar(new BigDecimal(tutar)
							.divide(new BigDecimal(100)));

					Calendar c1 = GregorianCalendar.getInstance();
					int year = Integer.valueOf("20" + tarih.substring(0, 2));
					int month = Integer.valueOf(tarih.substring(3, 5));
					int date = Integer.valueOf(tarih.substring(6, 8));
					int hourOfDay = Integer.valueOf(saat.substring(0, 2));
					int minute = Integer.valueOf(saat.substring(3, 5));
					int second = Integer.valueOf(saat.substring(6, 8));

					c1.set(year, month, date, hourOfDay, minute, second);

					c1.set(year, month - 1, date, hourOfDay, minute, second);
					hareket.setTarih(c1.getTime());
					hareket.setSube(sube);

					if (!isFirst) {
						isFirst = true;

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");

						Query query = entityManager
								.createQuery(
										"delete from Hareket where to_char(tarih,'YYYY-MM-DD')=:t and sube=:sube and hareketTipi=:ht  ")
								.setParameter("t",
										formatter.format(c1.getTime()))
								.setParameter("sube", sube)
								.setParameter("ht", hareketTipi);
						int recordCount = query.executeUpdate();

						if (recordCount > 0) {
							logger.debug("Same data found,hareket"
									+ hareketTipi + sube.toString()
									+ " processing deleted record count:"
									+ recordCount);
						} else {
							logger.debug("Same file for," + sube.toString()
									+ "is not found");
						}
					}

					entityManager.persist(hareket);
					successCounter = successCounter + 1;
				} catch (Exception e) {
					logger.error("Error while processing line skip this line,"
							+ sCurrentLine);
					failedCounter = failedCounter + 1;
				}

			}

			logger.info("File for " + fileName + ",SuccessCount:"
					+ successCounter + ",FailedCount:" + failedCounter);

		} catch (Exception e) {

			logger.error("Error while processing file", e);

			event.setDurum(Durum.BASARISIZ);

			throw e;
		} finally {
			event.setBitisTarih(new Date());
			jobLogDao.log(event);
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
