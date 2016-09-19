package com.hastatakip.fm;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.hastatakip.Util;
import com.hastatakip.model.entity.Sube;

public class FtpFileManager implements FileManagerIntf {

	private final Logger logger = Logger.getLogger(FtpFileManager.class);

	private boolean downloadSingleFile(FTPClient ftpClient,
			String remoteFilePath, String savePath) throws Exception {
		File downloadFile = new File(savePath);

		File parentDir = downloadFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		OutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean result = ftpClient.retrieveFile(remoteFilePath,
					outputStream);
			outputStream.flush();
			return result;
		} catch (Exception ex) {
			try {
				downloadFile.delete();
			} catch (Exception e) {
			}
			
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static void main(String[] args) {
		String a = "EXtarih.TXT";

		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		String date = format.format(new Date());

		String fileName = a.replaceAll("tarih", date);

		System.out.println(fileName);
	}

	public void downloadDirectory(Sube sube, FTPClient ftpClient,
			String sourceDirectory, String saveDir, String fileName)
			throws Exception {

		try {

			logger.info("Download file start" + sube.toString() + ","
					+ sourceDirectory + "," + saveDir);

			String newDirPath = saveDir + File.separator + Util.ftpFilePrefix
					+ sube.getId() + Util.waitingFilePrefix + fileName;

			String sourceFile = sourceDirectory + fileName;

			// download the file
			boolean success = downloadSingleFile(ftpClient, sourceFile,
					newDirPath);
			if (success) {
				logger.info("Downloaded the file: " + sourceFile);
			} else {
				logger.info("Could not downloaded the ftp file: " + sourceFile);
				throw new Exception("Could not downloaded ftp the file: "
						+ sourceFile);
			}

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void copyFiles(Sube sube, String fileName) throws Exception {

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.setDefaultTimeout(10000);
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setConnectTimeout(5000);
			ftpClient.connect(sube.getUrl(), sube.getFtpPort());
			ftpClient.setSoTimeout(10000);
			
			int reply = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.debug(sube.toString() + "FTP server refused connection.");
				return;
			}

			ftpClient.login(sube.getUserName(), sube.getPass());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlKeepAliveTimeout(300);
			ftpClient.configure(new FTPClientConfig(FTPClientConfig.SYST_NT));

			downloadDirectory(sube, ftpClient, sube.getFtpDirectoryPath(),
					Util.getProp("waitingFilesPath"), fileName);

		} catch (Exception ex) {
			logger.error("Error while copy file," + sube.toString(), ex);
			throw ex;
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (Exception ex) {
				logger.error("Error in while copy file", ex);
			}
		}

	}
}